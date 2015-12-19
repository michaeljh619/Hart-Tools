package com.mrhart.collisions;

import java.util.Iterator;

import com.mrhart.sprites.Sprite;
import com.mrhart.sprites.SpriteHandler;
import com.mrhart.sprites.SpritePair;

/**
 * A class providing static functions for collisions. Methods are provided
 * for checking collisions as well as for performing different styles of
 * collisions.
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.00
 */
public class CollisionHandler {
	/*
	 * Named Constants
	 */
	// Collision Modes
	public static final int ALL = 0;
	public static final int NEIGHBORS = 1;
	// Collision Constants
	private static final int LARGE_NUMBER = 2000000000;
	
	/**
	 * Checks collisions for the Array of dangles of Sprites.
	 * 
	 * Mode:
	 * 	- ALL = Check every sprite with every other sprite in every
	 * 					dangle that is in the SpritePair array (including
	 * 					the same dangle)
	 *  - NEIGHBORS = Check every sprite with every other sprite in every
	 *  			  			dangle except the one this sprite is in.
	 * 
	 * @version v1.00
	 * @since v1.00
	 * @param sprites SpriteHandler to be checked for collisions
	 * @param mode Mode for checking collisions
	 */
	public static void checkCollisions(SpriteHandler sprites, int mode) {
		checkCollisions(sprites.sprites, mode);
	}

	/**
	 * Checks collisions for the Array of dangles of Sprites.
	 * 
	 * Mode:
	 * 	- ALL = Check every sprite with every other sprite in every
	 * 					dangle that is in the SpritePair array (including
	 * 					the same dangle)
	 *  - NEIGHBORS = Check every sprite with every other sprite in every
	 *  			  			dangle except the one this sprite is in.
	 * 
	 * @version v1.00
	 * @since v1.00
	 * @param sprites Sprite dangle array
	 * @param mode Mode for checking collisions
	 */
	public static void checkCollisions(SpritePair[] sprites, int mode) {
		// Checks each dangle and each Sprite inside the dangle against
		// every other dangle that comes after this dangle in the array.
		// Notice we don't check the last dangle, because that dangle (at the
		// end) has already been checked by all the others.
		Sprite current;
		for (int x = 0; x < (sprites.length - 1); x++) {
			// If this isn't a collidable dangle, continue to next dangle.
			if (!sprites[x].isCollidable()) {
				continue;
			}

			// Use an iterator to traverse each ArrayList
			for (Iterator<Sprite> iterator = sprites[x].iterator(); iterator
					.hasNext();) {
				current = iterator.next();
				checkSingleSprite(sprites, current, x, mode, false);
			}
		}
	}

	/**
	 * Checks the current sprite with dangles based on mode.
	 * 
	 * @since v1.0
	 * @version v1.0
	 * @param sprites
	 * @param current
	 * @param index
	 * @param mode
	 */
	private static void checkSingleSprite(SpritePair[] sprites, Sprite current,
			int index, int mode, boolean calledFromCollide) {
		// Next Sprite
		Sprite next;
		// Current Sprite
		boolean isOptimal = false;
		boolean isCollidable = false;
		// Mode
		int newIndex;
		boolean ALL_ready = false;
		
		// If called from a collide function, recheck the old sprites too
		// since the sprite could have been pushed into another old sprite
		if(calledFromCollide)
			ALL_ready = true;
		
		// ALL Mode starts at its own dangle
		if(mode == ALL)
			newIndex = index;
		// NEIGHBORS Mode starts at the next dangle.
		else
			newIndex = index + 1;
		
		// Check what the current Sprite actually is
		if(current instanceof OptimalCollidable){
			isOptimal = true;
			if(((OptimalCollidable) current).canCollide())
				isCollidable = true;
		}
		else if(current instanceof Collidable){
			if(((Collidable) current).canCollide())
				isCollidable = true;
		}

		// Loop for checking against all Sprites in all Dangles that
		// come after this dangle. This way we don't check old dangles that
		// already checked this dangle.
		if (isCollidable){ 
			for (int x = newIndex; x < sprites.length; x++) {
				// Checks against all Sprites in this Dangle
				for (Iterator<Sprite> iterator = sprites[x].iterator(); iterator
						.hasNext();) {
					// Get next Sprite
					next = iterator.next();
					// In ALL mode, skip checking this sprite against itself
					if(mode == ALL && current == next){
						ALL_ready = true;
						continue;
					}
					// In ALL mode, once the next sprite and the current sprite
					// are detected to be the same, we can skip this iteration
					// and start collision checking. Otherwise, we will be double
					// colliding since sprite1 and sprite2 (for example) would both
					// collision check each other and collide with each other.
					if(mode == ALL && !ALL_ready){
						continue;
					}

					// Check if this is even a Collidable object
					if (next instanceof Collidable) {
						// Get the casted collidables for each Sprite
						Collidable currCo = ((Collidable) current);
						Collidable nextCo = ((Collidable) next);
						// Check for Optimal Collisions
						boolean isNextOptimal = false;
						if(next instanceof OptimalCollidable){
							isNextOptimal = true;
						}
						
						/*
						 * Check optimal collisions
						 */
						boolean check = false;
						if(isOptimal && isNextOptimal){
							OptimalCollidable currOp 
								= ((OptimalCollidable) current);
							OptimalCollidable nextOp
								= ((OptimalCollidable) next);
							
							// Check Optimal Collisions
							if(currOp.getOptimalCollisionArea().checkCollisions
									(nextOp.getOptimalCollisionArea())){
								check = true;
							}
						}
						else if(!isOptimal && isNextOptimal){
							OptimalCollidable nextOp
								= ((OptimalCollidable) next);
							
							// Check Optimal Collisions
							if(currCo.getCollisionArea().checkCollisions
									(nextOp.getOptimalCollisionArea())){
								check = true;
							}
						}
						else if(isOptimal && !isNextOptimal){
							OptimalCollidable currOp
								= ((OptimalCollidable) current);

							// Check Optimal Collisions
							if(currOp.getOptimalCollisionArea().checkCollisions
									(nextCo.getCollisionArea())){
								check = true;
							}
						}
						// If Neither is optimal collidable, then we can just
						// check the regular collisions
						else{
							check = true;
						}
						
						/*
						 * Check collisions if made it through the optimal checks
						 */
						if(check){
							// Finally check the collision!
							if(currCo.getCollisionArea().checkCollisions
									(nextCo.getCollisionArea())){
								currCo.collide(nextCo, calledFromCollide);
								nextCo.collide(currCo, calledFromCollide);
							}
						}
					}
				}
			}
		}
	}
	
	
	
