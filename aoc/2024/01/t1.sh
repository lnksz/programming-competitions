#!/bin/bash
paste \
        <(awk '{print $1}' $1 | sort) \
        <(awk '{print $2}' $1 | sort) \
        | awk '{d=$2-$1;s+=sqrt(d*d)}END{print s}'