#!/bin/bash

paste -d_ first second | while IFS="_" read -r p1 p2
do
  echo "$p1" | grep -o . | sort | uniq >/tmp/lookup
  echo "$p2" | grep -o . | grep -oF -m 1 -f /tmp/lookup
done
