package ReaderWriter;

public class Writer implements Runnable 
{
    private  ReaderWriter database;

    public Writer(ReaderWriter database)
    {
        this.database = database;
    }
    public void run() 
    {
        // Assign a name to the thread for better logging
        Thread.currentThread().setName("W-" + Thread.currentThread().getId());
    	for(int round = 0; round < 5; round++)
    	{
    		try
    		{
                database.writeLock();
                Thread.sleep(100); // Simulate writing time
                database.writeUnlock();
                Thread.sleep(100); // Simulate doing other work
            }
         catch (InterruptedException e) {
            System.out.println("WRITER " + Thread.currentThread().getName() + " interrupted.");
            Thread.currentThread().interrupt();
        }
    	}
        System.out.println("WRITER " + Thread.currentThread().getName() + " finished all rounds.");
    }
}
