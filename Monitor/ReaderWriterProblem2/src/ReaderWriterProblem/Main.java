package ReaderWriterProblem;


public class Main {
	 public static void main(String[] args) throws InterruptedException 
	 {
		long start = System.nanoTime();
		Thread[] listReader = new Thread[5];
		Thread[] listWriter = new Thread[5];
		ReaderWriterLock lock = new ReaderWriterLock();
		
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
