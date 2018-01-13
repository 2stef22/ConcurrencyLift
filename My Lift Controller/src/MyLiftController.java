
package lift;

/**
 * Null implementation of a Lift Controller.
 * @author K. Bryson
 */
public class MyLiftController implements LiftController {
	
	int gotofirst = 9;
	int fromfirst = 9;
	int currentfloor;
	Direction dir ; // = Direction.UP;
	boolean passengerIn = false;
	
	//int[] endfloor = {9,9};
	//int[] startfloor = {9,9};
	//Direction[] direction;
	//boolean[] passengerin ;
	//int threadflag;
	
	int[] endfloor = {0,0,0,0,0,0,0,0,0};
	int[] startupfloor = {0,0,0,0,0,0,0,0,0};
	int[] startdownfloor = {0,0,0,0,0,0,0,0,0};
	
	
	
    /* Interface for People */
    public synchronized void pushUpButton(int floor) throws InterruptedException {
    	
    	//System.out.println("UP");
    	startupfloor[floor] = startupfloor[floor] + 1;
    	
    	while((startupfloor[currentfloor] == 0) || (dir != Direction.UP) ) wait();
    	startupfloor[currentfloor] = startupfloor[currentfloor] -1;
    	notifyAll();
    	System.out.println("wow I entered");
    /*	//System.out.println("My name is");
    	//System.out.println(Thread.currentThread().getName());
    	fromfirst = floor;
    	dir = Direction.UP;
    	System.out.println("Up");
    	System.out.println(fromfirst);
		System.out.println(gotofirst);
    	while (fromfirst !=currentfloor)wait();
    	//passengerIn = true;
    	notifyAll();
    	//System.out.println("A"); */
    }

    public synchronized void pushDownButton(int floor) throws InterruptedException {
    	
    	//System.out.println("DOWN");
    	startdownfloor[floor] = startdownfloor[floor] + 1;
    	while((startdownfloor[currentfloor] == 0) || (dir != Direction.DOWN) ) wait();
    	startdownfloor[currentfloor] = startdownfloor[currentfloor]  - 1 ;
    	notifyAll();
    	System.out.println("wow I entered");
    	/*  
    	System.out.println("My name is");
    	System.out.println(Thread.currentThread().getName());
    	fromfirst = floor;
    	dir = Direction.DOWN;
    	System.out.println("Down");
    	System.out.println(fromfirst);
		System.out.println(gotofirst);
    	while (fromfirst != currentfloor) 
    		{
    			wait();
    		}
    	//passengerIn = true;
    	notifyAll();
    	System.out.println("O");

    	*/
    }
    
    public synchronized void selectFloor(int floor) throws InterruptedException {
    	
    	endfloor[floor] = endfloor[floor] + 1; // change + -  1 according to direction?
    	
    	System.out.println(floor);
    	System.out.println(endfloor[floor]);
    	while (endfloor[currentfloor] == 0) wait();
    	endfloor[currentfloor] = endfloor[currentfloor] -1 ;
    	
    	notifyAll();
    	/*
    	System.out.println("got in");
    	gotofirst = floor;
    	passengerIn = true;
    	while (gotofirst != currentfloor) wait();
    	*/
    	
    	//notifyAll();
    	//System.
    	
    }

    
    /* Interface for Lifts */
    public boolean liftAtFloor(int floor, Direction direction) {
    	currentfloor = floor;
    	dir = direction;
    	//System.out.println("the c floor");
    	//System.out.println(currentfloor);
    	//System.out.println(gotofirst);
    	//System.out.println(passengerIn);
    	if (endfloor[floor] != 0)
    	{
    		return true;
    	}
    	if  (startupfloor[floor] == 0 ) 
    	{
    		if (startdownfloor[floor] == 0 )
				{
					return false;
				}
				else if ( (direction == Direction.UP))
				{
					return false;
				}
    	}
    	else if(startupfloor[floor] != 0)
    	{
    		if (direction == Direction.DOWN)
    		{
    			return false;
    		}
    	}
    	
    	
    	return true;
    	//if ((startupfloor[floor] != 0 ) && (direction == Direction.DOWN) ||  (startdownfloor[floor] != 0) && (direction == Direction.UP) || (startupfloor[floor] == 0 ) || (startupfloor[floor] == 0 ) )
    	//{
    		//return false;
    	//}
 
    	//else if (endfloor[floor] == 0)
    //	{
    		//return false;
    	//}
    	//else return true;
    	
    	/*if (passengerIn)
    	{
    		if (currentfloor != gotofirst) return false;
    		else return true;
    	}
    	else if (currentfloor != fromfirst || (dir != direction))
   
    		{
    			
    			//System.out.println(fromfirst);
    			//System.out.println(gotofirst);
    			return false;
    		}*/
    	
    	//else if (currentfloor != gotofirst && (!passengerIn))
    	//{
    	//	return false;
    	//}
    	
        ///return true;
    }

    public synchronized void doorsOpen(int floor) throws InterruptedException {
    	//System.out.println("LA");
    	//System.out.println(passengerIn);
		//System.out.println(fromfirst);
    	
    	//System.out.println(gotofirst);
		//System.out.println(currentfloor);
    	
    	while((startupfloor[currentfloor] == 0 ) && endfloor[currentfloor] == 0 && startdownfloor[currentfloor] == 0 ) wait();
    	
    	notifyAll();
    	//while ((currentfloor != fromfirst) && (!passengerIn) || (currentfloor != gotofirst) && (passengerIn) ) wait();
    	//passengerIn  = !passengerIn;
    	//notifyAll();
    	//System.out.println("smth");
    	/*
    	if (passengerIn)
    	{
    		//while(currentfloor != gotofirst) wait();
    		if (currentfloor == gotofirst)
    		{
    		passengerIn = false;
    		//notifyAll();
    		System.out.println("reached");
    		}
    	}
    	
    	else
    	{
    		while (currentfloor != fromfirst) wait();
    	  	//passengerIn = true;		
    		notifyAll();
    		System.out.println("open");
    	}	*/	
    	
    }

    public void doorsClosed(int floor) {
    	
    }
}
