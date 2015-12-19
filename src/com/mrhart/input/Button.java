package com.mrhart.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mrhart.backend.Debuggable;
import com.mrhart.backend.Touch;
import com.mrhart.renderable.RenderableObject;
import com.mrhart.settings.Settings_Input;
import com.mrhart.shapes.Hart_Shape2D;

/**
 * An onscreen UI button that can be used for input from the user.
 *
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v3.00
 */
public class Button implements Debuggable{
	/*
	 * Instance Variables
	 */
	// Dimensions
	protected Vector2 origin;
	protected int width, height;
	// Touch
	protected Hart_Shape2D shape;
	// Flags
	protected boolean touched;
	protected boolean activatedOnce;
	// Graphics
	protected RenderableObject renderObject;
	protected RenderableObject onPressRenderObject;
	// Variables for methods
	protected Vector2 touchIndex;
	protected float tempX, tempY;
	
	public Button(Vector2 position, int width, int height, Hart_Shape2D shape){
		this(position, width, height, shape, null);
	}
	public Button(Vector2 position, int width, int height,
			Hart_Shape2D shape, RenderableObject renderObject){
		this(position, width, height, shape, renderObject, null);
	}
	public Button(Vector2 position, int width, int height,
			Hart_Shape2D shape, RenderableObject renderObject,
			RenderableObject onPressRenderObject){
		// Set up flags
		touched = false;
		// Set up dimensions
		this.width = width;
		this.height = height;
		origin = position;
		// Touch
		this.shape = shape;
		// RenderObjects
		this.renderObject = renderObject;
		this.onPressRenderObject = onPressRenderObject;
	}
	
	
	/*****************************************
	 * Main Methods
	 *****************************************/
	/**
	 * Updates the button, checking for touches
	 */
	public void update(){
		if(touched){
			activatedOnce = true;
		}
		
		touchIndex = getTouchIndex();
		
		// Check if the button is being held down
		if(touchIndex == null){
			reset();
		}
		else{
			touched = true;
		}
	}
	
	/**
	 * Renders the button to the screen, performs a null check so we never
	 * crash.
	 * 
	 * @param batcher
	 */
	public void render(SpriteBatch batcher, float runtime) {
		if(renderObject != null){
			batcher.draw(renderObject.getTextureRegion(runtime), 
					origin.x - width/2, origin.y - height/2,
					width, height);
		}
	}
	
	/*****************************************
	 * Main Methods [END]
	 *****************************************/
	
	/*****************************************
	 * Touch Methods
	 *****************************************/

	/**
	 * Resets the button
	 */
	public void reset() {
		touched = false;
		touchIndex = null;
		activatedOnce = false;
	}
	
	/**
	 * Checks to see if the touch should be registered to the joystick
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isInRange(float x, float y){
		return shape.contains(x, y);
	}
	
	public RenderableObject getRenderObject() {
		return renderObject;
	}
	public void setRenderObject(RenderableObject renderObject) {
		this.renderObject = renderObject;
	}
	public RenderableObject getOnPressRenderObject() {
		return onPressRenderObject;
	}
	public void setOnPressRenderObject(RenderableObject onPressRenderObject) {
		this.onPressRenderObject = onPressRenderObject;
	}
	/**
	 * Gets a vector of the first touch to the joystick
	 * 
	 * @return Will return null if no touch is registered
	 */
	private Vector2 getTouchIndex(){
		for(int index = 0; index < Settings_Input.TOUCH_INDEXES; index++){
			if (Gdx.input.isTouched(index)){
				tempX = Touch.convertX(Gdx.input.getX(index));
				tempY = Touch.convertY(Gdx.input.getY(index));
				
				if(isInRange(tempX, tempY)){
					return new Vector2(tempX, tempY);
				}
			}
		}
		
		return null;
	}
	
	/*****************************************
	 * Touch Methods [END]
	 *****************************************/
	
	/*****************************************
	 * Debug Methods
	 *****************************************/
	/**
	 * Renders the button circle
	 * 
	 * @param shapes
	 *            A begun ShapeRenderer
	 */
	public void debug(ShapeRenderer shapes){
		
	}
	/*****************************************
	 * Debug Methods [END]
	 *****************************************/

	/*****************************************
	 * Getters & Setters
	 *****************************************/

	public boolean isTouched() {
		return touched;
	}



	public void setTouched(boolean touched) {
		this.touched = touched;
	}


	/**
	 * Once this method is called, it will return true one time until the button is pressed again.
	 * Basically allows the user to catch a single press of this button even if the button is 
	 * being held down by the user. If you want to know if the button is held down, use
	 * isTouched() method.
	 * 
	 * @return
	 */
	public boolean catchSinglePress() {
		if(touchIndex != null && activatedOnce == false){ // touchIndex != null && activatedOnce == false
			activatedOnce = true;
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean catchSinglePressDesktop() {
		if(activatedOnce == false){ // touchIndex != null && activatedOnce == false
			activatedOnce = true;
			return true;
		}
		else{
			return false;
		}
	}
}