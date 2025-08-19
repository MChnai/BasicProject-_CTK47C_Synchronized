/*PRODUCER CONSUMER PROBLEM
  - Problem description:
  	+ The producer: these threads generates data items and adds them to the shared buffer.
  					It must stop producing if the buffer is full
  	+ The consumer: these threads retrieves data items from the buffer and consumes them.
  	 				It must stop consuming if the buffer is empty.
  	+ The buffer: this is a shared, fixed-size data structure that acts as a storage area
  	 			  between the producer and consumer.
 
  - Semaphore solution description:
  	+ Producer's logic:
  		. Wait on buffer to be empty.
  		. Wait on mutex acquires critical section.
  		. Produce items: add new items to the buffer.
  		. Signal mutex: release critical section.
  		. Signal the buffer is full.
  	+ Consumer's logic:
  		. Wait on the buffer to be full.
  		. Wait on mutex acquires critical section.
  		. Consume items in the buffer.
  		. Signal mutex: release critical section.
  		. Signal the buffer is empty.
*/
package ProducerConsumer;

public class Main {
	public static void main(String[] args)
	{
		Company company = new Company();
		Thread producer = new Thread(new Producer(company));
		Thread consumer = new Thread(new Consumer(company));
		producer.start();
		consumer.start();
	}
}
