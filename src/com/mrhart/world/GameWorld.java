package com.mrhart.world;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mrhart.Initializer;
import com.mrhart.mode.CollisionUpdateable;
import com.mrhart.mode.AssetsNotLoadedException;
import com.mrhart.mode.MetaMode;
import com.mrhart.mode.Mode;
import com.mrhart.mode.ModeBin;
import com.mrhart.mode.StateUpdateable;

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
	private static Class<? extends Mode> STARTING_MODE = Initializer.STARTING_MODE;
	
	/*
	 * Instance Vars
	 */
	// Current Mode
	protected Mode currentMode;
	protected MetaMode metaMode;
	protected boolean isInMeta;
	// Loading Screen Assets
	protected AssetManager metaAssets;
	protected AssetManager assets;
	// Volume Modifier
	public static float volume = 1.0f;
	// Camera
	protected OrthographicCamera camera;
	
	
	/**
	 * The constructor should initialize the game state and jump to any state the dev
	 * wishes to jump to for easy testing.
	 * 
	 * @since v2.20
	 */
	public GameWorld(OrthographicCamera camera) {
    	// Camera
    	this.camera = camera;
    	
    	// Assets
    	metaAssets = new AssetManager();
    	assets = new AssetManager();
    	
    	// CurentMode
    	ModeBin modeBin = new ModeBin();
    	modeBin.camera = camera;
    	try {
			currentMode = STARTING_MODE
					.getConstructor(ModeBin.class, AssetManager.class)
					.newInstance(modeBin, assets);
			currentMode.loadAssets();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// MetaMode
    	metaMode = new MetaMode(modeBin, assets, metaAssets);
    	metaMode.loadAssets();
    	isInMeta = false;
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
			metaMode.finalize();
		}
		
		if(assets.getProgress() < 1.0f)
			isInMeta = true;

		// If there are assets to be loaded in the AssetManager, then call the
		// metaMode's update method, which will load little chunks of assets.
		if(isInMeta){
			// Will update the meta mode which should be loading assets as well
			// as whatever else you want it to do. If it returns true and the
			// assets are not done loading, an AssetsNotLoaded Exception will
			// be thrown.
			try{
				updateMeta(delta);
			}
			catch(AssetsNotLoadedException e){
				e.printStackTrace();
			}
			if(DEBUG_ON)
				System.err.println("Current Mode's Load Progress: " + assets.getProgress());
			
			// Check if current mode's assets are done loading to initialize
			// all texture regions and animations
			if(!isInMeta){
				currentMode.finalize();
				if(DEBUG_ON)
					System.err.println("Finalizing Current Mode's Assets");
			}
		}
		
		// If the current mode is already loaded, then go ahead
		// and continue with the current mode's update.
		if(!isInMeta){
			// Update current mode
			Class<? extends Mode> nextMode = currentMode.update(delta);
			ModeBin nextModeBin = currentMode.getNextModeBin();
			if(nextModeBin == null){
				nextModeBin = new ModeBin();
			}
			// Update collisions if collision updateable
			if(currentMode instanceof CollisionUpdateable)
				((CollisionUpdateable) currentMode).updateCollisions();
			// Update states if state updateable
			if(currentMode instanceof StateUpdateable)
				((StateUpdateable) currentMode).updateStates();
			// Dispose the currentMode's assets if currentMode is finished
			if(nextMode != null){
				currentMode.unloadAssets();
			}
			// Return if currentMode's update returns null, we are not transitioning
			// to another mode.
			else{
				return;
			}
			
			// Construct next mode
			try {
				nextModeBin.lastMode = currentMode.getClass();
				nextModeBin.camera = camera;
				metaMode.modeBin = nextModeBin;
				currentMode = nextMode
						.getConstructor(ModeBin.class, AssetManager.class)
						.newInstance(nextModeBin, assets);
				currentMode.loadAssets();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Updates the MetaMode and asserts that when the MetaMode returns true to
	 * transition to another mode, that the assets AssetManager is finished
	 * loading.
	 * 
	 * @param delta
	 * @throws AssetsNotLoadedException
	 */
	private void updateMeta(float delta) throws AssetsNotLoadedException{
		if(metaMode.update(delta) == true){
			isInMeta = false;
		}
		else{
			isInMeta = true;
		}
		
		if(!isInMeta && assets.getProgress() < 1.0f)
			throw new AssetsNotLoadedException();
	}
}
