package PtoducerConsumer;
import java.util.concurrent.*;

public class Company {
	int value;

	  Semaphore semProd = new Semaphore(1); 
	  Semaphore semCon = new Semaphore(0);
	  

	  void Consume() {
	    try {
	      semCon.acquire();
	    } catch (InterruptedException e) {
	      
	    }
	    System.out.println("Consumer consumes: " + value);
	    for(int i = value; i > 0; i--)
	    	this.value--;
	    semProd.release();
	  }

	  void Produce() {
	    try {
	      semProd.acquire();
	    } catch (InterruptedException e) {
	      
	    }
	    for(int i = 0; i < 10; i++)
	    	this.value++;
	    System.out.println("Producer produces: " + this.value);
	    semCon.release();
	  }
}
