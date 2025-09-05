package ProducerConsumer;

import java.util.concurrent.TimeUnit;

public class Producer implements Runnable
{
	private Company company;
	public Producer(Company object)
	{
		this.company = object;
	}
	public void run()
	{
		for(int round = 1; round <= 10; round++)
		{
			try
			{
				this.company.producer(10);
				TimeUnit.MILLISECONDS.sleep(100);
			}catch (Exception e) {}
		}
	}
}
