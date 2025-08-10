package dgroomes;

import org.rocksdb.RocksIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.util.Locale;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance(Locale.US);

    public static void main(String[] args) {
        var repo = new RocksDbRepo();
        repo.init();

        try {
            demonstrateBasicOperations(repo);
            demonstrateRangeQueries(repo);
            demonstrateTestData(repo);
            demonstrateZipCodeData(repo);
        } finally {
            repo.shutdown();
        }
    }

    private static void demonstrateBasicOperations(RocksDbRepo repo) {
        log.info("Writing and reading basic key-value pairs...");

        repo.write("greeting", "Hello, RocksDB!");
        repo.write("language", "Java");
        repo.write("version", "10.2.1");

        log.info("Greeting: {}", repo.read("greeting"));
        log.info("Language: {}", repo.read("language"));
        log.info("Version: {}", repo.read("version"));
        log.info("");
    }

    private static void demonstrateRangeQueries(RocksDbRepo repo) {
        log.info("Demonstrating range queries with sequential keys...");

        for (int i = 1; i <= 10; i++) {
            String key = String.format("item_%02d", i);
            String value = "Value for item " + i;
            repo.write(key, value);
        }

        log.info("Wrote 10 sequential items (item_01 through item_10)");
        log.info("Reading items 3-5:");
        for (int i = 3; i <= 5; i++) {
            String key = String.format("item_%02d", i);
            log.info("  {} = {}", key, repo.read(key));
        }
        log.info("");
    }

    private static void demonstrateTestData(RocksDbRepo repo) {
        log.info("Generating and storing test data...");

        int numEntries = 100;
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numEntries; i++) {
            String key = TestData.generateKey(i);
            String value = TestData.generateValue(i);
            repo.write(key, value);
        }

        long duration = System.currentTimeMillis() - startTime;
        log.info("Wrote {} test entries in {} ms", NUMBER_FORMAT.format(numEntries), duration);

        log.info("Sample test data:");
        for (int i : new int[]{0, 25, 50, 75, 99}) {
            String key = TestData.generateKey(i);
            log.info("  {} = {}", key, repo.read(key));
        }
        log.info("");
    }

    private static void demonstrateZipCodeData(RocksDbRepo repo) {
        log.info("Loading ZIP code data from zips.jsonl...");

        long startTime = System.currentTimeMillis();
        int count = ZipCodeLoader.loadZipCodes(repo, "zips.jsonl");
        long duration = System.currentTimeMillis() - startTime;

        log.info("Loaded {} ZIP codes in {} ms", NUMBER_FORMAT.format(count), NUMBER_FORMAT.format(duration));
        log.info("");

        ZipCodeLoader.demonstrateQueries(repo);
        log.info("");
    }
}
