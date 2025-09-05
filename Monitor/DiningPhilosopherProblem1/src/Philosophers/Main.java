/*DINING PHILOSOPHERS PROBLEMS
  - Problem description:
  	+ There are 5 philosophers seated around a circular table.
  	+ There is a bowl of spaghetti in the center of the table.
  	+ And 1 single chopstick placed on the table between each pair of adjacent philosophers.
  	+ The philosopher alternate between thinking and eating.
  	+ In oder to eat, philosopher must have both both chopsticks to their left and right.
  	+ Each chopstick is shared resource, so philosopher must put back chopsticks after eating.
  
  - Solution description:
  	+ Thinking until left chopstick is available.
  	+ Thinking until right chopstick is available.
  	+ Eating for a moment.
  	+ Putting back left chopstick.
  	+ Putting back right chopstick.
  	
  - Using Semaphore:
  	+ Waiting until chopstick[index] is release.
  	+ Waiting until chopstick[index + 1] is release.
  	+ Philosopher[index] uses chopstck[index] and chopstick[index + 1]
  	+ chopstick[index] is acquire.
  	+ chopstick[index + 1] is acquire.
  	+ Philosopher[index] finishs.
  	+ Chopstick[index] is release.
  	+ Chopstick[index + 1] is release.
*/
package Philosophers;


public class Main 
{
public static void main(String[] args){
		
        Chopstick CH = new Chopstick();
            new Philosopher(CH).start();
            new Philosopher(CH).start();
            new Philosopher(CH).start();
            new Philosopher(CH).start();
}
}
