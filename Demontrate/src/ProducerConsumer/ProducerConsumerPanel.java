package ProducerConsumer;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.*;

public class ProducerConsumerPanel extends JPanel {
    public final LinkedList<Integer> buffer = new LinkedList<>();
    public final int CAPACITY = 5;
    public volatile String producerStatus = "Idle";
    public volatile String consumerStatus = "Idle";
    private ArrayList<Thread> company = new ArrayList<Thread>();
    
    private boolean running = false;

    public ProducerConsumerPanel() {
        setLayout(new BorderLayout());
        JButton startBtn = new JButton("Bắt đầu");
        JButton endBtn = new JButton("Dừng");
        endBtn.addActionListener(e -> endSimulation());
        startBtn.addActionListener(e -> startSimulation());
        JPanel controlPanel = new JPanel();
        controlPanel.add(startBtn);
        controlPanel.add(endBtn);
        
        add(controlPanel, BorderLayout.SOUTH);
    }
	private void endSimulation()
    {
    	running = false;
    	for(Thread proCon : company)
    		proCon.interrupt();
    }
    private void startSimulation() {
        if (running) return;
        running = true;
        
        // Create separate thread objects and pass THIS panel to them
        for(int i = 0; i < 5; i++)
        {
        	Producer pro = new Producer(this);
        	Consumer con = new Consumer(this);
        	company.add(pro);
        	company.add(con);
        	pro.start();
        	con.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Draw Producer Box
        g2.setColor(new Color(52, 152, 219));
        g2.fillRect(100, h/2 - 50, 100, 100);
        g2.setColor(Color.WHITE);
        g2.drawString("PRODUCER", 115, h/2);
        g2.drawString(producerStatus, 115, h/2 + 20);

        // Draw Buffer Box
        g2.setColor(Color.DARK_GRAY);
        g2.drawRect(w/2 - 100, h/2 - 40, 200, 80);
        g2.drawString("Buffer: " + buffer.size() + "/" + CAPACITY, w/2 - 30, h/2 - 50);

        // Draw Items inside Buffer
        g2.setColor(new Color(243, 156, 18));
        for(int i=0; i<buffer.size(); i++) {
            g2.fillRect(w/2 - 90 + (i * 35), h/2 - 30, 30, 60);
        }

        // Draw Consumer Box
        g2.setColor(new Color(155, 89, 182));
        g2.fillRect(w - 200, h/2 - 50, 100, 100);
        g2.setColor(Color.WHITE);
        g2.drawString("CONSUMER", w - 185, h/2);
        g2.drawString(consumerStatus, w - 185, h/2 + 20);
    }
	public boolean isRunning() {
		return running;
	}
}