package thread;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author : lzp
 */
public class ExecutorCompletionServiceDemo {

    private final ExecutorService executorService;

    public ExecutorCompletionServiceDemo(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void executeTask(List<String> tasks) throws InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis();
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(executorService);
        for (String task : tasks) {
            completionService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    TimeUnit.SECONDS.sleep(Integer.parseInt(task));
                    return task + " executed.";
                }
            });
        }
        for (int i = 0; i < tasks.size(); i++) {
            Future<String> future = completionService.take();
            System.out.println(future.get());
        }
        System.out.println((System.currentTimeMillis() - startTime) + "ms");

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new ExecutorCompletionServiceDemo(Executors.newFixedThreadPool(10))
                .executeTask(List.of("1", "2", "4", "3", "1", "5"));
    }
}
