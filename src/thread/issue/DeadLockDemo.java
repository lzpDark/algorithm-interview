package thread.issue;

import java.util.concurrent.TimeUnit;

/**
 * @author : lzp
 */
public class DeadLockDemo {

    private static class A {
        private B b;

        public void setB(B b) {
            this.b = b;
        }

        public synchronized void callA() {

        }

        public synchronized void callB() {
            b.callB();
        }
    }

    private static class B {
        private A a;

        public void setA(A a) {
            this.a = a;
        }

        public synchronized void callA() {
            a.callA();
        }

        public synchronized void callB() {

        }
    }

    // https://www.jeremysong.cn/cn/jvm-gc-dump
    // `jps` get pid
    // `jstack -l <pid>` print thread information
    //
    // example:
    //"Thread-4":
    //        at thread.issue.DeadLockDemo$B.callB(DeadLockDemo.java:39)
    //        - waiting to lock <0x000000072fc16eb8> (a thread.issue.DeadLockDemo$B)
    //        at thread.issue.DeadLockDemo$A.callB(DeadLockDemo.java:22)
    //        - locked <0x000000072fc15d40> (a thread.issue.DeadLockDemo$A)
    //        at thread.issue.DeadLockDemo$$Lambda$14/0x0000000800066c40.run(Unknown Source)
    //        at java.lang.Thread.run(java.base@11.0.2/Thread.java:834)
    //
    //Found 1 deadlock.
    public static void main(String[] args) throws InterruptedException {
        A a = new A();
        B b = new B();
        a.setB(b);
        b.setA(a);
        for(int i = 0; i < 100; i++) {
            new Thread(a::callB).start();
            new Thread(b::callA).start();
        }
        System.out.println("Wait...");
        TimeUnit.SECONDS.sleep(10);
        System.out.println("Done");
    }

}
