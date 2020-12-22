package dgroomes;

/**
 * This interface defines common methods that make up a so-called "repository". This interface is implemented by concrete
 * classes using different underlying database technologies like RocksDB and SQLite.
 */
public interface Repo {

    /**
     * Initialize the database
     */
    void init();

    /**
     * Read an entry by its key
     * @param key the key of the entry
     * @return the value of the entry
     */
    String read(String key);

    /**
     * Write an entry
     * @param key the key of the entry
     * @param value the value of the entry
     */
    void write(String key, String value);
}
