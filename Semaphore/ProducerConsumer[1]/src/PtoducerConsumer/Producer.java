package PtoducerConsumer;

public class Producer implements Runnable {
	 Company company;

	  Producer(Company c) {
	    this.company = c;
	    new Thread(this).start();
	  }

	  public void run() {
	    for (int i = 1; i < 11; i++)
	      company.Produce();
	  }
}
