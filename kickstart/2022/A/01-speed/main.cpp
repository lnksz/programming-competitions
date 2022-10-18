// T [1, 100]
// I1 [1, 1e5]
// P1
// I2
// P2

/*
Per test-case (N):

*/
#include <iostream>
#include <string>
#include <cstdio>

using namespace std;

int main(int argc, char **) {
    std::size_t ntests, li, lo;
    string inp;
    string out;
    inp.reserve(1e5);
    out.reserve(1e5);
    const bool dkri = (argc > 1);

    cin >> ntests;
    std::cin >> std::ws; // for getline to not get '\n'
    for (auto t = 0u; t < ntests; ++t)
    {
        getline(cin, inp);
        getline(cin, out);

        if (dkri) printf("%s\n%s\n", inp.c_str(), out.c_str());

        li = inp.length();
        lo = out.length();

        if (lo < li) {
            printf("Case #%u: IMPOSSIBLE\n", t + 1);
            continue;
        }
        // O(1) if std uses memcmp
        if (inp == out) {
            printf("Case #%u: 0\n", t + 1);
            continue;
        }
        if (li == lo) {
            printf("Case #%u: IMPOSSIBLE\n", t + 1);
            continue;
        }

        // TODO: Maybe a bsearch memcmp here to find only parts where the strings differ?
        bool skipping = false;
        size_t ndel = 0;
        for (size_t i = 0, o = 0; o < lo; ++o) {
            if (!skipping && (inp[i] == out[o])) {
                ++i;
            }
            else if (skipping && (inp[i] != out[o])) {
                if (dkri) printf("\tdel1 %zu %zu\n", i, o);
                ++ndel;
            }
            else if (inp[i] != out[o]) {
                if (dkri) printf("\tdel2 %zu %zu\n", i, o);
                skipping = true;
                ++ndel;
            }
            else if (skipping && (inp[i] == out[o])) {
                skipping = false;
                ++i;
            }

            // Input is done, chop of additional chars at end
            if (i == li && (o != (lo - 1)) && !skipping) {
                if (dkri) printf("\tdel3 %zu %zu %zu\n", i, o, lo);
                ndel += (lo - (o + 1));
                break;
            }
            // Is possible at all from the left chars to setisfy I=P
            if (skipping && ((li - i) > (lo - o)))
                break;
        }
        if (skipping)
            printf("Case #%u: IMPOSSIBLE\n", t + 1);
        else
            printf("Case #%u: %zu\n", t + 1, ndel);
    }
    return 0;
}