/*

1931. Painting a Grid With Three Different Colors

You are given two integers m and n. Consider an m x n grid where each cell is initially white. You can paint each cell red, green, or blue. All cells must be painted.

Return the number of ways to color the grid with no two adjacent cells having the same color. Since the answer can be very large, return it modulo 109 + 7.

 

*/

import java.util.*;

class Solution {
    static final int MOD = 1_000_000_007;
    List<int[]> patterns = new ArrayList<>();
    Map<Integer, List<Integer>> compat = new HashMap<>();

    public int colorTheGrid(int m, int n) {
        // 1. Generate all valid column colorings
        generatePatterns(m, new ArrayList<>());

        int size = patterns.size();
        // Map each pattern to its index for quick access
        Map<String, Integer> patternIndex = new HashMap<>();
        for (int i = 0; i < size; i++) {
            patternIndex.put(Arrays.toString(patterns.get(i)), i);
        }

        // 2. Build compatibility map
        for (int i = 0; i < size; i++) {
            compat.put(i, new ArrayList<>());
            for (int j = 0; j < size; j++) {
                if (isCompatible(patterns.get(i), patterns.get(j))) {
                    compat.get(i).add(j);
                }
            }
        }

        // 3. DP initialization
        long[] dp = new long[size];
        Arrays.fill(dp, 1);  // base case: one way to have each valid first column

        // 4. Fill dp for each column
        for (int col = 1; col < n; col++) {
            long[] nextDp = new long[size];
            for (int i = 0; i < size; i++) {
                for (int j : compat.get(i)) {
                    nextDp[j] = (nextDp[j] + dp[i]) % MOD;
                }
            }
            dp = nextDp;
        }

        // 5. Sum all valid ways for the last column
        long result = 0;
        for (long count : dp) result = (result + count) % MOD;
        return (int) result;
    }

    private void generatePatterns(int m, List<Integer> curr) {
        if (curr.size() == m) {
            patterns.add(curr.stream().mapToInt(i -> i).toArray());
            return;
        }
        for (int color = 0; color < 3; color++) {
            if (curr.size() == 0 || curr.get(curr.size() - 1) != color) {
                List<Integer> next = new ArrayList<>(curr);
                next.add(color);
                generatePatterns(m, next);
            }
        }
    }

    private boolean isCompatible(int[] p1, int[] p2) {
        for (int i = 0; i < p1.length; i++) {
            if (p1[i] == p2[i]) return false;
        }
        return true;
    }
}
