// alice:   .*[aeiou]
// bob:     .*[^aeiouy]
// T [1,300]
// ['A', 'E', 'I', 'O', 'U', 'a', 'e', 'i', 'o', 'u']{3,20}|{,100}
#include <iostream>
#include <cstdio>
#include <cctype>

using namespace std;

enum class ruler              { nobody, alice, bob, };
static const char *Rulers[] = {"nobody", "Alice", "Bob"};

static inline void printRes(int n, const char *city, ruler r) {
    std::printf("Case #%d: %s is ruled by %s.\n", n, city, Rulers[(size_t)r]);
}

static ruler rule(int c) {
    if (c == 'y') return ruler::nobody;
    if (c == 'a' || c == 'e' || c == 'u' || c == 'i' || c == 'o') return ruler::alice;
    return ruler::bob;
}

int main() {
    int ncity;
    string city;

    cin >> ncity;

    for (auto i = 0; i < ncity; ++i) {
        cin >> city;
        printRes(i+1, city.c_str(), rule(tolower(city.back())));
    }
    return 0;
}