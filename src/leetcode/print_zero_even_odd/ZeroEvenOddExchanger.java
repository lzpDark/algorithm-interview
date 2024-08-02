import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.function.IntConsumer;

/**
 * @author lzp
 */
public class ZeroEvenOddExchanger {


    private int n;
    private Exchanger<Integer> exchanger0 = new Exchanger<>();
    private Exchanger<Integer> exchanger1 = new Exchanger<>();
    private Exchanger<Integer> exchanger2 = new Exchanger<>();

    public ZeroEvenOddExchanger(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException, BrokenBarrierException {
        for (int i = 1; i <= n; i++) {
            printNumber.accept(0);
            if (i % 2 == 0) {
                exchanger2.exchange(0);
            } else {
                exchanger1.exchange(0);
            }
            exchanger0.exchange(0);
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException, BrokenBarrierException {
        for (int i = 2; i <= n; i += 2) {
            exchanger2.exchange(0);
            printNumber.accept(i);
            exchanger0.exchange(0);
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException, BrokenBarrierException {
        for (int i = 1; i <= n; i += 2) {
            exchanger1.exchange(0);
            printNumber.accept(i);
            exchanger0.exchange(0);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ZeroEvenOddExchanger zeroEvenOdd = new ZeroEvenOddExchanger(11);
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

    public static void main1(String[] args) throws InterruptedException {
        Exchanger<Integer> exchanger = new Exchanger<>();
        new Thread(() -> {
            Integer exchange = null;
            try {
                exchange = exchanger.exchange(11);
                System.out.println("exchanged: 11 -> " + exchange + ", threadId: " + Thread.currentThread().getId());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        TimeUnit.SECONDS.sleep(2);

        new Thread(() -> {
            try {
                Integer exchange = exchanger.exchange(22);
                System.out.println("exchanged: 22 -> " + exchange + ", threadId: " + Thread.currentThread().getId());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
