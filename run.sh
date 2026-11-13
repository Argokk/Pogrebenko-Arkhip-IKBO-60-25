#!/bin/sh
set -eu
cd "$(dirname "$0")"
mkdir -p out
javac -d out src/Main.java
java -cp out Main "${1:-src/vfs_minimal.csv}" "${2:-src/commands.txt}"
