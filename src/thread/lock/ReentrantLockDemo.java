package thread.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lzp
 */
public class ReentrantLockDemo {

    ReentrantLock lockA = new ReentrantLock();
    ReentrantLock lockB = new ReentrantLock();

    public void callAB() throws InterruptedException {
        try {
            if(lockA.tryLock(1, TimeUnit.MICROSECONDS)) {
//                System.out.println("call A 1st...");
                try {
                    if(lockB.tryLock(1, TimeUnit.MICROSECONDS)) {
//                        System.out.println("call B 2nd...");
                    }
                } finally {
                    lockB.unlock();
                }
            }
        } finally {
            lockA.unlock();
        }
    }

    public void callBA() throws InterruptedException {
        try {
            if(lockB.tryLock(1, TimeUnit.MICROSECONDS)) {
//                System.out.println("call B 1st...");
                try {
                    if(lockA.tryLock(1, TimeUnit.MICROSECONDS)) {
//                        System.out.println("call A 2nd...");
                    }
                } finally {
                    lockA.unlock();
                }
            }
        }  finally {
            lockB.unlock();
        }
    }

    public void lockAB() {
        lockA.lock();
//        System.out.println("call A");
        lockB.lock();
//        System.out.println("call B");
        lockA.unlock();
        lockB.unlock();
    }
    public void lockBA() {
        lockB.lock();
//        System.out.println("call B");
        lockA.lock();
//        System.out.println("call A");
        lockB.unlock();
        lockA.unlock();
    }

    ExecutorService service1 = Executors.newFixedThreadPool(100);
    ExecutorService service2 = Executors.newFixedThreadPool(100);

    private void testNonDeadLock() {
        for(int i = 0; i < 300; i++) {
            service1.submit(()->{
                try {
                    callAB();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        for(int i = 0; i < 300; i++) {
            service2.submit(()->{
                try {
                    callBA();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void testDeadLock() {
        for(int i = 0; i < 300; i++) {
            service1.submit(()->{
                lockAB();
            });
        }
        for(int i = 0; i < 300; i++) {
            service1.submit(()->{
                lockBA();
            });
        }
    }

    private void waitFinish() throws InterruptedException {
        service1.shutdown();
        service2.shutdown();
        System.out.println("service1 finished: " +
                service1.awaitTermination(10, TimeUnit.SECONDS));
        System.out.println("service2 finished: " +
                service2.awaitTermination(10, TimeUnit.SECONDS));
    }

    public static void main(String[] args) throws InterruptedException {
        Thread test1 = new Thread(()->{
            ReentrantLockDemo demo = new ReentrantLockDemo();
            demo.testDeadLock();
            try {
                demo.waitFinish();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        test1.start();

        Thread test2 = new Thread(()->{
            ReentrantLockDemo demo = new ReentrantLockDemo();
            demo.testNonDeadLock();
            try {
                demo.waitFinish();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        test2.start();

        test1.join(12000);
        test2.join(12000);

    }
}
