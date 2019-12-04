public class Driver {
  public static void main(String args[]) {
    Scheduler sched = new Scheduler();
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
  }
}
