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
		//create a lock
		ReadWriteLock lock = new ReadWriteLock();
		
		Thread reader1 = new Thread(new Reader(lock));
		Thread writer1 = new Thread(new Writer(lock));
		Thread writer2 = new Thread(new Writer(lock));
		Thread writer3 = new Thread(new Writer(lock));
		Thread writer4 = new Thread(new Writer(lock));
		
		Thread reader2 = new Thread(new Reader(lock));
		Thread reader3 = new Thread(new Reader(lock));
		Thread reader4 = new Thread(new Reader(lock));
		Thread reader5 = new Thread(new Reader(lock));
		Thread writer5 = new Thread(new Writer(lock));
		
		//let threads start to run
		reader1.start();
		writer1.start();
		writer2.start();
		writer3.start();
		writer4.start();
		
		reader2.start();
		reader3.start();
		reader4.start();
		reader5.start();
		writer5.start();
	}
}
