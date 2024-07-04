#### 原始题目描述

有10个运动员，打印出他们的比赛时间

#### 代码

使用`countDownLatch`解决这里的场景，
注意`object.wait();object.notifyAll();`不适合这里的场景而是更适合资源竞争的场景,
`Thread`只有一个执行而不是‘都被唤醒并都开始执行’。

代码见 `src/PrintPlayersRaceTime.java`