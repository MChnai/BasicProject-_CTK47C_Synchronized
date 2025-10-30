package ProducerConsumer;

public class Main {
    public static void main(String[] args) {
        long start = System.nanoTime();
        Company company = new Company();
        Thread producer = new Thread(new Producer(company));
        Thread consumer = new Thread(new Consumer(company));
        
        producer.setName("ProducerThread"); // Give threads names for clearer output
        consumer.setName("ConsumerThread");

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.nanoTime() - start;
        System.out.println("Took " + end + " nano seconds");
        System.out.println("Final buffer size: " + company.buffer.toArray().length); // Assuming buffer is accessible for debug
    }
}
