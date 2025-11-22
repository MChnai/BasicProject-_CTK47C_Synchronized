package demo;

import DiningPhilosopher.*;
import ProducerConsumer.*;
import ReaderWriter.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class demo extends JFrame {

	    public demo() {
	        setTitle("Mô phỏng bài toán đồng bộ hóa của hệ điều hành");
	        setSize(900, 600);
	        //ensure program stop when click x button
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        //location: center screen
	        setLocationRelativeTo(null);

	        JTabbedPane tabbedPane = new JTabbedPane();
	        tabbedPane.addTab("Dining Philosophers", new DiningPanel());
	        tabbedPane.addTab("Producer Consumer", new ProducerConsumerPanel());
	        tabbedPane.addTab("Readers Writers", new ReaderWriterPanel());

	        add(tabbedPane);
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            new demo().setVisible(true);
	        });
	    }
   
}
