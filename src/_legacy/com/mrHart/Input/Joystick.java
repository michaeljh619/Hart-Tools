package com.mrHart.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * Creates a new joystick. The joystick works based on how many directions you
 * specify. These directions should be in multiples of 4.
 * 
 * @author Michael
 * @version v1.2
 * @since 02/01/15
 */
public class Joystick {
	// Named Constants
	private static final String JOYSTICK_DIR = "joystick/";
	private static final String STICK_FILE = "stick.png";
	private static final String BACKGROUND_FILE = "background.png";

	private static final int BACKGROUND_SIZE = 160;
	private static final int STICK_WIDTH = 96;
	private static final int STICK_HEIGHT = 100;

	private static final float STICK_SCALE = 0.5f;
	private static final float INNER_CIRCLE_SCALE = 0.1f; // Scales the inner
	private static final float STICK_OFFSET = 1.74f;
															// circle's radius
	private static final float TRIANGLE_SCALE = 1.6f;

	private static final float DEGREES_360 = 360.0f;
	private static final int FOUR = 4;
	
	private static final int TOUCH_INDEXES = 5;

	// Assets (Texture and TextureRegions are static since you only want one
	// style of joystick in the game)
	private Texture backgroundTexture, stickTexture;
	private TextureRegion background, stick;

	// Instance Variables
	private Circle circle, innerCircle;
	private Polygon[] triangles;

	private Vector2 origin;
	private Vector2 position;
	private boolean touched;

	private int directions;
	private float radius;
	private float angle;
	private float scaleX, scaleY;

	// Variables for methods
	private float bastardScale;
	private Vector2 touchIndex;
	private float tempX, tempY;

	/*****************************************
	 * Main Methods
	 *****************************************/

	/**
	 * Creates a joystick with a number of directions. Assets are automatically
	 * loaded
	 * 
	 * @param styleOfJoystick
	 *            Gamepad.STYLE_
	 * @param numberOfDirections
	 *            How many directions should this controller have
	 * @param positionX
	 *            Origin position x
	 * @param positionY
	 *            Origin position y
	 * @param inRadius
	 *            Radius of the controller
	 */
	public Joystick(int styleOfJoystick, int numberOfDirections, int positionX,
			int positionY, int inRadius) {
		load(styleOfJoystick);

		// Create the joystick's position and creates a circle with the origin
		// and an edge on the circle
		// Check Circle API if confused.
		radius = inRadius;
		origin = new Vector2(positionX, positionY);
		position = new Vector2(positionX, positionY);
		circle = new Circle(origin, new Vector2(origin.x, origin.y + radius));
		innerCircle = new Circle(origin, new Vector2(origin.x, origin.y
				+ (radius * INNER_CIRCLE_SCALE)));

		// Checks if it's a multiple of four and if it isn't, then it makes it a
		// multiple of four
		if (numberOfDirections <= 0) {
			numberOfDirections = 4;
		}
		while ((numberOfDirections % FOUR) != 0) {
			numberOfDirections--;
		}
		directions = numberOfDirections;
		angle = DEGREES_360 / directions;

		// Create the triangles for use in input
		triangles = new Polygon[directions];
		createTriangles(directions, origin);
	}
	
	/**
	 * Updates any touches done to the joystick
	 */
	public void update(){
		touchIndex = getTouchIndex();
		
		// If no touches
		if(touchIndex == null){
			touched = false;
			reset();
		}
		else{
		
		/*
		 * In reality this checks if the touch is within the triangles and
		 * without the inner circle
		 */
		if (updateScales(touchIndex.x, touchIndex.y)
				&& !innerCircle.contains(touchIndex.x, touchIndex.y)) {
			touched = true;
			
				// If the drag is not inside the circle then the position needs to
				// be converted
				// Else you can just use the raw x and y
				if (!circle.contains(touchIndex.x, touchIndex.y)) {
					// Get the scale to modify raw coordinates with
					touchIndex = getCircleCoordinates(touchIndex.x, touchIndex.y);
					bastardScale = getThresholdScale(touchIndex.x,
						touchIndex.y);

					// Update your positions of joystick
					position.x = (circle.x + (touchIndex.x * bastardScale));
					position.y = (circle.y + (touchIndex.y * bastardScale));
				} else {
					// Update your positions of joystick
					position.x = touchIndex.x;
					position.y = touchIndex.y;
				}
			} else {
				reset();
			}
		}
	}
	
