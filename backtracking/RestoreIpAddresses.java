/*
93. Restore IP Addresses

A valid IP address consists of exactly four integers separated by single dots. Each integer is between 0 and 255 (inclusive) and cannot have leading zeros.

For example, "0.1.2.201" and "192.168.1.1" are valid IP addresses, but "0.011.255.245", "192.168.1.312" and "192.168@1.1" are invalid IP addresses.
Given a string s containing only digits, return all possible valid IP addresses that can be formed by inserting dots into s. You are not allowed to reorder or remove any digits in s. You may return the valid IP addresses in any order.

 

Example 1:

Input: s = "25525511135"
Output: ["255.255.11.135","255.255.111.35"]
Example 2:

Input: s = "0000"
Output: ["0.0.0.0"]
Example 3:

Input: s = "101023"
Output: ["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
 

Constraints:

1 <= s.length <= 20
s consists of digits only.


*/
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> restoreIpAddresses(String s) {
        List<List<String>> result = new ArrayList<>();
        backtrack(s, 0, new ArrayList<>(), result);

        List<String> flattened = new ArrayList<>();
        for (List<String> innerList : result) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < innerList.size(); i++) {
                sb.append(innerList.get(i));
                if (i < innerList.size() - 1) {
                    sb.append(".");
                }
            }
            flattened.add(sb.toString());
        }

        return flattened;
    }

    private void backtrack(String s, int start, List<String> path, List<List<String>> result) {
        if (path.size() > 4) return;
        if (start == s.length() && path.size() == 4) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int end = start + 1; end <= s.length() && end <= start + 3; end++) {
            String substring = s.substring(start, end);
            if (isValidBitRange(substring)) {
                path.add(substring);
                backtrack(s, end, path, result);
                path.remove(path.size() - 1); // backtrack
            }
        }
    }

    private boolean isValidBitRange(String s) {
        if (s.length() > 1 && s.charAt(0) == '0') return false; // avoid "01", "001"
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res = res * 10 + (s.charAt(i) - '0');
        }
        return res >= 0 && res <= 255;
    }
}
