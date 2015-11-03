package com.mrhart.world;

import com.mrhart.mode.Mode;
import com.mrhart.mode.Mode_Logo;
import com.mrhart.state.GameState;

/**
 * GameWorld handles all updates to game objects. To maintain an OOP design, GameWorld should be
 * responsible for transitioning between Modes and merely calling their updates. Asset Loading
 * should be done in each mode.
 * 
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 * 
 */
public class GameWorld {
	/*
	 * Named Constants
	 */
	// Used to initialize the game in a certain mode
	private static int JUMP_TO_STATE = GameState.STATE_LOGO;
	
	/*
	 * Instance Vars
	 */
	// Modes of the game
	protected Mode currentMode;
	private Mode_Logo mode_logo;
	private int nextState = 0;
	// Volume Modifier
	public static float volume = 1.0f;
	
	
	/**
	 * The constructor should initialize the game state and jump to any state the dev
	 * wishes to jump to for easy testing.
	 */
	public GameWorld() {
		// Start the GameState
    	GameState.start();
    	// This is the original jump state, this is how the game should always initialize
    	if(JUMP_TO_STATE == GameState.STATE_LOGO){
    		mode_logo = new Mode_Logo();
    		currentMode = mode_logo;
    	}
	}

	/**
	 * Updates the current mode and when finished, disposes the currentMode and
	 * transitions to the next mode returned by the update method of currentMode.
	 * 
	 * @param delta Seconds between each cpu cycle.
	 */
	public void update(float delta) {
		// Update current mode
		nextState = currentMode.update(delta);
		// Dispose the currentMode's assets if currentMode is finished
		if(nextState != GameState.STATE_NULL){
			currentMode.dispose();
		}
		// Return if currentMode's update returns null, we are not transitioning
		// to another mode.
		else{
			return;
		}
		
		// Decide what to do with the next state
		if(nextState == GameState.STATE_MENU){
			// Transition to this mode, for example:
			// mode_menu = new Mode_Logo()
			// currentMode = mode_menu;
		}
	}
}

