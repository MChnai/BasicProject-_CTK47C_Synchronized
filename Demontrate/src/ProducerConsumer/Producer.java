package ProducerConsumer;

public class Producer extends Thread {
    private ProducerConsumerPanel panel;

    // We need the panel to access the 'buffer' and 'repaint'
    public Producer(ProducerConsumerPanel panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        while (panel.isRunning()) {
            try {
                // Access the buffer inside the panel
                synchronized (panel.buffer) {
                    while (panel.buffer.size() == panel.CAPACITY) {
                        panel.producerStatus = "Đang chờ (Đầy)";
                        panel.repaint();
                        panel.buffer.wait();
                    }
                    
                    panel.producerStatus = "Đang sản xuất...";
                    panel.repaint();
                    Thread.sleep(500);
                    panel.buffer.add((int)(Math.random() * 100));
                    panel.buffer.notify();
                }
                
                panel.producerStatus = "Tạm ngưng";
                panel.repaint();
                
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            }
        }
    }
}
