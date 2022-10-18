// len >= 7
// A-Z
// a-z
// 0-9
// [#@*&]

#include <iostream>
#include <string>
#include <cstdio>
#include <cmath>
#include <cstring>
#include <vector>
#include <algorithm>

using namespace std;

bool isspec(int c) {
    return (c == '#' || c == '@' || c == '*' || c == '&');
}

int main(int argc, char **) {
    const bool dkri = (argc > 1);

    size_t ntests;
    size_t nfabric;
    struct FabNum {
        int durab;
        int uuid;
        FabNum(int d, int id) : durab(d), uuid(id) {}
    };
    struct FabStr {
        string color;
        int uuid;
        FabStr(string s, int id) : color(std::move(s)), uuid(id) {}
    };

    vector<FabStr> colors;
    vector<FabNum> durabs;

    std::cin >> ntests;
    for (auto t = 0u; t < ntests; ++t)
    {
        printf("Case #%u: ", t + 1);
        std::cin >> nfabric;
        durabs.reserve(nfabric);
        colors.reserve(nfabric);
        for (auto f = 0u; f < nfabric; ++f)
        {
            int dur;
            int id;
            string color;
            std::cin >> color;
            std::cin >> dur;
            std::cin >> id;
            
            colors.emplace_back(color, id);
            durabs.emplace_back(dur, id);
        }

        std::sort(durabs.begin(), durabs.end(), [](const FabNum &a, const FabNum &b){
            if (a.durab == b.durab)
                return (a.uuid < b.uuid);
            else
                return (a.durab < b.durab);
        });
        std::sort(colors.begin(), colors.end(), [](const FabStr &a, const FabStr &b){
            auto pred = a.color.compare(b.color);
            if (pred == 0)
                return (a.uuid < b.uuid);
            else
                return (pred < 0);
        });

        size_t same = 0;
        for (auto i = 0u; i < nfabric; ++i) {
            if (colors[i].uuid == durabs[i].uuid)
                ++same;
        }

        colors.clear();
        durabs.clear();
        printf("%zu\n", same);
    }
    return 0;
}