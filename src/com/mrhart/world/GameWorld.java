package com.mrhart.world;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.mrhart.assets.loaders.Loader_Meta;
import com.mrhart.collisions.CollisionUpdateable;
import com.mrhart.mode.Mode;
import com.mrhart.mode.Mode_Logo;
import com.mrhart.mode.Mode_Test_TSP;
import com.mrhart.mode.Mode_Test_Backgrounds;
import com.mrhart.mode.Mode_Test_Input;
import com.mrhart.mode.Mode_Test_Selection;
import com.mrhart.mode.Mode_Test_Sprites;
import com.mrhart.state.GameState;
import com.mrhart.state.StateUpdateable;

/**
 * GameWorld handles all updates to game objects. To maintain an OOP design, GameWorld should be
 * responsible for transitioning between Modes and merely calling their updates. Asset Loading
 * should be done in each mode.
 * 
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.20
 * 
 */
public class GameWorld {
	/*
	 * Settings
	 */
	private static final boolean DEBUG_ON = true;
	
	/*
	 * Named Constants
	 */
	// Files
	// Used to initialize the game in a certain mode
	private static int JUMP_TO_STATE = GameState.TEST_SPRITES;
	// Load Time
	private static final int LOAD_TIME = 100;
	
	/*
	 * Instance Vars
	 */
	// Current Mode
	protected Mode currentMode;
	private int nextState = 0;
	// TODO: DEV - Modes of the game
	private Mode_Logo mode_logo;
	// Test Modes
	private Mode_Test_Input mode_test_input;
	private Mode_Test_Sprites mode_test_sprite;
	private Mode_Test_Backgrounds mode_test_background;
	private Mode_Test_Selection mode_test_selection;
	private Mode_Test_TSP mode_test_tsp;
	// Loading Screen Assets
	protected AssetManager metaAssets;
	protected Animation loadingIcon;
	// Volume Modifier
	public static float volume = 1.0f;
	// Camera
	private OrthographicCamera camera;
	
	
	/**
	 * The constructor should initialize the game state and jump to any state the dev
	 * wishes to jump to for easy testing.
	 * 
	 * @since v2.20
	 */
	public GameWorld(OrthographicCamera camera) {
    	// Camera
    	this.camera = camera;

    	// TODO: DEV - Add Modes here and set JUMP_TO_STATE to that modes state to
    	//             jump to it.
    	switch(JUMP_TO_STATE){
    	case GameState.LOGO:
    		mode_logo = new Mode_Logo();
    		currentMode = mode_logo;
    		break;
    	case GameState.TEST_INPUT:
    		mode_test_input = new Mode_Test_Input();
    		currentMode = mode_test_input;
    		break;
    	case GameState.TEST_SPRITES:
    		mode_test_sprite = new Mode_Test_Sprites();
    		currentMode = mode_test_sprite;
    		break;
    	case GameState.TEST_BACKGROUNDS:
    		mode_test_background = new Mode_Test_Backgrounds(camera);
    		currentMode = mode_test_background;
    		break;
    	case GameState.TEST_SELECTION:
    		mode_test_selection = new Mode_Test_Selection();
    		currentMode = mode_test_selection;
    		break;
    	case GameState.TEST_TSP:
    		mode_test_tsp = new Mode_Test_TSP();
    		currentMode = mode_test_tsp;
    		break;
    	}
    	
    	// Assets
    	metaAssets = new AssetManager();
    	Loader_Meta.loadIcon(metaAssets);
	}

	/**
	 * Updates the current mode and when finished, disposes the currentMode and
	 * transitions to the next mode returned by the update method of currentMode.
	 * Does automatic loading of assets that have been loaded in the currentMode.
	 * 
	 * @param delta Seconds between each cpu cycle.
	 * @since v2.20
	 */
	public void update(float delta) {
		// Always load meta assets as a block element first, it will be necessary
		// to show whatever loading screen it is that you have
		if(metaAssets.getQueuedAssets() > 0){
			if(DEBUG_ON)
				System.err.println("Block-loading metaAssets");
			metaAssets.finishLoading();
			loadingIcon = Loader_Meta.getIcon(metaAssets);
		}
		
		// If the current mode is already loaded, then go ahead
		// and continue with the current mode's update.
		if(currentMode.isFinishedLoading()){
			// Update current mode
			nextState = currentMode.update(delta);
			// Update collisions if collision updateable
			if(currentMode instanceof CollisionUpdateable)
				((CollisionUpdateable) currentMode).updateCollisions();
			// Update states if state updateable
			if(currentMode instanceof StateUpdateable)
				((StateUpdateable) currentMode).updateStates();
			// Dispose the currentMode's assets if currentMode is finished
			if(nextState != GameState.NULL){
				currentMode.dispose();
			}
			// Return if currentMode's update returns null, we are not transitioning
			// to another mode.
			else{
				return;
			}
			
			// TODO: DEV - Decide what to do with the next state
			switch(nextState){
			case GameState.MENU:
				// Transition to this mode, for example:
				// mode_menu = new Mode_Menu()
				// currentMode = mode_menu;
				// break;
			}
		}
		// Else we are going to load little blocks of the assets until its finished
		else{
			// Load little block of assets
			currentMode.updateAssetManager(LOAD_TIME);
			if(DEBUG_ON)
				System.err.println("Current Mode's Load Progress: " + currentMode.getLoadProgress());
			
			// Check if current mode's assets are done loading to initialize
			// all texture regions and animations
			if(currentMode.isFinishedLoading()){
				currentMode.finalize();
				if(DEBUG_ON)
					System.err.println("Finalizing Current Mode's Assets");
			}
		}

	}
}
