package ProducerConsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Company {
    final Queue<Integer> buffer = new LinkedList<>(); // Using a LinkedList as a queue (the buffer)
    private final int CAPACITY = 5; // Fixed size for the buffer

    // ReentrantLock to protect access to the buffer and shared state
    private final ReentrantLock lock = new ReentrantLock(true); // 'true' for fairness (optional)

    // Conditions for waiting threads
    private final Condition notFull = lock.newCondition();  // Producers wait on this if buffer is full
    private final Condition notEmpty = lock.newCondition(); // Consumers wait on this if buffer is empty

    public void produce(int itemToProduce) throws InterruptedException {
        lock.lock(); // Acquire the lock
        try {
            // Wait if the buffer is full
            while (buffer.size() == CAPACITY) {
                System.out.println("Producer WAITING: Buffer is FULL. Size: " + buffer.size());
                notFull.await(); // Releases the lock and waits until signaled
            }

            // Critical Section: Add item to the buffer
            for(int i = 0; i < itemToProduce; i++)
            	buffer.add(itemToProduce);
            System.out.println("Producer produced: " + itemToProduce + ". Buffer size: " + buffer.size());

            notEmpty.signalAll(); // Signal consumers that the buffer is no longer empty
        } finally {
            lock.unlock(); // Always release the lock in a finally block
        }
    }

    public int consume() throws InterruptedException {
        lock.lock(); // Acquire the lock
        try {
            // Wait if the buffer is empty
            while (buffer.isEmpty()) {
                System.out.println("Consumer WAITING: Buffer is EMPTY. Size: " + buffer.size());
                notEmpty.await(); // Releases the lock and waits until signaled
            }

            // Critical Section: Remove item from the buffer
            int consumedItem = buffer.remove();
            for(int i = 0; i < consumedItem - 1; i++)
            	buffer.remove();
            System.out.println("Consumer consumed: " + consumedItem + ". Buffer size: " + buffer.size());

            notFull.signalAll(); // Signal producers that the buffer is no longer full
            return consumedItem;
        } finally {
            lock.unlock(); // Always release the lock in a finally block
        }
    }
}