	/***************************************************************************
	 * 							Hard Collide
	 ***************************************************************************/
	/**
	 * Performs a hard bounce collision on 2 Resettable objects. A hard bounce is
	 * basically a bounce that takes whoever was responsible for the collision
	 * and mirrors their velocity.
	 * 
	 * @version v1.00
	 * @since v1.00
	 * @param obj1
	 * @param obj2
	 */
	public static void collide_Hard_Bounce(Resettable obj1, Resettable obj2){
		float obj1VelocityX = obj1.getVelocity().x;
		float obj1VelocityY = obj1.getVelocity().y;
		float obj2VelocityX = obj2.getVelocity().x;
		float obj2VelocityY = obj2.getVelocity().y;
		
		collide_Hard(obj1, obj2);
		
		if(obj1.getVelocity().x == 0 && obj1VelocityX != 0){
			obj1.getVelocity().x = -obj1VelocityX;
		}
		if(obj1.getVelocity().y == 0 && obj1VelocityY != 0){
			obj1.getVelocity().y = -obj1VelocityY;
		}
		
		if(obj2.getVelocity().x == 0 && obj2VelocityX != 0){
			obj2.getVelocity().x = -obj2VelocityX;
		}
		if(obj2.getVelocity().y == 0 && obj2VelocityY != 0){
			obj2.getVelocity().y = -obj2VelocityY;
		}
	}
	/**
	 * Performs a hard collision on 2 Resettable-Collidable objects. A hard
	 * collision is a collision where the two objects cannot overlap and the
	 * one who is "responsible" for the collision, gets pushed back into the
	 * last position they were in.
	 * 
	 * @version v1.00
	 * @since v1.00
	 * @param obj1
	 * @param obj2
	 */
	public static void collide_Hard(Resettable obj1, Resettable obj2){
		int obj1X = getDistanceXOfFirst(obj1, obj2);
		int obj2X = getDistanceXOfFirst(obj2, obj1);
		
		int obj1Y = getDistanceYOfFirst(obj1, obj2);
		int obj2Y = getDistanceYOfFirst(obj2, obj1);

		/*
			* See which distance is larger, the larger distance will be set to 0 since we want to process
			* the distance which is smaller
			*/
		if(obj1X > obj1Y){
			obj1X = 0;
		}
		else if(obj1Y > obj1X){
			obj1Y = 0;
		}
		else{	// Why is this? Width of obj is smaller than height of obj, so this gives
				// a slight advantage to objs who have an equidistance of x and y moving horizontally
			obj1X = 0;
		}
		if(obj2X > obj2Y){
			obj2X = 0;
		}
		else if(obj2Y > obj2X){
			obj2Y = 0;
		}
		else{	// Why is this? Width of obj is smaller than height of obj, so this gives
				// a slight advantage to objs who have an equidistance of x and y moving horizontally
			obj2X = 0;
		}
		
		/*
			* Now based on previous calculations:
			* 
			* If the first obj has a smaller maxed distance to travel versus the second obj's
			* distance, then obj 1 is at fault for the collision and should be reset in their smallest
			* x or y distance (calculated previously).
			* Else if, goes the same for obj 2.
			* 
			* "Else" gets interesting. If the objs have the same distance in the same direction, then
			* we reset both their positions. However, if the x and y distance is the same, then the obj
			* with the y distance will be reset since we want to give an advantage to the obj moving
			* horizontally.
			*/
		if(Math.max(obj1X, obj1Y) < Math.max(obj2X, obj2Y)){
			if(obj1X > 0){
				obj1.setPosition(obj1.getLastPosition().x, obj1.getPosition().y);
				obj1.setVelocity(0, obj1.getVelocity().y);
			}
			else if(obj1Y > 0){
				obj1.setPosition(obj1.getPosition().x, obj1.getLastPosition().y);
				obj1.setVelocity(obj1.getVelocity().x, 0);
			}
		}
		else if(Math.max(obj2X, obj2Y) < Math.max(obj1X, obj1Y)){
			if(obj2X > 0){
				obj2.setPosition(obj2.getLastPosition().x, obj2.getPosition().y);
				obj2.setVelocity(0, obj2.getVelocity().y);
			}
			else if(obj2Y > 0){
				obj2.setPosition(obj2.getPosition().x, obj2.getLastPosition().y);
				obj2.setVelocity(obj2.getVelocity().x, 0);
			}
		}
		else{
			if((obj1X == obj2X) && (obj1X != 0)){
				obj1.setPosition(obj1.getLastPosition().x, obj1.getPosition().y);
				obj1.setVelocity(0, obj1.getVelocity().y);
				obj2.setPosition(obj2.getLastPosition().x, obj2.getPosition().y);
				obj2.setVelocity(0, obj2.getVelocity().y);
			}
			else if((obj1Y == obj2Y) && (obj1Y != 0)){
				obj1.setPosition(obj1.getPosition().x, obj1.getLastPosition().y);
				obj1.setVelocity(obj1.getVelocity().x, 0);
				obj2.setPosition(obj2.getPosition().x, obj2.getLastPosition().y);
				obj2.setVelocity(obj2.getVelocity().x, 0);
			}
			else if((obj1X == obj2Y) && (obj1X != 0)){
				obj2.setPosition(obj2.getPosition().x, obj2.getLastPosition().y);
				obj2.setVelocity(obj2.getVelocity().x, 0);
			}
			else{
				obj1.setPosition(obj1.getPosition().x, obj1.getLastPosition().y);
				obj1.setVelocity(obj1.getVelocity().x, 0);
			}
		}
	}
	/**
	 * Gets the distance that obj1 has to travel before its not colliding with
	 * obj2. Checks the X direction.
	 * 
	 * @version v1.00
	 * @since v1.00
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	private static int getDistanceXOfFirst(Resettable obj1, Resettable obj2){
		int collisionMod = 0;
		
		// Checks which direction to test in
		if(obj1.getVelocity().x > 0){
			collisionMod = -1;
		}
		else if(obj1.getVelocity().x < 0){
			collisionMod = 1;
		}
		else{	// If the player isn't even moving, then his collision distance can't be calculated
			return LARGE_NUMBER; 
		}
			
		// Make sure to always set index to 0 before use
		int tempIndex = 0;
		// Tests how far the player has to go before he is no longer collided
		while(obj1.getCollisionArea().checkCollisions(obj2.getCollisionArea())){
			tempIndex++;
			obj1.getPosition().x += collisionMod;
			obj1.updateCollisionArea();
			
			// Obviously not the smaller direction
			if(tempIndex > (1 + Math.abs(obj1.getVelocity().x) 
												+ Math.abs(obj2.getVelocity().x)))
				break;
		}
		// To reset his position;
		obj1.getPosition().x -= tempIndex*collisionMod;
		obj1.updateCollisionArea();
		
		return tempIndex;
	}
	/**
	 * Gets the distance that obj1 has to travel before its not colliding with
	 * obj2. Checks the Y direction.
	 * 
	 * @version v1.00
	 * @since v1.00
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	private static int getDistanceYOfFirst(Resettable obj1, Resettable obj2){
		int collisionMod = 0;
		
		// Checks which direction to test in
		if(obj1.getVelocity().y > 0){
			collisionMod = -1;
		}
		else if(obj1.getVelocity().y < 0){
			collisionMod = 1;
		}
		else{	// If the player isn't even moving, then his collision distance can't be calculated
			return LARGE_NUMBER; 
		}
			
		// Make sure to always set index to 0 before use
		int tempIndex = 0;
		// Tests how far the player has to go before he is no longer collided
		while(obj1.getCollisionArea().checkCollisions(obj2.getCollisionArea())){
			tempIndex++;
			obj1.getPosition().y += collisionMod;
			obj1.updateCollisionArea();
			
			// Obviously not the smaller direction
			if(tempIndex > (1 + Math.abs(obj1.getVelocity().y) 
												+ Math.abs(obj2.getVelocity().y)))
				break;
		}
		// To reset his position;
		obj1.getPosition().y -= tempIndex*collisionMod;
		obj1.updateCollisionArea();
		
		return tempIndex;
	}
	/***************************************************************************
	 * 							Hard Collide [END]
	 ***************************************************************************/
}
