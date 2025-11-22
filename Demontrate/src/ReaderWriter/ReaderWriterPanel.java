package ReaderWriter;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class ReaderWriterPanel extends JPanel {
    public int activeReaders = 0;
    public boolean activeWriter = false;
    public final Object lock = new Object();

    public final ArrayList<Reader> readers = new ArrayList<>();
    public final ArrayList<Writer> writers = new ArrayList<>();
    
    private final ArrayList<String> logs = new ArrayList<>();
    private boolean running = false;

    public ReaderWriterPanel() {
        setLayout(new BorderLayout());
        JButton startBtn = new JButton("Start");
        JButton endBtn = new JButton("Stop");
        endBtn.addActionListener(e -> endSimulation());
        startBtn.addActionListener(e -> startSimulation());
        JPanel controlPanel = new JPanel();
        controlPanel.add(startBtn);
        controlPanel.add(endBtn);
        add(controlPanel, BorderLayout.SOUTH);
        setBackground(Color.WHITE);
    }
    
    public boolean isRunning() {
        return running;
    }

    public void addLog(String s) {
        if(logs.size() > 5) 
        	logs.remove(0);
        logs.add(s);
        repaint();
    }

    private void endSimulation() {
        running = false;
        for(Reader r : readers) 
        	r.interrupt();
        for(Writer w : writers) 
        	w.interrupt();
    }

    private void startSimulation() {
        if (running) return;
        running = true;
        
        readers.clear();
        writers.clear();
        logs.clear();

        for(int i = 1; i <= 4; i++) {
            Reader r = new Reader(this, i);
            readers.add(r);
            r.start();
        }
        
        for(int i = 1; i <= 2; i++) {
            Writer w = new Writer(this, i);
            writers.add(w);
            w.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        int cx = w / 2;
        int cy = h / 2;

        // --- 1. Draw Readers (Top) ---
        g2.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2.setColor(Color.DARK_GRAY);
        g2.drawString("Readers", cx - 30, cy - 120);
        
        int rStartX = cx - (readers.size() * 60) / 2 + 30;
        for (int i = 0; i < readers.size(); i++) {
            Reader r = readers.get(i);
            // Green if reading, Gray if idle
            if (r.state == Reader.READING) g2.setColor(new Color(46, 204, 113)); 
            else g2.setColor(Color.LIGHT_GRAY);
            
            g2.fillOval(rStartX + (i * 60) - 25, cy - 90 - 25, 50, 50);
            g2.setColor(Color.WHITE);
            g2.drawString("R" + r.id, rStartX + (i * 60) - 10, cy - 90 + 5);
        }

        // --- 2. Draw Shared Resource (Center) ---
        Stroke defaultStroke = g2.getStroke();
        // Create a dashed stroke
        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2.setStroke(dashed);
        g2.setColor(Color.GRAY);
        g2.drawRect(cx - 100, cy - 40, 200, 80);
        g2.setStroke(defaultStroke);

        g2.setFont(new Font("SansSerif", Font.BOLD, 18));
        String resourceText = "Shared Resource";
        Color resourceColor = Color.DARK_GRAY;

        if (activeWriter) {
            resourceText = "Writing...";
            resourceColor = new Color(231, 76, 60); // Red
        } else if (activeReaders > 0) {
            resourceText = "Reading (" + activeReaders + ")";
            resourceColor = new Color(46, 204, 113); // Green
        }
        
        g2.setColor(resourceColor);
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(resourceText, cx - fm.stringWidth(resourceText) / 2, cy + 5);

        // --- 3. Draw Writers (Bottom) ---
        g2.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2.setColor(Color.DARK_GRAY);
        g2.drawString("Writers", cx - 25, cy + 80);

        int wStartX = cx - (writers.size() * 60) / 2 + 30;
        for (int i = 0; i < writers.size(); i++) {
            Writer wr = writers.get(i);
            // Red if writing, Gray if idle
            if (wr.state == Writer.WRITING) g2.setColor(new Color(231, 76, 60));
            else g2.setColor(Color.LIGHT_GRAY);

            g2.fillOval(wStartX + (i * 60) - 25, cy + 110 - 25, 50, 50);
            g2.setColor(Color.WHITE);
            g2.drawString("W" + wr.id, wStartX + (i * 60) - 10, cy + 110 + 5);
        }
        
        // --- 4. Draw Logs ---
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        for(int i=0; i<logs.size(); i++) {
            g2.drawString(logs.get(i), 20, h - 120 + (i*20));
        }
    }
}