package thread.cancel;

import java.util.concurrent.*;

/**
 * @author : lzp
 */
public class CancelBlockingQueueDemo {

    private static class Producer {

        private final BlockingQueue<String> queue;

        public Producer(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        public void startProduce() {
            while (true) {
                try {
                    queue.put("" + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Finish produce.");
                    break;
                }
            }
        }
    }

    private static class Consumer {

        private final BlockingQueue<String> queue;

        public Consumer(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        public void startConsume() {
            while (true) {
                try {
                    String job = queue.take();
                    System.out.println("Process job:" + job);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Finish consume.");
                    break;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Thread consumeThread = new Thread(()->{
            Consumer consumer = new Consumer(queue);
            consumer.startConsume();
        });
        consumeThread.start();
        Thread produceThread = new Thread(()->{
            Producer producer = new Producer(queue);
            producer.startProduce();
        });
        produceThread.start();

        TimeUnit.MILLISECONDS.sleep(100);
        consumeThread.interrupt();
        produceThread.interrupt();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("Done");
    }
}
