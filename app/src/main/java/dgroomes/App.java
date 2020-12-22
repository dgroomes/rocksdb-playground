package dgroomes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A made-up application that uses a repository. It reads, it writes.
 */
public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    Repo repo;

    public App(Repo repo) {
        this.repo = repo;
    }

    public static void main(String[] args) {
        Repo rocksDbRepo = new RocksDbRepo();
        var app = new App(rocksDbRepo);

        app.run();
    }

    private void run() {
        repo.init();

        repo.write("message", "hello world!");
        String message = repo.read("message");

        log.info("Found message: {}", message);
    }
}
