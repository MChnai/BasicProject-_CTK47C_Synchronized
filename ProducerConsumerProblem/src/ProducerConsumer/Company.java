package ProducerConsumer;

public class Company 
{
	private int item = 0;
	//run = true: chance for producer.
	//run = false: chance for consumer.
	private boolean run = true;
	
	public synchronized void producer(int amount) throws Exception
	{
		if(run)
			wait();
		//critical section
		this.item = amount;
		System.out.println("Producer produces " + item);
		run = true;
		notifyAll();
	}
	public synchronized int consumer() throws Exception
	{
		if (!run)
			wait();
		//critical section
		System.out.println("Consumer consumes " + item);
		run = false;
		notifyAll();
		return this.item;
	}
}
