package com.mrhart.collisions;

/**
 * A class providing static functions for collisions. Methods are provided
 * for checking collisions as well as for performing different styles of
 * collisions.
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.00
 */
public class Collisions {
	/*
	 * Named Constants
	 */
	// Collision Modes
	public static final int ALL = 0;
	public static final int NEIGHBORS = 1;
	// Collision Constants
	private static final int LARGE_NUMBER = 2000000000;

	/***************************************************************************
	 * 							Hard Collide
	 ***************************************************************************/
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
				obj1.updateCollisionArea();
			}
			else if(obj1Y > 0){
				obj1.setPosition(obj1.getPosition().x, obj1.getLastPosition().y);
				obj1.setVelocity(obj1.getVelocity().x, 0);
				obj1.updateCollisionArea();
			}
		}
		else if(Math.max(obj2X, obj2Y) < Math.max(obj1X, obj1Y)){
			if(obj2X > 0){
				obj2.setPosition(obj2.getLastPosition().x, obj2.getPosition().y);
				obj2.setVelocity(0, obj2.getVelocity().y);
				obj2.updateCollisionArea();
			}
			else if(obj2Y > 0){
				obj2.setPosition(obj2.getPosition().x, obj2.getLastPosition().y);
				obj2.setVelocity(obj2.getVelocity().x, 0);
				obj2.updateCollisionArea();
			}
		}
		else{
			if((obj1X == obj2X) && (obj1X != 0)){
				obj1.setPosition(obj1.getLastPosition().x, obj1.getPosition().y);
				obj1.setVelocity(0, obj1.getVelocity().y);
				obj2.setPosition(obj2.getLastPosition().x, obj2.getPosition().y);
				obj2.setVelocity(0, obj2.getVelocity().y);
				obj1.updateCollisionArea();
				obj2.updateCollisionArea();
			}
			else if((obj1Y == obj2Y) && (obj1Y != 0)){
				obj1.setPosition(obj1.getPosition().x, obj1.getLastPosition().y);
				obj1.setVelocity(obj1.getVelocity().x, 0);
				obj2.setPosition(obj2.getPosition().x, obj2.getLastPosition().y);
				obj2.setVelocity(obj2.getVelocity().x, 0);
				obj1.updateCollisionArea();
				obj2.updateCollisionArea();
			}
			else if((obj1X == obj2Y) && (obj1X != 0)){
				obj2.setPosition(obj2.getPosition().x, obj2.getLastPosition().y);
				obj2.setVelocity(obj2.getVelocity().x, 0);
				obj2.updateCollisionArea();
			}
			else{
				obj1.setPosition(obj1.getPosition().x, obj1.getLastPosition().y);
				obj1.setVelocity(obj1.getVelocity().x, 0);
				obj1.updateCollisionArea();
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
