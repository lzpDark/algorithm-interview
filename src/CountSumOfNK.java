/**
 * @author : lzp
 */
public class CountSumOfNK {
    // give integers n,k
    // generate k groups numbers:
    // n, n+1, n+2 ... n+k-1
    // get sum
    public int calculate(int n, int k) {
        int sum = 0;
        int current = 0;
        for(int i = 0; i < k; i++) {
            if(i == 0) {
                current = n;
            } else {
                current++;
            }
            sum += current;
        }
        return sum;
    }
}
