import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.IntConsumer;

/**
 * @author lzp
 */
public class ZeroEvenOddSemaphore {
    private int n;

    Semaphore semaphore0 = new Semaphore(0);
    Semaphore semaphore1 = new Semaphore(0);
    Semaphore semaphore2 = new Semaphore(0);

    public ZeroEvenOddSemaphore(int n) {
        this.n = n;
        semaphore0.release();
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            semaphore0.acquire();
            printNumber.accept(0);
            if (i % 2 == 0) {
                semaphore2.release();
            } else {
                semaphore1.release();
            }
        }

    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            semaphore2.acquire();
            printNumber.accept(i);
            semaphore0.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            semaphore1.acquire();
            printNumber.accept(i);
            semaphore0.release();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ZeroEvenOddSemaphore zeroEvenOdd = new ZeroEvenOddSemaphore(20);
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
