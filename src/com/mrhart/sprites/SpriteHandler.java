package com.mrhart.sprites;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mrhart.collisions.Collidable;
import com.mrhart.collisions.OptimalCollidable;
import com.mrhart.structures.SortedArrayList;

/**
 * A handler for your Sprites. Sprites are added via the add() method and kept
 * in various data structures for ease of updating, rendering, and collision
 * handling.
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.20
 */
public class SpriteHandler {
	/*
	 * Named Constants
	 */
	private static final int COMP_RENDER_LAYER = 0;
	private static final int COMP_X = 1;
	private static final int COMP_Y = 2;
	
	/*
	 * Instance Vars
	 */
	private SortedArrayList<Sprite> sprites;
	private LinkedList<Sprite> removedSprites;
	private int currentComparator = COMP_RENDER_LAYER;
	
	public SpriteHandler(){
		sprites = new SortedArrayList<Sprite>();
		removedSprites = new LinkedList<Sprite>();
	}
	
	/**
	 * Updates all sprites in all the nodes, removes any removable sprites.
	 * 
	 * @since v1.0
	 * @version v1.02
	 * @param delta
	 */
	public void update(float delta){
		// Function Vars
		Sprite current;
		// This loop checks each individual nodes
		// @version v1.02 - Removed iterators, slight performance benefit
		for(int x = 0; x < sprites.size(); x++){
			current = sprites.get(x);
			// Normal Update
			if(current instanceof Sprite_Moderator){
				// Update Sprite and get Action
				SpriteHandler_Action action;
				action = ((Sprite_Moderator) current).update(delta, null);
				// Perform action if not null
				if(action != null)
					action.performAction(this);
			}
			else{
				current.update(delta);
			}
		}
	}
	
	/**
	 * Renders all the sprites to the screen
	 * 
	 * @since v1.0
	 * @version v1.00
	 * @param batcher
	 * @param runtime
	 */
	public void render(SpriteBatch batcher, float runtime){
		this.setComparable(COMP_RENDER_LAYER);
		sprites.sort();
		for(int x = 0; x < sprites.size(); x++){
//			System.err.println(sprites.get(x).getRenderLayer());
			sprites.get(x).render(batcher, runtime);;
		}
	}
	
	/**
	 * Processes all the collisions of all the sprites that are colliding.
	 */
	public void checkCollisions(){
		// Decide what comparator we will be using before we start colliding.
		decideNextComparable();
		
		// A comparator has been chosen, either X or Y and we can now sort our
		// SortedArrayList
		sprites.sort();
		do{
			checkCollisions_SinglePass();
		}while(!removedSprites.isEmpty());
	}
	
