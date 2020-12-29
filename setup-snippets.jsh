/**
 * This file contains "set up" statements that we want loaded automatically every time we start a jshell session.
 * This is boilerplate that we don't want to write by hand over and over.
 */

import dgroomes.RocksDbRepo;
import dgroomes.TestData;
import static dgroomes.TestData.writeTestData;

var repo = new RocksDbRepo();
repo.init();
