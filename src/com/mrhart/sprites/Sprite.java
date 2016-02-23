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
 * @version v2.60
 */
public abstract class Sprite implements Comparable<Sprite> {
	/*
	 * Instance Vars
	 */
	// ID
	private int renderLayer;
	// Vectors
	public Vector2 position, velocity, acceleration;
	private Vector2 bastardVector;
	// Sizes
	public int width, height;
	// Flags
	public boolean isRemovable;
	// Graphics
	public RenderableObject renderObject;
	// Comparable Methods
	private boolean comparableX = false;
	private boolean comparableY = false;
	private boolean comparableRenderLayer = true;
	
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
	
	/**
	 * By calling this constructor, you acknowledge that you know what you are
	 * doing. Calling this constructor creates a sprite whose original position
	 * will be top left assigned. In other words, if you call this constructor
	 * with a positionX and positionY of (0, 0), you will get a sprite whose
	 * origin position is at (width/2, height/2). topLefted is a dummy variable
	 * and serves no purpose other than making this constructor unique, it can
	 * be set to true or false and will not affect what will happen.
	 * 
	 * @param positionX
	 * @param positionY
	 * @param inWidth
	 * @param inHeight
	 * @param topLefted
	 */
	public Sprite(int positionX, int positionY, int inWidth, int inHeight, boolean topLefted){
		this(positionX + inWidth/2, positionY + inHeight/2, inWidth, inHeight, null);
	}
	
	/**
	 * By calling this constructor, you acknowledge that you know what you are
	 * doing. Calling this constructor creates a sprite whose original position
	 * will be top left assigned. In other words, if you call this constructor
	 * with a positionX and positionY of (0, 0), you will get a sprite whose
	 * origin position is at (width/2, height/2). topLefted is a dummy variable
	 * and serves no purpose other than making this constructor unique, it can
	 * be set to true or false and will not affect what will happen.
	 * 
	 * @param positionX
	 * @param positionY
	 * @param inWidth
	 * @param inHeight
	 * @param topLefted
	 */
	public Sprite(int positionX, int positionY, int inWidth, int inHeight,
			RenderableObject renderObject, boolean topLefted){
		this(positionX + inWidth/2, positionY + inHeight/2, inWidth, inHeight, renderObject);
	}

	public Sprite(int positionX, int positionY, int inWidth, int inHeight,
			RenderableObject renderObject, int ID, boolean topLefted){
		this(positionX + inWidth/2, positionY + inHeight/2, inWidth, inHeight, renderObject, ID);
	}

	public Sprite(int positionX, int positionY, int width, int height,
			RenderableObject renderObject){
		this(positionX, positionY, width, height, renderObject, 0);
	}
	
	public Sprite(int positionX, int positionY, int width, int height,
			RenderableObject renderObject, int ID){
		// Set up ID
		this.renderLayer = ID;
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
	
	protected float getRenderPositionX(){
		return position.x - width/2;
	}
	protected float getRenderPositionY(){
		return position.y - height/2;
	}
	
	protected float getLeftMostPositionX(){
		return position.x - width/2;
	}
	protected float getTopMostPositionY(){
		return position.y - height/2;
	}
	protected float getRightMostPositionX(){
		return position.x + width/2;
	}
	protected float getBotMostPositionY(){
		return position.y + height/2;
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

	
	
	/*************************************************************************
	 * 							Comparable Methods
	 *************************************************************************/
	/**
	 * After calling this function, the next call to compareTo will use the
	 * compareTo_X method.
	 */
	protected void use_Comparable_X(){
		comparableX = true;
		comparableY = false;
		comparableRenderLayer = false;
	}
	/**
	 * Compares based on the left most endpoint of the Sprite. If this is a
	 * Collidable Sprite, then its CollisionArea's left most endpoint will
	 * be used.
	 * 
	 * @param other
	 * @return
	 */
	private int compareTo_X(Sprite other){
		/*
		 *  Precedence order of compare
		 */
		float rPosThis = getLeftMostPositionX();
		float rPosOther = other.getLeftMostPositionX();
		// Position first
		if(rPosThis > rPosOther)
			return 1;
		else if(rPosThis < rPosOther)
			return -1;
		else
			return 0;
	}

	/**
	 * After calling this function, the next call to compareTo will use the
	 * compareTo_X method.
	 */
	protected void use_Comparable_Y(){
		comparableX = false;
		comparableY = true;
		comparableRenderLayer = false;
	}
	/**
	 * Compares based on the left most endpoint of the Sprite. If this is a
	 * Collidable Sprite, then its CollisionArea's left most endpoint will
	 * be used.
	 * 
	 * @param other
	 * @return
	 */
	private int compareTo_Y(Sprite other){
		/*
		 *  Precedence order of compare
		 */
		float rPosThis = getTopMostPositionY();
		float rPosOther = other.getTopMostPositionY();
		// Position first
		if(rPosThis > rPosOther)
			return 1;
		else if(rPosThis < rPosOther)
			return -1;
		else
			return 0;
	}
	
	/**
	 * After calling this function, the next call to compareTo will use the
	 * compareTo_X method.
	 */
	protected void use_Comparable_RenderLayer(){
		comparableX = false;
		comparableY = false;
		comparableRenderLayer = true;
	}
	/**
	 * Compares based on the render layer of the Sprite. Sprites with lower
	 * render layers will be rendered first. If Sprites are at equal render
	 * layers... TODO
	 * 
	 * @param other
	 * @since v2.50
	 * @version v1.10
	 * @return
	 */
	private int compareTo_RenderLayer(Sprite other){
		/*
		 *  Precedence order of compare
		 */
		int thisRenderLayer = renderLayer;
		int otherRenderLayer = other.renderLayer;
		// Position first
		if(thisRenderLayer > otherRenderLayer)
			return 1;
		else if(thisRenderLayer < otherRenderLayer)
			return -1;
		else
			return this.compareTo_SameRenderLayer(other);
	}
	
	/**
	 * This method will decide how sprites are rendered when they are at the
	 * same render layer. If you don't care, just return 0. Otherwise, return
	 * -1 for lower level sprites and 1 for sprites that are rendered at a 
	 * higher level.
	 * 
	 * @param other The other Sprite to compareTo.
	 * @return
	 */
	protected int compareTo_SameRenderLayer(Sprite other){
		return 0;
	}
	
	/**
	 * This function will change what it's comparing based on what the last
	 * "use_Comparable_" function was called.
	 */
	@Override
	public int compareTo(Sprite other) {
		if(comparableX)
			return compareTo_X(other);
		else if(comparableY)
			return compareTo_Y(other);
		else
			return compareTo_RenderLayer(other);
	}

	public int getRenderLayer() {
		return renderLayer;
	}

	public void setRenderLayer(int renderLayer) {
		this.renderLayer = renderLayer;
	}
}
