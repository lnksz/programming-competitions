#!/bin/awk -f

BEGIN {
    lowermap="abcdefghijklmnopqrstuvwxyz"
    uppermap="ABCDEFGHIJKLMNOPQRSTUVWXYZ"
}
/[a-z]/ {
    num = index(lowermap, $1)
    total += num
}
/[A-Z]/ {
    num = index(uppermap, $1) + 26
    total += num
}
END {
    print total
}