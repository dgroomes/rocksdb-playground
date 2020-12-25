# rocksdb-playground

NOT YET IMPLEMENTED. 

📚 Learning and exploring [RocksDB](https://github.com/facebook/rocksdb).

## Instructions

* `./gradlew :app:run`

## Design and Architecture

The project is split into sub-modules:

* `app/` The application layer
* `repo/` The interface definition for the repository layer 
* `rocksdb-repo/`  An implementation of the repository layer using RocksDB 
* `sqlite-repo/` An implementation of the repository layer using SQLite

The application layer codes to a so-called *repository interface* and executes read and write operations to the repository.
There are two concrete implementations of the repository interface. One implementation uses RocksDB and the other uses
SQLite. I hope that this design can give us an apples-to-apples comparison of RocksDB vs SQLite for this particular workload. 


## Reference Material

* [RocksDB GitHub site: *RocksJava Basics*](https://github.com/facebook/rocksdb/wiki/RocksJava-Basics)
* [`gradle-playground`: *`multi-module` example Gradle project*](https://github.com/dgroomes/gradle-playground/tree/main/multi-module)


## TODO

* Everything!
* Define the workload. Is it write heavy? Read heavy? Highly concurrent? RocksDB accommodates write-heavy workloads so
  we should probably make it write-heavy, or at least configurable.
* Do we need to build RocksJava from source on Apple silicon? See this [related GitHub PR](https://github.com/facebook/rocksdb/pull/7714). 
