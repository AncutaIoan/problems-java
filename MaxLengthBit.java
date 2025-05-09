/*
1239. Maximum Length of a Concatenated String with Unique Characters

You are given an array of strings arr. A string s is formed by the concatenation of a subsequence of arr that has unique characters.

Return the maximum possible length of s.

A subsequence is an array that can be derived from another array by deleting some or no elements without changing the order of the remaining elements.

 

Example 1:

Input: arr = ["un","iq","ue"]
Output: 4
Explanation: All the valid concatenations are:
- ""
- "un"
- "iq"
- "ue"
- "uniq" ("un" + "iq")
- "ique" ("iq" + "ue")
Maximum length is 4.
Example 2:

Input: arr = ["cha","r","act","ers"]
Output: 6
Explanation: Possible longest valid concatenations are "chaers" ("cha" + "ers") and "acters" ("act" + "ers").
Example 3:

Input: arr = ["abcdefghijklmnopqrstuvwxyz"]
Output: 26
Explanation: The only string in arr has all 26 characters.
 

Constraints:

1 <= arr.length <= 16
1 <= arr[i].length <= 26
arr[i] contains only lowercase English letters.


*/

class Solution {
    public int maxLength(List<String> arr) {
        List<int[]> masks = new ArrayList<>();

        // Preprocess: convert strings to bitmasks, skip invalid strings
        for (String s : arr) {
            int mask = 0;
            boolean isValid = true;
            for (char c : s.toCharArray()) {
                int bit = c - 'a';
                if ((mask & (1 << bit)) != 0) {
                    isValid = false; // duplicate character in string
                    break;
                }
                mask |= 1 << bit;
            }
            if (isValid) {
                masks.add(new int[]{mask, s.length()});
            }
        }

        // Start backtracking
        return backtrack(masks, 0, 0, 0);
    }

    private int backtrack(List<int[]> masks, int index, int currentMask, int length) {
        int max = length;

        for (int i = index; i < masks.size(); i++) {
            int nextMask = masks.get(i)[0];
            int nextLen = masks.get(i)[1];

            // Check if current string can be added (no overlap)
            if ((currentMask & nextMask) == 0) {
                max = Math.max(max, backtrack(masks, i + 1, currentMask | nextMask, length + nextLen));
            }
        }

        return max;
    }
}
