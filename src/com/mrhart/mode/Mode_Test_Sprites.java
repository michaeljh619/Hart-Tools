package com.mrhart.mode;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mrhart.assets.loaders.Loader_Input;
import com.mrhart.collisions.Collidable;
import com.mrhart.collisions.CollisionArea;
import com.mrhart.collisions.CollisionArea_SingleCirc;
import com.mrhart.collisions.CollisionArea_SingleRect;
import com.mrhart.collisions.CollisionHandler;
import com.mrhart.collisions.Resettable;
import com.mrhart.renderable.RenderableTextureRegion;
import com.mrhart.sprites.Sprite;
import com.mrhart.sprites.SpriteHandler;
import com.mrhart.state.GameState;
import com.mrhart.tools.Timer;

public class Mode_Test_Sprites extends Mode{
	/*
	 * Named Constants
	 */
	// Dangles
	private static final int DANGLE_CIRCLE_A = 0;
	private static final int DANGLE_CIRCLE_B = 1;
	private static final int DANGLE_CIRCLE_X = 2;
	private static final int DANGLE_CIRCLE_Y = 3;
	private static final int DANGLE_CIRCLE_L = 4;
	private static final int DANGLE_CIRCLE_R = 5;
	private static final int DANGLE_BOXES = 6;
	// Bench Test
	private static final int MAX_TESTS = 100;
	private static final int NUM_OBJECTS = 5;
	
	/*
	 * Instance Vars
	 */
	// Sprites
	private SpriteHandler sprites;
	// Timer
	private Timer benchTime = new Timer();
	private Timer graceTime = new Timer();
	// benchTime test
	private long[] testedTimes = new long[MAX_TESTS];
	private int benchTestCounter = 0;
	private boolean isBenchTestDone = false;
	private boolean isReadyToBenchTest = false;

	public Mode_Test_Sprites() {
		super(GameState.TEST_SPRITES);
		
		// Load Assets
		Loader_Input.loadButtonDark_A(assets);
		Loader_Input.loadButtonDark_B(assets);
		Loader_Input.loadButtonDark_X(assets);
		Loader_Input.loadButtonDark_Y(assets);
		Loader_Input.loadButtonDark_R(assets);
		Loader_Input.loadButtonDark_L(assets);
		
		// Sprite Handler
		sprites = new SpriteHandler(7);
		// Set up adjacency list for collisions
		sprites.addAllCollisions();
		sprites.removeCollision(DANGLE_CIRCLE_A, DANGLE_CIRCLE_A);
		sprites.removeCollision(DANGLE_CIRCLE_B, DANGLE_CIRCLE_B);
		sprites.removeCollision(DANGLE_CIRCLE_X, DANGLE_CIRCLE_X);
		sprites.removeCollision(DANGLE_CIRCLE_Y, DANGLE_CIRCLE_Y);
		sprites.removeCollision(DANGLE_CIRCLE_L, DANGLE_CIRCLE_L);
		sprites.removeCollision(DANGLE_CIRCLE_R, DANGLE_CIRCLE_R);
		
		// Timers
		graceTime.initMilliseconds(1000);
	}

	@Override
	public int update(float delta) {
		// Allow grace time to finish before testing
		if(graceTime.isDone())
			isReadyToBenchTest = true;
		
		// Initialize Bench Time
		if(isReadyToBenchTest)
			benchTime.init();

		// Update all sprites
		sprites.update(delta);

		// Add to tested times
		if(isReadyToBenchTest){
			// Add to array and update counter
			testedTimes[benchTestCounter] = benchTime.getElapsedNanoseconds();
			benchTestCounter++;
			// Update flags
			if(benchTestCounter == MAX_TESTS){
				isReadyToBenchTest = false;
				isBenchTestDone = true;
			}
		}
		
		// Get average time
		if(isBenchTestDone){
			// Calculate average time
			long averageTime = 0;
			for(int x = 0; x < MAX_TESTS; x++){
				averageTime += testedTimes[x]/MAX_TESTS;
			}
			// Output
			System.err.println("Average Nanoseconds over " 
					+ MAX_TESTS + " tests: " + averageTime);
			isBenchTestDone = false;
		}
		
		// Check collisions
		CollisionHandler.checkCollisions(sprites);
		
		// Stuck in here forever! Muahahah
		return GameState.NULL;
	}

	@Override
	public void render(SpriteBatch batcher, float runtime) {
		// Render all sprites
		sprites.render(batcher, runtime);
	}

