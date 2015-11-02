package com.mrHart.World;

import com.mrHart.Assets.AssetLoader;
import com.mrHart.State.GameState;
import com.mrHart.Tools.Timer;

/**
 * GameWorld handles all updates to game objects, the timing of the world, transitions of backgrounds etc.
 * No rendering should be done here and most of the events' progression should rely on GameStates and
 * timers.
 * 
 * @author Michael
 * @version v1.00
 * @since 9/12/2014
 * 
 */
public class GameWorld {
	
	// Create a few timers for use
	protected Timer timerAlpha;
	
	// Important variable, used for ensuring things only occur once
	private int oneTimeAlpha;
	
	/**
	 * The Constructor should load the logo assets and initialize a timer for
	 * rendering, sounds, and game flow.
	 */
	public GameWorld() {
		// Set up alpha timer for the logo
		timerAlpha = new Timer();
		
		// Initialize Game
		AssetLoader.loadLogo();
		
		// Start the GameState
    	GameState.start();
    	
    	// Setup one time for sounds
    	oneTimeAlpha = 0;
	}

	/**
	 * Updates all of the objects that need to be updated in the game. Will need
	 * to communicate with GameState to see what objects actually need to be
	 * updated.
	 * 
	 * @param delta
	 */
	public void update(float delta) {
		updateGameObjects(delta);
		updateSound();
		updateTransitions();
		updateGameState();
	}

	/**
	 * Updates all Game Objects inside gameObjects package
	 */
	private void updateGameObjects(float delta) {
		switch (GameState.currentState) {
		
		}
	}

	/**
	 * Checks for events that would force the GameState to move to the next
	 * state. Inside each case may be more if/then statements to take into
	 * account that states may not always just load one state.
	 * 
	 * For example, GAME may go to PAUSED or TITLE.
	 */
	private void updateGameState() {
		switch (GameState.currentState) {
		
		}
	}

	/**
	 * Checks events to see if a sound should be played
	 */
	private void updateSound() {
		switch (GameState.currentState) {
			case GameState.STATE_LOGO:
				if(timerAlpha.isActive()){
					if (timerAlpha.tasks[3].isFinished() && oneTimeAlpha == 0){
						AssetLoader.logo_s_beat.play();
						oneTimeAlpha = 1;
					}
					else if (timerAlpha.tasks[9].isFinished() && oneTimeAlpha == 1){
						AssetLoader.logo_s_beat.play();
						oneTimeAlpha = 2;
					}
				}
		}
	}

	private void updateTransitions() {
		switch (GameState.currentState) {
		
		}
	}
}
