package com.mrhart.state;

/**
 * GameState contains a list of states for Modes.
 * 
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.0
 * @since 11/01/2015
 *
 */

public class GameState {
	/*****************************************
	 * States
	 *****************************************/
	
	// Provided States
	public final static int NULL = 0;					// The Null State
	public final static int LOGO = 1;					// Mr. Hart Logo State
	public final static int MENU = 2;					// For the Main menu
	public final static int GAME = 3;					// Used for when the game is playing
	
	// Test States
	public final static int TEST_INPUT = 100;		// Used for when testing input
	public final static int TEST_SPRITES = 101; 	// Used for when testings Sprites
	public final static int TEST_BACKGROUNDS = 103; // Used for when testing backgrounds.
	public final static int TEST_SELECTION = 104; 	// Used for when testing Selections.
	public final static int TEST_TSP = 105; 		// Used for playing with the TSP.
	
	// User Created States, could start at 200 maybe?
	/* YOUR STATES GOES HERE */
	
	/*****************************************
	 * States [END]
	 *****************************************/
}