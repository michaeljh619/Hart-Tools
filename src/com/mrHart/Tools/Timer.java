package com.mrhart.tools;

import com.badlogic.gdx.utils.TimeUtils;

/**
 * 	Timer class is set up to wait a certain amount of seconds. The "isDone()" function should be checked
 * in GameWorld until it returns true, that's when the timer is finished.
 * 
 * 	This class uses the System's time in NanoSeconds as opposed to "frame time", this allows for maximum
 * accuracy of waited time.
 * 
 * 	One last note, you will notice that there are two init methods. The one taking the long parameter seems
 * as though it would be the only one worth using (other than when we first create the timer and it would
 * be redundant to pass in the seconds two times). However, the default init can, in a way, be used
 * polymorphically. 
 * 
 * 	Take, for example, a situation where you will wait a different amount of seconds
 * depending on what happens in game. If you use the parameterized init, you will need a set of if statements
 * that all call that init statement with the parameter and they will all have to be called at exactly the
 * time when you want to wait.
 * 
 * 	However, if you use the default init, then it will only be called once when you want the timer to
 * actually start running. The setters for secondsToWait can all be called whenever you realize the timer
 * needs to be changed and some of the setters can overwrite the setters of others depending on the path
 * the game takes.
 * 
 * 	One example:
 * 		if(a)
 * 			set to 5
 * 		else
 * 			set to 10
 * 		...
 * 		init()
 * 
 * Note: This class potentially has a huge fault for hackers to exploit. If the system time is used
 * for timing, a hacker can run a script that continuously changes the system time, resulting in a timer
 * that either never reaches its goal (by setting system time to the same time each CPU cycle) or a timer
 * that instantaneously reaches its goal (by setting system time to a further time each CPU cycle). Not
 * sure if I should be worried about this just yet.
 * 
 * 
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 * 
 */
public class Timer {
	// Named Constants
	public final static int SECONDS = 1;
	public final static int MILLISECONDS = 2;
	
	private final long NANO = 1000000000;
	private final long MICRO = 1000000;
	
	private final int TASKS_SIZE = 20;

	// Instance variables
	private long startTime;
	private long endTime;
	private long secondsToWait;
	private long millisecondsToWait;
	
//	public Task[] tasks;
	
	
	/*****************************************
	 * Main Methods
	 *****************************************/
	
	/**
	 * Default constructor
	 */
	public Timer() {
		secondsToWait = 0;

		startTime = endTime = 0;
	}

	/**
	 * Creates a new Timer that waits a certain amount of seconds.
	 * 
	 * @param secondsToWait
	 *            The amount of seconds to wait before the timer is finished
	 */
	public Timer(long inSecondsToWait) {
		secondsToWait = inSecondsToWait;

		startTime = endTime = 0;
	}

	/**
	 * Initializes the Timer to wait for the amount of time defined by the instance variable
	 * "secondsToWait" starting now.
	 * 
	 * @param callingMethod The string should be the method that is calling for the request.
	 * 						Format: "ClassName MethodName"
	 */
	public void initSeconds(String callingMethod) {
		// Turn off any potential disasters with milliseconds being active as well
		millisecondsToWait = 0;
		
		startTime = TimeUtils.nanoTime() / NANO;
		endTime = (startTime + secondsToWait) * NANO;
	}
	
	/**
	 * Initializes the Timer to wait for the set amount of seconds starting now. Timer is
	 * initialized with the new inSecondsToWait argument.
	 * 
	 * @param inSecondsToWait Sets the secondsToWait variable to this one
	 * @param callingMethod The string should be the method that is calling for the request.
	 * 						Format: "ClassName MethodName"
	 */
	public void initSeconds(long inSecondsToWait, String callingMethod) {
		// Turn off any potential disasters with milliseconds being active as well
		millisecondsToWait = 0;
				
		secondsToWait = inSecondsToWait;
		
		startTime = TimeUtils.nanoTime() / NANO;
		endTime = (startTime + secondsToWait) * NANO;
	}
	
	/**
	 * Initializes the Timer to wait for the amount of time defined by the instance variable
	 * "secondsToWait" starting now.
	 * 
	 * @param callingMethod The string should be the method that is calling for the request.
	 * 						Format: "ClassName MethodName"
	 */
	public void initMilliseconds(String callingMethod) {
		// Turn off any potential disasters with milliseconds being active as well
		secondsToWait = 0;
		
		startTime = TimeUtils.nanoTime() / MICRO;
		endTime = (startTime + millisecondsToWait) * MICRO;
	}
	
