package ProducerConsumer;

import java.util.concurrent.TimeUnit;

public class Consumer  implements Runnable
{
	private Company company;
	
	public Consumer (Company object)
	{
		this.company = object;
	}
	public void run ()
	{
		for(int round = 1; round <= 10; round++)
		{
			try
			{
				this.company.consumer();
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (Exception e) {}
		}
	}
}
