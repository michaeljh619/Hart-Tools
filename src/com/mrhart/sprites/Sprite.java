package com.mrhart.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * A sprite that has a position, velocity, acceleration, width, and height.
 * This object will be able to update and render to the screen.
 *
 * Note: If you will not be using acceleration in your game, feel free to remove
 * 		 that instance variable so you don't waste RAM.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.01
 * @since 11/01/2015
 */
public abstract class Sprite {
	/*
	 * Instance Vars
	 */
	// Vectors
	public Vector2 position, velocity, acceleration;
	private Vector2 bastardVector;
	// Sizes
	public int width, height;
	
	/**
	 * Creates a new Sprite at a position with a width and height
	 * 
	 * @param positionX
	 * @param positionY
	 * @param inWidth
	 * @param inHeight
	 */
	public Sprite(int positionX, int positionY, int inWidth, int inHeight){
		// Set up vectors
		position = new Vector2(positionX, positionY);
		velocity = new Vector2();
		acceleration = new Vector2();
		// Set up bastard vectors for calculations
		bastardVector = new Vector2();
		// Set up dimensions
		width = inWidth;
		height = inHeight;
	}

	// The update method
	public abstract void update(float delta);
	
	/**
	 * Updates the position based on velocity and the velocity based on the 
	 * acceleration; scaled by delta.
	 * 
	 * @param delta
	 * @since v2.01
	 */
	protected void updateVectors(float delta){
		// Make a copy of acceleration and add it to velocity
		if(!acceleration.isZero()){
			bastardVector.set(acceleration.x, acceleration.y);
			velocity.add(bastardVector.scl(delta));
		}
		// Make a copy of velocity and add it to position
		if(!velocity.isZero()){
			bastardVector.set(velocity.x, velocity.y);
			position.add(bastardVector.scl(delta));
		}
	}
	
	// Render methods
	public abstract void render(SpriteBatch batcher, float runtime);
	
	protected int getRenderPositionX(){
		return (int) (position.x - width/2);
	}
	protected int getRenderPositionY(){
		return (int) (position.y - height/2);
	}
}
