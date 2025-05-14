/*

3337. Total Characters in String After Transformations II

You are given a string s consisting of lowercase English letters, an integer t representing the number of transformations to perform, and an array nums of size 26. In one transformation, every character in s is replaced according to the following rules:

Replace s[i] with the next nums[s[i] - 'a'] consecutive characters in the alphabet. For example, if s[i] = 'a' and nums[0] = 3, the character 'a' transforms into the next 3 consecutive characters ahead of it, which results in "bcd".
The transformation wraps around the alphabet if it exceeds 'z'. For example, if s[i] = 'y' and nums[24] = 3, the character 'y' transforms into the next 3 consecutive characters ahead of it, which results in "zab".
Return the length of the resulting string after exactly t transformations.

Since the answer may be very large, return it modulo 109 + 7.

*/


import java.util.*;

class Solution {
    private static final int MOD = 1_000_000_007;
    private static final int SIZE = 26;

    public int lengthAfterTransformations(String s, int t, List<Integer> nums) {
        // Step 1: Initial frequency vector
        long[] freq = new long[SIZE];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Step 2: Build transformation matrix M
        long[][] M = new long[SIZE][SIZE];
        for (int j = 0; j < SIZE; j++) {
            int range = nums.get(j);
            for (int k = 1; k <= range; k++) {
                int i = (j + k) % SIZE;
                M[i][j] = (M[i][j] + 1) % MOD;
            }
        }

        // Step 3: Raise M to the power t
        long[][] Mt = matrixPower(M, t);

        // Step 4: Multiply Mt with frequency vector
        long[] result = new long[SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                result[i] = (result[i] + Mt[i][j] * freq[j]) % MOD;
            }
        }

        // Step 5: Sum final frequencies
        long total = 0;
        for (long val : result) {
            total = (total + val) % MOD;
        }

        return (int) total;
    }

    private long[][] matrixPower(long[][] mat, int power) {
        long[][] result = new long[SIZE][SIZE];
        // Initialize result as identity matrix
        for (int i = 0; i < SIZE; i++) {
            result[i][i] = 1;
        }

        while (power > 0) {
            if ((power & 1) == 1) {
                result = multiplyMatrices(result, mat);
            }
            mat = multiplyMatrices(mat, mat);
            power >>= 1;
        }

        return result;
    }

    private long[][] multiplyMatrices(long[][] A, long[][] B) {
        long[][] C = new long[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int k = 0; k < SIZE; k++) {
                for (int j = 0; j < SIZE; j++) {
                    C[i][j] = (C[i][j] + A[i][k] * B[k][j]) % MOD;
                }
            }
        }
        return C;
    }
}

/*

import java.util.*;

class Solution {
    private static final int MOD = 1_000_000_007;

    public int lengthAfterTransformations(String s, int t, List<Integer> nums) {
        int[] count = new int[26];
        
        // Step 1: Count initial frequency
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        // Step 2: Perform t transformations
        for (int step = 0; step < t; step++) {
            int[] newCount = new int[26];

            for (int i = 0; i < 26; i++) {
                int times = count[i];
                int range = nums.get(i);
                for (int j = 1; j <= range; j++) {
                    int index = (i + j) % 26;
                    newCount[index] = (newCount[index] + times) % MOD;
                }
            }

            count = newCount;
        }

        // Step 3: Sum all counts
        int totalLength = 0;
        for (int val : count) {
            totalLength = (totalLength + val) % MOD;
        }

        return totalLength;
    }
}
*/
