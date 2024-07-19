package thread.cancel;

import java.util.concurrent.TimeUnit;

/**
 * @author : lzp
 */
public class CancelThreadDemo {

    private static class TestThread extends Thread {
        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                System.out.println("Timestamp:" + System.currentTimeMillis());
            }
            System.out.println("Finished");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestThread testThread = new TestThread();
        testThread.start();
        TimeUnit.SECONDS.sleep(1);

        testThread.interrupt();
        TimeUnit.SECONDS.sleep(1);
    }
}
