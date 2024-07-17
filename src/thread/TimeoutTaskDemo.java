package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author : lzp
 */
public class TimeoutTaskDemo {

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final Callable<String> task = new Callable<String>() {
        @Override
        public String call() throws Exception {
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException ignore) {
            }
            return "done";
        }
    };
    private final Callable<String> longTask = new Callable<String>() {
        @Override
        public String call() throws Exception {
            try {
                TimeUnit.SECONDS.sleep(6);
            } catch (InterruptedException ignore) {
            }
            return "done longTask";
        }
    };

    private void shutdown() {
        executorService.shutdown();
    }

    public void defaultResultDemo() {
        System.out.println("default demo:");
        Future<String> future = executorService.submit(task);
        String res = "defaultRes";
        try {
            res = future.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException ignore) {
        } catch (TimeoutException e) {
            future.cancel(true);
        }
        System.out.println(res);
    }

    public void successResultDemo() {
        System.out.println("success demo:");
        Future<String> future = executorService.submit(task);
        String res = "defaultRes";
        try {
            res = future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException ignore) {
        } catch (TimeoutException e) {
            future.cancel(true);
        }
        System.out.println(res);
    }

    public void listDemo() throws InterruptedException {
        System.out.println("listDemo:");
        List<Future<String>> futures = executorService.invokeAll(List.of(task, task, longTask),
                5, TimeUnit.SECONDS);
        List<String> result = new ArrayList<>();
        for (Future<String> future : futures) {
            try {
                result.add(future.get());
            } catch (ExecutionException e) {
                result.add("failed execute");
            } catch (CancellationException e) {
                result.add("timeout cancel");
            }
        }
        for (String s : result) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TimeoutTaskDemo demo = new TimeoutTaskDemo();
        demo.defaultResultDemo();
        demo.successResultDemo();
        demo.listDemo();
        demo.shutdown();
    }



}
