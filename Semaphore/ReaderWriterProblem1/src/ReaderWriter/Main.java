/*READER WRITER PROBLEMS
  - Problem description:
  	+ The reader: threads that only read from the shares resource. They do not modified it.
  	+ The writer: threads that read and write to shared resources. They must have exclusive access to modified it.
  	+ Multiple readers can read simultaneously. Since reader cann't modified data,
  	they can all access the shares resource at the same time.
  	+ Only 1 writer can write at 1 time/ A writer need exclusive access to prevent
  	other threads (both readers and writers) from accessing the resource while it
  	being modified. This ensures data integrity.
  	+ No thread can read nor write while a writer is writing. This is the core of
  	the mutual exclusion principle for writers.
  
  - Solution description:
  	+ declare semaphores: enter, enter2 and writer and 3 variables:
  		. startReader: count readers that started reading.
  		. completeReader: count readers that completed reading.
  		. waitingWriter: check if there any waiting writing threads.
  	+ Writer: when a thread that performs a writing operation is running, semaphore enter and
  	enter2 will go on waiting state and the algorithm will check if all the read operations are done yet.
  		. If it is done, semaphore enter2 will be signaled and write operation will be performed.
  		Because enter is in the waiting state, no other thread can be run at that time.
  	+Reader: when a read operation is running, the startReader variable will increase belong to the 
  	number of reading threads go in and read data. Since the reading operation is performed parallel.
  		. At the same time reading is still going on, writer won't be able to write because startReader
  		cann't equal to completeReader.
  		. After reading is complete, semaphore enter2 will go on waiting state and completeReader will
  		be increased. 
  		. If both conditions are provided, the semaphore writer will be signaled so that a writing operarion
  		can be performed. After that semaphore enter2 will be signaled.
  	
  - Using Semaphore:
  	+
*/

package ReaderWriter;

public class Main 
{
	public static void main(String[] args) throws InterruptedException
	{

		long start = System.nanoTime();
		Thread[] listReader = new Thread[5];
		Thread[] listWriter = new Thread[5];
		ReadWriteLock lock = new ReadWriteLock();
		for(int i = 0; i < 5; i++)
		{
			listReader[i] = new Thread(new Reader(lock));
			listWriter[i] = new Thread(new Writer(lock));
		}
		//let threads start to run
		for(int i = 0; i < 5; i++)
		{
			listReader[i].start();
			listWriter[i].start();
		}
		for(int i = 0; i < 5; i++)
		{
			listReader[i].join();
			listWriter[i].join();
		}
		long end = System.nanoTime()-start;
		System.out.println( "Took  "+ end + " nano seconds");
	}
}
