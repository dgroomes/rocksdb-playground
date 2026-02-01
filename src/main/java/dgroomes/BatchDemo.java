package dgroomes;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;
import org.rocksdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;

/**
 * Bulk writing and reading data into RocksDB using WriteBatch.
 */
public class BatchDemo {

    private static final Logger log = LoggerFactory.getLogger(BatchDemo.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance(Locale.US);

    public static void main(String[] args) throws RocksDBException, IOException {
        RocksDB.loadLibrary();

        Path tempDir = Files.createTempDirectory("rocksdb-playground-batch-demo");
        log.info("Created temporary directory for RocksDB: {}", tempDir);

        try (Options options = new Options().setCreateIfMissing(true);
             RocksDB db = RocksDB.open(options, tempDir.toString())) {

            loadZipCodes(db);
            demonstrateRangeQuery(db);

        } finally {
            log.info("Cleaning up temporary directory: {}", tempDir);
            deleteRecursively(tempDir);
        }
    }

    private static void loadZipCodes(RocksDB db) throws IOException, RocksDBException {
        log.info("Loading ZIP Codes...");

        Path zipFile = Paths.get("zips.jsonl");
        if (!Files.exists(zipFile)) {
            log.error("ZIP code data file not found: zips.jsonl");
            return;
        }

        var start = Instant.now();
        int count = 0;
        int batchSize = 1000;

        try (BufferedReader reader = Files.newBufferedReader(zipFile);
             var batch = new WriteBatch();
             var writeOptions = new WriteOptions()) {

            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    JsonNode node = mapper.readTree(line);

                    String zipCode = node.get("_id").asString();
                    String state = node.get("state").asString();

                    // Key: state code (2 chars) + zip code (5 chars)
                    String key = state + zipCode;

                    // Create a new JSON object for the value (without state and zip)
                    ObjectNode valueNode = mapper.createObjectNode();
                    valueNode.put("city", node.get("city").asString());
                    valueNode.put("pop", node.get("pop").asInt());
                    valueNode.set("loc", node.get("loc"));

                    String value = mapper.writeValueAsString(valueNode);

                    batch.put(key.getBytes(), value.getBytes());
                    count++;

                    // Write batch every N records
                    if (count % batchSize == 0) {
                        db.write(writeOptions, batch);
                        batch.clear();

                        if (count % 5000 == 0) {
                            log.info("Loaded {} ZIP codes...", NUMBER_FORMAT.format(count));
                        }
                    }
                } catch (Exception e) {
                    log.warn("Failed to parse line: {}", line, e);
                }
            }

            // Write any remaining records in the batch
            if (batch.count() > 0) {
                db.write(writeOptions, batch);
            }
        }

        var duration = Duration.between(start, Instant.now());
        log.info("Loaded {} ZIP codes in {}", NUMBER_FORMAT.format(count), duration);
    }

    private static void demonstrateRangeQuery(RocksDB db) {
        log.info("");
        log.info("Read the first 5 ZIP codes in California...");

        try (RocksIterator iterator = db.newIterator()) {
            // Seek to the start of California ZIP codes
            iterator.seek("CA".getBytes());

            int count = 0;
            while (iterator.isValid() && count < 5) {
                var key = new String(iterator.key());

                // Stop if we've moved past California
                if (!key.startsWith("CA")) {
                    break;
                }

                var state = key.substring(0, 2);
                var zip = key.substring(2);

                JsonNode valueNode = mapper.readTree(iterator.value());
                log.info("  {} ({}) -> City: {}, Pop: {}",
                        zip, state,
                        valueNode.get("city").asString(),
                        NUMBER_FORMAT.format(valueNode.get("pop").asInt()));

                iterator.next();
                count++;
            }
        }
    }

    private static void deleteRecursively(Path path) throws IOException {
        if (!Files.exists(path)) return;
        Files.walkFileTree(path, new SimpleFileVisitor<>() {

            @SuppressWarnings("NullableProblems")
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
