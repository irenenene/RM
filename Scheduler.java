import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler implements Runnable {
  public static int[] overruns;
  public Work[] workObjs;
  public Thread[] threads;
  public static int currPeriod;
  public static int counter;

  public Scheduler(Work[] w, Thread[] t) {
    currPeriod = 0;
    counter = 0;
    overruns = new int[4];
    Arrays.fill(overruns, 0);
    workObjs = w;
    threads = t;

    determinePriority();
  }

  public void determinePriority() {
    int currPriority = Thread.currentThread().getPriority(); //should be 5.

    for (int j = 0; j < 4; j++) {
      int minPeriod = 16;
      int minThread = 3;

      for (int i = 0; i < 4; i++) {
        //if the thread has the shortest period and its priority is the default 5
        if (workObjs[i].getPeriod() <= minPeriod && threads[i].getPriority() == 5) {
          minPeriod = workObjs[i].getPeriod();
          minThread = i;
        }
      }

      threads[minThread].setPriority(--currPriority);
    }
    //priorities should be at 4, 3, 2, 1
    /*
    for (int k = 0; k < 4; k++) {
      System.out.println(threads[k].getPriority());
    }*/
  }

  public void run() {
    while(counter < 10) {
    //System.out.println("Time: " + currPeriod + " Period: " + counter);
      for (int i = 0; i < 4; i++) {
        if(currPeriod % workObjs[i].getPeriod() == 0) {
          if(workObjs[i].done) {
            threads[i] = new Thread(workObjs[i]);
            threads[i].start();
            //System.out.println("Running: " + i);
          }
          else {
            overruns[i]++;
            //System.out.println("overrun");
          }

        }
      }
      currPeriod++;
      if(currPeriod >= 16) {
        currPeriod = 0;
        counter++;
      }
      try {
        Thread.sleep(10);
      }
      catch(InterruptedException e) {

      }
    }

    getResults();
  }

  public void getResults() {
    for (int i = 0; i < 4; i++) {
      System.out.println("Thread " + i + ": " + workObjs[i].getCount() + " - Overruns: " + overruns[i]);
    }
  }

}
