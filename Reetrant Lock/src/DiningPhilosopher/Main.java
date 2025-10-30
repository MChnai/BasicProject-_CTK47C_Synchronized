package DiningPhilosopher;

public class Main {
	public static void main(String[] args) {
		long start = System.nanoTime();
        Chopstick chopstickMonitor = new Chopstick();
        
        Philosopher[] philosophers = new Philosopher[5];
        
        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher(chopstickMonitor);
            philosophers[i].start();
        }
        for (int i = 0; i < 5; i++) {
            try {
				philosophers[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        long end = System.nanoTime()-start;
    	System.out.println( "5 phylosophers took  "+ end + " nano seconds");
    }
}
