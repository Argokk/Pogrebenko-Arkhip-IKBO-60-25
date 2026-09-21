# Практическая 1

## Задание 1

``` grep -v "^\s*#" /etc/passwd | cut -d: -f1 | sort  ``` 

## Задание 2
``` grep -v "^#" /etc/protocols | tail -5 | sort -nk2r | awk '{print $2, $3}'```
## Задание 3
``` nano banner ```

```
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
