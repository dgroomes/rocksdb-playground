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

Follow these instructions to build and run the example programs.

1. Use Java 21
2. Run the basic demo
   * ```shell
     ./gradlew runBasic
     ```
   * It should print something like the following.
   * ```text
     INFO dgroomes.BasicDemo - Created temporary directory for RocksDB: <...>
     INFO dgroomes.BasicDemo - Writing and reading key-value pairs...
     INFO dgroomes.BasicDemo - greeting: Hello, RocksDB!
     INFO dgroomes.BasicDemo - language: Java
     INFO dgroomes.BasicDemo - java_version: 25
     INFO dgroomes.BasicDemo -
     INFO dgroomes.BasicDemo - Reading a range of sequential entries...
     INFO dgroomes.BasicDemo - Wrote 10 sequential items (item_01 through item_10)
     INFO dgroomes.BasicDemo - Reading a slice of them (items 3 - 6):
     INFO dgroomes.BasicDemo -   item_03 = Value for item 3
     INFO dgroomes.BasicDemo -   item_04 = Value for item 4
     INFO dgroomes.BasicDemo -   item_05 = Value for item 5
     INFO dgroomes.BasicDemo -   item_06 = Value for item 6
     INFO dgroomes.BasicDemo - Cleaning up temporary directory: <...>
     ```
3. Run the batch demo
   * ```shell
     ./gradlew runBatch
     ```
   * It should print something like the following.
   * ```text
     INFO dgroomes.BatchDemo - Created temporary directory for RocksDB: <...>
     INFO dgroomes.BatchDemo - Loading ZIP Codes...
     INFO dgroomes.BatchDemo - Loaded 5,000 ZIP codes...
     INFO dgroomes.BatchDemo - Loaded 10,000 ZIP codes...
     INFO dgroomes.BatchDemo - Loaded 15,000 ZIP codes...
     INFO dgroomes.BatchDemo - Loaded 20,000 ZIP codes...
     INFO dgroomes.BatchDemo - Loaded 25,000 ZIP codes...
     INFO dgroomes.BatchDemo - Loaded 29,353 ZIP codes in PT0.152663S
     INFO dgroomes.BatchDemo -
     INFO dgroomes.BatchDemo - Read the first 5 ZIP codes in California...
     INFO dgroomes.BatchDemo -   90001 (CA) -> City: LOS ANGELES, Pop: 51,841
     INFO dgroomes.BatchDemo -   90002 (CA) -> City: LOS ANGELES, Pop: 40,629
     INFO dgroomes.BatchDemo -   90003 (CA) -> City: LOS ANGELES, Pop: 53,938
     INFO dgroomes.BatchDemo -   90004 (CA) -> City: LOS ANGELES, Pop: 64,062
     INFO dgroomes.BatchDemo -   90005 (CA) -> City: LOS ANGELES, Pop: 35,864
     INFO dgroomes.BatchDemo - Cleaning up temporary directory: <...>
     ```


## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

- [x] DONE Flesh out the README with instructions (in particular JShell-based instructions for exploring)
- [x] DONE Consolidate repo and rocksdb-repo into one
- [x] DONE Define some utility methods to generate test data
- [x] DONE Do a "range query". E.g. query by "key1" through "key10" using an iterator
- [x] DONE Add a JShell setup script that `new`s up and initializes the database
- [x] DONE Add a slf4j config file
- [x] DONE Upgrades across the board.
- [x] DONE (the LLM did it without asking.. I'll keep it but only as a first take) Emulate the ZIP codes example from my other database playground-style projects. I need something with higher volume.
- [ ] IN PROGRESS Split into sub-projects
   - DONE Pre-work is to split into two classes
   - Split into actual different sub-projects
- [ ] High volume data. Use the ZIP codes domain data. I want to see timings and volume (MB, GB) when writing, querying and compacting. Maybe up to 1GB is a good compromise.
   * This needs to write a run of data, then a new run of data that must be merged (key overlap) with the existing data. We don't want to accidentally paper over real world complexity.
- [ ] Handle shutdown properly (clean up temp directory)
- [x] DONE Remove JShell (I like it in general, but a tutorial has a limited budget of stuff/content)
- [x] DONE Use Jackson instead of the silly parsing the LLM wrote
- [x] DONE Do not use the repo pattern. I want the code to directly use the Rocks Java APIs for the sake of an effective/pure tutorial.
- [ ] There has to be some useful abstraction for expressing fixed-width schemas of types, to make it easy to work with keys. I guess Java's ValueLayout APIs that were made as part of the Foreign Memory Access work? Or maybe something in the realm of FlatBuffers or something?


## Reference

- [RocksDB GitHub site: *RocksJava Basics*](https://github.com/facebook/rocksdb/wiki/RocksJava-Basics)
