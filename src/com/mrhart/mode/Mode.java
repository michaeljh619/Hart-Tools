package com.mrhart.mode;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Modes are used for smooth transitions from different game states. Each
 * mode should have an update and a render function. They should also have a
 * stateID, which is essentially what state this Mode should be active in.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.10
 */
public abstract class Mode {
	/*
	 * Named Constants
	 */
	
	/*
	 * Instance Vars
	 */
	// Assets
	protected AssetManager assets;
	protected ModeBin modeBin;
	
	public Mode(ModeBin modeBin){
		// Assets
		assets = new AssetManager();
		// ModeBin
		this.modeBin = modeBin;
	}
	
	
	
	/*************************************************************************
	 * 							Main Functions								 *
	 *************************************************************************/
	/**
	 * Update function to update objects
	 * 
	 * Returns a Class that is the next state to update to. If there is
	 * to be no mode update, then return null.
	 * GameWorld should handle the mode updates and memory management
	 * when update returns a different class.
	 * 
	 * Pre-Condition: All assets must be finished loading.
	 * 
	 * @param delta Number of seconds in between each frame.
	 */
	public abstract Class<Mode> update(float delta);
	
	/**
	 * Render function to render objects to the screen
	 * 
	 * Pre-Condition: All assets must be finished loading.
	 * 
	 * @param batcher A SpriteBatch that has called begin()
	 * @param runtime Number of seconds since program has started
	 */
	public abstract void render(SpriteBatch batcher, float runtime);
	
	
	
	/*************************************************************************
	 * 							Assets Functions						     *
	 *************************************************************************/
	/**
	 * After all loading is finished, this function will get all the loaded
	 * assets from the AssetManager and pull them into instance variables in the
	 * child class.
	 */
	public abstract void finalize();
	
	/**
	 * Disposes assets in AssetManager
	 */
	public void dispose(){
		assets.dispose();
	}
	
	/**
	 * Checks if the AssetManager is finished loading
	 */
	public boolean isFinishedLoading(){
		return (assets.getProgress() >= 1.0f);
	}
	
	/**
	 * Calls the AssetManager's update method.
	 * 
	 * @return Boolean returned from AssetManager's update.
	 */
	public boolean updateAssetManager(){
		return assets.update();
	}

	/**
	 * Calls the AssetManager's update method for a set
	 * amount of milliseconds.
	 * 
	 * @param milliseconds Milliseconds to load for.
	 * @return Boolean returned from AssetManager's update.
	 */
	public boolean updateAssetManager(int milliseconds){
		return assets.update(milliseconds);
	}
	
	/**
	 * Returns the current progress of the AssetManager's loading
	 * as a float:
	 *      - 0.0f = Nothing Loaded
	 *      - 1.0f = Everything Loaded
	 * @return
	 */
	public float getLoadProgress(){
		return assets.getProgress();
	}
	
	/**
	 * Gets the ModeBin that will be passed to the next Mode. If you have
	 * nothing to send, merely return the ModeBin you are currently using.
	 * Otherwise, construct some child class of ModeBin and set its camera
	 * to the camera the old ModeBin was using.
	 * 
	 * @return
	 */
	public abstract ModeBin getNextModeBin();
}
