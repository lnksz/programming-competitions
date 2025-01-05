#!/bin/bash
awk '{print $1}' $1 >1
awk '{print $2}' $1 >2
while read p; do
        n=$(grep $p -c 2)
        ((s+=n*p))
done < 1
echo $s