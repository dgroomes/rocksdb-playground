package dgroomes;

/**
 * This interface defines common methods that make up a so-called "repository". This interface is implemented by concrete
 * classes using different underlying data storage technologies like RocksDB and SQLite.
 */
public interface Repo {

    /**
     * Initialize the repository. This must be called before the repo can be used.
     */
    void init();

    /**
     * Shutdown the repository. This should be called before exiting the program.
     */
    void shutdown();

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
