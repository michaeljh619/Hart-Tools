package com.mrHart.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Button {
	// Named Constants
	private static final String BUTTON_DIR = "button/";
	private static final int BUTTON_SIZE = 80;
	
	private static final int TOUCH_INDEXES = 10;
	
	public static final int BUTTON_A = 1;
	private static final String BUTTON_A_FILE = "a.png";
	public static final int BUTTON_B = 2;
	private static final String BUTTON_B_FILE = "b.png";
	public static final int BUTTON_L = 3;
	private static final String BUTTON_L_FILE = "l.png";
	public static final int BUTTON_R = 4;
	private static final String BUTTON_R_FILE = "r.png";
	public static final int BUTTON_X = 5;
	private static final String BUTTON_X_FILE = "x.png";
	public static final int BUTTON_Y = 6;
	private static final String BUTTON_Y_FILE = "y.png";
	public static final int BUTTON_ACCEPT = 7;
	private static final String BUTTON_ACCEPT_FILE = "accept.png";
	public static final int BUTTON_CANCEL = 8;
	private static final String BUTTON_CANCEL_FILE = "cancel.png";
	
	// Instance Variables
	public Vector2 origin;
	private int radius;
	
	private boolean touched;
	private boolean activatedOnce;
	
	private Circle circle;
	
	private Texture texture;
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
		
		load(styleOfButton, buttonPicture);
	}
	
	
	
	/*****************************************
	 * Loader Methods
	 *****************************************/
	
	private void load(int style, int buttonPicture){
		String directory = GamePad.getDir(style) + BUTTON_DIR;
		
		texture = new Texture(Gdx.files.internal(directory
				+ getFileName(buttonPicture)));
		textureRegion = new TextureRegion(texture, 0, 0,
				BUTTON_SIZE, BUTTON_SIZE);
		textureRegion.flip(false, true);
	}
	
	private String getFileName(int type){
		if(type == BUTTON_A){
			return BUTTON_A_FILE;
		}
		else if(type == BUTTON_B){
			return BUTTON_B_FILE;
		}
		else if(type == BUTTON_L){
			return BUTTON_L_FILE;
		}
		else if(type == BUTTON_R){
			return BUTTON_R_FILE;
		}
		else if(type == BUTTON_X){
			return BUTTON_X_FILE;
		}
		else if(type == BUTTON_Y){
			return BUTTON_Y_FILE;
		}
		else if(type == BUTTON_ACCEPT){
			return BUTTON_ACCEPT_FILE;
		}
		else if(type == BUTTON_CANCEL){
			return BUTTON_CANCEL_FILE;
		}
		
		return "";
	}
	
	/*****************************************
	 * Loader Methods [END]
	 *****************************************/
	
	
	
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
				tempX = InputHandler.convertX(Gdx.input.getX(index));
				tempY = InputHandler.convertY(Gdx.input.getY(index));
				
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