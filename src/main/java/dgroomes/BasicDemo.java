package dgroomes;

import org.rocksdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Basic RocksDB demo: write, read, and range query.
 */
public class BasicDemo {

    private static final Logger log = LoggerFactory.getLogger(BasicDemo.class);

    public static void main(String[] args) throws RocksDBException, IOException {
        RocksDB.loadLibrary();

        Path tempDir = Files.createTempDirectory("rocksdb-playground-basic-demo");
        log.info("Created temporary directory for RocksDB: {}", tempDir);

        var options = new Options().setCreateIfMissing(true);
        try (RocksDB db = RocksDB.open(options, tempDir.toString())) {
            demonstrateWriteAndRead(db);
            demonstrateRangeQuery(db);
        } finally {
            log.info("Cleaning up temporary directory: {}", tempDir);
            deleteRecursively(tempDir);
        }
    }

    private static void demonstrateWriteAndRead(RocksDB db) throws RocksDBException {
        log.info("Writing and reading key-value pairs...");

        db.put("greeting".getBytes(), "Hello, RocksDB!".getBytes());
        db.put("language".getBytes(), "Java".getBytes());
        db.put("java_version".getBytes(), System.getProperty("java.version").getBytes());

        // Read them back
        byte[] value = db.get("greeting".getBytes());
        log.info("greeting: {}", new String(value));

        value = db.get("language".getBytes());
        log.info("language: {}", new String(value));

        value = db.get("java_version".getBytes());
        log.info("java_version: {}", new String(value));
        log.info("");
    }


    private static void demonstrateRangeQuery(RocksDB db) throws RocksDBException {
        log.info("Reading a range of sequential entries...");

        for (int i = 1; i <= 10; i++) {
            String key = String.format("item_%02d", i);
            String value = "Value for item " + i;
            db.put(key.getBytes(), value.getBytes());
        }

        log.info("Wrote 10 sequential items (item_01 through item_10)");
        log.info("Reading a slice of them (items 3 - 6):");

        // Use an iterator to scan a range
        try (RocksIterator iterator = db.newIterator()) {
            // Seek to the start of our range
            iterator.seek("item_03".getBytes());

            int count = 0;
            while (iterator.isValid() && count < 4) {
                var key = new String(iterator.key());
                var value = new String(iterator.value());
                log.info("  {} = {}", key, value);
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
