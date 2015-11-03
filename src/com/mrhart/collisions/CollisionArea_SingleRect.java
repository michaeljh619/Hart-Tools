package com.mrhart.collisions;

import com.badlogic.gdx.math.Rectangle;

/**
 * A CollisionArea that has a single Rectangle.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */
public class CollisionArea_SingleRect implements CollisionArea {
	public Rectangle collisionArea;

	public boolean checkCollisions(CollisionArea other){
		// Check if this object has any null pointers
		if(collisionArea == null){
			System.out.println("FATAL ERROR: collisionArea on calling SingleRect object is null!");
			return false;
		}

		/*
		 * Begin Collision Checking
		 */
		/* SingleRect vs. MultCirc (Already Done)
		 */
		if(other instanceof CollisionArea_MultipleCircs){
			return ((CollisionArea_MultipleCircs) other).checkCollisions(this);
		}
		/* SingleRect vs. MultMixed (Already Done)
		 */
		else if(other instanceof CollisionArea_MultipleMixed){
			return ((CollisionArea_MultipleMixed) other).checkCollisions(this);
		}
		/* SingleRect vs. MultRects (Already Done)
		 */
		else if(other instanceof CollisionArea_MultipleRects){
			return ((CollisionArea_MultipleRects) other).checkCollisions(this);
		}
		/* SingleRect vs. SingleCirc (Already Done)
		 */
		else if(other instanceof CollisionArea_SingleCirc){
			return ((CollisionArea_SingleCirc) other).checkCollisions(this);
		}
		/* SingleRect vs. SingleMixed (Already Done)
		 */
		else if(other instanceof CollisionArea_SingleMixed){
			return ((CollisionArea_SingleMixed) other).checkCollisions(this);
		}
		/* SingleRect vs. SingleRect
		 */
		else if(other instanceof CollisionArea_SingleRect){
			// Null pointer check
			if(((CollisionArea_SingleRect) other).collisionArea == null){
				System.out.println("FATAL ERROR: collisionArea on parameter 'other' SingleRect object is null!");
				return false;
			}
			
			/*
			 * Check Collisions
			 */
			if(collisionArea.overlaps(((CollisionArea_SingleRect) other).collisionArea)){
				return true;
			}
			
			// Reaching this point means the collision didn't happen
			return false;
		}
		/* This means the collidable object is not a part of the CollisionArea_ classes
		 */
		else{
			System.out.println("FATAL ERROR: Parameter 'other' does not belong to CollisionArea_ classes!");
			return false;
		}
	}
}
