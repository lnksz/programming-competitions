// len >= 7
// A-Z
// a-z
// 0-9
// [#@*&]

#include <iostream>
#include <string>
#include <cstdio>
#include <cmath>

using namespace std;

bool isspec(int c) {
    return (c == '#' || c == '@' || c == '*' || c == '&');
}

int main(int argc, char **) {
    const bool dkri = (argc > 1);

    size_t ntests;
    size_t l, wrote;
    string s;
    string extra;

    cin >> ntests;
    for (auto t = 0u; t < ntests; ++t)
    {
        cin >> l;
        cin >> s;

        printf("Case #%u: ", t + 1);
        if (dkri) printf("(%zu - %s)", l, s.c_str());
        wrote = 0;

        bool has_cap = false;
        bool has_low = false;
        bool has_dig = false;
        bool has_spc = false;
        bool has_chr = false;

        for (auto &c : s)
        {
            printf("%c", c);
            if (!has_dig && isdigit(c)) {
                has_dig = true;
                ++wrote;
                continue;
            }

            if (!has_cap && isupper(c)) {
                has_cap = true;
                ++wrote;
                continue;
            }

            if (!has_low && islower(c)) {
                has_low = true;
                ++wrote;
                continue;
            }

            if (!has_spc && isspec(c)) {
                has_spc = true;
                ++wrote;
                continue;
            }

            if (has_dig && has_cap && has_low && has_spc)
            {
                if (++wrote < l)
                    printf("%s", &s.c_str()[wrote]);
                wrote = l;
                break;
            }

            ++wrote;
        }

        if (!has_dig) {
            printf("1");
            ++wrote;
        }
        if (!has_cap) {
            printf("A");
            ++wrote;
        }
        if (!has_low) {
            printf("a");
            ++wrote;
        }
        if (!has_spc) {
            printf("#");
            ++wrote;
        }
        
        if (wrote < 7) {
            for (int i = 7 - wrote; i > 0; --i)
                printf("%c", 'a');
        }

        puts("");
    }
    return 0;
}