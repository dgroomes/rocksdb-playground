#!/usr/bin/env bash
# Build the project and start a jshell session that's loaded with the project's source code and library dependencies.

set -eu

./gradlew build

programClasses="build/classes/java/main"
programResources="build/resources/main"
depsFile="build/runtime-dependencies.txt"

jshell \
--startup DEFAULT --startup setup-snippets.jsh \
--class-path "${programClasses}:${programResources}:$(cat "$depsFile")"
