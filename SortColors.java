class Solution {
    public void sortColors(int[] nums) {
        int low = 0, mid = 0, high = nums.length - 1;

        while (mid <= high) {
            if (nums[mid] == 0) {
                // swap nums[low] and nums[mid], then increment both
                int temp = nums[low];
                nums[low++] = nums[mid];
                nums[mid++] = temp;
            } else if (nums[mid] == 1) {
                mid++;
            } else {
                // swap nums[mid] and nums[high], only decrement high
                int temp = nums[high];
                nums[high--] = nums[mid];
                nums[mid] = temp;
            }
        }
    }
}