	/**
	 * Initializes the Timer to wait for the set amount of seconds starting now. Timer is
	 * initialized with the new inSecondsToWait argument.
	 * 
	 * @param inSecondsToWait Sets the secondsToWait variable to this one
	 * @param callingMethod The string should be the method that is calling for the request.
	 * 						Format: "ClassName MethodName"
	 */
	public void initMilliseconds(long inMillisecondsToWait) {
		// Turn off any potential disasters with milliseconds being active as well
		secondsToWait = 0;
		
		millisecondsToWait = inMillisecondsToWait;
		
		startTime = TimeUtils.nanoTime() / MICRO;
		endTime = (startTime + millisecondsToWait) * MICRO;
	}

	/**
	 * Checks if the timer is done waiting for the set amount of seconds. Once the timer
	 * is finished, the timer will be reset to 0 and can be re-used by setting a new time
	 * to secondsToWait and running init()
	 * 
	 * Important!!!!
	 * 	This function call has side effects. If you just want to check if the timer is running or not
	 * running, use isActive() function
	 * 
	 * @return Boolean telling whether the timer finished
	 */
	public boolean isDone() {
		if (startTime > 0 && endTime > 0){
			if (TimeUtils.nanoTime() >= endTime) {
				// Null all the tasks, since no tasks will be executed once the timer is finished
//				nullAllTasks();
				
				startTime = endTime = 0;
				return true;
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
//		nullAllTasks();
	}
	
	/*****************************************
	 * Main Methods [END]
	 *****************************************/

	
	
	/*****************************************
	 * Task Methods
	 *****************************************/
	
	/**
	 * Adds a new task to this timer, the task's schedule will be set to the start of the timer
	 * plus the time delay that is given.
	 * 
	 * delayType specifies what the timeDelay will be in seconds or milliseconds. Use timer's public static
	 * SECONDS or MILLISECONDS to specify what kind of delay you are using.
	 * 
	 * @param delayType Use Timer's constants SECONDS or MILLISECONDS
	 * @param timeDelay The amount of time to wait after the timer has started
	 * 
	 * @return Returns the position in the array that the task was added
	 */
//	public int addTask(int delayType, long timeDelay){
//		// This may be the very first Task being added, therefore
//		// check if the array still hasn't been initialized
//		if(tasks == null){
//			tasks = new Task[TASKS_SIZE];
//		}
//		
//		// Search for an empty task slot
//		for(int x = 0; x < TASKS_SIZE; x++){
//			if(tasks[x] == null){
//				tasks[x] = new Task(delayType, timeDelay);
//				return x;
//			}
//		}
//		
//		// If this code is reached, that means the size of the array has been exceeded and
//		// no further tasks can be added to the timer.
//		return -1;
//	}
	
	/**
	 * Releases all memory to all tasks held by this timer
	 */
//	public void nullAllTasks(){
//		// Search for an empty task slot
//		if(tasks != null){
//			for(int x = 0; x < TASKS_SIZE; x++){
//				if(tasks[x] != null){
//					tasks[x] = null;
//				}
//			}
//		}
//	}
	
	/*****************************************
	 * Task Methods [END]
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
	
	/*****************************************
	 * Getters & Setters [END]
	 *****************************************/
	
	
	
	/*****************************************
	 * Task Class
	 *****************************************/
	
	/**
	 * Tasks are used to schedule events on a timeline. So if you set up a timer for 20 seconds, but
	 * you also want something to happen at 10 seconds, you can schedule a task to occur at 10 seconds
	 * and once the task's isFinished method returns true, you can do whatever you need to do at the
	 * 10 seconds.
	 * 
	 * @author Michael James Hart, michaeljh619@yahoo.com
	 * @version v1.0
	 * @since 12/28/2014
	 */
	public class Task{
		// Instance Variables
		private long delay;
		
		private boolean taskFinished;
		
		/**
		 * Creates a new Task with a type of delay in either seconds or milliseconds and
		 * gives it the delay of time.
		 * 
		 * @param inDelayType Seconds or Milliseconds
		 * @param inTimeDelay How much time to wait
		 */
		public Task(int inDelayType, long inTimeDelay){
			delay = inTimeDelay;
			
			taskFinished = false;
			
			// Calculate how long the delay is
			if(inDelayType == MILLISECONDS)
				delay = (startTime + delay) * MICRO;
			else if(inDelayType == SECONDS)
				delay = (startTime + delay) * NANO;
		}
		
		/**
		 * Checks to see if the task's delay has passed
		 * 
		 * @return True or false if the task's delay is up
		 */
		public boolean isFinished(){
			if (startTime > 0 && endTime > 0){
				if (TimeUtils.nanoTime() >= delay) {
					taskFinished = true;
				}
			}
			
			return taskFinished;
		}
		
		
	}
	
	/*****************************************
	 * Task Class [END]
	 *****************************************/
}