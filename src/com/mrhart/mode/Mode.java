package com.mrhart.mode;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
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
	// StateID that is attached to a GameState
	public int stateID;
	// Assets
	protected AssetManager assets;
	
	public Mode(int stateID){
		// ID
		this.stateID = stateID;
		// Assets
		assets = new AssetManager();
	}
	
	
	
	/*************************************************************************
	 * 							Main Functions								 *
	 *************************************************************************/
	/**
	 * Update function to update objects
	 * 
	 * Returns an int that is the next state to update to. If there is
	 * to be no state update, then return STATE_NULL or 0.
	 * GameWorld should handle the state updates and memory management
	 * when update returns a state update.
	 * 
	 * Pre-Condition: All assets must be finished loading.
	 * 
	 * @param delta Number of seconds in between each frame.
	 */
	public abstract int update(float delta);
	
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
}
