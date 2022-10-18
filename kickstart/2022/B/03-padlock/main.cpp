#include <iostream>
#include <string>
#include <vector>
#include <cstdio>
#include <algorithm>

using namespace std;

int main(int argc, char **) {
    const bool dkri = (argc > 1);

    size_t ntests;
    unsigned int ndials;
    unsigned int dialmax;
    unsigned int iters;
    vector<unsigned int> dials {};
    dials.reserve(400);

    cin >> ntests;
    for (auto t = 0u; t < ntests; ++t)
    {
        cin >> ndials;
        cin >> dialmax;
        iters = 0;

        if (dkri) cout << ndials << ' ' << dialmax << '\n';

        for (auto i = 0u; i < ndials; ++i)
        {
            unsigned int v;
            cin >> v;
            dials.push_back(v);
            if (dkri) cout << ' ' << v;
        }
        if (dkri) cout << '\n';

        if (!all_of(dials.cbegin(), dials.cend(), [](auto e){return e == 0;}))
        {
            printf("Case #%u: %u\n", t + 1, 0);
            if (t + 1 < ntests) dials.clear();
            continue;
        }

        printf("Case #%u: %u\n", t + 1, iters);
        if (t + 1 < ntests) dials.clear();
    }
    return 0;
}