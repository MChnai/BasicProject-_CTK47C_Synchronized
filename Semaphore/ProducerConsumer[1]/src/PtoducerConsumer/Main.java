package PtoducerConsumer;

public class Main {
	public static void main(String args[]) {
	    Company company = new Company();
	    new Consumer(company);
	    new Producer(company);
	  }
}
