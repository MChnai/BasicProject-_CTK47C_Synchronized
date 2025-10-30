package DiningPhilosopher;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Chopstick {
    private final ReentrantLock lock = new ReentrantLock(true); // Fairness=true is often good for concurrency problems
    private final Condition condition = lock.newCondition();
    private final boolean[] taking = {false, false, false, false, false};

    // Release method using ReentrantLock
    public void release() {
        lock.lock(); // Acquire the lock
        try {
            Philosopher phi = (Philosopher) Thread.currentThread();
            int Num = phi.getNum(); // Use the getter method
            
            System.out.format("Philosopher " + Num + " releases Chopstick\n");
            
            // Release both the left and right chopsticks
            taking[Num] = false;
            taking[((Num + 1) % 5)] = false;
            
            condition.signalAll(); // Wake up all waiting threads (equivalent to notifyAll())
        } finally {
            lock.unlock(); // Always release the lock in a finally block
        }
    }

    // Take method using ReentrantLock
    public void take() {
        lock.lock(); // Acquire the lock
        try {
            Philosopher phi = (Philosopher) Thread.currentThread();
            int Num = phi.getNum(); // Use the getter method
            
            // Wait while EITHER the left or right chopstick is already taken
            while (taking[((Num + 1) % 5)] || taking[Num]) {
                try {
                	System.out.format("Philosopher " + Num + " is waiting\n");
                    condition.await(); // Equivalent to wait(), releases lock and waits
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            
            // Acquire both the left and right chopsticks
            System.out.format("Philosopher " + Num + " takes Chopstick\n");
            taking[Num] = true;
            taking[((Num + 1) % 5)] = true;
            
        } finally {
            lock.unlock(); // Always release the lock in a finally block
        }
    }
}