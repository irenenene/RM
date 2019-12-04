public class Work implements Runnable {
  //public Semaphore p;
  //public Semaphore q;

  public int period;
  public int numLoops;
  public int counter;
  public int[][] workMatrix;

  public Work(int p, int n) {
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
    for (int i = 0; i < numLoops; i++) {
      doWork();
    }

    counter++;
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
