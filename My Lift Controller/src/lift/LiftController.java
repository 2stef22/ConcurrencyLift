
package lift;

/**
 * Null implementation of a Lift Controller.
 * @author K. Bryson
 */
public class MyLiftController implements LiftController {

	int currentfloor = 0; //holds the current floor
	int[] endfloor = {0,0,0,0,0,0,0,0,0}; //store all the floor destinations. These arrays are of size 9 to represent floors 0 to 8.
	int[] startupfloor = {0,0,0,0,0,0,0,0,0}; //to store  the pushUpButton requests
	int[] startdownfloor = {0,0,0,0,0,0,0,0,0}; //to store the pushDown requests
	volatile boolean doorsOpen  = false; // to check if the doors are open
	Direction currentDirection  = Direction.UP; //holds the direction the lift is going
	
	
	
    /* Interface for People */
    public synchronized void pushUpButton(int floor) throws InterruptedException {
 
    	notifyAll();
    	startupfloor[floor] = startupfloor[floor] + 1;  //Store 1 to the correct array position that corresponds to a floor.
    
    	 /*This is the same as having (!( (currentDirection == Direction.UP) && (doorsOpen) && (currentfloor == floor))). (De Morgan's Law)
    	 I just find it easier to understand with the OR statments.
    	 The person thread will wait until the lift is going up, a sign has been given to open the doors and the floor is correct. */
    	while( (currentDirection != Direction.UP) || (!doorsOpen) || (currentfloor != floor)) wait(); 
    
    	startupfloor[floor] = startupfloor[floor] -1;
    	//Shows that one person is going to enter the thread and has lift on his floor. 
  
    	notifyAll();
    
    

    }

    public synchronized void pushDownButton(int floor) throws InterruptedException {
    	
    	
    	notifyAll();
    	/*Same as above but with different array representing pushDown requests as no way to differentiate between Up and Down requests 
    	 in a single array */
    	startdownfloor[floor] = startdownfloor[floor] + 1;
    
    	
    	//Again the person waits until doors are open on his floor and with the correct destination.
    	while((currentDirection != Direction.DOWN) || (!doorsOpen) || (currentfloor !=floor)) wait();
    
    	startdownfloor[floor] = startdownfloor[floor]  - 1 ;
    
    	notifyAll();
    
    
    }
    
    public synchronized void selectFloor(int floor) throws InterruptedException {
    	
    	
    	notifyAll();
    	
    	//Only one array this time, as we assume that a person will enter the lift when it is moving with his desired direction.
    	endfloor[floor] = endfloor[floor] + 1; 
    
    	while((currentfloor !=floor) || (!doorsOpen)) wait(); //wait until doors open and correct floor.

    	endfloor[floor] = endfloor[floor] -1 ; //remove one from the list, representing one person thread exiting the method.
    	
    	notifyAll();
    	
    	
    }

    
    /* Interface for Lifts */
    public synchronized boolean liftAtFloor(int floor, Direction direction) {
    	
    	currentfloor = floor;
    	currentDirection = direction;
    	
    	//if all of the arrays have 0 in that floor that means there are no requests for people to enter/exit.
    	if ((endfloor[floor] == 0) && (startupfloor[floor] == 0 ) && (startdownfloor[floor] == 0 )) 
    	{
    		return false;
    	}
    	else if  ( (startupfloor[floor] != 0 ) && (direction == Direction.DOWN)) // OR if there is an up request but the elevator is going down
    	{
    		return false;
    	}
    	else if ((startdownfloor[floor] != 0 ) && (direction == Direction.UP)) // and vise versa.
    	{
    		return false;
    	}
    	
    	return true; // all other occassions would mean there is a request and direction is correct.
    	
    	
    }

    public synchronized void doorsOpen(int floor) throws InterruptedException {
    	
    	doorsOpen = true; // notify that doors are open so people can exit and then enter.
    	notifyAll();
    	
    	
    	while(endfloor[floor] > 0 ) wait(); //wait until all people that have reached their floor 
    	 
    	/*now that everyone has exited allow people in. 
    	Need to check direction again otherwise people who maybe going the other direction will enter as well.  */
    	
    	if(currentDirection ==Direction.UP) while(startupfloor[floor] > 0 ) wait();  
    		 
        else if(currentDirection == Direction.DOWN) while(startdownfloor[floor] > 0 ) wait();
    	
    	notifyAll();
    
    }

    public synchronized void doorsClosed(int floor) {
    	//If we have reached this method that means that all people have successfully exited and entered the lift. Need to notify that doors are closing
    	doorsOpen = false;
    	notifyAll();
    }
}
