package ReaderWriterProblem;

public class Writer implements Runnable 
{
    private  ReaderWriterLock database;

    public Writer(ReaderWriterLock database)
    {
        this.database = database;
    }
    public void run() 
    {
    	for(int round = 0; round < 5; round++)
    	{
    		try
    		{
                database.writeLock();
                Thread.sleep(100); 
                database.writeUnlock();
                Thread.sleep(100);
            }
         catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    	}
    }
}