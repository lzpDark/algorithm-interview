import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.function.IntConsumer;

/**
 * @author lzp
 */
public class ZeroEvenOddCyclicBarrier {

    private int n;
    private CyclicBarrier cyclicBarrier0 = new CyclicBarrier(2);
    private CyclicBarrier cyclicBarrier1 = new CyclicBarrier(2);
    private CyclicBarrier cyclicBarrier2 = new CyclicBarrier(2);

    public ZeroEvenOddCyclicBarrier(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException, BrokenBarrierException {
        for (int i = 1; i <= n; i++) {
            printNumber.accept(0);
            if (i % 2 == 0) {
                cyclicBarrier2.await();
            } else {
                cyclicBarrier1.await();
            }
            cyclicBarrier0.await();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException, BrokenBarrierException {
        for (int i = 2; i <= n; i += 2) {
            cyclicBarrier2.await();
            printNumber.accept(i);
            cyclicBarrier0.await();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException, BrokenBarrierException {
        for (int i = 1; i <= n; i += 2) {
            cyclicBarrier1.await();
            printNumber.accept(i);
            cyclicBarrier0.await();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ZeroEvenOddCyclicBarrier zeroEvenOdd = new ZeroEvenOddCyclicBarrier(20);
        IntConsumer intConsumer = System.out::println;
        new Thread(() -> {
            try {
                zeroEvenOdd.zero(intConsumer);
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                zeroEvenOdd.even(intConsumer);
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                zeroEvenOdd.odd(intConsumer);
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }).start();

        TimeUnit.SECONDS.sleep(4);
        System.out.println("Done");
    }
}
