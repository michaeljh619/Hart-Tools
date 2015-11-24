package com.mrhart.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mrhart.backend.Debuggable;
import com.mrhart.backend.Touch;
import com.mrhart.backend.Messages;

/**
 * An onscreen UI joystick that can be used for input from the user. This class
 * features JoystickCommands, which allows for you to automatically extract the
 * normalized vector that the user is touching. This can be accessed directly so
 * a sprite can automatically be updated by merely using the JoystickCommands.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */
public class Joystick implements Debuggable{
	/*
	 *  Named Constants
	 */
	// States
	private static final int STATE_IDLE = 0;
	private static final int STATE_TOUCHED = 1;
	// Backend
	private static final int TOTAL_TOUCHES_CHECKED = 3;
	/*
	 *  Instance Variables
	 */
	// Touch Detection
	private Circle circle_startTouch;
	private int currentTouchIndex;
	// States
	private int currentState;
	// Dimensions
	private int totalRadius, joystickRadius;
	// Vectors
	private Vector2 position, joystickOffset;
	private Vector2 bastardVector = new Vector2();
	// Graphics Files and directories
	private TextureRegion region_joystick, region_background;
	// Return variables
	private float rotation = 0;
	public JoystickCommands commands = new JoystickCommands(0, 0);
	
	public Joystick(Vector2 position, int totalRadius, int joystickRadius){
		// Initialize State
		currentState = STATE_IDLE;
		
		// Vector & null pointer check
		this.position = position;
		if(this.position == null){
			System.err.println(Messages.ERROR + Messages.TYPE_NULL_POINTER
					+ "Vector2 'position' parameter passed to Joystick is null!");
		}
		joystickOffset = new Vector2();
		
		// Dimensions & 0 check
		this.totalRadius = totalRadius;
		if(this.totalRadius <= 0){
			System.err.println(Messages.WARNING + Messages.TYPE_BAD_VALUE 
					+ "'totalRadius' parameter is less than or equal to 0, this may result in unexpected behavior.");
		}
		this.joystickRadius = joystickRadius;
		if(this.joystickRadius <= 0){
			System.err.println(Messages.WARNING + Messages.TYPE_BAD_VALUE 
					+ "'joystickRadius' parameter is less than or equal to 0, this may result in unexpected behavior.");
		}
		
		// Initialize Touch Detection
		circle_startTouch = new Circle(position.x, position.y, totalRadius);
		currentTouchIndex = -1;
	}
	
	public void update(){
		float x = 0;
		float y = 0;
		/*
		 * In idle state, check if the user has touched inside the startTouch circle
		 */
		if(currentState == STATE_IDLE){
			// Check every touch on the screen up to TOTAL TOUCHES CHECKED to see if any land within
			// the joystick circle
			float tempX, tempY;
			for(int index = 0; index < TOTAL_TOUCHES_CHECKED; index++){
				// Gets this touch's coordinates and converts them to game coordinates
				if (Gdx.input.isTouched(index)){
					tempX = Touch.convertX(Gdx.input.getX(index));
					tempY = Touch.convertY(Gdx.input.getY(index));
					// Checks if the coordinates are within our threshold circle
					if(circle_startTouch.contains(tempX, tempY)){
						currentTouchIndex = index;
						x = tempX;
						y = tempY;
						currentState = STATE_TOUCHED;
						break;
					}
				}
			}
		}
		/*
		 * Else the user is touching and we merely need to get the new touch coordinates,
		 * if the user stopped touching then we reset and set our state to idle
		 */
		else{
			if(Gdx.input.isTouched(currentTouchIndex)){
				x = Touch.convertX(Gdx.input.getX(currentTouchIndex));
				y = Touch.convertY(Gdx.input.getY(currentTouchIndex));
				currentState = STATE_TOUCHED;
			}
			else{
				reset();
				currentState = STATE_IDLE;
			}
		}
		
		// If we determined the joystick is being touched, then compute math and position of joystick vector
		if(currentState == STATE_TOUCHED){
			/*
			 *  Math for rotation and vector scale
			 */
			float tempX, tempY;
			tempX = x - position.x;
			tempY = y - position.y;
			bastardVector.set(tempX, tempY);
			
			rotation = bastardVector.angle();
			commands.normalized.set(bastardVector);
			commands.normalized.nor();
			
			/*
			 *  Update position of joystick vector
			 */
			if(circle_startTouch.contains(x, y)){
				joystickOffset.set(tempX, tempY); // Touch is in circle, show the joystick there
			}
			else{	// Touch is not in circle, moves the joystick position to a length of radius in the touch positions direction
				float lengthOfOffset = bastardVector.len();
				bastardVector.scl(totalRadius/lengthOfOffset);
				joystickOffset.set(bastardVector);
			}
		}
	}
	
	public void render(SpriteBatch batcher){
		batcher.draw(region_background, position.x - totalRadius, position.y - totalRadius,
				totalRadius*2, totalRadius*2);
		batcher.draw(region_joystick, position.x + joystickOffset.x - joystickRadius, 
				position.y + joystickOffset.y - joystickRadius, joystickRadius*2,
				joystickRadius*2);
	}
	 public void setTextureRegions(TextureRegion backgroundRegion,
			 TextureRegion stickRegion){
		// Set up TextureRegions
		region_joystick = stickRegion;
		region_background = backgroundRegion;
		 
	 }
	
	private void reset(){
		joystickOffset.setZero();
		commands.normalized.setZero();
		rotation = 0;
	}
	
	public boolean isTouched(){
		if(currentState == STATE_IDLE){
			return false;
		}
		else{
			return true;
		}
	}
	
	public float getAngle(){
		return rotation;
	}

	@Override
	public void debug(ShapeRenderer shapes) {
		
	}
}
