import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler extends TimerTask {
  public int[] overruns;
  public Work[] workObjs;
  public Thread[] threads;
  public Timer time;
  public int counter;

  public Scheduler(Work[] w, Thread[] t, Timer time) {
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

    counter++;

    if(counter >= 10)
      time.cancel();
    /*

    for (int i = 0; i < 4; i++) {
      try {
        threads[i].join();
      }
      catch(Exception e) {

      }
    }*/

    getResults();
  }

  public void getResults() {
    for (int i = 0; i < 4; i++) {
      System.out.println("Thread: " + i + " : " + workObjs[i].getCount());
    }
  }

}
