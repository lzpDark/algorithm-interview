package thread.cancel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author : lzp
 */
public class CancelFutureDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<String>> futures = new ArrayList<>();
        for(int i = 0; i < 1000; i++) {
            futures.add(executorService.submit(()-> "" + System.currentTimeMillis()));
        }
        TimeUnit.MILLISECONDS.sleep(30);
        List<Runnable> cancelledTask = executorService.shutdownNow();
        futures.get(futures.size() - 1).cancel(false);
        System.out.println("Cancelled waiting task: " + cancelledTask.size());
        System.out.println("Done");
    }
}
