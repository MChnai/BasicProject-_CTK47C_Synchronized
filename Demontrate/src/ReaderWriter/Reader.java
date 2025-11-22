package ReaderWriter;

public class Reader extends Thread {
    private ReaderWriterPanel panel;
    public int id;
    // State for visualization: 0 = Idle, 1 = Reading
    public volatile int state = IDLE;
    
    public static final int IDLE = 0;
    public static final int READING = 1;

    public Reader(ReaderWriterPanel panel, int id) {
        this.panel = panel;
        this.id = id;
    }

    @Override
    public void run() {
        while(panel.isRunning()) {
            try {
                Thread.sleep((long)(Math.random() * 2000));
                
                synchronized (panel.lock) {
                    while(panel.activeWriter) {
                        panel.lock.wait();
                    }
                    panel.activeReaders++;
                    panel.addLog("Reader " + id + " started reading.");
                }
                
                // Update state and repaint for visualization
                this.state = READING;
                panel.repaint();
                
                Thread.sleep(500); // Reading action
                
                // Reading finished
                this.state = IDLE;
                panel.repaint();

                synchronized (panel.lock) {
                    panel.activeReaders--;
                    panel.addLog("Reader " + id + " finished.");
                    if(panel.activeReaders == 0) {
                        panel.lock.notifyAll();
                    }
                }
            } catch (InterruptedException e) { 
                Thread.currentThread().interrupt(); // Restore interruption status
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}