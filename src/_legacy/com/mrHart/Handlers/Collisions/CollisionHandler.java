package com.mrHart.Handlers.Collisions;

import java.util.ArrayList;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * A class to handle all the collisions of the game, objects that need to be checked for collisions should
 * have methods added that automagically check for the collisions inside here.
 * 
 * There are two types of collisions thus far:
 * 		processCollisions: 			Pros- Fast, Easily manages moving object collisions
 * 									Cons- May result in "Force Field" collisions, cannot be used to push
 * 										  characters around if one is heavier
 * 
 * 						   Processes a collision based on the characters last position.
 * 						   When a character runs into a wall, his lastPosition vector will
 * 						   be reset and his velocity vector will be set to 0 accordingly.
 * 
 * 		processCollisions2: 			Pros- Accurate for wall collisions, 
 * 										Cons- High Overhead, Difficult to manage moving object collisions
 * 
 * 						   	Processes collisions based on the shortest distance to a non-colliding
 * 							position. For example, if a character runs into a wall, the function will
 * 							test different directions to push the character back, the one with the shortest
 * 							distance will be the one to be pushed back towards. There are issues with this
 * 							when dealing with two moving characters, lastPosition reset is suggested for
 * 							movable object collisions
 * 							IMPORTANT: When using processCollisions2, as long as some collisions were
 * 									   processed, more collisions need to be checked, there is push bias
 * 									   based on which objects collide first; one object may push another 
 * 									   object into another object that already checked a collision, resulting
 * 									   in errors.
 * 
 * The class isn't finished, some processCollisions still need to be created.
 * 
 * @author Michael Hart, michaeljh619@yahoo.com
 * @version v1.5
 * @since 01/13/2015
 *
 */
public class CollisionHandler {
	// Named Constant
	private static final int MEDIUM_NUMBER = 150;
	private static final int LARGE_NUMBER = 10000;
	
//	private static final int MAX_OBJECTS = 255;
	
	public static final int MOVABLE_RECT = 1;
	public static final int UNMOVABLE_RECT = 2;
	public static final int MOVABLE_CIRCLE = 3;
	public static final int UNMOVABLE_CIRCLE = 4;
	
	// Instance vars
	public ArrayList<MovableRect> movableRects;
	public ArrayList<MovablePolygon> movablePolygons;
	public ArrayList<UnMovableRect> unMovableRects;
	public ArrayList<MovableCircle> movableCircles;
	
	public int movableRectsIndex = 0;	// Permanent index, do not re-use
	public int movableCirclesIndex = 0;	// Permanent index, do not re-use
	public int unMovableRectsIndex = 0;	// Permanent index, do not re-use
	
	private int tempIndex;  // Temporary index, re-usable
	private int counter, counter2;
	private int collisionsRemaining;
	private int obj1X, obj1Y, obj2X, obj2Y;
	private int collisionMod;
	
	/**
	 * Creates a new collision handler
	 * 
	 * @param inGameWorld
	 */
	public CollisionHandler(ArrayList<MovableRect> inMovableRects,
			ArrayList<UnMovableRect> inUnMovableRects,
			ArrayList<MovableCircle> inMovableCircles){
		movableRects = inMovableRects;
		unMovableRects = inUnMovableRects;
		movableCircles = inMovableCircles;
	}
	
	/*****************************************
	 * Mass Collision Methods
	 *****************************************/
	
	/**
	 * Processes all collisions within the specified objects arrayList
	 * 
	 * @param typeOfObject
	 * @return How many collisions were processed
	 */
	public int processMassCollisions(int typeOfObject){
		collisionsRemaining = 0;
		
		if(typeOfObject == MOVABLE_RECT){
			for(counter = 0; counter < movableRects.size(); counter++){
				for(counter2 = counter+1; counter2 < movableRects.size(); counter2++){
					collisionsRemaining += processCollision(movableRects.get(counter),
							movableRects.get(counter2));
				}
			}
		}
		
		return collisionsRemaining;
	}
	
