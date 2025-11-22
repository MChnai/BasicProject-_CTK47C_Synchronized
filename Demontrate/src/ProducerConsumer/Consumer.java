package ProducerConsumer;

public class Consumer extends Thread {
    private ProducerConsumerPanel panel;

    public Consumer(ProducerConsumerPanel panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        while (panel.isRunning()) {
            try {
                synchronized (panel.buffer) {
                    while (panel.buffer.isEmpty()) {
                        panel.consumerStatus = "Đang chờ (Rỗng)";
                        panel.repaint();
                        panel.buffer.wait();
                    }
                    
                    panel.consumerStatus = "Đang tiêu thụ...";
                    panel.repaint();
                    Thread.sleep(500);
                    
                    panel.buffer.removeFirst();
                    panel.buffer.notify();
                }
                
                panel.consumerStatus = "Tạm ngưng";
                panel.repaint();
                
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            }
        }
    }
}
