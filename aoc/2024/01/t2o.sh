#!/bin/bash
awk '{print $1}' $1 | sort | uniq -c >d1
awk '{print $2}' $1 | sort | uniq -c >d2
while read L; do
        read i p <<<$L
        M=$(grep -m 1 "$p\$" d2)
        read n _ <<<$M
        ((s+=n*p*i))
        #echo $i $p $n $s
done < d1
echo $s
