package DiningPhilosopher;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList; // Used for readers/writers, not needed here but common

import javax.swing.*;

public class DiningPanel extends JPanel {
    private final int N = 5;
    private final VisualChopstick monitor = new VisualChopstick(); 
    private final VisualPhilosopher[] philosophers = new VisualPhilosopher[N];
    private boolean running = false;

    public DiningPanel() {
        setLayout(new BorderLayout());
        
        JButton startBtn = new JButton("Start");
        JButton endBtn = new JButton("Stop");
        
        startBtn.addActionListener(e -> startSimulation());
        endBtn.addActionListener(e -> endSimulation());
        
        JPanel controlPanel = new JPanel();
        controlPanel.add(startBtn);
        controlPanel.add(endBtn);
        
        add(controlPanel, BorderLayout.SOUTH);
        setBackground(Color.WHITE); // Set background to white
    }

    public boolean isRunning() {
        return running;
    }

    private void endSimulation() {
        running = false;
        // Interrupt all philosophers to stop their threads
        for(VisualPhilosopher p : philosophers) {
            if (p != null) p.interrupt();
        }
    }

    private void startSimulation() {
        if (running) return;
        running = true;
        
        VisualPhilosopher.Number = 0; 
        
        // Reset chopstick states (ensure no phantom chopsticks are taken)
        monitor.resetChopsticks();

        for (int i = 0; i < N; i++) {
            philosophers[i] = new VisualPhilosopher(monitor, this);
            philosophers[i].start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE); // For crisp lines

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int tableRadius = 100; // Radius of the central gray circle
        int philosopherOffset = 60; // Distance from table edge to philosopher center

        // Draw Table (Light Gray circle)
        g2.setColor(new Color(230, 230, 230)); // Very light gray
        g2.fill(new Ellipse2D.Double(cx - tableRadius, cy - tableRadius, tableRadius * 2, tableRadius * 2));
        g2.setColor(new Color(180, 180, 180)); // Darker gray for outline
        g2.setStroke(new BasicStroke(4)); // Thicker border for the table
        g2.draw(new Ellipse2D.Double(cx - tableRadius, cy - tableRadius, tableRadius * 2, tableRadius * 2));
        
        // Don't draw "Press Start..." text
        if (!running) {
             g2.setColor(Color.BLACK);
             g2.setFont(new Font("SansSerif", Font.PLAIN, 16));
             String msg = "Simulation stopped.";
             FontMetrics fm = g2.getFontMetrics();
             g2.drawString(msg, cx - fm.stringWidth(msg) / 2, cy);
             return;
        }


        // Draw Chopsticks and Philosophers
        for (int i = 0; i < N; i++) {
            if (philosophers[i] == null) continue;

            double angleRad = Math.toRadians(i * (360.0 / N) - 90); // Angle for philosopher position
            double chopstickAngleRad = Math.toRadians((i * (360.0 / N) - 90) + (360.0 / N) / 2); // Angle for chopstick position

            int pX = (int) (cx + (tableRadius + philosopherOffset) * Math.cos(angleRad));
            int pY = (int) (cy + (tableRadius + philosopherOffset) * Math.sin(angleRad));

            // --- Draw Chopsticks ---
            // Calculate chopstick position (halfway between philosophers)
            int chopX1 = (int) (cx + (tableRadius - 20) * Math.cos(chopstickAngleRad));
            int chopY1 = (int) (cy + (tableRadius - 20) * Math.sin(chopstickAngleRad));
            int chopX2 = (int) (cx + (tableRadius + 20) * Math.cos(chopstickAngleRad));
            int chopY2 = (int) (cy + (tableRadius + 20) * Math.sin(chopstickAngleRad));
            

            g2.setColor(new Color(150, 150, 150)); // Lighter gray if available            
            g2.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.draw(new Line2D.Double(chopX1, chopY1, chopX2, chopY2));


            // --- Draw Philosophers ---
            VisualPhilosopher p = philosophers[i];
            
            Color fillColor;
            Color outlineColor = Color.WHITE; // Default for outline
            
            if (p.state == VisualPhilosopher.EATING) {
                fillColor = new Color(76, 209, 140); // Green
                outlineColor = new Color(178, 245, 213); // Lighter green for glow
            } else if (p.state == VisualPhilosopher.WAITING) {
                fillColor = new Color(255, 205, 80); // Yellow
                outlineColor = new Color(255, 235, 170); // Lighter yellow for glow
            } else {
                fillColor = new Color(235, 235, 235); // Light Gray (Thinking)
                outlineColor = new Color(210, 210, 210); // Slightly darker gray for subtle outline
            }

            // Draw the glow/outline effect
            g2.setColor(outlineColor);
            g2.fill(new Ellipse2D.Double(pX - 28, pY - 28, 56, 56)); // Slightly larger circle for glow

            // Draw the main philosopher circle
            g2.setColor(fillColor);
            g2.fill(new Ellipse2D.Double(pX - 25, pY - 25, 50, 50));
            
            // Draw philosopher ID
            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            String pText = "P" + (p.Num + 1); // +1 to start IDs from 1
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(pText, pX - fm.stringWidth(pText) / 2, pY + fm.getAscent() / 2 - 2);
        }
    }
}