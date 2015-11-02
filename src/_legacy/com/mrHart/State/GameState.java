package com.mrHart.State;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * GameState controls the flow of the game. Basically, the game is always in one State and one SubState.
 * If you wish to change a GameState, then it should be requested using the requestState method. This allows
 * for you to finish the current CPU cycle and then end with processing the request.
 * 
 * A State is the main part of the game that is running, for example the game or the menu or the loading
 * logo. However SubStates are not necessarily specific to States; for example the SubState 
 * "Transition In" can be used with virtually any State to tell you when that particular game State is
 * just beginning.
 * 
 * Lastly, as requests can come from many locations (GameWorld, InputHandler, etc.), a logger should be 
 * used with this class, as it logs the requests original location to the console. Merely fill in the 
 * final logger's [TOP CLASS NAME] with the main logger's class name.
 * 
 * 
 * Note: GameState is a purely static class, it should never be instantiated.
 * 
 * @author Michael
 * @version v1.5
 * @since 12/27/2014
 *
 */

public class GameState {
	
	// Logger objects
	private final static Logger logger = [TOP CLASS NAME].logger;
	
	
	
	
	
	/*****************************************
	 * States
	 *****************************************/
	
	// Provided States
	public final static int STATE_NULL = 0;		// The Null State
	public final static int STATE_LOGO = 1;		// Mr. Hart Logo State
	public final static int STATE_MENU = 2;		// For the Main menu
	public final static int STATE_GAME = 3;		// Used for when the game is playing
	
	// User Created States
	/* YOUR STATES GOES HERE */
	
	/*****************************************
	 * States [END]
	 *****************************************/
	
	
	
	
	
	/*****************************************
	 * SubStates
	 *****************************************/
	
	// Provided SubStates
	public final static int SUBSTATE_NULL = 0;				// Self-Explanatory
	public final static int SUBSTATE_TRANSITION_IN = 1;		// Used for beginning the state
	public final static int SUBSTATE_TRANSITION_OUT = 2;	// Used for ending the state
	public final static int SUBSTATE_NORMAL = 3;			// The Generic SubState
	public final static int SUBSTATE_PAUSED = 4;			// When the Game is Paused
	
	public final static int SUBSTATE_STAGE_1 = 5;			// Used for whatever you like
	public final static int SUBSTATE_STAGE_2 = 6;			// Used for whatever you like
	public final static int SUBSTATE_STAGE_3 = 7;			// Used for whatever you like
	public final static int SUBSTATE_STAGE_4 = 8;			// Used for whatever you like
	public final static int SUBSTATE_STAGE_5 = 9;			// Used for whatever you like
	public final static int SUBSTATE_STAGE_6 = 10;			// Used for whatever you like
	public final static int SUBSTATE_STAGE_7 = 11;			// Used for whatever you like
	public final static int SUBSTATE_STAGE_8 = 12;			// Used for whatever you like
	public final static int SUBSTATE_STAGE_9 = 13;			// Used for whatever you like
	public final static int SUBSTATE_STAGE_10 = 14;			// Used for whatever you like
	
	// User Created SubStates
	/* YOUR SUBSTATES GO HERE */
	
	/*****************************************
	 * SubStates [END]
	 *****************************************/
	
	
	
	
	
	
	// The current State of the game
	public static int currentState;
	public static int currentSubState;
	
	// To see if a request has been issued
	public static boolean stateHasBeenRequested;
	public static boolean subStateHasBeenRequested;
	
	// Current Request to be processed
	public static int requestedState;
	public static int requestedSubState;
	
	
	
	
	
/*****************************************
 * Methods
 *****************************************/
	
	/**
	 * Initializes the Game States. Game State is a static class, therefore all variables must be initialized
	 * with start() method, otherwise static variables keep their spot in RAM even after the program is closed.
	 */
	public static void start(){
		// Set Static variables to null
		currentState = STATE_LOGO;
		currentSubState = SUBSTATE_NULL;
		
		stateHasBeenRequested = false;
		subStateHasBeenRequested = false;
		
		requestedState = STATE_NULL;
		requestedSubState = SUBSTATE_NULL;
		
		logger.log(Level.FINER, "\n[GameState Log | start]{\n"
				+ "\tGameState Initialized\n}\n");
	}
	
	
	
	
	
