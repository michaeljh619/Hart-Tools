package com.mrhart.backend;

import java.util.ArrayList;

import com.badlogic.gdx.utils.TimeUtils;

/**
 * Timers are used to wait a certain amount of seconds or milliseconds. Timers use system time
 * for keeping track of time. Use init() to start counting and isDone() to check if the wait is over.
 * IsDone has the side effect of resetting the timer as well, so once it returns true, the timer will
 * stop counting.
 * 
 * Note: This class potentially has a huge fault for hackers to exploit. If the system time is used
 * for timing, a hacker can run a script that continuously changes the system time, resulting in a timer
 * that either never reaches its goal (by setting system time to the same time each CPU cycle) or a timer
 * that instantaneously reaches its goal (by setting system time to a further time each CPU cycle). Not
 * sure if I should be worried about this just yet. This would only apply when a user is running the game
 * on his cpu, otherwise a multiplayer game could not be exploited this way if a server is running Timers.
 * 
 * 
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.10
 * @since 11/01/2015
 * 
 */
public class Timer {
	/*
	 *  Named Constants
	 */
	// Second amounts
	private static final long NANO = 1000000000;
	private static final long MICRO = 1000000;
	
	/*
	 *  Instance variables
	 */
	// Times
	private long startTime;
	private long endTime;
	private long secondsToWait;
	private long millisecondsToWait;
	// Freeze List
	private int ID;
	private boolean wasFrozen = false;
	
	/*
	 * Static Variables
	 */
	// Timer Freeze array list
	private static ArrayList<Boolean> freezeList;
	private static ArrayList<Long> freezeListTimes;
	
	/*****************************************
	 * Main Methods
	 *****************************************/
	
