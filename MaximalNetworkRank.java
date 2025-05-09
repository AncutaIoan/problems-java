/*
1615. Maximal Network Rank


There is an infrastructure of n cities with some number of roads connecting these cities. Each roads[i] = [ai, bi] indicates that there is a bidirectional road between cities ai and bi.

The network rank of two different cities is defined as the total number of directly connected roads to either city. If a road is directly connected to both cities, it is only counted once.

The maximal network rank of the infrastructure is the maximum network rank of all pairs of different cities.

Given the integer n and the array roads, return the maximal network rank of the entire infrastructure.

*/

class Solution {
    public int maximalNetworkRank(int n, int[][] roads) {
        int[] degree = new int[n]; // Number of roads connected to each city
        boolean[][] connected = new boolean[n][n]; // Direct connection map

        // Populate degree and connection information
        for (int[] road : roads) {
            int a = road[0], b = road[1];
            degree[a]++;
            degree[b]++;
            connected[a][b] = true;
            connected[b][a] = true;
        }

        int maxRank = 0;

        // Evaluate network rank for every pair of cities
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int rank = degree[i] + degree[j];
                if (connected[i][j]) {
                    rank--; // Subtract one if there is a direct connection
                }
                maxRank = Math.max(maxRank, rank);
            }
        }

        return maxRank;
    }
}
