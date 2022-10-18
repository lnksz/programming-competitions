// len >= 7
// A-Z
// a-z
// 0-9
// [#@*&]

#include <iostream>
#include <string>
#include <cstdio>
#include <deque>
#include <vector>
#include <numeric>

using namespace std;


int main(int argc, char **) {
    const bool dkri = (argc > 1);

    size_t ntests;
    size_t naudio, nvideo;
    unsigned int nchoice;
    // vector<unsigned int> audio, video, choice;
    deque<unsigned int> audio, video, choice;

    unsigned long long gain;

    cin >> ntests;
    for (auto t = 0u; t < ntests; ++t)
    {
        gain = 0;
        cin >> naudio;
        // audio.reserve(naudio);
        for (auto i = 0u; i < naudio; ++i)
        {
            unsigned int a;
            cin >> a;
            audio.push_back(a);
        }
        cin >> nvideo;
        // video.reserve(nvideo);
        for (auto i = 0u; i < nvideo; ++i)
        {
            unsigned int v;
            cin >> v;
            video.push_back(v);
        }
        cin >> nchoice;
        // choice.reserve(nchoice);

        printf("Case #%u: ", t + 1);
        if (nchoice == 1) {
            printf("%u\n", max(max(video.front(), video.back()), max(audio.front(), audio.back())));
            continue;
        }
        size_t alim = (naudio > nchoice) ? nchoice : naudio; 
        size_t vlim = (nvideo > nchoice) ? nchoice : nvideo;

        // unsigned long long Afront, Aback, Vfront, Vback;
        // Afront = accumulate(audio.begin(), audio.begin() + alim, 0);
        // Aback = accumulate(audio.rbegin(), audio.rbegin() + alim, 0);
        // Vfront = accumulate(video.begin(), video.begin() + vlim, 0);
        // Vback = accumulate(video.rbegin(), video.rbegin() + vlim, 0);
        // cout << Afront << " " << Aback << " " << Vfront << " " << Vback;

        gain = 0;
        size_t o1, o2, o3, o4;
        o1 = o2 = o3 = o4 = 0;
        auto A = audio;
        auto V = video;

        for (nchoices)
            if (!A.empty())
                A.pop_front();


        printf("%llu\n", gain);

        audio.clear();
        video.clear();
    }
    return 0;
}