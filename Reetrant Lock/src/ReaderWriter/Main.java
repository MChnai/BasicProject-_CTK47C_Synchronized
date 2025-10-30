package ReaderWriter;

public class Main {
	public static void main(String[] args) {
		long start = System.nanoTime();
        ReaderWriter database = new ReaderWriter();

        // Create multiple readers
        for (int i = 0; i < 5; i++) {
            new Thread(new Reader(database)).start();
        }

        // Create multiple writers
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(new Writer(database));
            thread.start();
            try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        long end = System.nanoTime()-start;
    	System.out.println( "5 phylosophers took  "+ end + " nano seconds");
    }
}
