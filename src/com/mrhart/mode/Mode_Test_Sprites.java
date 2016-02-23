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
import com.mrhart.sprites.SpriteHandler_Dangles;
import com.mrhart.sprites.SpriteHandler;
import com.mrhart.sprites.Sprite_Resettable;
import com.mrhart.state.GameState;
import com.mrhart.tools.Timer;

public class Mode_Test_Sprites extends Mode {
	/*
	 * Named Constants
	 */
	private static final int TEST_DEFAULT = 0;
	private static final int TEST_DANGLES = 1;
	private static final int TEST = 0;
	// Dangles
	private static final int DANGLE_CIRCLE_A = 0;
	private static final int DANGLE_CIRCLE_B = 1;
	private static final int DANGLE_CIRCLE_X = 2;
	private static final int DANGLE_CIRCLE_Y = 3;
	private static final int DANGLE_CIRCLE_L = 4;
	private static final int DANGLE_CIRCLE_R = 5;
	private static final int DANGLE_BOXES = 6;
	// Bench Test
	private static final int MAX_TESTS = 1000;
	private static final int NUM_OBJECTS = 300; // Times 6
	// Sprites
	private static final int WIDTH = 25;
	
	/*
	 * Instance Vars
	 */
	// Sprites
	private SpriteHandler_Dangles sprites;
	private SpriteHandler sprites2;
	// Timer
	private Timer benchTime = new Timer();
	private Timer graceTime = new Timer();
	// benchTime test
	private long[] testedTimes = new long[MAX_TESTS];
	private int benchTestCounter = 0;
	private boolean isBenchTestDone = false;
	private boolean isReadyToBenchTest = false;

	@SuppressWarnings("unused")
	public Mode_Test_Sprites() {
		super(GameState.TEST_SPRITES);
		
		// Load Assets
		Loader_Input.loadButtonDark_A(assets);
		Loader_Input.loadButtonDark_B(assets);
		Loader_Input.loadButtonDark_X(assets);
		Loader_Input.loadButtonDark_Y(assets);
		Loader_Input.loadButtonDark_R(assets);
		Loader_Input.loadButtonDark_L(assets);
		
		if(TEST != TEST_DEFAULT){
			// Sprite Handler
			sprites = new SpriteHandler_Dangles(7);
			// Set up adjacency list for collisions
			sprites.addAllCollisions();
			sprites.removeCollision(DANGLE_CIRCLE_A, DANGLE_CIRCLE_A);
			sprites.removeCollision(DANGLE_CIRCLE_B, DANGLE_CIRCLE_B);
			sprites.removeCollision(DANGLE_CIRCLE_X, DANGLE_CIRCLE_X);
			sprites.removeCollision(DANGLE_CIRCLE_Y, DANGLE_CIRCLE_Y);
			sprites.removeCollision(DANGLE_CIRCLE_L, DANGLE_CIRCLE_L);
			sprites.removeCollision(DANGLE_CIRCLE_R, DANGLE_CIRCLE_R);
		}
		else{
			sprites2 = new SpriteHandler();
		}
		// Timers
		graceTime.initMilliseconds(1000);
	}

	@SuppressWarnings("unused")
	@Override
	public int update(float delta) {
		// Allow grace time to finish before testing
		if(graceTime.isDone())
			isReadyToBenchTest = true;
		
		// Update all sprites
		if(TEST == TEST_DANGLES)
			sprites.update(delta);
		else
			sprites2.update(delta);
		
		// Initialize Bench Time
		if(isReadyToBenchTest)
			benchTime.init();

		if(TEST == TEST_DANGLES)
			CollisionHandler.checkCollisions(sprites);
		else
			sprites2.checkCollisions();

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
			System.err.println("Average Microseconds over " 
					+ MAX_TESTS + " tests: " + averageTime/1000);
			isBenchTestDone = false;
		}
		
