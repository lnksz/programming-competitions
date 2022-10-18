#include <iostream>
#include <string>
#include <cstdio>
#include <cmath>
#include <cstring>
#include <vector>
#include <algorithm>
#include <numeric>

using namespace std;

//                                                          -1          0                 1
//                                                           0          1                 2
//                                                           1          4                 3
void tank_per_level(const vector<vector<int>> &listofcons, int parent, int current, size_t counting_level, vector<size_t> &levels) {
    const auto & connections = listofcons[current];
    // Don't count the parent, root doesn't have parent
    if (parent < 0)
        levels[counting_level] += connections.size();
    else
        levels[counting_level] += connections.size() - 1;

    for (auto con : connections) {
        // Check each connection accept parent (don't go backwards in tree)
        if (con == parent)
            continue;
        if (listofcons[con].size() > 1)
            tank_per_level(listofcons, current, con, counting_level + 1, levels);
    }
}

int main(int argc, char **) {
    size_t ntests;

    std::cin >> ntests;
    size_t ntank, nliter;
    for (auto t = 0u; t < ntests; ++t)
    {
        std::printf("Case #%u: ", t + 1);
        std::cin >> ntank;
        std::cin >> nliter;
        vector<vector<int>> connections(ntank); // Maps TankNum - 1 : list of children
        vector<size_t> levels(ntank, 0); // contains the number of tanks per level
        vector<size_t> lsums(ntank, 0); // partial sums over the levels
        for (auto edge = 1u; edge < ntank; ++edge)
        {
            int id1;
            int id2;
            std::cin >> id1;
            std::cin >> id2;

            connections[id1-1].emplace_back(id2-1);
            connections[id2-1].emplace_back(id1-1);
        }
        for (auto q = 0u; q < nliter; ++q){
            int id;
            std::cin >>id;
        }

        levels[0] = 1;
        if (ntank > 1)
            tank_per_level(connections, -1, 0, 1, levels);

        partial_sum(levels.begin(), levels.end(), lsums.begin());
        size_t filled = 0;
        for (auto l = 0u; l < levels.size(); ++l){
            if (lsums[l] > nliter)
                break;
            filled += levels[l];
        }
        printf("%zu\n", filled);
    }
    return 0;
}