	/**
	 * Used for processing all collisions between two types of collision objects. Will process
	 * the collisions as normal reset position collisions.
	 * 
	 * @param typeOfObject1 CollisionHandler.(Type of Object)
	 * @param typeOfObject2 CollisionHandler.(Type of Object)
	 * @return how many collisions were processed
	 */
	public int processMassCollisions(int typeOfObject1, int typeOfObject2){
		collisionsRemaining = 0;
		
		if(typeOfObject1 == MOVABLE_RECT && typeOfObject2 == UNMOVABLE_RECT){
			for(counter = 0; counter < movableRects.size(); counter++){
				for(counter2 = 0; counter2 < unMovableRects.size(); counter2++){
					collisionsRemaining += processCollision(movableRects.get(counter),
							unMovableRects.get(counter2));
				}
			}
		}
		else if(typeOfObject2 == MOVABLE_RECT && typeOfObject1 == UNMOVABLE_RECT){
			for(counter = 0; counter < movableRects.size(); counter++){
				for(counter2 = 0; counter2 < unMovableRects.size(); counter2++){
					collisionsRemaining += processCollision(movableRects.get(counter),
							unMovableRects.get(counter2));
				}
			}
		}
		else if(typeOfObject1 == MOVABLE_CIRCLE && typeOfObject2 == UNMOVABLE_RECT){
			for(counter = 0; counter < movableCircles.size(); counter++){
				for(counter2 = 0; counter2 < unMovableRects.size(); counter2++){
					collisionsRemaining += processCollision(movableCircles.get(counter),
							unMovableRects.get(counter2));
				}
			}
		}
		else if(typeOfObject2 == MOVABLE_CIRCLE && typeOfObject1 == UNMOVABLE_RECT){
			for(counter = 0; counter < movableCircles.size(); counter++){
				for(counter2 = 0; counter2 < unMovableRects.size(); counter2++){
					collisionsRemaining += processCollision(movableCircles.get(counter),
							unMovableRects.get(counter2));
				}
			}
		}
		
		return collisionsRemaining;
	}
	
	/**
	 * Used for processing all collisions between two types of collision objects. Will process
	 * the collisions as push back collisions
	 * 
	 * @param typeOfObject1 CollisionHandler.(Type of Object)
	 * @param typeOfObject2 CollisionHandler.(Type of Object)
	 * @return how many collisions were processed
	 */
	public int processMassCollisions2(int typeOfObject1, int typeOfObject2){
		collisionsRemaining = 0;
		
		if(typeOfObject1 == MOVABLE_RECT && typeOfObject2 == UNMOVABLE_RECT){
			for(counter = 0; counter < movableRects.size(); counter++){
				for(counter2 = 0; counter2 < unMovableRects.size(); counter2++){
					collisionsRemaining += processCollision2(movableRects.get(counter),
							unMovableRects.get(counter2));
				}
			}
		}
		else if(typeOfObject2 == MOVABLE_RECT && typeOfObject1 == UNMOVABLE_RECT){
			for(counter = 0; counter < movableRects.size(); counter++){
				for(counter2 = 0; counter2 < unMovableRects.size(); counter2++){
					collisionsRemaining += processCollision2(movableRects.get(counter),
							unMovableRects.get(counter2));
				}
			}
		}
		else if(typeOfObject1 == MOVABLE_CIRCLE && typeOfObject2 == UNMOVABLE_RECT){
			for(counter = 0; counter < movableCircles.size(); counter++){
				for(counter2 = 0; counter2 < unMovableRects.size(); counter2++){
					collisionsRemaining += processCollision2(movableCircles.get(counter),
							unMovableRects.get(counter2));
				}
			}
		}
		else if(typeOfObject2 == MOVABLE_CIRCLE && typeOfObject1 == UNMOVABLE_RECT){
			for(counter = 0; counter < movableCircles.size(); counter++){
				for(counter2 = 0; counter2 < unMovableRects.size(); counter2++){
					collisionsRemaining += processCollision2(movableCircles.get(counter),
							unMovableRects.get(counter2));
				}
			}
		}
		
		return collisionsRemaining;
	}
	
