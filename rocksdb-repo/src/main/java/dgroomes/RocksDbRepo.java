package dgroomes;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

public class RocksDbRepo implements Repo {

    private RocksDB db;

    @Override
    public void init() {

        RocksDB.loadLibrary();

        try (var options = new Options().setCreateIfMissing(true)) {
            options.close();

            db = RocksDB.open(options, "path/to/db");
        } catch (RocksDBException e) {
            throw new IllegalStateException("Someting went wrong when opening the RocksDB connection", e);
        }

        throw new IllegalStateException("not yet implemented");
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
