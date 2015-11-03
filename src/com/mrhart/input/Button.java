package com.mrhart.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mrhart.assets.InputLoader;
import com.mrhart.backend.Messages;
import com.mrhart.backend.Touch;

/**
 * An onscreen UI button that can be used for input from the user.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */
public class Button {
	// Named Constants
	private static final int TOUCH_INDEXES = 3;
	
	public static final int STYLE_DARK = 1;
	public static final int STYLE_LIGHT = 2;
	public static final int STYLE_DARK_TRANSPARENT = 3;
	
	public static final int BUTTON_A = 1;
	public static final int BUTTON_B = 2;
	public static final int BUTTON_L = 3;
	public static final int BUTTON_R = 4;
	public static final int BUTTON_X = 5;
	public static final int BUTTON_Y = 6;
	public static final int BUTTON_ACCEPT = 7;
	public static final int BUTTON_CANCEL = 8;
	
	// Instance Variables
	public Vector2 origin;
	private int radius;
	
	private boolean touched;
	private boolean activatedOnce;
	
	private Circle circle;
	
	private TextureRegion textureRegion;
	
	// Variables for methods
	private Vector2 touchIndex;
	private float tempX, tempY;
	
	/**
	 * Creates a new button.
	 * 
	 * @param styleOfButton Basically the color of the button, use Gamepad.STYLE_
	 * @param buttonGraphic Picture on the button, use Button.BUTTON_
	 * @param positionX The x position to put the button at
	 * @param positionY The y position to put the button at
	 * @param inRadius The radius of the button
	 */
	public Button(int styleOfButton, int buttonPicture, int positionX, int positionY, int inRadius){
		touched = false;
		
		radius = inRadius;
		
		origin = new Vector2(positionX, positionY);
		
		circle = new Circle(origin.x, origin.y, radius);
		
		switch(styleOfButton){
		case STYLE_DARK:
			if(buttonPicture == BUTTON_A)
				textureRegion = InputLoader.button_dark_A;
			else if(buttonPicture == BUTTON_B)
				textureRegion = InputLoader.button_dark_B;
			else
				System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
						+ "buttonPicture parameter is not a valid input!");
			break;
		case STYLE_LIGHT:
			if(buttonPicture == BUTTON_A)
				textureRegion = InputLoader.button_light_A;
			else if(buttonPicture == BUTTON_B)
				textureRegion = InputLoader.button_light_B;
			else
				System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
						+ "buttonPicture parameter is not a valid input!");
			break;
		case STYLE_DARK_TRANSPARENT:
			if(buttonPicture == BUTTON_A)
				textureRegion = InputLoader.button_darkTransparent_A;
			else if(buttonPicture == BUTTON_B)
				textureRegion = InputLoader.button_darkTransparent_B;
			else
				System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
						+ "buttonPicture parameter is not a valid input!");
			break;
		default:
				System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
						+ "styleOfButton parameter is not a valid input!");
		}
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
	public boolean isInRange(float x, float y){
		if(circle.contains(x, y))
			return true;
		else
			return false;
	}
	
	/**
	 * Gets a vector of the first touch to the joystick
	 * 
	 * @return Will return null if no touch is registered
	 */
	private Vector2 getTouchIndex(){
		for(int index = 0; index < TOUCH_INDEXES; index++){
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
	public void renderCircle(ShapeRenderer shapes) {
		shapes.circle(circle.x, circle.y, circle.radius);
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