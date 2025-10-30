package ReaderWriter;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReaderWriter {
    private int readerCount = 0;
    private boolean writing = false;

    // The single lock protecting access to readerCount and writing flags
    private final ReentrantLock lock = new ReentrantLock(true); // 'true' for fairness (optional, but good practice)

    // Conditions for waiting:
    // Readers wait on this condition if a writer is currently writing
    private final Condition canRead = lock.newCondition();
    // Writers wait on this condition if there are active readers or another writer is writing
    private final Condition canWrite = lock.newCondition();


    public void readLock() throws InterruptedException {
        lock.lock(); // Acquire the lock
        try {
            // Readers wait if a writer is currently active
            while (writing) {
                System.out.println("READER " + Thread.currentThread().getName() + " is waiting to read.");
                canRead.await(); // Equivalent to wait(), releases the lock and waits
            }
            readerCount++; // Increment reader count
            System.out.println("READER " + Thread.currentThread().getName() + " is reading. (Readers: " + readerCount + ")");
        } finally {
            lock.unlock(); // Always release the lock in a finally block
        }
    }

    public void readUnlock() {
        lock.lock(); // Acquire the lock
        try {
            readerCount--; // Decrement reader count
            System.out.println("READER " + Thread.currentThread().getName() + " has finished reading. (Readers: " + readerCount + ")");
            
            // If this was the last reader, signal any waiting writers
            if (readerCount == 0) {
                canWrite.signalAll(); // Wake up all waiting writers
            }
        } finally {
            lock.unlock(); // Always release the lock in a finally block
        }
    }

    public void writeLock() throws InterruptedException {
        lock.lock(); // Acquire the lock
        try {
            // Writers wait if there are active readers or another writer is active
            while (readerCount > 0 || writing) {
                System.out.println("WRITER " + Thread.currentThread().getName() + " is waiting to write.");
                canWrite.await(); // Equivalent to wait(), releases the lock and waits
            }
            writing = true; // Set writing flag to true
            System.out.println("WRITER " + Thread.currentThread().getName() + " is writing.");
        } finally {
            lock.unlock(); // Always release the lock in a finally block
        }
    }

    public void writeUnlock() {
        lock.lock(); // Acquire the lock
        try {
            writing = false; // Release writing flag
            System.out.println("WRITER " + Thread.currentThread().getName() + " has finished writing.");
            
            // Now that writing is done, both readers and writers can potentially proceed.
            // Signal both conditions to let the scheduler decide, or prioritize:
            canRead.signalAll();  // Wake up all waiting readers
            canWrite.signalAll(); // Wake up all waiting writers (if any)
            // It's often safer to signal all if multiple types of threads might be waiting.
        } finally {
            lock.unlock(); // Always release the lock in a finally block
        }
    }
}
