package ProducerConsumer;

import java.util.concurrent.TimeUnit;

public class Producer implements Runnable {
    private Company company;

    public Producer(Company object) {
        this.company = object;
    }

    public void run() {
        for (int round = 1; round <= 10; round++) {
            try {
                // Producer now produces an actual item (e.g., the round number)
                this.company.produce(round); 
                TimeUnit.MILLISECONDS.sleep(100); // Simulate production time
            } catch (InterruptedException e) {
                System.out.println("Producer interrupted.");
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println("Producer finished all rounds.");
    }
}
