#!/bin/awk -f

{
    print substr($1, 0, length/2)
    print substr($1, length/2 + 1)  | system(grep -o . | sort | uniq)
}