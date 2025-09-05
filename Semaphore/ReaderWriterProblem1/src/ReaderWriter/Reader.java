package ReaderWriter;

import java.util.concurrent.TimeUnit;

public class Reader implements Runnable
{
	private ReadWriteLock lock;
	
	public Reader(ReadWriteLock rw)
	{
		this.lock = rw;
	}
	public void run()
	{
		for(int round = 0; round < 5; round++)
		{
			try
			{
				lock.readLock();
				System.out.println("Thread " + round + " is reading");
				TimeUnit.MILLISECONDS.sleep(100);
				System.out.println("Thread " + round + " has finished reading");
				lock.readUnlock();
				TimeUnit.MILLISECONDS.sleep(100);
			}catch (InterruptedException e) {}
		}
	}
}
