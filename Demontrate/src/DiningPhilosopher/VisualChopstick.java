package DiningPhilosopher;

public class VisualChopstick {
    // 5 chopsticks, one for each philosopher's "right" side (or left, depends on convention)
    // taking[i] means the chopstick *to the right* of philosopher i is taken.
    public final boolean[] taking = {false, false, false, false, false}; // Made public for direct drawing access

    // New method to reset all chopsticks when simulation restarts
    public synchronized void resetChopsticks() {
        for(int i = 0; i < taking.length; i++) {
            taking[i] = false;
        }
        notifyAll(); // Wake up any waiting threads that might be stuck
    }

    // Philosopher i takes their RIGHT chopstick (i) and LEFT chopstick ((i+1)%N)
    public synchronized void take(VisualPhilosopher phi) {
        int Num = phi.Num; // Philosopher's ID (0 to N-1)

        // Wait if either of the required chopsticks are taken
        while (taking[Num] || taking[(Num + 1) % taking.length]) {
            try {
                phi.setState(VisualPhilosopher.WAITING); // Set to WAITING
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return; // Exit if interrupted while waiting
            }
        }
        
        // Take the chopsticks
        taking[Num] = true;
        taking[(Num + 1) % taking.length] = true;
    }

    // Philosopher i releases their RIGHT chopstick (i) and LEFT chopstick ((i+1)%N)
    public synchronized void release(VisualPhilosopher phi) {
        int Num = phi.Num;

        taking[Num] = false;
        taking[(Num + 1) % taking.length] = false;
        notifyAll(); // Notify waiting philosophers that chopsticks are available
    }
}