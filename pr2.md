# Практика 2
## Задание 1
``` bash
brew info python-matplotlib
```
Результат:
``` bash
==> python-matplotlib ✔: stable 3.11.2 (bottled)
Python library for creating static, animated, and interactive visualizations
https://matplotlib.org/
Installed (on request)
From: https://github.com/Homebrew/homebrew-core/blob/HEAD/Formula/p/python-matplotlib.rb
License: PSF-2.0
==> Installed Versions
python-matplotlib ✔ 3.11.2 (1,102 files, 29.4MB) [Linked]
==> Dependencies
Required (5): freetype ✔, numpy ✔, pillow ✔, python@3.14 ✔, qhull ✔
Recursive Runtime (53): all installed ✔
==> Analytics
install: 557 (30 days), 3,004 (90 days), 17,103 (365 days)
install-on-request: 548 (30 days), 2,893 (90 days), 16,900 (365 days)
build-error: 0 (30 days)
```
stable - версия
from - откуда скачался пакет
license - лицензия
dependencies - зависимости, нужные matplotlib

чтобы получить пакет без менеджера, его надо скачать вручную


## Задание 2
``` bash
brew info node
```
``` bash
==> node: stable 26.10.0 (bottled), HEAD
Open-source, cross-platform JavaScript runtime environment
https://nodejs.org/
Aliases: node.js, node@26, nodejs, npm
Not installed
Bottle Size: 21.3MB
Installed Size: 74.9MB
From: https://github.com/Homebrew/homebrew-core/blob/HEAD/Formula/n/node.rb
License: MIT
==> Dependencies
Required (19): abseil, ada-url, brotli, c-ares, hdrhistogram_c, icu4c@78 ✔, libffi, libnghttp2, libuv, llhttp, merve, nbytes, openssl@3 ✔, simdjson, simdutf, sqlite ✔, uvwasi, zstd ✔, highway
==> Options
--HEAD
	Install HEAD version
==> Caveats
Single Executable Application is disabled as it doesn't work with shared libnode.
==> Downloading https://formulae.brew.sh/api/formula/node.json
==> Analytics
install: 129,134 (30 days), 660,530 (90 days), 3,091,259 (365 days)
install-on-request: 104,309 (30 days), 533,926 (90 days), 2,474,237 (365 days)
build-error: 724 (30 days)
```
stable - версия
aliases - псевдонимы
bottle size - размер скачанного пакета из интернета
installed size - размер пакета после распаковки 
from - откуда скачали
license - лицензия
dependencies - зависимости
caveats - примечания об установленном пакете
downloading - загрузка формулы
analytics - статистика скачивания express
build-error - количество ошибок у пользователей

чтобы получить пакет без менеджера, его надо скачать вручную

## Задание 3
``` bash
digraph matplotlib{
        matplotlib [shape=box, label = "matplotlib"]
        freetype [shape=box, label = "freetype"]
        numpy [shape=box, label = "numpy"]
        pillow [shape=box, label = "pillow"]
        "python@3.14" [shape=box, label = "python@3.14"]
        qhull [shape=box, label = "qhull"]

        matplotlib -> freetype
        matplotlib -> numpy
        matplotlib -> pillow
        matplotlib -> "python@3.14"
        matplotlib -> qhull
}
```

``` bash
dot -Tpng diag.dot -o matplotlib.png
``` bash
digraph express{
        node [shape=box]
        abseil [label="abseil"]
        "ada-url" [label="ada-url"]
        brotli [label="brotli"]
        "c-ares" [label="c-ares"]
        "hdrhistogram_c" [label="hdrhistogram_c"]
        "icu4c@78" [label="icu4c@78"]
        libffi [label="libffi"] 
        libnghttp2 [label="libnghttp2"]
        libuv [label="libuv"]
        llhttp [label="llhttp"]
        merve [label="merve"]
        nbytes [label="nbytes"]
        "openssl@3" [label="openssl@3"]
        simdjson [label="simdjson"]
        simdutf [label="simdutf"]
        sqlite [label="sqlite"]
        uvwasi [label="uvwasi"]
        zstd [label="zstd"]
        highway [label="highway"]
        NodeJS [label="node"]
        
        NodeJS -> abseil
        NodeJS -> "ada-url"
        NodeJS -> brotli
        NodeJS -> "c-ares"
        NodeJS -> hdrhistogram_c
        NodeJS -> "icu4c@78"
        NodeJS -> libffi
        NodeJS -> libnghttp2
        NodeJS -> libuv
        NodeJS -> llhttp
        NodeJS -> merve
        NodeJS -> nbytes
        NodeJS -> "openssl@3"
        NodeJS -> simdjson
        NodeJS -> simdutf
        NodeJS -> sqlite   
        NodeJS -> uvwasi
        NodeJS -> zstd
        NodeJS -> highway
}
```
``` bash
dot -Tpng diag2.dot -o express.png
```
## Задание 4
``` MiniZinc
% Use this editor as a MiniZinc scratch book
include "globals.mzn";
var 0..9: a;
var 0..9: b;
var 0..9: c;
var 0..9: d;
var 0..9: e;
var 0..9: f;

