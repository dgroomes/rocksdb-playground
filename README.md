# rocksdb-playground

NOT YET IMPLEMENTED. 

📚 Learning and exploring [RocksDB](https://github.com/facebook/rocksdb).

## Instructions

See the README in [rocksdb-repo/](rocksdb-repo/) for instructions.

## Design and Architecture

The project is split into sub-modules:

* `repo/` The interface definition for the repository layer 
* `rocksdb-repo/`  An implementation of the repository layer using RocksDB
  * See the README in [rocksdb-repo/](rocksdb-repo/) for more information.
  
## Reference Material

* [RocksDB GitHub site: *RocksJava Basics*](https://github.com/facebook/rocksdb/wiki/RocksJava-Basics)
* [`gradle-playground`: *`multi-module` example Gradle project*](https://github.com/dgroomes/gradle-playground/tree/main/multi-module)
* [`jshell-playground`: *`with-gradle` example Gradle project with JShell*](https://github.com/dgroomes/jshell-playground)


## TODO

* Flesh out the README with instructions (in particular JShell-based instructions for exploring)
* Consolidate repo and rocksdb-repo into one
