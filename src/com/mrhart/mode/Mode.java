package com.mrhart.mode;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Modes are used for smooth transitions from different game states. Each
 * mode should have an update and a render function. They should also have a
 * stateID, which is essentially what state this Mode should be active in.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */
public abstract class Mode {
	// StateID that is attached to a GameState
	public int stateID;
	
	/*
	 * Update function to update objects
	 * 
	 * Returns an int that is the next state to update to. If there is
	 * to be no state update, then return STATE_NULL or 0.
	 * GameWorld should handle the state updates and memory management
	 * when update returns a state update.
	 */
	public abstract int update(float delta);
	
	/*
	 * Render function to render objects to the screen
	 */
	public abstract void render(SpriteBatch batcher, float runtime);
}
