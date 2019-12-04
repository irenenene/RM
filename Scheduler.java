import java.util.Arrays;

public class Scheduler implements Runnable {
  public int[] overruns;
  public int timeUnit;
  public int majorFrame;
  public int totalPeriods;

  public Work[] workObjs;
  public Thread[] threads;

  public Scheduler() {
    overruns = new int[4];
    Arrays.fill(overruns, 0);
    timeUnit = 20;
    majorFrame = 16;
    totalPeriods = 10;
    workObjs = new Work[4];
    threads = new Thread[4];

    workObjs[0] = new Work(1, 100);
    workObjs[1] = new Work(2, 200);
    workObjs[2] = new Work(4, 400);
    workObjs[3] = new Work(16, 1600);

    for (int i = 0; i < 4; i++) {
      threads[i] = new Thread(workObjs[i]);
    }

    determinePriority();
  }

  public void determinePriority() {
    //determine priority
    int currPriority = Thread.currentThread().getPriority(); //should be 5.

    for (int j = 0; j < 4; j++) {
      int minPeriod = 16;
      int minThread = 0;

      for (int i = 0; i < 4; i++) {
        //if the thread has the shortest period and its priority is the default 5
        if (workObjs[i].getPeriod() <= minPeriod && threads[i].getPriority() == 5) {
          minPeriod = workObjs[i].getPeriod();
          minThread = i;
        }
      }

      threads[minThread].setPriority(currPriority--);
    }
    //priorities should be at 5, 4, 3, 2, 1
  }

  public void run() {
    for (int i = 0; i < 4; i++) {
      threads[i].run();
    }

    for (int i = 0; i < 4; i++) {
      try {
        threads[i].join();
      }
      catch(Exception e) {

      }
    }
  }

  public void getResults() {
    for (int i = 0; i < 4; i++) {
      System.out.println("Thread: " + i + " : " + workObjs[i].getCount());
    }
  }

}
