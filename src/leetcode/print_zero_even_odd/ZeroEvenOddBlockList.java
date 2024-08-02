import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.IntConsumer;

/**
 * @author lzp
 */
public class ZeroEvenOddBlockList {
    private int n;

    LinkedBlockingQueue<Integer> queue0 = new LinkedBlockingQueue<>();
    LinkedBlockingQueue<Integer> queue1 = new LinkedBlockingQueue<>();
    LinkedBlockingQueue<Integer> queue2 = new LinkedBlockingQueue<>();

    public ZeroEvenOddBlockList(int n) {
        this.n = n;
        try {
            queue0.put(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            queue0.take();
            printNumber.accept(0);
            if (i % 2 == 0) {
                queue2.put(i);
            } else {
                queue1.put(i);
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            printNumber.accept(queue2.take());
            queue0.put(0);
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            printNumber.accept(queue1.take());
            queue0.put(0);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ZeroEvenOddBlockList zeroEvenOdd = new ZeroEvenOddBlockList(20);
        IntConsumer intConsumer = System.out::println;
        new Thread(() -> {
            try {
                zeroEvenOdd.zero(intConsumer);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                zeroEvenOdd.even(intConsumer);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                zeroEvenOdd.odd(intConsumer);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        TimeUnit.SECONDS.sleep(4);
        System.out.println("Done");
    }
}
