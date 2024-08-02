import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.function.IntConsumer;

/**
 * @author lzp
 */
public class ZeroEvenOddLockSupport {

    private int n;
    private volatile int flag = 0;
    Thread[] threads = new Thread[3];

    public ZeroEvenOddLockSupport(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        threads[0] = Thread.currentThread();
        for (int i = 1; i <= n; i++) {
            while (flag % 2 != 0) {
                LockSupport.park();
            }
            printNumber.accept(0);
            flag++;
            if (i % 2 == 0) {
                LockSupport.unpark(threads[2]);
            } else {
                LockSupport.unpark(threads[1]);
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        threads[2] = Thread.currentThread();
        for (int i = 2; i <= n; i += 2) {
            while (flag % 4 != 3) {
                LockSupport.park();
            }
            printNumber.accept(i);
            flag++;
            LockSupport.unpark(threads[0]);
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        threads[1] = Thread.currentThread();
        for (int i = 1; i <= n; i += 2) {
            while (flag % 4 != 1) {
                LockSupport.park();
            }
            printNumber.accept(i);
            flag++;
            LockSupport.unpark(threads[0]);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ZeroEvenOddLockSupport zeroEvenOdd = new ZeroEvenOddLockSupport(22);
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
