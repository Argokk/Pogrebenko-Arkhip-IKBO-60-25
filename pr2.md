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

##Задание 3
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

