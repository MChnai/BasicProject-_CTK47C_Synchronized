package Philosophers;



public class Philosopher extends Thread
{
	int Num;
    static int Number = 0;
    private Chopstick Chop;
    
    public Philosopher(Chopstick Chop){
	this.Chop = Chop;
    Num = Number;
	Number++;
    }
    
    private void eating(){
        System.out.format("Philosopher " + Num + " tis Eating\n");
    }

    private void thinking(){
        System.out.format("Philosopher " + Num + " is Thinking\n");

    }
    public void run(){
            thinking();
            Chop.take();
            eating();
            Chop.release();
    }
}
