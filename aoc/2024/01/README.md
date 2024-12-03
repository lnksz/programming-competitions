# Set Up

As repl.it shortened their limits in the free tier, I will try out some new mobile alternatives

- Codespaces in chrome directly
- Termux for Linux cmds (great!)
- smart IDE offline Kotlin compiler app

# Task 1

awk again... I still like `paste`, found during a past AoC.

```
bash t1.sh 01
```

# Task 2

Interestingly the simple solution runs "faster" than the smarter one...
Both take nearly 30s though...

```
time bash t2.sh 01
time bash t2o.sh 01
```

This is a kinda nice bash only decomposition from a space separated array on a variable: `read i p <<<$L`