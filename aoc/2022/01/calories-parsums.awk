#!/bin/awk -f

# Empty line, time to sum up!
/^$/ {
    print maxTmp
    maxTmp = 0
}

// {
    maxTmp += $1
}
