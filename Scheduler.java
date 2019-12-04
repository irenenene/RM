import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler extends TimerTask {
  public int[] overruns;
  public Work[] workObjs;
  public Thread[] threads;
  public Timer time;
  public int currPeriod;
  public int counter;

  public Scheduler(Work[] w, Thread[] t, Timer time) {
    currPeriod = 0;
    counter = 0;
    this.time = time;
    overruns = new int[4];
    Arrays.fill(overruns, 0);
    workObjs = new Work[4];
    threads = new Thread[4];
    workObjs = w;
    threads = t;

    determinePriority();
  }

  public void determinePriority() {
    //determine priority
    System.out.println(Thread.currentThread().getPriority());
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
    for (int k = 0; k < 4; k++) {
      System.out.println(threads[k].getPriority());
    }
  }

  public void run() {
    System.out.println("Time: " + currPeriod + " Period: " + counter);
    for (int i = 0; i < 4; i++) {
      if(currPeriod % workObjs[i].getPeriod() == 0) {
        if(workObjs[i].done) {
          threads[i].run();
          System.out.println("Running: " + i);
        }
        else {
          overruns[i]++;
          System.out.println("overrun");
        }

      }
    }


    currPeriod++;
    if(currPeriod >= 16) {
      currPeriod = 0;
      counter++;
    }
    if(counter >= 10) {
      time.cancel();
      getResults();
    }
  }

  public void getResults() {
    for (int i = 0; i < 4; i++) {
      System.out.println("Thread " + i + ": " + workObjs[i].getCount() + " - Overruns: " + overruns[i]);
    }
  }

}
