package ReaderWriter;

import java.util.concurrent.Semaphore;

public class ReadWriteLock 
{
	//create 3 semaphores and 3 variables
	private Semaphore enter = new Semaphore(1);
	private Semaphore enter2 = new Semaphore(1);
	private Semaphore writer = new Semaphore(0);
	private int startReader = 0;
	private int completeReader = 0;
	private boolean waitingWriter = false;
	
	public void readLock() throws InterruptedException
	{
		//critical section
		enter.acquire();
		startReader++;
		enter.release();
	}
	public void readUnlock() throws InterruptedException
	{
		//critical section
		enter2.acquire();
		completeReader++;
		if(waitingWriter && startReader == completeReader)
			writer.release();
		enter2.release();
	}
	public void writeLock() throws InterruptedException
	{
		//critical section
		enter.acquire();
		enter2.acquire();
		if(startReader == completeReader)
			enter2.release();
		else
		{
			waitingWriter = true;
			enter2.release();
			writer.acquire();
			waitingWriter = false;
		}
	}
	public void writeUnlock() throws InterruptedException
	{
		enter.release();
	}
}
