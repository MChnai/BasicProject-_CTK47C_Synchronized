package PtoducerConsumer;


public class Consumer implements Runnable {
	Company company;

	  Consumer(Company company) {
	    this.company = company;
	    new Thread(this).start();
	  }

	  public void run() {
	    for (int i = 1; i < 10; i++)
	      company.Consume();
	  }
}
