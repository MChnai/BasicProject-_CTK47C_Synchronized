BASIC PROJECT CTK47C - SIMULATE THE SYNCHRONIZATION PROBLEMS OF OPERATING SYSTEM.

Authors:
  2312805_Trần Ngọc Như Ý, 2312806_Nguyễn Kim Yến, 2312760_Biện Trần Anh Thư


READER - WRITER PROBLEMS
  - Problem description:
  	+ The reader: threads that only read from the shares resource. They do not modified it.
  	+ The writer: threads that read and write to shared resources. They must have exclusive access to modified it.
  	+ Multiple readers can read simultaneously. Since reader cann't modified data, they can all access the shares resource at the same time.
  	+ Only 1 writer can write at 1 time/ A writer need exclusive access to prevent other threads (both readers and writers) from accessing the resource while it being modified. This ensures data integrity.
  	+ No thread can read nor write while a writer is writing. This is the core of the mutual exclusion principle for writers.
  - Solution description:
  	+ declare semaphores: enter, enter2 and writer and 3 variables:
  		. startReader: count readers that started reading.
  		. completeReader: count readers that completed reading.
  		. waitingWriter: check if there any waiting writing threads.
  	+ Writer: when a thread that performs a writing operation is running, semaphore enter and	enter2 will go on waiting state and the algorithm will check if all the read operations are done yet.
  		. If it is done, semaphore enter2 will be signaled and write operation will be performed.	Because enter is in the waiting state, no other thread can be run at that time.
  	+Reader: when a read operation is running, the startReader variable will increase belong to the number of reading threads go in and read data. Since the reading operation is performed parallel.
  		. At the same time reading is still going on, writer won't be able to write because startReader cann't equal to completeReader.
  		. After reading is complete, semaphore enter2 will go on waiting state and completeReader will be increased. 
  		. If both conditions are provided, the semaphore writer will be signaled so that a writing operation can be performed. After that semaphore enter2 will be signaled.


PRODUCER - CONSUMER PROBLEM
  - Problem description:
  	+ The producer: these threads generates data items and adds them to the shared buffer. It must stop producing if the buffer is full
  	+ The consumer: these threads retrieves data items from the buffer and consumes them.	It must stop consuming if the buffer is empty.
  	+ The buffer: this is a shared, fixed-size data structure that acts as a storage area between the producer and consumer.
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

     
DINING PHILOSOPHERS PROBLEMS
  - Problem description:
  	+ There are 5 philosophers seated around a circular table.
  	+ There is a bowl of spaghetti in the center of the table.
  	+ And 1 single chopstick placed on the table between each pair of adjacent philosophers.
  	+ The philosopher alternate between thinking and eating.
  	+ In oder to eat, philosopher must have both both chopsticks to their left and right.
  	+ Each chopstick is shared resource, so philosopher must put back chopsticks after eating.
  
  - Solution description:
  	+ Thinking until left chopstick is available.
  	+ Thinking until right chopstick is available.
  	+ Eating for a moment.
  	+ Putting back left chopstick.
  	+ Putting back right chopstick.
  	
  - Using Semaphore:
  	+ Waiting until chopstick[index] is release.
  	+ Waiting until chopstick[index + 1] is release.
  	+ Philosopher[index] uses chopstck[index] and chopstick[index + 1]
  	+ chopstick[index] is acquire.
  	+ chopstick[index + 1] is acquire.
  	+ Philosopher[index] finishs.
  	+ Chopstick[index] is release.
  	+ Chopstick[index + 1] is release.
