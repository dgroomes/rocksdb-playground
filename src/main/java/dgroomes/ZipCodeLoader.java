package dgroomes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

/**
 * Loads ZIP code data from a JSONL file into RocksDB.
 */
public class ZipCodeLoader {

    private static final Logger log = LoggerFactory.getLogger(ZipCodeLoader.class);

    // This is so silly... just bring in Jackson.
    private static final Pattern JSON_PATTERN = Pattern.compile("\\{ \"_id\" : \"([^\"]+)\", \"city\" : \"([^\"]+)\", \"loc\" : \\[ ([^,]+), ([^\\]]+) \\], \"pop\" : (\\d+), \"state\" : \"([^\"]+)\" \\}");

    public static int loadZipCodes(RocksDbRepo repo, String filename) {
        Path path = Paths.get(filename);
        if (!Files.exists(path)) {
            log.error("ZIP code data file not found: {}", filename);
            return 0;
        }

        int count = 0;
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                var matcher = JSON_PATTERN.matcher(line);
                if (matcher.matches()) {
                    String zipCode = matcher.group(1);
                    String city = matcher.group(2);
                    String longitude = matcher.group(3);
                    String latitude = matcher.group(4);
                    String population = matcher.group(5);
                    String state = matcher.group(6);

                    String key = "zip:" + zipCode;
                    String value = String.format("{\"city\":\"%s\",\"state\":\"%s\",\"pop\":%s,\"loc\":[%s,%s]}",
                                                city, state, population, longitude, latitude);

                    repo.write(key, value);
                    count++;

                    if (count % 5000 == 0) {
                        log.debug("Loaded {} ZIP codes...", count);
                    }
                }
            }
        } catch (IOException e) {
            log.error("Failed to read ZIP code data file", e);
        }

        return count;
    }

    public static void demonstrateQueries(RocksDbRepo repo) {
        log.info("Sample ZIP code queries:");

        String[] sampleZips = {"10001", "90210", "60601", "02134", "94102"};
        for (String zip : sampleZips) {
            String key = "zip:" + zip;
            String value = repo.read(key);
            if (value != null) {
                log.info("  ZIP {} -> {}", zip, value);
            }
        }
    }
}
