package DiningPhilosopher;

public class Philosopher extends Thread {
    private int Num; // Keep it private
    private static int Number = 0;
    private final Chopstick Chop; // Use final keyword

    public Philosopher(Chopstick Chop) {
        this.Chop = Chop;
        Num = Number;
        Number++;
    }

    // Public getter for Num, required by the Chopstick class logic
    public int getNum() {
        return Num;
    }

    private void eating() {
        System.out.format("Philosopher " + Num + " is Eating\n");
        // Optionally add a sleep here to simulate eating time
        try {
            Thread.sleep(100); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void thinking() {
        System.out.format("Philosopher " + Num + " is Thinking\n");
        // Optionally add a sleep here to simulate thinking time
        try {
            Thread.sleep(100); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void run() {
        // Run indefinitely to truly simulate the dining philosophers
        for (int i = 0; i < 5; i++) { 
            thinking();
            Chop.take();
            eating();
            Chop.release();
        }
    }
}