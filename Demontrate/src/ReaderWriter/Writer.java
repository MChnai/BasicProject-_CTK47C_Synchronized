package ReaderWriter;

public class Writer extends Thread {
    private ReaderWriterPanel panel;
    public int id;
    // State for visualization: 0 = Idle, 1 = Writing
    public volatile int state = IDLE;
    
    public static final int IDLE = 0;
    public static final int WRITING = 1;

    public Writer(ReaderWriterPanel panel, int id) {
        this.panel = panel;
        this.id = id;
    }

    @Override
    public void run() {
        while(panel.isRunning()) {
            try {
                Thread.sleep((long)(Math.random() * 5000)); 
                
                synchronized (panel.lock) {
                    while(panel.activeReaders > 0 || panel.activeWriter) {
                        panel.lock.wait();
                    }
                    panel.activeWriter = true;
                    panel.addLog("Writer " + id + " started WRITING.");
                }
                
                // Update state and repaint for visualization
                this.state = WRITING;
                panel.repaint();
                
                Thread.sleep(500); // Writing action
                
                // Writing finished
                this.state = IDLE;
                panel.repaint();

                synchronized (panel.lock) {
                    panel.activeWriter = false;
                    panel.addLog("Writer " + id + " finished.");
                    panel.lock.notifyAll();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}