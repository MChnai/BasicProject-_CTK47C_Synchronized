package ReaderWriterProblem;

public class Reader implements Runnable {
    private ReaderWriterLock database;

    public Reader(ReaderWriterLock database) {
        this.database = database;
    }

    public void run() {
		for(int round = 0; round < 5; round++)
		{
			try
			{
                database.readLock();
                Thread.sleep(100); 
                database.readUnlock();
                Thread.sleep(100);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
		}
    }
}
