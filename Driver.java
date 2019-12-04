import java.util.Timer;

public class Driver {
  public static void main(String args[]) {
    int timeUnit = 20; //in milliseconds
    int majorFrame = 16;
    int totalPeriods = 10;

    Work[] workObjs = new Work[4];
    Thread[] threads = new Thread[4];

    workObjs[0] = new Work(1, 100);
    workObjs[1] = new Work(2, 200);
    workObjs[2] = new Work(4, 400);
    workObjs[3] = new Work(16, 1600);

    for (int i = 0; i < 4; i++) {
      threads[i] = new Thread(workObjs[i]);
    }

    Timer sTimer = new Timer();
    Scheduler sched = new Scheduler(workObjs, threads, sTimer);
    sTimer.schedule(sched, 2000, 2000);


    for (int i = 0; i < 4; i++) {
      try {
        threads[i].join();
      }
      catch(Exception e) {

      }
    }

    sched.getResults();
    /*
    Thread t = new Thread(sched);
    t.run();

    try {
      t.join();
    }
    catch(Exception e) {

    }
    finally {
      sched.getResults();
    }

    */

    //sched.getResults();
  }
}
