package com.mrhart.sprites;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mrhart.collisions.Collidable;
import com.mrhart.collisions.OptimalCollidable;
import com.mrhart.structures.SortedArrayList;

public class SpriteHandler {
	/*
	 * Instance Vars
	 */
	private SortedArrayList<Sprite> sprites;
	private LinkedList<Sprite> removedSprites;
	
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
	 * @param batcher
	 * @param runtime
	 */
	public void render(SpriteBatch batcher, float runtime){
		for(Iterator<Sprite> i = sprites.iterator(); i.hasNext();){
			i.next().render(batcher, runtime);
		}
	}
	
	/**
	 * Processes all the collisions of all the sprites that are colliding.
	 */
	public void checkCollisions(){
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
		int x = 0;
		int y = 0;
		float currentOrigX;
		float currentOrigY;
		float nextOrigX;
		float nextOrigY;
		int size = sprites.size();
		boolean xWasRemoved;
		boolean yWasRemoved;
		boolean check = false;
		// Add back into the sprites SortedList all the sprites that collided
		// and were removed in the previous iteration
		while(!removedSprites.isEmpty()){
			sprites.add(removedSprites.remove());
		}
		// Runs through all the sprites in sprites.
		while(x < size){
			xWasRemoved = false;
			current = sprites.get(x);
			// If this is not a collidable sprite, update index and continue.
			if(!(current instanceof Collidable) || !((Collidable) current).canCollide()){
				x++;
				continue;
			}
			currentCol = (Collidable) current;
			y = x + 1;
			while(y < size){
				yWasRemoved = false;
				next = sprites.get(y);
				// Next is not a collidable Sprite
				if(!(next instanceof Collidable) || !((Collidable) next).canCollide()){
					y++;
					continue;
				}
				// Convert to collidable for next
				nextCol = (Collidable) next;
				if(!nextCol.canCollideWith(currentCol)){
					y++;
					continue;
				}
				// Imagine two rectangles. If the right side of the rectangle
				// that is further to the left is smaller than the left side
				// of the rectangle on the right, then they cannot be
				// overlapping! This if statement will check that.
				// CHANGE BACK TO <=
				if(currentCol.get_CollisionArea_LeftEndpointX() 
						+ currentCol.get_CollisionArea_Width() 
						<= nextCol.get_CollisionArea_LeftEndpointX()){
					// The rest of the sprites after this next one will all
					// also be out of range of our current sprite, so we can
					// stop checking at this point.
					break;
				}
				// Else there is a possibility these could be colliding.
				else{
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
								removedSprites.add(sprites.remove(x));
							}
							// Next was moved!
							if(next.position.x != nextOrigX
									|| next.position.y != nextOrigY){
								yWasRemoved = true;
								if(y < x)
									removedSprites.add(sprites.remove(y));
								else
									removedSprites.add(sprites.remove(y-1));
							}
							if(xWasRemoved)
							break;
						}
					}
				}
				if(!yWasRemoved)
					y++;
				size = sprites.size();
			}
			// Only update our index if no sprites were removed
			if(!xWasRemoved)
				x++;
			// Update size, in case it changed
			else
				size = sprites.size();
		}
	}
	
	public void add(Sprite sprite){
		sprites.add(sprite);
	}
}
