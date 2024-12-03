#!/bin/bash
awk '{print $1}' $1 | sort >1
awk '{print $2}' $1 | sort >2
paste 1 2 | awk '{d=$2-$1;s+=sqrt(d*d)}END{print s}'
