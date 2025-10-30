package ReaderWriter;

public class Reader implements Runnable {
    private ReaderWriter database;

    public Reader(ReaderWriter database) {
        this.database = database;
    }

    public void run() {
        // Assign a name to the thread for better logging
        Thread.currentThread().setName("R-" + Thread.currentThread().getId());
		for(int round = 0; round < 5; round++)
		{
			try
			{
                database.readLock();
                Thread.sleep(100); // Simulate reading time
                database.readUnlock();
                Thread.sleep(100); // Simulate doing other work
            
        } catch (InterruptedException e) {
            System.out.println("READER " + Thread.currentThread().getName() + " interrupted.");
            Thread.currentThread().interrupt();
        }
		}
        System.out.println("READER " + Thread.currentThread().getName() + " finished all rounds.");
    }
}