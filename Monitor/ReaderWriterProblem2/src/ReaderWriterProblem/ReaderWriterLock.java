package ReaderWriterProblem;

public class ReaderWriterLock {
	private int readerCount = 0;
    private boolean writing = false;


        public synchronized void readLock() throws InterruptedException {
            while (writing) {
                wait();
            }
            System.out.println("READER " + Thread.currentThread().getName() + " is reading. (Readers: " + readerCount + ")");
            readerCount++;
        }

        public synchronized void readUnlock() {
            readerCount--;
            if (readerCount == 0) {
                notifyAll();
            }
            System.out.println("READER " + Thread.currentThread().getName() + " has finished reading. (Readers: " + readerCount + ")");
        }

        public synchronized void writeLock() throws InterruptedException {
            while (readerCount > 0 || writing) {
                wait();
            }
            System.out.println("WRITER " + Thread.currentThread().getName() + " is writing.");
            writing = true; 
        }

        public synchronized void writeUnlock() {
            writing = false; 
            notifyAll(); 
            System.out.println("WRITER " + Thread.currentThread().getName() + " has finished writing.");
        }
}