	/*****************************************
	 * Mass Collision Methods [END]
	 *****************************************/
	
	
	
	/*******************************
	 * processCollision() polymorphs
	 *******************************/
	
	public int processCollision(MovableRect obj1, UnMovableRect obj2){
		if(!isColliding(obj1.getCollisionBox(), obj2.getCollisionBox())){
			return 0;
		}
		else{
			// Calculate all the objs collision distances
			obj1X = getDistanceXOfFirst(obj1, obj2.getCollisionBox());
			obj1Y = getDistanceYOfFirst(obj1, obj2.getCollisionBox());
			
			if(obj1X < obj1Y){
				obj1.resetX();
			}
//			else if(obj1Y < obj1X){
//				obj1.resetY();
//			}
			else{
				obj1.resetY();
			}
			
			return 1;
		}
	}
	
	public int processCollision(MovableCircle obj1, UnMovableRect obj2){
		if(!isColliding(obj2.getCollisionBox(), obj1.getCollisionCircle())){
			return 0;
		}
		else{
			// Calculate all the objs collision distances
			obj1X = getDistanceXOfFirst(obj1, obj2.getCollisionBox());
			obj1Y = getDistanceYOfFirst(obj1, obj2.getCollisionBox());
			
			if(obj1X < obj1Y){
				obj1.resetX();
			}
			else if(obj1Y < obj1X){
				obj1.resetY();
			}
			
			return 1;
		}
	}
	
	public int processCollision(MovableRect obj1, MovableRect obj2){
		if(!isColliding(obj1.getCollisionBox(), obj2.getCollisionBox()))
			return 0;
		else{
			obj1X = getDistanceXOfFirst(obj1, obj2.getCollisionBox());
			obj2X = getDistanceXOfFirst(obj2, obj1.getCollisionBox());
			
			obj1Y = getDistanceYOfFirst(obj1, obj2.getCollisionBox());
			obj2Y = getDistanceYOfFirst(obj2, obj1.getCollisionBox());

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
					obj1.resetX();
				}
				else if(obj1Y > 0){
					obj1.resetY();
				}
			}
			else if(Math.max(obj2X, obj2Y) < Math.max(obj1X, obj1Y)){
				if(obj2X > 0){
					obj2.resetX();
				}
				else if(obj2Y > 0){
					obj2.resetY();
				}
			}
			else{
				if((obj1X == obj2X) && (obj1X != 0)){
					obj1.resetX();
					obj2.resetX();
				}
				else if((obj1Y == obj2Y) && (obj1Y != 0)){
					obj1.resetY();
					obj2.resetY();
				}
				else if((obj1X == obj2Y) && (obj1X != 0)){
					obj2.resetY();
				}
				else{
					obj1.resetY();
				}
			}
			
