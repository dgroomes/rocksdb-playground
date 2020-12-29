package dgroomes;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A RocksDB implementation for the Repo interface. It will create a temporary directory to store the data and delete it
 * upon program exit.
 */
public class RocksDbRepo implements Repo {

    private static final Logger log = LoggerFactory.getLogger(RocksDbRepo.class);

    private RocksDB db;

    private File dataDirectory;

    @Override
    public void init() {
        RocksDB.loadLibrary();

        Path tempDir;
        try {
            tempDir = Files.createTempDirectory("rocksdb-playground");
            log.debug("Created temporary directory to use as the RocksDB data directory: {}", tempDir);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create temporary directory for the RocksDB data.", e);
        }
        dataDirectory = tempDir.toFile();

        try (var options = new Options().setCreateIfMissing(true)) {
            db = RocksDB.open(options, dataDirectory.getAbsolutePath());
        } catch (RocksDBException e) {
            throw new IllegalStateException("Something went wrong when opening the RocksDB connection", e);
        }
    }

    @Override
    public void shutdown() {
        log.debug("Shutting down. (TODO) Deleting the data files and data directory {}", dataDirectory.getAbsolutePath());
    }

    @Override
    public String read(String key) {
        var keyBytes = key.getBytes();
        try {
            var value = db.get(keyBytes);
            return new String(value);
        } catch (RocksDBException e) {
            throw new IllegalStateException("Something went wrong when trying to 'get' an entry from the RocksDB database", e);
        }
    }

    @Override
    public void write(String key, String value) {
        var keyBytes = key.getBytes();
        var valueBytes = value.getBytes();
        try {
            db.put(keyBytes, valueBytes);
        } catch (RocksDBException e) {
            throw new IllegalStateException("Something went wrong when trying to 'put' an entry into the RocksDB database", e);
        }
    }
}
