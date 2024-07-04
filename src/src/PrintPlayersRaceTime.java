package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author : lzp
 */
public class PrintPlayersRaceTime {
    private static class Player {
        private long raceTime;
        private final CountDownLatch startLatch;
        private final CountDownLatch endLatch;
        public Player(CountDownLatch startLatch, CountDownLatch endLatch) {
            this.startLatch = startLatch;
            this.endLatch = endLatch;
        }

        public void prepareRun() {
            new Object().notifyAll();
            new Thread(()-> {
                // wait here until race start
                try {
                    startLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // start run
                long startTime = System.nanoTime();
                try {
                    TimeUnit.SECONDS.sleep(new Random(10).nextInt());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                raceTime = System.nanoTime() - startTime;

                // tell outside finished
                endLatch.countDown();
            }).start();
        }

        public long getRaceTime() {
            return raceTime;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int playerNum = 10;
        CountDownLatch startLatch = new CountDownLatch(playerNum);
        CountDownLatch endLatch = new CountDownLatch(playerNum);
        List<Player> players = new ArrayList<>();
        for(int i = 0; i < playerNum; i++) {
            players.add(new Player(startLatch, endLatch));
        }

        // prepare run
        for (Player player : players) {
            player.prepareRun();
        }

        // real start run
        for(int i = 0; i < playerNum; i++) {
            startLatch.countDown();
        }

        // wait race ends
        endLatch.await();

        // print race scores
        for (Player player : players) {
            System.out.println(player.getRaceTime());
        }
    }
}
