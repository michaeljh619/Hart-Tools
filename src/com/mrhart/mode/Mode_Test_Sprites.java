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

public class Mode_Test_Sprites extends Mode{
	/*
	 * Named Constants
	 */
	private static final int DANGLE_CIRCLE_A = 0;
	private static final int DANGLE_CIRCLE_B = 1;
	private static final int DANGLE_BOXES = 2;
	
	/*
	 * Instance Vars
	 */
	// Sprites
	private SpriteHandler sprites;
	private boolean[] bools = {true, true, true};

	public Mode_Test_Sprites() {
		super(GameState.TEST_SPRITES);
		
		// Load Assets
		Loader_Input.loadButtonDark_A(assets);
		Loader_Input.loadButtonDark_B(assets);
		
		// Sprite Handler
		sprites = new SpriteHandler(bools);
	}

	@Override
	public int update(float delta) {
		// Update all sprites
		sprites.update(delta);
		
		// Check collisions
		CollisionHandler.checkCollisions(sprites, CollisionHandler.NEIGHBORS);
		
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
		sprites.add(DANGLE_CIRCLE_A, 
				new Sprite_Circle(50, 50, 50, Loader_Input.getButtonDark_A(assets)));
		sprites.add(DANGLE_CIRCLE_A, 
				new Sprite_Circle(100, 50, 50, Loader_Input.getButtonDark_A(assets)));
		sprites.add(DANGLE_CIRCLE_A, 
				new Sprite_Circle(50, 100, 50, Loader_Input.getButtonDark_A(assets)));
		sprites.add(DANGLE_CIRCLE_A, 
				new Sprite_Circle(100, 100, 50, Loader_Input.getButtonDark_A(assets)));
		
		// Add Circle B's
		sprites.add(DANGLE_CIRCLE_B, 
				new Sprite_Circle(750, 450, 50, Loader_Input.getButtonDark_B(assets)));
		sprites.add(DANGLE_CIRCLE_B, 
				new Sprite_Circle(700, 450, 50, Loader_Input.getButtonDark_B(assets)));
		sprites.add(DANGLE_CIRCLE_B, 
				new Sprite_Circle(750, 400, 50, Loader_Input.getButtonDark_B(assets)));
		sprites.add(DANGLE_CIRCLE_B, 
				new Sprite_Circle(700, 400, 50, Loader_Input.getButtonDark_B(assets)));
		
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
				System.out.println("Sprite_Circle: I collided with another Circle!");
				CollisionHandler.collide_Hard_Bounce(this, (Resettable) other);
			}
			else if(other instanceof Sprite_Box){
				System.out.println("Sprite_Circle: I collided with a Sprite_Box!");
				CollisionHandler.collide_Hard_Bounce(this, (Resettable) other); 
			}
			else{
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