		// Stuck in here forever! Muahahah
		return GameState.NULL;
	}

	@SuppressWarnings("unused")
	@Override
	public void render(SpriteBatch batcher, float runtime) {
		// Render all sprites
		if(TEST == TEST_DANGLES)
			sprites.render(batcher, runtime);
		else
			sprites2.render(batcher, runtime);
	}

	@SuppressWarnings("unused")
	@Override
	public void finalize() {
		if(TEST == TEST_DANGLES){
			// Add Circle A's
			for(int x = 0; x < NUM_OBJECTS; x++){
				sprites.add(DANGLE_CIRCLE_A, 
					new Sprite_Circle(100, 100, WIDTH, 
							Loader_Input.getButtonDark_A(assets), DANGLE_CIRCLE_A));
			}
			// Add Circle B's
			for(int x = 0; x < NUM_OBJECTS; x++){
				sprites.add(DANGLE_CIRCLE_B, 
					new Sprite_Circle(700, 400, WIDTH, 
							Loader_Input.getButtonDark_B(assets), DANGLE_CIRCLE_B));
			}
			// Add Circle X's
			for(int x = 0; x < NUM_OBJECTS; x++){
				sprites.add(DANGLE_CIRCLE_X, 
					new Sprite_Circle(700, 100, WIDTH, 
							Loader_Input.getButtonDark_X(assets), DANGLE_CIRCLE_X));
			}
			// Add Circle Y's
			for(int x = 0; x < NUM_OBJECTS; x++){
				sprites.add(DANGLE_CIRCLE_Y, 
					new Sprite_Circle(100, 400, WIDTH, 
							Loader_Input.getButtonDark_Y(assets), DANGLE_CIRCLE_Y));
			}
			// Add Circle L's
			for(int x = 0; x < NUM_OBJECTS; x++){
				sprites.add(DANGLE_CIRCLE_L, 
					new Sprite_Circle(100, 250, WIDTH,
							Loader_Input.getButtonDark_L(assets), DANGLE_CIRCLE_L));
			}
			// Add Circle R's
			for(int x = 0; x < NUM_OBJECTS; x++){
				sprites.add(DANGLE_CIRCLE_R, 
					new Sprite_Circle(700, 250, WIDTH, 
							Loader_Input.getButtonDark_R(assets), DANGLE_CIRCLE_R));
			}
			// Add Out of Screen Rects
			sprites.add(DANGLE_BOXES, new Sprite_Box(-100, 0, 100, 500));
			sprites.add(DANGLE_BOXES, new Sprite_Box(0, -100, 800, 100));
			sprites.add(DANGLE_BOXES, new Sprite_Box(800, 0, 100, 500));
			sprites.add(DANGLE_BOXES, new Sprite_Box(0, 500, 800, 100));
		}
		else{
			// Add Circle A's
			for(int x = 0; x < NUM_OBJECTS; x++){
				sprites2.add(
					new Sprite_Circle(100, 100, WIDTH, 
							Loader_Input.getButtonDark_A(assets), DANGLE_CIRCLE_A));
			}
			// Add Circle B's
			for(int x = 0; x < NUM_OBJECTS; x++){ // x was at 700
				sprites2.add(
					new Sprite_Circle(400, 400, WIDTH, 
							Loader_Input.getButtonDark_B(assets), DANGLE_CIRCLE_B));
			}
			// Add Circle X's
			for(int x = 0; x < NUM_OBJECTS; x++){ // x was at 700
				sprites2.add(
					new Sprite_Circle(400, 100, WIDTH, 
							Loader_Input.getButtonDark_X(assets), DANGLE_CIRCLE_X));
			}
			// Add Circle Y's
			for(int x = 0; x < NUM_OBJECTS; x++){ 
				sprites2.add(
					new Sprite_Circle(100, 400, WIDTH, 
							Loader_Input.getButtonDark_Y(assets), DANGLE_CIRCLE_Y));
			}
			// Add Circle L's
			for(int x = 0; x < NUM_OBJECTS; x++){
				sprites2.add(
					new Sprite_Circle(100, 250, WIDTH,
							Loader_Input.getButtonDark_L(assets), DANGLE_CIRCLE_L));
			}
			// Add Circle R's
			for(int x = 0; x < NUM_OBJECTS; x++){ // x was at 700
				sprites2.add(
					new Sprite_Circle(400, 250, WIDTH, 
							Loader_Input.getButtonDark_R(assets), DANGLE_CIRCLE_R));
			}
			// Add Out of Screen Rects
			sprites2.add(new Sprite_Box(-100, 0, 100, 500)); // Left
			sprites2.add(new Sprite_Box(0, -100, 800, 100)); // Top
			sprites2.add(new Sprite_Box(800, 0, 100, 500));  // Right
			sprites2.add(new Sprite_Box(0, 500, 800, 100));  // Bot
		}
	}
	
	

	/*
	 * Sprite Circle Class
	 */
	private class Sprite_Circle extends Sprite_Resettable{
		private static final int SPEED = 100;
		private static final boolean DEBUG_ON = false;
		
		private Vector2 lastVelocity;
		// Used as a second reference to the top level collision area
		// This way we don't have to be downcasting all the time
		CollisionArea_SingleCirc circ;
		
		public Sprite_Circle(int positionX, int positionY, int inWidth,
				TextureRegion region, int ID) {
			super(positionX, positionY, inWidth, inWidth,
					new RenderableTextureRegion(region), new CollisionArea_SingleCirc(), ID);
			lastPosition = new Vector2(position.x, position.y);
			// Set up CollisionArea
			circ = ((CollisionArea_SingleCirc) collisionArea);
			circ.collisionArea = new Circle(position.x, position.y, width/2);
			// Set Random Velocity
			setRandomVelocity();
			lastVelocity = new Vector2(velocity);
		}

		@Override
		public void update(float delta) {
			if(velocity.x == 0){
				velocity.x = -lastVelocity.x;
			}
			if(velocity.y == 0){
				velocity.y = -lastVelocity.y;
			}
			lastVelocity.set(velocity);
			lastPosition.set(position);
			super.update(delta);
			updateCollisionArea();
		}
		
		@Override
		public CollisionArea getCollisionArea() {
			// TODO Auto-generated method stub
			return collisionArea;
		}

		@Override
		public void collide(Collidable other, boolean collidedOnce) {
			if(other instanceof Sprite_Circle){
				if(DEBUG_ON)
					System.out.println("Sprite_Circle: I collided with another Circle!");
				CollisionHandler.collide_Hard(this, (Resettable) other);
			}
			else if(other instanceof Sprite_Box){
				if(DEBUG_ON)
					System.out.println("Sprite_Circle: I collided with a Sprite_Box!");
				CollisionHandler.collide_Hard(this, (Resettable) other); 
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
		
		public boolean canCollideWith(Collidable other){
			if(other instanceof Sprite_Circle && equals((Sprite_Circle) other)){
				return false;
			}
			else{
				return true;
			}
		}
		
		private boolean equals(Sprite_Circle other){
			return this.getRenderLayer() == other.getRenderLayer();
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
		protected int compareTo_SameRenderLayer(Sprite other) {
			if(this.position.y < other.position.y)
				return -1;
			else if(this.position.y == other.position.y)
				return 0;
			else
				return 1;
		}
	}
	
	
	
	private class Sprite_Box extends Sprite_Resettable{
		private static final boolean DEBUG_ON = false;
		
		// Collisions
		private CollisionArea_SingleRect rect;

		public Sprite_Box(int positionX, int positionY, int inWidth, int inHeight) {
			super(positionX, positionY, inWidth, inHeight, new CollisionArea_SingleRect(),
					DANGLE_BOXES, true);
			rect = (CollisionArea_SingleRect) super.collisionArea;
			rect.collisionArea = new Rectangle(positionX, positionY, inWidth, inHeight);
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
		
		public boolean canCollideWith(Collidable other){
			if(other instanceof Sprite_Box){
				return false;
			}
			else{
				return true;
			}
		}

		@Override
		public void update(float delta) {
			
		}

		@Override
		public void render(SpriteBatch batcher, float runtime) {
			
		}

		@Override
		public void updateCollisionArea() {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected int compareTo_SameRenderLayer(Sprite other) {
			if(this.position.y < other.position.y)
				return 1;
			else if(this.position.y == other.position.y)
				return 0;
			else
				return -1;
		}
	}



}