	/**
	 * Default constructor; sets the ID of the timer to 0.
	 */
	public Timer() {
		ID = 0;
		secondsToWait = 0;
		startTime = endTime = 0;
	}
	/**
	 * Creates a timer with an ID that you specify. 
	 * 
	 * @param ID
	 */
	public Timer(int ID){
		this();
		this.ID = ID;
		/*
		 * Checks to see if the specified ID is not a part of the ArrayList already.
		 */
		if(ID == freezeList.size()){
			addFreezeID(ID);
		}
		// This means the dev put in an index that is too high, for example: ID's 0 and 1
		// are being used and the user creates a timer with a state ID of 4.
		else if(ID > freezeList.size()){
			System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
					+ "ID inserted is too high! Next ID should be: "
					+ freezeList.size());
		}
		// This means the dev is just playing games
		else if(ID < 0){
			System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
					+ "ID inserted must be greater than or equal to 0.");
		}
	}
	
	public void init(){
		startTime = TimeUtils.nanoTime();
	}
	
	/**
	 * Initializes the Timer to wait for the set amount of seconds starting now. Timer is
	 * initialized with the new inSecondsToWait argument.
	 * 
	 * @param inSecondsToWait Sets the secondsToWait variable to this one
	 */
	public void initSeconds(long inSecondsToWait){
		// Turn off any potential disasters with milliseconds being active as well
		millisecondsToWait = 0;
				
		secondsToWait = inSecondsToWait;
		
		startTime = TimeUtils.nanoTime() / NANO;
		endTime = (startTime + secondsToWait) * NANO;
	}
	
	/**
	 * Initializes the Timer to wait for the set amount of seconds starting now. Timer is
	 * initialized with the new inMillisecondsToWait argument.
	 * 
	 * @param inSecondsToWait Sets the millisecondsToWait variable to this one
	 */
	public void initMilliseconds(long inMillisecondsToWait) {
		// Turn off any potential disasters with milliseconds being active as well
		secondsToWait = 0;
		
		millisecondsToWait = inMillisecondsToWait;
		
		startTime = TimeUtils.nanoTime() / MICRO;
		endTime = (startTime + millisecondsToWait) * MICRO;
	}
	
	public long getElapsedMilliseconds(){
		long currentTime = TimeUtils.nanoTime();
		return (currentTime - startTime)/MICRO;
	}

	public long getElapsedNanoseconds(){
		long currentTime = TimeUtils.nanoTime();
		return currentTime - startTime;
	}
	
	/**
	 * Checks if the timer is done waiting for the set amount of seconds. Once the timer
	 * is finished, the timer will be reset to 0 and can be re-used by running an init method.
	 * 
	 * Important!!!!
	 * 	This function call has side effects. If you just want to check if the timer is running or not
	 * running, use isActive() function
	 * 
	 * @return Boolean telling whether the timer finished
	 */
	public boolean isDone() {
		
		// First checks if the timer is actually running, equivalent to calling
		// isActive()
		if (startTime > 0 && endTime > 0){
			// Now check to see if this timer's ID is part of the freeze list
			if(!freezeList.get(ID)){
				// If the timer was frozen previously and is now unfrozen
				if(wasFrozen){
					wasFrozen = false;
					startTime = TimeUtils.nanoTime();
					endTime = startTime + endTime - freezeListTimes.get(ID);
				}
				// Checks if the timer is actually past the waited time
				if (TimeUtils.nanoTime() >= endTime) {
					// Essentially resets the timer, returns true since timer is done
					startTime = endTime = 0;
					return true;
				}
			}
			// If the timer is frozen modify the wasFrozen flag
			else{
				wasFrozen = true;
			}
		}
		
		return false;
	}
	
	/**
	 * Tells you whether the timer is running or not
	 * 
	 * @return Boolean signifying whether the timer is active and counting
	 */
	public boolean isActive(){
		if (startTime > 0 && endTime > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Sets start and end time to 0
	 * @return
	 */
	public void reset(){
		startTime = endTime = 0;
	}
	
	/*****************************************
	 * Main Methods [END]
	 *****************************************/
	/*****************************************
	 * Getters & Setters
	 *****************************************/
	
	public long getSecondsToWait() {
		return secondsToWait;
	}

	public void setSecondsToWait(long secondsToWait) {
		this.secondsToWait = secondsToWait;
	}

	public long getMillisecondsToWait() {
		return millisecondsToWait;
	}

	public void setMillisecondsToWait(long millisecondsToWait) {
		this.millisecondsToWait = millisecondsToWait;
	}
	
	public int getID(){
		return ID;
	}
	
	/*****************************************
	 * Getters & Setters [END]
	 *****************************************/
	
	/*****************************************
	 * Static Functions
	 *****************************************/
	
	/**
	 * Initializes the freeze list for the ability to freeze timers.
	 */
	public static void initialize(){
		// Initialize the array list and add the default timer ID and set
		// it to false, since we are not freezing these timers by default.
		freezeList = new ArrayList<Boolean>();
		freezeListTimes = new ArrayList<Long>();
		addFreezeID(0);
	}
	
	/**
	 * Freezes all timers from counting that have the specified ID
	 * 
	 * @param ID The Timer's with this ID will be frozen.
	 */
	public static void freezeTimers(int ID){
		// Some error checking first
		if(ID >= freezeList.size() || ID < 0){
			System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
					+ "Parameter ID is out of the scope of freezeList!");
		}
		// Freeze all timers of this ID if not already frozen
		else if(!freezeList.get(ID)){
			freezeListTimes.set(ID, TimeUtils.nanoTime());
			freezeList.set(ID, true);
		}
		// User has called freeze timers while timers are already frozen
		else{
			System.err.println(Messages.WARNING + Messages.TYPE_BAD_FUNCTION_CALL
					+ "freezeTimers(" + ID + ") has been called after timers with ID "
					+ ID + "has already been called and not unfrozen!");
		}
	}
	
	/**
	 * Unfreezes all timers from counting that have the specified ID
	 * 
	 * @param ID The Timer's with this ID will be unfrozen.
	 */
	public static void unfreezeTimers(int ID){
		// Some error checking first
		if(ID >= freezeList.size() || ID < 0){
			System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
					+ "Parameter ID is out of the scope of freezeList!");
		}
		// Unfreeze all timers of this ID
		else{
			freezeList.set(ID, false);
		}
	}
	
	public static boolean isTimerFrozen(int ID){
		// Some error checking first
		if(ID >= freezeList.size() || ID < 0){
			System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
					+ "Parameter ID is out of the scope of freezeList!");
			return false;
		}
		else{
			return freezeList.get(ID);
		}
	}
	
	private static void addFreezeID(int ID){
		freezeList.add(false);
		freezeListTimes.add((long) 0);
	}
	
	/*****************************************
	 * Static Functions [END]
	 *****************************************/
	
	/*****************************************
	 * Main Method
	 *****************************************/
	/**
	 * Just a simple example to show how timers work and how freezing and unfreezing timers works.
	 * Essentially we create two timers, one with a default ID 0 and one with ID 1. The 0 ID is frozen as
	 * soon as initiated and the 1 ID runs. When the 1 ID timer isDone, then unfreeze the 0 ID timers.
	 * 
	 * @param args
	 */
	public static void main(String [] args){
		// Set up some constants
		final int TIMER_TIME = 5000;
		final int TIMER_1_TIME = 1000;
		final int TIMER_2_TIME = 2000;
		// Set up some variables
		long startingTime = TimeUtils.nanoTime();
		long endingTime;
		// Start up all the timers
		Timer.initialize();
		Timer timer = new Timer();
		System.out.println("'timer' ID: " + timer.getID());
		timer.initMilliseconds(TIMER_TIME);
		Timer timer1 = new Timer(1);
		System.out.println("'timer1' ID: " + timer1.getID());
		timer1.initMilliseconds(TIMER_1_TIME);
		Timer timer2 = new Timer();
		System.out.println("'timer2' ID: " + timer2.getID());
		
		// Freeze timer
		Timer.freezeTimers(0);
		
		// Break out of this loop when timer finishes, as it is currently frozen
		while(!timer.isDone()){
			if(timer1.isDone()){
				System.out.println("'timer1' is done! Unfreezing 'timer'");
				Timer.unfreezeTimers(0);
			}
		}
		System.out.println("'timer' is done!");
		// Now init the next timer
		timer2.initMilliseconds(TIMER_2_TIME);
		while(!timer2.isDone()){
			
		}
		System.out.println("'timer2' is done!");
		
		endingTime = TimeUtils.nanoTime();
		System.out.println("Expected time to finish: " 
				+ (TIMER_TIME + TIMER_1_TIME + TIMER_2_TIME) 
				+ "milliseconds");
		System.out.println("Actual time to finish: " 
				+ ((endingTime - startingTime)/MICRO) + "milliseconds");
	}
}