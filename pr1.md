# Практическая 1

## Задание 1

``` bash 
grep -v "^\s*#" /etc/passwd | cut -d: -f1 | sort
 ``` 

## Задание 2
``` bash 
grep -v "^#" /etc/protocols | tail -5 | sort -nk2r | awk '{print $2, $3}'
```
## Задание 3
``` bash 
nano banner
```

``` bash
#!/bin/bash
text="$1"

echo -n "+"

count=0

while [ $count -le $((${#text}+1)) ]
do
echo -n "-"
((count++))
done
echo "+"
echo "| ${text} |"
count=0
echo -n "+"
while [ $count -le $((${#text}+1)) ]
do
echo -n "-"
((count++))
done
echo "+"
```

``` bash
./banner "Hello from RTU MIREA!"
```
## Задание 4
``` bash 
grep -oE "[a-zA-Z_][a-zA-Z0-9_]*" hello.java | sort -u | paste -sd ' ' -
```
## Задание 5
``` bash
 #!/bin/bash
file="$1"
chmod 755 $file
sudo cp $file /usr/local/bin
```

``` bash 
./reg banner
```
## Задание 6
``` bash
#!/bin/bash
if [ -z  "$(grep -o "^#[A-Za-z]*" test.py)" ]
then
echo "No comments"
else
echo "Comment"
fi
```
## Задание 7
``` bash
#!/bin/bash
path="$1"
sha256sum "$path"/* > hashes.txt
sort hashes.txt | awk '{
hash = $1
fileName = $2
if(files[hash] != ""){
        print files[hash]
        print fileName
}
else{
        files[hash] = fileName
}
}'
```
## Задание 8
``` bash
path="$1"
type="$2"
tar -cf  /Users/argok/arc.tar $(find "$path" -name "*."$type"")
```

``` bash
./arcFunc /Users/argok/test txt
```
## Задание 9
``` bash
nano funcTab
```
``` bash
#!/bin/bash
pathIn="$1"
pathOut="$2"
sed  "s/    /\t/g" "$pathIn">"$pathOut"
```
``` bash
./funcTab /Users/argok/testTab /Users/argok/testTabResult
```
## Задание 10
``` bash
find /Users/argok/test -empty -name "*.txt"
```
