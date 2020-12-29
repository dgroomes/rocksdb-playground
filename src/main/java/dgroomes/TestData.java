package dgroomes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class with methods to generate test data and insert it into RocksDB.
 */
public class TestData {

    private static final Logger log = LoggerFactory.getLogger(TestData.class);

    /**
     * Generate and write a range of data in the form:
     *   * Key: "key1", Value: "value1"
     *   * Key: "key2", Value: "value2"
     *   * etc.
     * @param repo the RocksDB database to write into
     * @param numberOfEntries the number of entries to generate
     */
    public static void writeTestData(RocksDbRepo repo, int numberOfEntries) {
        log.debug("Writing {} entries of test data.", numberOfEntries);
        for (int i = 1; i <= numberOfEntries; i++) {
            var key = "key" + i;
            var value = "value" + i;
            repo.write(key, value);
        }
        log.debug("Finished writes.");
    }
}
