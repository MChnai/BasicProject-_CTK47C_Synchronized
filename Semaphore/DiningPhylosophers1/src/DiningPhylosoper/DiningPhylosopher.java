package DiningPhylosoper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.*;


public class DiningPhylosopher implements Runnable 
{
	private int initialValue = 0;
	private int leftChopstick = 0;
	private int rightChopstick = 0;
	private Semaphore[] chopstickValue;
	
	public DiningPhylosopher (int Value, Semaphore[] chopstick)
	{
		this.initialValue = Value;
		this.chopstickValue = chopstick;
	}
	public void run()
	{
		int count = 0;
		while(count < 4)
		{
			System.out.println(initialValue + " phylosopher: thinking");
			try
			{
				//Phylosopher thinking for 100ms
				TimeUnit.MILLISECONDS.sleep(100);
				//value of 1 chopstick
				int value = (initialValue + 1) % 5; 
				//Caculate left chopstick
				leftChopstick = Math.min(initialValue, value );
				//Caculate right chopstick
				rightChopstick = Math.max(initialValue, value);
				//Critical section
				//Lock critical section
				chopstickValue[leftChopstick].acquire();
				chopstickValue[rightChopstick].acquire();
				System.out.println(initialValue + " philosopher: eating");
				//Release critical setion
				chopstickValue[leftChopstick].release();
				chopstickValue[rightChopstick].release();
				count++;
			}catch (InterruptedException e)
			{
				System.out.println("ERROR: Phisolopher " + initialValue + "stoppped");
			}
		}
	}
}