	/*****************************************
	 * Methods Handling State Requests
	 *****************************************/
	
	/**
	 * Request is used to pass requests into the GameWorld, this is to prevent GameStates from being called
	 * directly from classes that should not have this access. Requests will be sent to the GameWorld function
	 * "updateGameState", this will allow you to do any further processing before actually updating the
	 * the game to a new gamestate.
	 * 
	 * @param state The argument that is passed should be the state that is being requested. For example,
	 * 				if the pause button is pressed in InputHandler use request(GameState.PAUSE);
	 * @param callingMethod The string should be the method that is calling for the request.
	 * 						Format: "ClassName MethodName"
	 */
	public static void requestState(int state, String callingMethod){
		stateHasBeenRequested = true;
		requestedState = state;

		// Logs where the request originated from
		logger.log(Level.FINE, "[GameState Log | requestState]{\n"
				+ "\tcurrentState: " + currentState + "\n"
				+ "\trequestedState: " + requestedState + "\n"
				+ "\t------------------"
				+ "\tRequested from:\n" 
				+ "\tClass: " + callingMethod.split(" ")[0]
				+ "\t | Method: " + callingMethod.split(" ")[1] + "\n}");
	}
	
	/**
	 * Clears all requests
	 */
	public static void clearRequestState(){
		stateHasBeenRequested = false;
		requestedState = STATE_NULL;
		
		logger.log(Level.FINER, "\n[GameState Log | clearRequestState]{\n"
				+ "\tState Request Cleared\n}\n");
	}
	
	/**
	 * Sets the State to the requested State and then clears the request. Also resets the current
	 * SubState, this way SubState's do not overlap.
	 */
	public static void processRequestedState(){
		currentState = requestedState;
		clearRequestState();
		
		// Since we are moving to a new state, the SubState should be set to null
		
		logger.log(Level.FINER, "\n[GameState Log | processRequestedState]{\n"
				+ "\tState Request Processed\n}\n");
	}
	
	/*****************************************
	 * Methods Handling State Requests [END]
	 *****************************************/
	
	
	
	
	
	/*****************************************
	 * Methods Handling SubState Requests
	 *****************************************/
	
	/**
	 * RequestSubState is used to pass requests into the GameWorld, this is to prevent GameStates from being called
	 * directly from classes that should not have this access. Requests will be sent to the GameWorld function
	 * "updateGameState", this will allow you to do any further processing before actually updating the
	 * the game to a new gamestate.
	 * 
	 * @param state The argument that is passed should be the state that is being requested. For example,
	 * 				if the pause button is pressed in InputHandler use request(GameState.PAUSE);
	 * @param callingMethod The string should be the method that is calling for the request.
	 * 						Format: "ClassName MethodName"
	 */
	public static void requestSubState(int subState, String callingMethod){
		subStateHasBeenRequested = true;
		requestedSubState = subState;
		
		// Logs where the request originated from
		logger.log(Level.FINE, "\n[GameState Log | requestSubState]{\n"
				+ "\tcurrentSubState: " + currentSubState + "\n"
				+ "\trequestedSubState: " + requestedSubState + "\n"
				+ "\t------------------"
				+ "\tRequested from:\n" 
				+ "\tClass: " + callingMethod.split(" ")[0]
				+ "\t | Method: " + callingMethod.split(" ")[1] + "\n}\n");
	}
	
	/**
	 * Clears the current substate
	 */
	public static void clearRequestSubState(){
		subStateHasBeenRequested = false;
		requestedSubState = SUBSTATE_NULL;
		
		logger.log(Level.FINER, "\n[GameState Log | clearRequestSubState]{\n"
				+ "\tSubState Request Cleared\n}\n");
	}
	
	/**
	 * Sets the subState to the requested subState and then clears the request
	 */
	public static void processRequestedSubState(){
		currentSubState = requestedSubState;
		clearRequestSubState();
		
		logger.log(Level.FINER, "\n[GameState Log | processRequestedSubState]{\n"
				+ "\tSubState Request Processed\n}\n");
	}
	
	/*****************************************
	 * Methods Handling SubState Requests [END]
	 *****************************************/
	
	
	
	
	
/*****************************************
 * Methods [END]
 *****************************************/
}