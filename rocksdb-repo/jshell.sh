#!/usr/bin/env bash
# Build the project and start a jshell session that's loaded with the project's source code and library dependencies.

set -eu

../gradlew build

programClasses="build/classes/java/main"
depsFile="build/runtime-dependencies.txt"

jshell --class-path "${programClasses}:$(cat "$depsFile")"
