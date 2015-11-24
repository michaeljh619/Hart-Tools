package com.mrhart.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mrhart.backend.Debuggable;
import com.mrhart.backend.Touch;
import com.mrhart.settings.Settings_Input;

/**
 * An onscreen UI button that can be used for input from the user.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */
public abstract class Button implements Debuggable{
	// Instance Variables
	protected Vector2 origin;
	protected int radius;
	
	protected boolean touched;
	protected boolean activatedOnce;
	
	protected TextureRegion textureRegion;
	
	// Variables for methods
	protected Vector2 touchIndex;
	protected float tempX, tempY;
	
	/**
	 * Creates a new button.
	 * 
	 * @param styleOfButton Basically the color of the button, use Gamepad.STYLE_
	 * @param buttonGraphic Picture on the button, use Button.BUTTON_
	 * @param positionX The x position to put the button at
	 * @param positionY The y position to put the button at
	 * @param inRadius The radius of the button
	 */
	public Button(Vector2 position, int inRadius){
		touched = false;
		
		radius = inRadius;
		
		origin = position;
	}
	
	
	/*****************************************
	 * Main Methods
	 *****************************************/
	
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
	 * Renders the joystick to the screen
	 * 
	 * @param batcher
	 */
	public void render(SpriteBatch batcher) {
		batcher.draw(textureRegion, origin.x - radius, origin.y - radius,
				radius * 2, radius * 2);
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
	public abstract boolean isInRange(float x, float y);
	
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
	public abstract void debug(ShapeRenderer shapes);
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