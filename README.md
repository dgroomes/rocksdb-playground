# rocksdb-playground

📚 Learning and exploring [RocksDB](https://github.com/facebook/rocksdb).


## Overview

This project is for me to learn RocksDB. I'm interested in building a better intuition for what it's useful for, how it
excels vs. other datastore tech (columnar, B-trees, etc), and how to use it via its Java API.

A lot of it boils down to a clear view of performance. Is it faster on some specific workload than, say DuckDB, or Postgres? I have a TODO to bring in the ZIP code domain like my other projects (Mongo, Kafka Streams, and others?). I think RocksDB should be able to compress (in a sense) the corpus down pretty well for the ZIP areas data. E.g. if the data is laid out in order by state code, city name, then ZIP code, then that should mean that the state code is not repeated for as many entries are in that state, right?


## Instructions

This codebase is not meant to be executed in a "run-to-completion" fashion but rather should be used interactively in a
[JShell](http://openjdk.java.net/jeps/222) session. This is useful for experimenting quickly. That said, here are some steps to get up and running.

1. Pre-requisite: Use Java 15
2. Load the program source code and library dependencies into a `jshell` session
    - ```shell
      ./jshell.sh
      ```
    - You are entered into a session in the `jshell` tool.
    - The variable `repo` is initialized in this session and ready to use. It is our entrypoint to RocksDB. 
3. Explore! For example, write an entry into the database
    - ```text
      repo.write("key1", "hello world")
      ```
4. Read the entry from the database:
    - ```text
      repo.read("key1")
      ```
    - You should see the original message. Altogether, it will look like the following.
    - ```text
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
5. When you're done, exit the JShell session:
    - ```text
      /exit
      ```


## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

* [x] DONE Flesh out the README with instructions (in particular JShell-based instructions for exploring)
* [x] DONE Consolidate repo and rocksdb-repo into one
* [x] DONE Define some utility methods to generate test data
* [ ] Do a "range query". E.g. query by "key1" through "key10" or something like that
* [x] DONE Add a JShell setup script that `new`s up and initializes the database
* [x] DONE Add a slf4j config file
* [x] IN PROGRESS Upgrades across the board.
* [ ] Emulate the ZIP codes example from my other database playground-style projects. I need something with higher volume.


## Reference

* [RocksDB GitHub site: *RocksJava Basics*](https://github.com/facebook/rocksdb/wiki/RocksJava-Basics)
* [`jshell-playground`: *`with-gradle` example Gradle project with JShell*](https://github.com/dgroomes/jshell-playground)
