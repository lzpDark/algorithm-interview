import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * @author lzp
 */
public class ZeroEvenOddReentrantLock1 {


    private int n;
    private volatile int flag = 0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public ZeroEvenOddReentrantLock1(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            lock.lock();
            printNumber.accept(0);
            flag++;
            condition.signalAll();
            condition.await();
            lock.unlock();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            lock.lock();
            while (flag % 4 != 3) {
                condition.await();
            }
            printNumber.accept(i);
            flag++;
            condition.signalAll();
            lock.unlock();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            lock.lock();
            while (flag % 4 != 1) {
                condition.await();
            }
            printNumber.accept(i);
            flag++;
            condition.signalAll();
            lock.unlock();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ZeroEvenOddReentrantLock1 zeroEvenOdd = new ZeroEvenOddReentrantLock1(23);
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

    public static void main1(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        new Thread(() -> {
            while (true) {
                try {
                    lock.lock();
                    condition1.await();
                    System.out.println("1," + System.currentTimeMillis());
                    condition2.signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    lock.lock();
                    condition2.await();
                    System.out.println("2," + System.currentTimeMillis());
                    condition1.signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        TimeUnit.SECONDS.sleep(2);
        try {
            lock.lock();
            condition1.signalAll();
        } finally {
            lock.unlock();
        }
        TimeUnit.SECONDS.sleep(20);
        System.out.println("Done");
    }
}
