#### 题目描述
在有序的数组里通过二分算法找到目标数值，返回下标，找不到的话返回-1

#### 代码
```java
class Main {
    public int binarySearch(int[] nums, int val) {
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
        if(nums[left] != val) {
            return -1;
        }
        return right;
    }
}
```