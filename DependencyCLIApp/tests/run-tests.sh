#!/bin/sh
set -eu
cd "$(dirname "$0")/.."
test_dir=$(mktemp -d)
trap 'rm -r "$test_dir"' EXIT
javac -d "$test_dir" src/*.java

run_case() {
    name=$1
    filter=$2
    expected=$3
    printf '%s\n' "<config><packageName>A</packageName><repositoryUrl></repositoryUrl>" \
        "<testRepositoryPath>$test_dir/repository.txt</testRepositoryPath>" \
        "<testMode>true</testMode><graphImageFile>graph.png</graphImageFile>" \
        "<asciiTree>false</asciiTree><packageFilter>$filter</packageFilter></config>" > "$test_dir/config.xml"
    actual=$(java -cp "$test_dir" Main "$test_dir/config.xml")
    if [ "$actual" != "$expected" ]; then
        printf 'FAIL %s\n%s\n' "$name" "$actual"
        exit 1
    fi
    printf 'PASS %s\n' "$name"
}

printf 'A: B\nB: C\nC:\n' > "$test_dir/repository.txt"
run_case chain '' 'A -> [B]
B -> [C]
C -> []'
printf 'A: B C\nB: D\nC: D\nD: A\n' > "$test_dir/repository.txt"
run_case cycle '' 'A -> [B, C]
B -> [D]
C -> [D]
D -> [A]'
run_case filter C 'A -> [B]
B -> [D]
D -> [A]'
printf 'A:\n' > "$test_dir/repository.txt"
run_case empty '' 'A -> []'
