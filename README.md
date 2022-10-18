# ccwg
Coding Competitions with Google

## Notes

C++ local helper:

```sh
g++ -Wall -Wextra -fsanitize=address,undefined main.cpp && time ./a.out <sample | diff -yZ - output
```