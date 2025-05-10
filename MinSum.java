/**
2918. Minimum Equal Sum of Two Arrays After Replacing Zeros
You are given two arrays nums1 and nums2 consisting of positive integers.

You have to replace all the 0's in both arrays with strictly positive integers such that the sum of elements of both arrays becomes equal.

Return the minimum equal sum you can obtain, or -1 if it is impossible.

 

Example 1:

Input: nums1 = [3,2,0,1,0], nums2 = [6,5,0]
Output: 12
Explanation: We can replace 0's in the following way:
- Replace the two 0's in nums1 with the values 2 and 4. The resulting array is nums1 = [3,2,2,1,4].
- Replace the 0 in nums2 with the value 1. The resulting array is nums2 = [6,5,1].
Both arrays have an equal sum of 12. It can be shown that it is the minimum sum we can obtain.
Example 2:

Input: nums1 = [2,0,2,0], nums2 = [1,4]
Output: -1
Explanation: It is impossible to make the sum of both arrays equal.
 

Constraints:

1 <= nums1.length, nums2.length <= 105
0 <= nums1[i], nums2[i] <= 106

*/

class Solution {

    /**
     * Computes the minimum sum possible between two given arrays after making sure
     * that none of the elements in the arrays are less than one.
     * It returns -1 if it is impossible to make both sums equal without the use of zeros.
     *
     * @param nums1 the first array of integers
     * @param nums2 the second array of integers
     * @return the minimum sum possible or -1 if it can't be done
     */
    public long minSum(int[] nums1, int[] nums2) {
        long sum1 = 0; // Initialize sum for the first array
        long sum2 = 0; // Initialize sum for the second array
        boolean hasZero = false; // Flag to check for presence of zero in nums1
      
        // Iterate over the first array
        for (int value : nums1) {
            hasZero |= value == 0; // Set the flag if zero is found
            sum1 += Math.max(value, 1); // Ensure that each value contributes at least 1 to the sum
        }
      
        // Iterate over the second array
        for (int value : nums2) {
            sum2 += Math.max(value, 1); // Ensure that each value contributes at least 1 to the sum
        }
      
        // If the sum of the first array is greater, call the function again with reversed parameters
        if (sum1 > sum2) {
            return minSum(nums2, nums1);
        }
      
        // If the sums are equal, return the sum of the first array
        if (sum1 == sum2) {
            return sum1;
        }
      
        // If there is a zero in the first array and the sums are not equal, returning the sum of the
        // second array is valid; otherwise, return -1 as it is impossible to make sums equal.
        return hasZero ? sum2 : -1;
    }
}
