package acwing;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Java template for: https://www.acwing.com/
 * @author : lzp
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = IntStream.range(0, n).map((i) -> scanner.nextInt()).toArray();


        System.out.println(n);
        for (int num : nums) {
            System.out.println(num);
        }
    }
}
