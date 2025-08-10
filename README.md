# rocksdb-playground

📚 Learning and exploring RocksDB.

> RocksDB: A Persistent Key-Value Store for Flash and RAM Storage
> 
> -- <cite>https://github.com/facebook/rocksdb</cite>


## Overview

This project is for me to learn RocksDB. I'm interested in building a better intuition for what it's useful for, how it
excels vs. other datastore tech (columnar, B-trees, etc), and how to use it via its Java API.

A lot of it boils down to a clear view of performance. Is it faster on some specific workload than, say DuckDB, or Postgres? I have a TODO to bring in the ZIP code domain like my other projects (Mongo, Kafka Streams, and others?). I think RocksDB should be able to compress (in a sense) the corpus down pretty well for the ZIP areas data. E.g. if the data is laid out in order by state code, city name, then ZIP code, then that should mean that the state code is not repeated for as many entries are in that state, right?


## Instructions

Follow these instructions to build and run the example program.

1. Use Java 21
2. Build and run the program
   * ```shell
     ./gradlew run
     ```
   * It should print something like the following.
   * ```text
     11:38:42 [main] INFO dgroomes.Main - Let's learn about RocksDB! A high-performance embedded key-value store.
     11:38:42 [main] INFO dgroomes.Main - 
     11:38:42 [main] INFO dgroomes.Main - Writing and reading basic key-value pairs...
     11:38:42 [main] INFO dgroomes.Main - Greeting: Hello, RocksDB!
     11:38:42 [main] INFO dgroomes.Main - Language: Java
     11:38:42 [main] INFO dgroomes.Main - Version: 10.2.1
     11:38:42 [main] INFO dgroomes.Main - 
     11:38:42 [main] INFO dgroomes.Main - Demonstrating range queries with sequential keys...
     11:38:42 [main] INFO dgroomes.Main - Wrote 10 sequential items (item_01 through item_10)
     11:38:42 [main] INFO dgroomes.Main - Reading items 3-5:
     11:38:42 [main] INFO dgroomes.Main -   item_03 = Value for item 3
     11:38:42 [main] INFO dgroomes.Main -   item_04 = Value for item 4
     11:38:42 [main] INFO dgroomes.Main -   item_05 = Value for item 5
     11:38:42 [main] INFO dgroomes.Main - 
     11:38:42 [main] INFO dgroomes.Main - Generating and storing test data...
     11:38:42 [main] INFO dgroomes.Main - Wrote 100 test entries in 2 ms
     11:38:42 [main] INFO dgroomes.Main - Sample test data:
     11:38:42 [main] INFO dgroomes.Main -   test-key-0 = test-value-0
     11:38:42 [main] INFO dgroomes.Main -   test-key-25 = test-value-25
     11:38:42 [main] INFO dgroomes.Main -   test-key-50 = test-value-50
     11:38:42 [main] INFO dgroomes.Main -   test-key-75 = test-value-75
     11:38:42 [main] INFO dgroomes.Main -   test-key-99 = test-value-99
     11:38:42 [main] INFO dgroomes.Main - 
     11:38:42 [main] INFO dgroomes.Main - Loading ZIP code data from zips.jsonl...
     11:38:42 [main] INFO dgroomes.Main - Loaded 29,353 ZIP codes in 111 ms
     11:38:42 [main] INFO dgroomes.Main - 
     11:38:42 [main] INFO dgroomes.ZipCodeLoader - Sample ZIP code queries:
     11:38:42 [main] INFO dgroomes.ZipCodeLoader -   ZIP 10001 -> {"city":"NEW YORK","state":"NY","pop":18913,"loc":[-73.99670500000001,40.74838]}
     11:38:42 [main] INFO dgroomes.ZipCodeLoader -   ZIP 90210 -> {"city":"BEVERLY HILLS","state":"CA","pop":20700,"loc":[-118.406477,34.090107]}
     11:38:42 [main] INFO dgroomes.ZipCodeLoader -   ZIP 60601 -> {"city":"CHICAGO","state":"IL","pop":4585,"loc":[-87.618123,41.885847]}
     11:38:42 [main] INFO dgroomes.ZipCodeLoader -   ZIP 02134 -> {"city":"ALLSTON","state":"MA","pop":23775,"loc":[-71.13286600000001,42.353519]}
     11:38:42 [main] INFO dgroomes.ZipCodeLoader -   ZIP 94102 -> {"city":"SAN FRANCISCO","state":"CA","pop":26908,"loc":[-122.416728,37.781334]}
     11:38:42 [main] INFO dgroomes.Main - 
     ```


## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

- [x] DONE Flesh out the README with instructions (in particular JShell-based instructions for exploring)
- [x] DONE Consolidate repo and rocksdb-repo into one
- [x] DONE Define some utility methods to generate test data
- [ ] Do a "range query". E.g. query by "key1" through "key10" or something like that
- [x] DONE Add a JShell setup script that `new`s up and initializes the database
- [x] DONE Add a slf4j config file
- [x] DONE Upgrades across the board.
- [x] DONE (the LLM did it without asking.. I'll keep it but only as a first take) Emulate the ZIP codes example from my other database playground-style projects. I need something with higher volume.
- [ ] High volume data. Use the ZIP codes domain data. I want to see timings and volume (MB, GB) when writing, querying and compacting. Maybe up to 1GB is a good compromise.
   * This needs to write a run of data, then a new run of data that must be merged (key overlap) with the existing data. We don't want to accidentally paper over real world complexity.
- [ ] Handle shutdown properly (clean up temp directory)
- [x] DONE Remove JShell (I like it in general, but a tutorial has a limited budget of stuff/content)


## Reference

- [RocksDB GitHub site: *RocksJava Basics*](https://github.com/facebook/rocksdb/wiki/RocksJava-Basics)
- [`jshell-playground`: *`with-gradle` example Gradle project with JShell*](https://github.com/dgroomes/jshell-playground)
