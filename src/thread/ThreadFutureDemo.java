package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author : lzp
 */
public class ThreadFutureDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        List<FutureTask<Integer>> taskList = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for(int i = 0; i < 10; i++) {
            FutureTask<Integer> task = new FutureTask<>(() -> {
                int result = new Random().nextInt(3) + 1;
                TimeUnit.SECONDS.sleep(result);
                countDownLatch.countDown();
                return result;
            });
            taskList.add(task);
            new Thread(task).start();
        }

        // wait all task finished
        if(!countDownLatch.await(5, TimeUnit.SECONDS)) {
            // time elapse but count not 0
            System.out.println("Error wait result");
        }
        for (FutureTask<Integer> futureTask : taskList) {
            System.out.println(futureTask.get());
        }
    }
}
