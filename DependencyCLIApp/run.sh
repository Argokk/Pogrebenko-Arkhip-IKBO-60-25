#!/bin/sh
set -eu
cd "$(dirname "$0")"
mkdir -p out
javac -d out src/*.java
java -cp out Main "${1:-config.xml}"
