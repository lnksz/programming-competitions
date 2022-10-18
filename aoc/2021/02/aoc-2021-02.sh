#!/bin/bash

usage() {
	echo "$0 <file> a/b"
}

a() {
	awk '/forward*/{f += $2} /down*/{d += $2} /up*/{d -= $2} \
	END {print f*d}' "$1"
}

b() {
	awk '/forward*/{f += $2; d += (a*$2)} /down*/{a += $2} /up*/{a -= $2} \
	END {print f*d}' "$1"
}

if [ -f "$1" ] && [[ "$2" =~ (a|b) ]]; then
	eval "$2 $1"
else
	usage
fi

