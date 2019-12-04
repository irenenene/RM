import java.util.Timer;
import java.util.concurrent.*;
//import java.util.TimeUnit;

public class Driver {
  public static void main(String args[]) {
    int initp = Integer.parseInt(args[0]);
    //long start = System.nanoTime();
    Semaphore p = new Semaphore(1);
    int timeUnit = 20; //in milliseconds
    int majorFrame = 16;
    int totalPeriods = 10;

    Work[] workObjs = new Work[4];
    Thread[] threads = new Thread[4];

    workObjs[0] = new Work(1, initp, p);
    workObjs[1] = new Work(2, 200, p);
    workObjs[2] = new Work(4, 400, p);
    workObjs[3] = new Work(16, 1600, p);

    for (int i = 0; i < 4; i++) {
      threads[i] = new Thread(workObjs[i]);
    }

    Timer sTimer = new Timer();
    Scheduler sched = new Scheduler(workObjs, threads, sTimer);
    sTimer.schedule(sched, 10, 10);

  }
}
