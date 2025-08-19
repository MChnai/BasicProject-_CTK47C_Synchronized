package ReaderWriter;

import java.util.concurrent.TimeUnit;

public class Writer implements Runnable
{
	private ReadWriteLock lock;
	
	public Writer(ReadWriteLock rw)
	{
		this.lock = rw;
	}
	public void run()
	{
		for(int round = 0; round < 5; round++)
		{
			try
			{
				lock.writeLock();
				System.out.println("Thread " + round + " is writing");
				TimeUnit.MILLISECONDS.sleep(100);
				System.out.println("Thread " + round + " has finished writing");
				lock.writeUnlock();
				TimeUnit.MILLISECONDS.sleep(100);
			}catch (InterruptedException e) {}
		}
	}
}