	/**
	 * Updates any touches done to the joystick
	 */
	public void updateCustom(){
		touchIndex = getTouchIndex();
		
		// If no touches
		if(touchIndex == null){
			touched = false;
			reset();
		}
		else{
		
		/*
		 * In reality this checks if the touch is within the triangles and
		 * without the inner circle
		 */
		if (updateScales(touchIndex.x, touchIndex.y)
				&& !innerCircle.contains(touchIndex.x, touchIndex.y)) {
			touched = true;
			
				// If the drag is not inside the circle then the position needs to
				// be converted
				// Else you can just use the raw x and y
				if (!circle.contains(touchIndex.x, touchIndex.y)) {
					// Get the scale to modify raw coordinates with
					touchIndex = getCircleCoordinates(touchIndex.x, touchIndex.y);
					bastardScale = getThresholdScale(touchIndex.x,
						touchIndex.y);

					// Update your positions of joystick
					position.x = (circle.x + (touchIndex.x * bastardScale));
					position.y = (circle.y + (touchIndex.y * bastardScale));
				} else {
					// Update your positions of joystick
					position.x = touchIndex.x;
					position.y = touchIndex.y;
				}
			} else {
				reset();
			}
		}
	}

	/**
	 * Renders the joystick to the screen
	 * 
	 * @param batcher
	 */
	public void render(SpriteBatch batcher) {
		batcher.draw(backgroundTexture, origin.x - radius, origin.y - radius,
				radius * 2, radius * 2);
		batcher.draw(stickTexture, origin.x + position.x - radius*STICK_OFFSET,//position.x-radius*STICK_SCALE
				position.y - radius * STICK_SCALE, radius * 2 * STICK_SCALE,
				radius * 2 * STICK_SCALE);
	}

	/*****************************************
	 * Main Methods [END]
	 *****************************************/

	
	
	/*****************************************
	 * Touch Methods
	 *****************************************/

	/**
	 * Resets the joystick position to the center
	 */
	public void reset() {
		position.x = circle.x;
		position.y = circle.y;
		scaleX = scaleY = 0;
	}
	
	/**
	 * Checks to see if the touch should be registered to the joystick
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isInRange(float x, float y){
		for (int index = 0; index < triangles.length; index++) {
			if (triangles[index].contains(x, y)) {
				return true;
			}
		}
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
	 * Secondary Methods
	 *****************************************/

	/**
	 * Creates the triangles[] array based on how many directions and the size
	 * of the angles in
	 * 
	 * PRE: Circle must be created already.
	 * 
	 * @param numDirections
	 * @param angleSections
	 */
	private void createTriangles(float numDirections, Vector2 inOrigin) {
		float angleChange = DEGREES_360 / numDirections;
		float currentAngle;

		float originX = inOrigin.x;
		float originY = inOrigin.y;

		float point1X, point1Y, point2X, point2Y;
		float anglePoint1, anglePoint2;

		for (int x = 0; x < numDirections; x++) {
			// Set up all the angles
			currentAngle = angleChange * x;
			anglePoint1 = currentAngle + angleChange / 2;
			anglePoint2 = currentAngle - angleChange / 2;

			// Get point 1
			point1X = (float) Math.cos(Math.toRadians(anglePoint1)) * radius
					* TRIANGLE_SCALE;
			point1Y = (float) Math.sin(Math.toRadians(anglePoint1)) * radius
					* TRIANGLE_SCALE;

			// Get point 2
			point2X = (float) Math.cos(Math.toRadians(anglePoint2)) * radius
					* TRIANGLE_SCALE;
			point2Y = (float) Math.sin(Math.toRadians(anglePoint2)) * radius
					* TRIANGLE_SCALE;

			// Set up the new triangle with the vertices
			float[] vertices = { originX, originY, point1X + originX,
					-point1Y + originY, point2X + originX, -point2Y + originY };
			triangles[x] = new Polygon(vertices);
		}
	}

