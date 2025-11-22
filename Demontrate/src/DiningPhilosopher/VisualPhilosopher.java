package DiningPhilosopher;

public class VisualPhilosopher extends Thread {
    public int Num; // Philosopher's ID (0 to N-1)
    public static int Number = 0; // Static counter for unique IDs
    
    private VisualChopstick Chop;
    private DiningPanel panel; // Reference to the GUI for repaint()
    
    // State constants for visualization
    public volatile int state = IDLE; 
    public static final int IDLE = 0;
    public static final int WAITING = 1;
    public static final int EATING = 2;

    public VisualPhilosopher(VisualChopstick Chop, DiningPanel panel) {
        this.Chop = Chop;
        this.panel = panel;
        this.Num = Number;
        Number++;
    }

    // Helper to update state and trigger GUI redraw
    public void setState(int s) { // Made public so Chopstick can set WAITING state
        this.state = s;
        panel.repaint();
    }

    private void eating() {
        setState(EATING); // Green
        try {
            Thread.sleep((long)(Math.random() * 2000 + 1000)); // Eat for 1-3 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void thinking() {
        setState(IDLE); // Gray
        try {
            Thread.sleep((long)(Math.random() * 2000)); // Think for 0-2 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        while (panel.isRunning()) {
            thinking();
            
            // setState(WAITING) is handled inside Chop.take() if blocked
            Chop.take(this); 
            
            eating();
            
            Chop.release(this);
        }
        setState(IDLE); // Ensure philosopher is idle when simulation stops
        panel.repaint(); // Final repaint to show idle state
    }
}