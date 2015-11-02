package com.mrhart.collisions;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

/**
 * A CollisionArea that has a single Circle.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */
public class CollisionArea_SingleCirc implements CollisionArea {
	public Circle collisionArea;
	
	public boolean checkCollisions(CollisionArea other){
		// Check if this object has any null pointers
		if(collisionArea == null){
			System.out.println("FATAL ERROR: collisionArea on calling SingleCirc object is null!");
			return false;
		}

		/*
		 * Begin Collision Checking
		 */
		/* SingleCirc vs. MultipleCircs (Already Done)
		 */
		if(other instanceof CollisionArea_MultipleCircs){
			return ((CollisionArea_MultipleCircs) other).checkCollisions(this);
		}
		/* SingleCirc vs. MultipleMixed (Already Done)
		 */
		else if(other instanceof CollisionArea_MultipleMixed){
			return ((CollisionArea_MultipleMixed) other).checkCollisions(this);
		}
		/* SingleCirc vs. MultipleRects (Already Done)
		 */
		else if(other instanceof CollisionArea_MultipleRects){
			return ((CollisionArea_MultipleRects) other).checkCollisions(this);
		}
		/* SingleCirc vs. SingleCirc
		 */
		else if(other instanceof CollisionArea_SingleCirc){
			// Null Pointer Checking
			if(((CollisionArea_SingleCirc) other).collisionArea == null){
				System.out.println("FATAL ERROR: collisionArea on parameter 'other' SingleCirc object is null!");
				return false;
			}
			
			/*
			 * Check Collisions
			 */
			if(collisionArea.overlaps(((CollisionArea_SingleCirc) other).collisionArea)){
				return true;
			}
			
			// Reaching this far in the code means that there were no collisions
			return false;
		}
		/* SingleCirc vs. SingleMixed
		 */
		else if(other instanceof CollisionArea_SingleMixed){
			// Null pointer check
			if(((CollisionArea_SingleMixed) other).collisionCirc == null
					|| ((CollisionArea_SingleMixed) other).collisionRect == null){
				System.out.println("FATAL ERROR: collisionArea(s) on parameter 'other' SingleMixed object is null!");
				return false;
			}
			
			/*
			 * Check Collisions
			 */
			if(collisionArea.overlaps(((CollisionArea_SingleMixed) other).collisionCirc)
					|| Intersector.overlaps(collisionArea, ((CollisionArea_SingleMixed) other).collisionRect)){
				return true;
			}
			
			// Reaching this point means there was no collision
			return false;
		}
		/* SingleCirc vs. SingleRect
		 */
		else if(other instanceof CollisionArea_SingleRect){
			// Null pointer check
			if(((CollisionArea_SingleRect) other).collisionArea == null){
				System.out.println("FATAL ERROR: collisionArea on parameter 'other' SingleRect object is null!");
				return false;
			}
			
			/*
			 * Check collisions
			 */
			if(Intersector.overlaps(collisionArea, ((CollisionArea_SingleRect) other).collisionArea)){
				return true;
			}
			
			// Reaching this point means there was no collision
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
