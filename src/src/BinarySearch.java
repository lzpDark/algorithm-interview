package src;

/**
 * @author : lzp
 */
public class BinarySearch {

    // if there are multiple item with same value,
    // return most left side index
    public int searchLeft(int[] nums, int val) {
        int left = 0;
        int right = nums.length - 1;
        while(left < right) {
            int mid = (left + right) / 2;
            if(nums[mid] >= val) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        if(nums[right] != val) {
            return -1;
        }
        return right;
    }

    // if there are multiple item with same value,
    // return most right side index
    public int searchRight(int[] nums, int val) {
        int left = 0;
        int right = nums.length - 1;
        while(left < right) {
            int mid = (left + right + 1) / 2;
            if(nums[mid] > val) {
                right = mid - 1;
            } else {
                left = mid;
            }
        }
        if(nums[left] != val) {
            return -1;
        }
        return left;
    }
}
