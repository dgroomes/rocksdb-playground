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
 * This class defines a so-called "repository" abstraction over a RocksDB database. Think CRUD (Create, Read, Update,
 * Delete), although I haven't implemented all of those methods.
 *
 * I might do away with this abstraction at some point as I get into more sophisticated RocksDB access patterns. But for
 * now, the simple String-based "read" and "write" methods are a nice encapsulation over the RocksDB API.
 *
 * Note: This class will create a temporary directory to store the data and delete it upon program exit! This class is
 * only meant for "learning and exploring" and not actual production use.
 */
public class RocksDbRepo {

    private static final Logger log = LoggerFactory.getLogger(RocksDbRepo.class);

    private RocksDB db;

    private File dataDirectory;

    /**
     * Initialize the RocksDB database. This must be called before the other methods can be used.
     */
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

    /**
     * Shutdown the database. This should be called before exiting the program.
     */
    public void shutdown() {
        log.debug("Shutting down. (TODO) Deleting the data files and data directory {}", dataDirectory.getAbsolutePath());
    }

    /**
     * Read an entry by its key
     * @param key the key of the entry
     * @return the value of the entry, or null if not found
     */
    public String read(String key) {
        var keyBytes = key.getBytes();
        try {
            var value = db.get(keyBytes);
            return value != null ? new String(value) : null;
        } catch (RocksDBException e) {
            throw new IllegalStateException("Something went wrong when trying to 'get' an entry from the RocksDB database", e);
        }
    }

    /**
     * Write an entry
     * @param key the key of the entry
     * @param value the value of the entry
     */
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
