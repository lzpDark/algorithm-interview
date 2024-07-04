#### ��Ŀ����
�������������ͨ�������㷨�ҵ�Ŀ����ֵ�������±꣬�Ҳ����Ļ�����-1

#### ����
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