			return 1;
		}
	}
	
	/*******************************
	 * processCollision() polymorphs [END]
	 *******************************/
	
	
	/*******************************
	 * processCollision2() polymorphs
	 *******************************/
	
	public int processCollision2(MovableRect obj1, UnMovableRect obj2){
		if(!isColliding(obj1.getCollisionBox(), obj2.getCollisionBox())){
			return 0;
		}
		else{
			// Calculate all the objs collision distances
			obj1X = getDistanceXOfFirst2(obj1, obj2.getCollisionBox());
			obj1Y = getDistanceYOfFirst2(obj1, obj2.getCollisionBox());
			
			if(Math.abs(obj1X) < Math.abs(obj1Y)){
				pushX(obj1X, obj1.getPosition(), obj1.getVelocity());
			}
			else if(Math.abs(obj1Y) < Math.abs(obj1X)){
				pushY(obj1Y, obj1.getPosition(), obj1.getVelocity());
			}
			else{
				pushX(obj1X, obj1.getPosition(), obj1.getVelocity());		
			}
			
			// Actually update the collision box
			obj1.updateRect();
			
			return 1;
		}
	}
	
	public int processCollision2(MovableCircle obj1, UnMovableRect obj2){
		if(!isColliding(obj2.getCollisionBox(), obj1.getCollisionCircle())){
			return 0;
		}
		else{
			// Calculate all the objs collision distances
			obj1X = getDistanceXOfFirst2(obj1, obj2.getCollisionBox());
			obj1Y = getDistanceYOfFirst2(obj1, obj2.getCollisionBox());
						
			if(Math.abs(obj1X) < Math.abs(obj1Y)){
				pushX(obj1X, obj1.getPosition(), obj1.getVelocity());
			}
			else if(Math.abs(obj1Y) < Math.abs(obj1X)){
				pushY(obj1Y, obj1.getPosition(), obj1.getVelocity());
			}
			
			// Update collision circle
			obj1.updateCircle();
						
			return 1;
		}
	}
	
	public int processCollision2(MovableRect obj1, MovableRect obj2){
		if(!isColliding(obj1.getCollisionBox(), obj2.getCollisionBox()))
			return 0;
		else{
			obj1X = getDistanceXOfFirst(obj1, obj2.getCollisionBox());
			obj2X = getDistanceXOfFirst(obj2, obj1.getCollisionBox());
			
			obj1Y = getDistanceYOfFirst(obj1, obj2.getCollisionBox());
			obj2Y = getDistanceYOfFirst(obj2, obj1.getCollisionBox());
			
			int obj1XUnsigned = Math.abs(obj1X);
			int obj2XUnsigned = Math.abs(obj2X);
			int obj1YUnsigned = Math.abs(obj1Y);
			int obj2YUnsigned = Math.abs(obj2Y);

			/*
			 * See which distance is larger, the larger distance will be set to 0 since we want to process
			 * the distance which is smaller
			 */
			if(obj1XUnsigned > obj1YUnsigned){
				obj1XUnsigned = 0;
			}
			else if(obj1YUnsigned > obj1XUnsigned){
				obj1YUnsigned = 0;
			}
			else{	// Why is this? Width of obj is smaller than height of obj, so this gives
					// a slight advantage to objs who have an equidistance of x and y moving horizontally
				obj1XUnsigned = 0;
			}
			if(obj2XUnsigned > obj2YUnsigned){
				obj2XUnsigned = 0;
			}
			else if(obj2YUnsigned > obj2XUnsigned){
				obj2YUnsigned = 0;
			}
			else{	// Why is this? Width of obj is smaller than height of obj, so this gives
					// a slight advantage to objs who have an equidistance of x and y moving horizontally
				obj2XUnsigned = 0;
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
			if(Math.max(obj1XUnsigned, obj1YUnsigned) < Math.max(obj2XUnsigned, obj2YUnsigned)){
				if(obj1XUnsigned > 0){
					pushX(obj1X, obj1.getPosition(), obj1.getVelocity());
				}
				else if(obj1YUnsigned > 0){
					pushY(obj1Y, obj1.getPosition(), obj1.getVelocity());
				}
			}
			else if(Math.max(obj2XUnsigned, obj2YUnsigned) < Math.max(obj1XUnsigned, obj1YUnsigned)){
				if(obj2XUnsigned > 0){
					pushX(obj2X, obj2.getPosition(), obj2.getVelocity());
				}
				else if(obj2YUnsigned > 0){
					pushY(obj2Y, obj2.getPosition(), obj2.getVelocity());
				}
			}
			else{
				if((obj1XUnsigned == obj2XUnsigned) && (obj1XUnsigned != 0)){
					pushX(obj1X, obj1.getPosition(), obj1.getVelocity());
					pushX(obj2X, obj2.getPosition(), obj2.getVelocity());
				}
				else if((obj1YUnsigned == obj2YUnsigned) && (obj1YUnsigned != 0)){
					pushY(obj1Y, obj1.getPosition(), obj1.getVelocity());
					pushY(obj2Y, obj2.getPosition(), obj2.getVelocity());
				}
				else if((obj1XUnsigned == obj2YUnsigned) && (obj1XUnsigned != 0)){
					pushY(obj2Y, obj2.getPosition(), obj2.getVelocity());
				}
				else{
					pushY(obj1Y, obj1.getPosition(), obj1.getVelocity());
				}
			}
			
			// Update the collision boxes
			obj1.updateRect();
			obj2.updateRect();
			
			return 1;
		}
	}
	
	/**
	 * Used for processCollisions2, pushes the object back the push amount, resets velocity.
	 * 	For x coordinates only
	 * 
	 * @param pushAmount	Amount to push by
	 * @param position		Position X to push
	 * @param velocity		Velocity X to zero
	 */
	private void pushX(int pushAmount, Vector2 position, Vector2 velocity){
		position.x -= pushAmount;
		velocity.x = 0;
	}
	
	/**
	 * Used for processCollisions2, pushes the object back the push amount, resets velocity
	 * 	For y coordinates only
	 * 
	 * @param pushAmount	Amount to push by
	 * @param position		Position Y to push
	 * @param velocity		Velocity Y to zero
	 */
	private void pushY(int pushAmount, Vector2 position, Vector2 velocity){
		position.y += pushAmount;
		velocity.y = 0;
	}
	
	/*******************************
	 * processCollision2() polymorphs [END]
	 *******************************/
	
	
	
	/**************************
	 * getDistance() polymorphs
	 **************************/
	public int getDistanceXOfFirst(MovableRect obj1, Rectangle obj2Rect){
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
		tempIndex = 0;
		// Tests how far the player has to go before he is no longer collided
		while(isColliding(obj1.getCollisionBox(), obj2Rect)){
			tempIndex++;
			obj1.getPosition().x += collisionMod;
			obj1.updateRect();
			
			// Obviously not the smaller direction
			if(tempIndex > MEDIUM_NUMBER)
				break;
		}
		// To reset his position;
		obj1.getPosition().x -= tempIndex*collisionMod;
		obj1.updateRect();
		
		return tempIndex;
	}
	
	public int getDistanceYOfFirst(MovableRect obj1, Rectangle obj2Rect){
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
		tempIndex = 0;
		// Tests how far the player has to go before he is no longer collided
		while(isColliding(obj1.getCollisionBox(), obj2Rect)){
			tempIndex++;
			obj1.getPosition().y += collisionMod;
			obj1.updateRect();
			
			// Obviously not the smaller direction
			if(tempIndex > MEDIUM_NUMBER)
				break;
		}
		// To reset his position;
		obj1.getPosition().y -= tempIndex*collisionMod;
		obj1.updateRect();
		
		return tempIndex;
	}
	
	private int getDistanceXOfFirst(MovableCircle obj1, Rectangle obj2Rect){
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
		tempIndex = 0;
		// Tests how far the player has to go before he is no longer collided
		while(isColliding(obj2Rect, obj1.getCollisionCircle())){
			tempIndex++;
			obj1.getPosition().x += collisionMod;
			obj1.updateCircle();
			
			// Obviously not the smaller direction
			if(tempIndex > MEDIUM_NUMBER)
				break;
		}
		// To reset his position;
		obj1.getPosition().x -= tempIndex*collisionMod;
		obj1.updateCircle();
		
		return tempIndex;
	}
	
	private int getDistanceYOfFirst(MovableCircle obj1, Rectangle obj2Rect){
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
		tempIndex = 0;
		// Tests how far the player has to go before he is no longer collided
		while(isColliding(obj2Rect, obj1.getCollisionCircle())){
			tempIndex++;
			obj1.getPosition().y += collisionMod;
			obj1.updateCircle();
			
			// Obviously not the smaller direction
			if(tempIndex > MEDIUM_NUMBER)
				break;
		}
		// To reset his position;
		obj1.getPosition().y -= tempIndex*collisionMod;
		obj1.updateCircle();
		
		return tempIndex;
	}
	
	private int getDistanceYOfFirst2(MovableCircle obj1, Rectangle obj2Rect){
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
		tempIndex = 0;
		// Tests how far the player has to go before he is no longer collided
		while(isColliding(obj2Rect, obj1.getCollisionCircle())){
			tempIndex += collisionMod;
			obj1.getPosition().y += collisionMod;
			obj1.updateCircle();
			
			// Obviously not the smaller direction
			if(Math.abs(tempIndex) > MEDIUM_NUMBER)
				break;
		}
		// To reset his position;
		obj1.getPosition().y -= Math.abs(tempIndex)*collisionMod;
		obj1.updateCircle();
		
		return tempIndex;
	}
	
	private int getDistanceXOfFirst2(MovableRect obj1, Rectangle obj2Rect){
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
		tempIndex = 0;
		// Tests how far the player has to go before he is no longer collided
		while(isColliding(obj1.getCollisionBox(), obj2Rect)){
			tempIndex += collisionMod;
			obj1.getPosition().x += collisionMod;
			obj1.updateRect();
			
			// Obviously not the smaller direction
			if(Math.abs(tempIndex) > MEDIUM_NUMBER)
				break;
		}
		// To reset his position;
		obj1.getPosition().x -= Math.abs(tempIndex)*collisionMod;
		obj1.updateRect();
		
		return tempIndex;
	}
	
	private int getDistanceYOfFirst2(MovableRect obj1, Rectangle obj2Rect){
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
		tempIndex = 0;
		// Tests how far the player has to go before he is no longer collided
		while(isColliding(obj1.getCollisionBox(), obj2Rect)){
			tempIndex += collisionMod;
			obj1.getPosition().y += collisionMod;
			obj1.updateRect();
			
			// Obviously not the smaller direction
			if(Math.abs(tempIndex) > MEDIUM_NUMBER)
				break;
		}
		// To reset his position;
		obj1.getPosition().y -= Math.abs(tempIndex)*collisionMod;
		obj1.updateRect();
		
		return tempIndex;
	}
	
	private int getDistanceXOfFirst2(MovableCircle obj1, Rectangle obj2Rect){
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
		tempIndex = 0;
		// Tests how far the player has to go before he is no longer collided
		while(isColliding(obj2Rect, obj1.getCollisionCircle())){
			tempIndex += collisionMod;
			obj1.getPosition().x += collisionMod;
			obj1.updateCircle();
			
			// Obviously not the smaller direction
			if(Math.abs(tempIndex) > MEDIUM_NUMBER)
				break;
		}
		// To reset his position;
		obj1.getPosition().x -= Math.abs(tempIndex)*collisionMod;
		obj1.updateCircle();
		
		return tempIndex;
	}
	
	/**************************
	 * isColliding() polymorphs
	 **************************/
	public static boolean isColliding(Rectangle rect1, Rectangle rect2){
		if (rect1.overlaps(rect2)) 
			return true;
		else
			return false;
	}
	
	public static boolean isColliding(Polygon shape1, Polygon shape2){
		if (Intersector.overlapConvexPolygons(shape1, shape2)) 
			return true;
		else
			return false;
	}
	
	public static boolean isColliding(Rectangle rect1, Circle circ1){
		if (Intersector.overlaps(circ1, rect1)) 
			return true;
		else
			return false;
	}
	
	public static boolean isColliding(Circle circ1, Circle circ2){
		if (circ1.overlaps(circ2)) 
			return true;
		else
			return false;
	}
	
	/*****************************************
	 * Collision Methods [END]
	 *****************************************/
}
