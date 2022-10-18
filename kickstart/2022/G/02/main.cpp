#include <iostream>
#include <string>
#include <cstdio>
#include <cstring>
#include <vector>
#include <algorithm>
#include <cmath>

using namespace std;

// work with pow2 distances. They are positive and maintain the relation
static inline double nearest_point(int x, int y, int radius)
{
    auto orig_dist = fabs(sqrt(x * x + y * y));
    if (isless(orig_dist, (radius * 1.0)))
        return 0.0; // Stone in the origo of the house
    else
        return orig_dist - (radius * 1.0); // nearest point
}

static inline bool is_inhouse(float house, double point)
{
    return islessequal(point, house);
}

int main(int argc, char **)
{
    size_t ntests;
    size_t Rs, Rh;
    size_t nred, nyellow;

    cin >> ntests;
    for (auto t = 0u; t < ntests; ++t)
    {
        // get coordinate
        // for reds:
        //  is it in the house (radius)? then save it dist / nearest point
        // sort(reds)
        // for yellow:
        //  is it in the house (radius) -> save dist / nearest point
        // sort(yellows)
        // mr = min(reds) if none, inf
        // my = min(yellows) if none, inf
        // max(mr, my) -> loser, 0 points
        // partition point in winner until other-min gives score

        vector<double> reds;
        vector<double> yellows;
        printf("Case #%u: ", t + 1);
        cin >> Rs;
        cin >> Rh;
        cin >> nred;
        reds.reserve(nred);
        for (auto i = 0u; i < nred; ++i)
        {
            int x, y;
            cin >> x;
            cin >> y;
            auto np = nearest_point(x, y, Rs);
            if (is_inhouse(Rh, np)) reds.emplace_back(np);
        }
        sort(reds.begin(), reds.end());
        cin >> nyellow;
        yellows.reserve(nyellow);
        for (auto i = 0u; i < nyellow; ++i)
        {
            int x, y;
            cin >> x;
            cin >> y;
            auto np = nearest_point(x, y, Rs);
            if (is_inhouse(Rh, np)) yellows.emplace_back(np);
        }
        sort(yellows.begin(), yellows.end());

        auto red_pnts = reds.size();
        auto ylw_pnts = yellows.size();

        if (!(red_pnts && ylw_pnts))
        {
            printf("%zu %zu\n", red_pnts, ylw_pnts);
            continue;
        }

        // Maybe some "loser" stone is between winning stones
        auto red_min = red_pnts ? reds[0] : Rh;
        auto ylw_min = ylw_pnts ? yellows[0] : Rh;

        if (red_min < ylw_min)
        {
            ylw_pnts = 0;
            auto lower = lower_bound(reds.begin(), reds.end(), ylw_min);
            red_pnts = distance(reds.begin(), lower);
        }
        else
        {
            red_pnts = 0;
            auto lower = lower_bound(yellows.begin(), yellows.end(), red_min);
            ylw_pnts = distance(yellows.begin(), lower);
        }

        printf("%zu %zu\n", red_pnts, ylw_pnts);
    }
    return 0;
}