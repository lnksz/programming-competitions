#!/bin/awk -f

# For explicit initialization
BEGIN {
    maxGlobal = 0
    maxTmp = 0
}

# Empty line, time to sum up!
/^$/ {
    if (maxTmp > maxGlobal)
        maxGlobal = maxTmp
    maxTmp = 0
}

// {
    maxTmp += $1
}

END {
    print maxGlobal
}
