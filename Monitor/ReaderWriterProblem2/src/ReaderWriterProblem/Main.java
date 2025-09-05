package ReaderWriterProblem;


public class Main {
	 public static void main(String[] args) 
	 {
       ReaderWriterLock lock = new ReaderWriterLock();
		
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
