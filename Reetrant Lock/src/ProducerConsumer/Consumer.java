package ProducerConsumer;

import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {
    private Company company;

    public Consumer(Company object) {
        this.company = object;
    }

    public void run() {
        for (int round = 1; round <= 10; round++) {
            try {
                int consumedItem = this.company.consume(); // Consumer receives the item
                TimeUnit.MILLISECONDS.sleep(150); // Simulate consumption time (slightly longer than producer)
            } catch (InterruptedException e) {
                System.out.println("Consumer interrupted.");
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println("Consumer finished all rounds.");
    }
}