#!/bin/sh
set -eu
cd "$(dirname "$0")/.."
test_dir=$(mktemp -d)
trap 'rm -r "$test_dir"' EXIT
javac -d "$test_dir" src/Main.java tests/EmulatorTest.java
java -Djava.awt.headless=true -cp "$test_dir" EmulatorTest
