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
 * @version v2.00
 * @since 11/01/2015
 */
public abstract class Sprite {
	/*
	 * Instance Vars
	 */
	// Vectors
	public Vector2 position, velocity, acceleration;
	// Sizes
	public int width, height;
	
	public Sprite(int positionX, int positionY, int inWidth, int inHeight){
		position = new Vector2(positionX, positionY);
		velocity = new Vector2();
		acceleration = new Vector2();
		width = inWidth;
		height = inHeight;
	}

	// The update method
	public abstract void update(float delta);
	
	// Render methods
	public abstract void render(SpriteBatch batcher, float runtime);
	
	protected int getRenderPositionX(){
		return (int) (position.x - width/2);
	}
	protected int getRenderPositionY(){
		return (int) (position.y - height/2);
	}
}