	@Override
	public void finalize() {
		// Add Circle A's
		for(int x = 0; x < NUM_OBJECTS; x++){
			sprites.add(DANGLE_CIRCLE_A, 
				new Sprite_Circle(100, 100, 25, Loader_Input.getButtonDark_A(assets)));
		}
		// Add Circle B's
		for(int x = 0; x < NUM_OBJECTS; x++){
			sprites.add(DANGLE_CIRCLE_B, 
				new Sprite_Circle(700, 400, 25, Loader_Input.getButtonDark_B(assets)));
		}
		// Add Circle X's
		for(int x = 0; x < NUM_OBJECTS; x++){
			sprites.add(DANGLE_CIRCLE_X, 
				new Sprite_Circle(700, 100, 25, Loader_Input.getButtonDark_X(assets)));
		}
		// Add Circle Y's
		for(int x = 0; x < NUM_OBJECTS; x++){
			sprites.add(DANGLE_CIRCLE_Y, 
				new Sprite_Circle(100, 400, 25, Loader_Input.getButtonDark_Y(assets)));
		}
		// Add Circle L's
		for(int x = 0; x < NUM_OBJECTS; x++){
			sprites.add(DANGLE_CIRCLE_L, 
				new Sprite_Circle(100, 250, 25, Loader_Input.getButtonDark_L(assets)));
		}
		// Add Circle R's
		for(int x = 0; x < NUM_OBJECTS; x++){
			sprites.add(DANGLE_CIRCLE_R, 
				new Sprite_Circle(700, 250, 25, Loader_Input.getButtonDark_R(assets)));
		}
		// Add Out of Screen Rects
		sprites.add(DANGLE_BOXES, new Sprite_Box(-100, 0, 100, 500));
		sprites.add(DANGLE_BOXES, new Sprite_Box(0, -100, 800, 100));
		sprites.add(DANGLE_BOXES, new Sprite_Box(800, 0, 100, 500));
		sprites.add(DANGLE_BOXES, new Sprite_Box(0, 500, 800, 100));
	}
	
	

	/*
	 * Sprite Circle Class
	 */
	private class Sprite_Circle extends Sprite implements Resettable{
		private static final int SPEED = 100;
		private static final boolean DEBUG_ON = false;
		
		private CollisionArea_SingleCirc circ;
		private Vector2 lastPosition;
		
		public Sprite_Circle(int positionX, int positionY, int inWidth,
				TextureRegion region) {
			super(positionX, positionY, inWidth, inWidth,
					new RenderableTextureRegion(region));
			lastPosition = new Vector2(position.x, position.y);
			// Set up CollisionArea
			circ = new CollisionArea_SingleCirc();
			circ.collisionArea = new Circle(position.x, position.y, width/2);
			// Set Random Velocity
			setRandomVelocity();
		}

		@Override
		public void update(float delta) {
			lastPosition.set(position);
			super.update(delta);
			updateCollisionArea();
		}
		
		@Override
		public CollisionArea getCollisionArea() {
			// TODO Auto-generated method stub
			return circ;
		}

		@Override
		public void collide(Collidable other, boolean collidedOnce) {
			if(other instanceof Sprite_Circle){
				if(DEBUG_ON)
					System.out.println("Sprite_Circle: I collided with another Circle!");
				CollisionHandler.collide_Hard_Bounce(this, (Resettable) other);
			}
			else if(other instanceof Sprite_Box){
				if(DEBUG_ON)
					System.out.println("Sprite_Circle: I collided with a Sprite_Box!");
				CollisionHandler.collide_Hard_Bounce(this, (Resettable) other); 
			}
			else{
				if(DEBUG_ON)
					System.out.println("Err... Something went wrong.");
			}
		}

		@Override
		public boolean canCollide() {
			return true;
		}
		
		private void setRandomVelocity(){
			double randX, randY;
			int randNegX, randNegY;
			
			randX = Math.random();
			randY = Math.random();
			randNegX = (int) (Math.random()*2);
			randNegY = (int) (Math.random()*2);
			
			if(randNegX == 1){
				randX = -randX;
			}
			if(randNegY == 1){
				randY = -randY;
			}
			
			velocity.x = (float) randX;
			velocity.y = (float) randY;
			velocity.nor();
			velocity.scl(SPEED);
		}

		@Override
		public void updateCollisionArea() {
			// TODO Auto-generated method stub
			circ.collisionArea.setPosition(position);
		}

		@Override
		public Vector2 getLastPosition() {
			return lastPosition;
		}
	}
	
	
	
	private class Sprite_Box extends Sprite implements Resettable{
		private static final boolean DEBUG_ON = false;
		
		// Collisions
		private CollisionArea_SingleRect rect;

		public Sprite_Box(int positionX, int positionY, int inWidth, int inHeight) {
			super(positionX, positionY, inWidth, inHeight);
			rect = new CollisionArea_SingleRect();
			rect.collisionArea = new Rectangle(positionX, positionY, inWidth, inHeight);
		}

		@Override
		public CollisionArea getCollisionArea() {
			return rect;
		}

		@Override
		public void collide(Collidable other, boolean collidedOnce) {
			if(DEBUG_ON)
				System.err.println("Sprite_Box: I pushed someone away!");
		}

		@Override
		public boolean canCollide() {
			return true;
		}

		@Override
		public void update(float delta) {
			
		}

		@Override
		public void render(SpriteBatch batcher, float runtime) {
			
		}

		@Override
		public void updateCollisionArea() {

		}

		@Override
		public Vector2 getLastPosition() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