	/**
	 * Updates the angle based on the coordinates given.
	 * 
	 * TODO: This can be made more efficient by doing this in the isInTriangles
	 * method
	 * 
	 * @param x
	 * @param y
	 */
	private boolean updateScales(float x, float y) {
		if (!innerCircle.contains(x, y)) {
			for (int index = 0; index < triangles.length; index++) {
				if (triangles[index].contains(x, y)) {
					scaleX = (float) Math.cos(Math.toRadians(angle * index));
					scaleY = (float) Math.sin(Math.toRadians(angle * index));
					
					// To deal with imprecision
					if(Math.abs(scaleX) < 0.0000001)
						scaleX = 0;
					if(Math.abs(scaleY) < 0.0000001)
						scaleY = 0;
					
					return true;
				} else {
					scaleX = scaleY = 0;
				}
			}
		} else
			scaleX = scaleY = 0;

		return false;
	}

	/**
	 * Used in determing what scale to multiply points that have gone outside of
	 * the circle's radius.
	 * 
	 * @param x
	 * @param y
	 */
	private float getThresholdScale(float x, float y) {
		return (float) (circle.radius / Math.sqrt(x * x + y * y));
	}

	/**
	 * Converts the coordinates into coordinates based on the origin of the
	 * circle
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private Vector2 getCircleCoordinates(float x, float y) {
		x -= circle.x;
		y -= circle.y;
		return new Vector2(x, y);
	}
	
	/**
	 * Scrolls the Joystick by x amount
	 * 
	 * @param x
	 */
	public void scroll(int x){
		origin.x += x;
	}

	/*****************************************
	 * Secondary Methods [END]
	 *****************************************/

	
	
	/*****************************************
	 * Loader Methods
	 *****************************************/

	/**
	 * Loads assets for the joystick
	 */
	private void load(int style) {
		String directory = GamePad.getDir(style) + JOYSTICK_DIR;

		backgroundTexture = new Texture(Gdx.files.internal(directory
				+ BACKGROUND_FILE));
		stickTexture = new Texture(Gdx.files.internal(directory + STICK_FILE));

		background = new TextureRegion(backgroundTexture, 0, 0,
				BACKGROUND_SIZE, BACKGROUND_SIZE);
		background.flip(false, true);
		stick = new TextureRegion(stickTexture, 0, 0, STICK_WIDTH, STICK_HEIGHT);
		stick.flip(false, true);
	}

	/**
	 * Must be called before setting the Joystick to null, this ensures that if
	 * any textures are loaded for use, they will be disposed.
	 */
	public void dispose() {
		backgroundTexture.dispose();
		stickTexture.dispose();
	}

	/*****************************************
	 * Loader Methods
	 *****************************************/

	/*****************************************
	 * Debug Methods
	 *****************************************/

	/**
	 * Renders the triangles used in checking input
	 * 
	 * @param shapes
	 *            A begun ShapeRenderer
	 */
	public void renderTriangles(ShapeRenderer shapes) {
		for (int x = 0; x < triangles.length; x++) {
			shapes.polygon(triangles[x].getVertices());
		}
	}

	/**
	 * Renders the innerCircles used in threshold checking for input
	 * 
	 * @param shapes
	 *            A begun ShapeRenderer
	 */
	public void renderInnerCircle(ShapeRenderer shapes) {
		shapes.circle(innerCircle.x, innerCircle.y, innerCircle.radius);
	}

	/**
	 * Renders the joystick circle
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

	public float getScaleX() {
		return scaleX;
	}

	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
	}

	public float getScaleY() {
		return scaleY;
	}

	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}

	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}
	
	
}