constraint a + b + c = d + e + f;
constraint all_different([a, b, c, d, e, f]);

solve minimize(a + b + c);

output("a + b + c = \(a) + \(b) + \(c) = \(a + b + c)\n");
output("d + e + f = \(d) + \(e) + \(f) = \(d + e + f)");
```
### Output:
```
a + b + c = 8 + 1 + 0 = 9
d + e + f = 4 + 3 + 2 = 9
----------
a + b + c = 6 + 2 + 0 = 8
d + e + f = 4 + 3 + 1 = 8
```
### Answer: 8

## Задание 5
``` MiniZinc
var 1.0..1.5: menu;
var 1.8..2.3: dropdown;
var 1.0..2.0: icons;

constraint  icons = 1.0;
constraint menu = 1.0 -> dropdown = 1.8;
constraint (menu >= 1.1 /\ menu <= 1.5) -> (dropdown >= 2.0 /\ dropdown <= 2.3);
constraint (dropdown >= 2.0 /\ dropdown <= 2.3) -> icons = 2.0;
solve satisfy;
```
### Output:
```
menu = 1.0;
dropdown = 1.80000000000001;
icons = 1.0;
```
## Задание 6
``` MiniZinc
var 1.0..1.1: foo;
var 1.0..2.0: target;
var 0..1.0: left;
var 0..1.0: right;
float: root = 1.0;
var 1.0..2.0: shared;

constraint root = 1.0 -> (foo = 1.0 /\ target = 2.0);
constraint foo = 1.1 -> (left = 1.0 /\ right = 1.0);
constraint left = 1.0 -> shared = 1.0;
constraint right = 1.0 -> shared = 2.0;
constraint shared = 1.0 -> target = 1.0;

solve satisfy;
```
### Output:
```
foo = 1.0;
target = 2.0;
left = 0.0;
right = 0.0;
shared = 1.00000000000001;
```
## Задание 7
``` MiniZinc
int: n;
int: m;
array[1..n] of string: names;
array[1..n] of string: versions;
array[1..m] of int: owners;
array[1..m] of string: needed_names;
array[1..m] of string: needed_versions;

array[1..n] of var bool: take;

constraint exists(i in 1..n)(
    names[i] = "root" /\
    versions[i] = "1.0.0" /\
    take[i]
);

constraint forall(i, j in 1..n where i < j)(
    names[i] = names[j] -> not (take[i] /\ take[j])
);

constraint forall(d in 1..m)(
    take[owners[d]] ->
    exists(i in 1..n)(
        names[i] = needed_names[d] /\
        versions[i] = needed_versions[d] /\
        take[i]
    )
);

solve minimize sum(i in 1..n)(bool2int(take[i]));

output [
    names[i] ++ " " ++ versions[i] ++ "\n"
    | i in 1..n where fix(take[i])
];
];    names[i] ++ " " ++ versions[i] ++ "\n"
    | i in 1..n where fix(take[i])
];
```
``` python
import json
import subprocess
from pathlib import Path

packages = {
    "root": {
        "1.0.0": {"foo": "1.0.0", "target": "2.0.0"}
    },
    "foo": {
        "1.0.0": {},
        "1.1.0": {"left": "1.0.0", "right": "1.0.0"}
    },
    "left": {
        "1.0.0": {"shared": "1.0.0"}
    },
    "right": {
        "1.0.0": {"shared": "2.0.0"}
    },
    "shared": {
        "1.0.0": {"target": "1.0.0"},
        "2.0.0": {}
    },
    "target": {
        "1.0.0": {},
        "2.0.0": {}
    }
}

names = []
versions = []
owners = []
needed_names = []
needed_versions = []

for name, variants in packages.items():
    for version, dependencies in variants.items():
        names.append(name)
        versions.append(version)

        for needed_name, needed_version in dependencies.items():
            owners.append(len(names))
            needed_names.append(needed_name)
            needed_versions.append(needed_version)

data = {
    "n": len(names),
    "m": len(owners),
    "names": names,
    "versions": versions,
    "owners": owners,
    "needed_names": needed_names,
    "needed_versions": needed_versions
}

folder = Path(__file__).parent
data_file = folder / "data.json"
data_file.write_text(json.dumps(data), encoding="utf-8")

subprocess.run([
    "/Applications/MiniZincIDE.app/Contents/Resources/minizinc",
    "--solver", "gecode",
    str(folder / "resolver.mzn"),
    str(data_file)
], check=True)
```
### Output: root 1.0.0
foo 1.0.0
target 2.0.0
----------
==========
