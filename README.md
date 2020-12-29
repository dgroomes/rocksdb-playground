# rocksdb-playground

📚 Learning and exploring [RocksDB](https://github.com/facebook/rocksdb).

## Instructions

This codebase is not meant to be executed in a "run-to-completion" fashion but rather should be used interactively in a
[JShell](http://openjdk.java.net/jeps/222) session. This is useful for experimenting quickly.

* Use Java 15
* Load the program source code and library dependencies into a `jshell` session!
    * `./jshell.sh`
    * You are entered into a session in the `jshell` tool.
    * The variable `repo` is initialized in this session and ready to use. It is our entrypoint to RocksDB. 
* Explore! For example, write an entry into the database:
    * `repo.write("key1", "hello world")`
* Read the entry from the database:
    * `repo.read("key1")`
    * You should see the original message!
* Altogether, it will look like this:
    ```
    ./jshell.sh
    
    BUILD SUCCESSFUL in 880ms
    3 actionable tasks: 3 up-to-date
    |  Welcome to JShell -- Version 15.0.1
    |  For an introduction type: /help intro
    
    jshell> repo.write("key1", "hello world")
    
    jshell> repo.read("key1")
    $2 ==> "hello world"
    
    jshell>
    ```

## Reference Material

* [RocksDB GitHub site: *RocksJava Basics*](https://github.com/facebook/rocksdb/wiki/RocksJava-Basics)
* [`jshell-playground`: *`with-gradle` example Gradle project with JShell*](https://github.com/dgroomes/jshell-playground)

## WishList

General clean-ups, TODOs and things I wish to implement for this project:

* DONE Flesh out the README with instructions (in particular JShell-based instructions for exploring)
* DONE Consolidate repo and rocksdb-repo into one
* DONE Define some utility methods to generate test data
* Do a "range query". E.g. query by "key1" through "key10" or something like that
* DONE Add a JShell setup script that `new`s up and initializes the database
* Add a slf4j config file
