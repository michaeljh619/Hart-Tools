package com.mrhart.world;

import com.mrhart.mode.Mode;
import com.mrhart.mode.Mode_Logo;
import com.mrhart.mode.Mode_Test_Ship;
import com.mrhart.state.GameState;

/**
 * GameWorld handles all updates to game objects, the timing of the world, transitions of backgrounds etc.
 * No rendering should be done here and most of the events' progression should rely on GameStates and
 * timers.
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
	// Volume Modifier
	public static float volume = 1.0f;
	
	
	/**
	 * The Constructor should load the logo assets and initialize a timer for
	 * rendering, sounds, and game flow.
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

	public void update(float delta) {
		// Update current mode
		currentMode.update(delta);
	}
}

