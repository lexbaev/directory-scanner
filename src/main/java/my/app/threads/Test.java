package my.app.threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
  public static void main(String[] args) throws InterruptedException {
    Task task = new Task();

    Thread thread1 = new Thread(
      () -> task.firstThread()
    );

    Thread thread2 = new Thread(
      () -> task.secondThread()
    );

    thread1.start();
    thread2.start();

    thread1.join();
    thread2.join();

    task.showCounter();
  }
}

class Task {
  private int counter;
  Lock lock = new ReentrantLock();

  private void increment() {
    for (int i = 0; i < 10000; i++) {
      counter++;
    }
  }

  public void firstThread() {
    lock.lock();
    System.out.println("locked first");
    increment();
    System.out.println("unlocked first");
    lock.unlock();
  }

  public void secondThread() {
    lock.lock();
    System.out.println("locked second");
    increment();
    System.out.println("unlocked second");
    lock.unlock();
  }

  public void showCounter() {
    System.out.println(counter);
  }
}
