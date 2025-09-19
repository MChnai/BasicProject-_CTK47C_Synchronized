package PtoducerConsumer;

public class Main {
	public static void main(String args[]) throws InterruptedException {
		long start = System.nanoTime();
	    Company company = new Company();
	    Consumer consumer = new Consumer(company);
	    Producer producer = new Producer(company);

	    Thread con = new Thread(consumer);
	    Thread pro = new Thread(producer);
	    
    	con.start();
    	pro.start();
    	
    	con.join();
		pro.join();
	        
	    long end = System.nanoTime()-start;
		System.out.println( "Took  "+ end + " nano seconds");
	  }
}
