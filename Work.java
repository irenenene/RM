import java.util.concurrent.*;

public class Work implements Runnable {
  public Semaphore lock;
  //public Semaphore q;
  boolean done;
  public int period;
  public int numLoops;
  public int counter;
  public int[][] workMatrix;

  public Work(int p, int n, Semaphore l) {
    done = true;
    lock = l;
    period = p;
    numLoops = n;
    counter = 0;
    workMatrix = new int[10][10];

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        workMatrix[i][j] = 1;
      }
    }
  }

  public int getCount() {
    return counter;
  }

  public int getPeriod() {
    return period;
  }

  public void run() {
    try {
      //System.out.println("running");
      done = false;
      lock.acquire();
      for (int i = 0; i < numLoops; i++) {
        doWork();
      }

      counter++;
      done = true;
      lock.release();
    }
    catch(InterruptedException e) {

    }
  }

  public void doWork() {
    int sum = 0;

    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 10; j++) {
        sum = workMatrix[i][j] * workMatrix[i+1][j];
      }
      for (int k = 0; k < 10; k++) {
        sum = workMatrix[i+4][k] * workMatrix[i+5][k];
      }
    }
  }
}
