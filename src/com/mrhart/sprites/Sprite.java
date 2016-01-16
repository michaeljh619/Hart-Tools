package com.mrhart.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mrhart.renderable.RenderableObject;

/**
 * A sprite that has a position, velocity, acceleration, width, and height.
 * This object will be able to update and render to the screen.
 * 
 * Note: Sprite's positions are origin centered! Of course, if you extend the 
 * 		 Sprite, you can make it however you like.
 *
 * Note: If you will not be using acceleration in your game, feel free to remove
 * 		 that instance variable so you don't waste RAM.
 *
 * @author Michael James Hart, MrHartGames@yahoo.com
 * @version v2.50
 */
public class Sprite {
	/*
	 * Instance Vars
	 */
	// Vectors
	public Vector2 position, velocity, acceleration;
	private Vector2 bastardVector;
	// Sizes
	public int width, height;
	// Flags
	public boolean isRemovable;
	// Graphics
	public RenderableObject renderObject;
	
	/**
	 * Creates a new Sprite at a position with a width and height
	 * 
	 * @version v2.51
	 * @since v2.00
	 * @param positionX
	 * @param positionY
	 * @param inWidth
	 * @param inHeight
	 */
	public Sprite(int positionX, int positionY, int inWidth, int inHeight){
		this(positionX, positionY, inWidth, inHeight, null);
	}
	
	public Sprite(int positionX, int positionY, int width, int height,
			RenderableObject renderObject){
		// Set up vectors
		position = new Vector2(positionX, positionY);
		velocity = new Vector2();
		acceleration = new Vector2();
		// Set up bastard vectors for calculations
		bastardVector = new Vector2();
		// Set up dimensions
		this.width = width;
		this.height = height;
		// Set up flags
		isRemovable = false;
		// Set up Render Object
		this.renderObject = renderObject;
	}

	/**
	 * Updates the Sprite's vectors, can be overwritten to update more things
	 * in child classes.
	 * 
	 * @param delta
	 */
	public void update(float delta){
		updateVectors(delta);
	}
	
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
	public void render(SpriteBatch batcher, float runtime){
		if(renderObject != null){
			batcher.draw(renderObject.getTextureRegion(runtime), 
					position.x - width/2, position.y - height/2, 
					width, height);
		}
	}
	
	protected int getRenderPositionX(){
		return (int) (position.x - width/2);
	}
	protected int getRenderPositionY(){
		return (int) (position.y - height/2);
	}
	
	
	/********************************
	 *     Getters and Setters
	 ********************************/
	public Vector2 getPosition(){
		return position;
	}
	public Vector2 getVelocity(){
		return velocity;
	}
	public void setPosition(float x, float y){
		position.set(x, y);
	}
	public void setVelocity(float x, float y){
		velocity.set(x, y);
	}
	/**
	 * Underneath, the position instance var is modified.
	 * 
	 * @since v2.51
	 * @param x
	 * @param y
	 */
	public void setRenderPosition(float x, float y){
		position.set(x + width/2, y + height/2);
	}
}
