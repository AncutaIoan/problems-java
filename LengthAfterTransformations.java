/*
3335. Total Characters in String After Transformations I

You are given a string s and an integer t, representing the number of transformations to perform. In one transformation, every character in s is replaced according to the following rules:

If the character is 'z', replace it with the string "ab".
Otherwise, replace it with the next character in the alphabet. For example, 'a' is replaced with 'b', 'b' is replaced with 'c', and so on.
Return the length of the resulting string after exactly t transformations.

Since the answer may be very large, return it modulo 109 + 7.



*/

class Solution {
 
  private static final int MOD = 1_000_000_007;

    public int lengthAfterTransformations(String s, int t) {
        long[] count = new long[26];

        // Initialize count from the input string
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        for (int step = 0; step < t; step++) {
            long[] next = new long[26];

            for (int i = 0; i < 26; i++) {
                if (i == 25) { // 'z'
                    next[0] = (next[0] + count[25]) % MOD;     // 'a'
                    next[1] = (next[1] + count[25]) % MOD;     // 'b'
                } else {
                    next[i + 1] = (next[i + 1] + count[i]) % MOD;
                }
            }

            count = next;
        }

        // Sum total length
        long result = 0;
        for (long c : count) {
            result = (result + c) % MOD;
        }

        return (int) result;
    }
}