	/**
	 * Performs a single pass of collision checking. If any Sprites collided
	 * and were moved, they will be removed from the SortedArrayList and be
	 * reinserted at the next iteration. Multiple passes will occur until all
	 * collisions that involve moving sprites around have been handled.
	 */
	private void checkCollisions_SinglePass(){
		// Function variables
		Sprite current;
		Sprite next;
		Collidable currentCol;
		Collidable nextCol;
		OptimalCollidable currentOpt;
		OptimalCollidable nextOpt;
		int first = 0;
		int second = 0;
		float currentOrigX;
		float currentOrigY;
		float nextOrigX;
		float nextOrigY;
		int size = sprites.size();
		boolean xWasRemoved;
		boolean yWasRemoved;
		boolean check = false;
		float currMinEndpoint, currSpan, nextMinEndpoint;
		// Add back into the sprites SortedList all the sprites that collided
		// and were removed in the previous iteration
		while(!removedSprites.isEmpty()){
			sprites.add(removedSprites.remove());
		}
		// Runs through all the sprites in sprites.
		while(first < size){
			xWasRemoved = false;
			current = sprites.get(first);
			// If this is not a collidable sprite, update index and continue.
			if(!(current instanceof Collidable) || !((Collidable) current).canCollide()){
				first++;
				continue;
			}
			currentCol = (Collidable) current;
			second = first + 1;
			while(second < size){
				yWasRemoved = false;
				next = sprites.get(second);
				// Next is not a collidable Sprite
				if(!(next instanceof Collidable) || !((Collidable) next).canCollide()){
					second++;
					continue;
				}
				// Convert to collidable for next
				nextCol = (Collidable) next;
				if(!nextCol.canCollideWith(currentCol)){
					second++;
					continue;
				}
				// Checks for possible collisions based on X-axis.
				if(currentComparator == COMP_X){
					// Imagine two rectangles. If the right side of the rectangle
					// that is further to the left is smaller than the left side
					// of the rectangle on the right, then they cannot be
					// overlapping! This if statement will check that.
					// CHANGE BACK TO <=
					currMinEndpoint = currentCol.get_CollisionArea_LeftEndpointX();
					currSpan = currentCol.get_CollisionArea_RightEndpointX()
									- currMinEndpoint;
					nextMinEndpoint = nextCol.get_CollisionArea_LeftEndpointX();
					if(currMinEndpoint + currSpan <= nextMinEndpoint){
						// The rest of the sprites after this next one will all
						// also be out of range of our current sprite, so we can
						// stop checking at this point.
						break;
					}
				}
				// Checks for possible collisions based on X-axis.
				if(currentComparator == COMP_Y){
					// Imagine two rectangles. If the right side of the rectangle
					// that is further to the left is smaller than the left side
					// of the rectangle on the right, then they cannot be
					// overlapping! This if statement will check that.
					// CHANGE BACK TO <=
					currMinEndpoint = currentCol.get_CollisionArea_TopEndpointY();
					currSpan = currentCol.get_CollisionArea_BotEndpointY()
									- currMinEndpoint;
					nextMinEndpoint = nextCol.get_CollisionArea_TopEndpointY();
					if(currMinEndpoint + currSpan <= nextMinEndpoint){
						// The rest of the sprites after this next one will all
						// also be out of range of our current sprite, so we can
						// stop checking at this point.
						break;
					}
				}
				/*
				 * Reaching this point means that there is a possibility for a collision.
				 */
				// Reset opts from last iteration
				check = false;
				currentOpt = null;
				nextOpt = null;
				// Convert to optimized if possible
				if(currentCol instanceof OptimalCollidable)
					currentOpt = (OptimalCollidable) currentCol;
				if(nextCol instanceof OptimalCollidable)
					nextOpt = (OptimalCollidable) nextCol;
				// Check for optimal collisions
				if(currentOpt != null && nextOpt != null)
					check = currentOpt.getOptimalCollisionArea()
						.checkCollisions(nextOpt.getOptimalCollisionArea());
				else if(currentOpt != null)
					check = currentOpt.getOptimalCollisionArea()
						.checkCollisions(nextCol.getCollisionArea());
				else if(nextOpt != null)
					check = nextOpt.getOptimalCollisionArea()
						.checkCollisions(currentCol.getCollisionArea());
				else
					check = true;
				// Passed optimal collision check
				if(check){
					if(currentCol.getCollisionArea()
							.checkCollisions(nextCol.getCollisionArea())){
						currentOrigX = current.position.x;
						currentOrigY = current.position.y;
						nextOrigX = next.position.x;
						nextOrigY = next.position.y;
						currentCol.collide(nextCol, false);
						nextCol.collide(currentCol, false);
						// Current was moved!
						if(current.position.x != currentOrigX
								|| current.position.y != currentOrigY){
							xWasRemoved = true;
							removedSprites.add(sprites.remove(first));
						}
						// Next was moved!
						if(next.position.x != nextOrigX
								|| next.position.y != nextOrigY){
							yWasRemoved = true;
							if(second < first)
								removedSprites.add(sprites.remove(second));
							else
								removedSprites.add(sprites.remove(second-1));
						}
						if(xWasRemoved)
							break;
					}
				}
				if(!yWasRemoved)
					second++;
				size = sprites.size();
			}
			// Only update our index if no sprites were removed
			if(!xWasRemoved)
				first++;
			// Update size, in case it changed
			else
				size = sprites.size();
		}
	}
	
	/**
	 * Decides what the next comparison should be, uses a calculation of the
	 * standard deviation of points in the x-axis and the y-axis. Whichever is
	 * larger will generally mean that there is more spread in sprites in that
	 * direction, meaning we want to use that comparator.
	 */
	private void decideNextComparable(){
		float avgX = 0;
		float avgY = 0;
		double varX = 0;
		double varY = 0;
		double sqrX, sqrY;
		double difX, difY;
		
		/*
		 * Calculate the Mean (avg)
		 */
		for(int x = 0; x < sprites.size(); x++){
			avgX += (sprites.get(x).getPosition().x/((float)sprites.size()));
			
			avgY += (sprites.get(x).getPosition().y/((float)sprites.size()));
		}
		/*
		 * Calculate the Variance (var)
		 */
		for(int x = 0; x < sprites.size(); x++){
			difX = (sprites.get(x).getPosition().x - avgX);
			sqrX = difX * difX;
			varX += sqrX/avgX;
			
			difY = (sprites.get(x).getPosition().y - avgY);
			sqrY = difY * difY;
			varY += sqrY/avgY;
		}
		
		if(varX > varY){
			setComparable(COMP_X);
		}
		else{
			setComparable(COMP_Y);
		}
	}
	
	private void setComparable(int COMP){
		/*
		 * Notice that we always set the new comparables, even if they didn't
		 * change since we last set them in the collision handler. This is due
		 * to the fact that we don't know if a use_Comparable function was 
		 * called from somewhere else. By always setting the comparables, we
		 * can assert that we are getting the correct comparator.
		 */
		if(COMP == COMP_X){
			for(int x = 0; x < sprites.size(); x++){
				sprites.get(x).use_Comparable_X();
			}
			currentComparator = COMP_X;
		}
		else if(COMP == COMP_Y){
			for(int x = 0; x < sprites.size(); x++){
				sprites.get(x).use_Comparable_Y();
			}
			currentComparator = COMP_Y;
		}
		else{
			for(int x = 0; x < sprites.size(); x++){
				sprites.get(x).use_Comparable_RenderLayer();
			}
			currentComparator = COMP_RENDER_LAYER;
		}
	}
	
	public void add(Sprite sprite){
		sprites.add(sprite);
	}
}
