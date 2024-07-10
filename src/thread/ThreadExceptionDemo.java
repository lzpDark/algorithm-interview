package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author : lzp
 */
public class ThreadExceptionDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        new Thread(() -> {
            System.out.println("Runnable");
//                throw new RuntimeException("Runnable Exception"); // throw exception directly here
        }).start();
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            System.out.println("Callable");
            throw new RuntimeException("Callable Exception");
        });
        new Thread(futureTask).start();
        System.out.println(futureTask.get()); // throw exception while call get
        TimeUnit.SECONDS.sleep(10);
    }
}
