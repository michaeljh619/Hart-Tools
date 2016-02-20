package com.mrhart.collisions;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

/**
 * A CollisionArea that has a single Circle and a single Rectangle.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */
public class CollisionArea_SingleMixed implements CollisionArea{
	public Circle collisionCirc;
	public Rectangle collisionRect;
	
	
	public boolean checkCollisions(CollisionArea other){
		// Check if this object has any null pointers
		if(collisionCirc == null || collisionRect == null){
			System.out.println("FATAL ERROR: collisionArea on calling SingleMixed object is null!");
			return false;
		}

		/*
		 * Begin Collision Checking
		 */
		/* SingleMixed vs. MultCirc (Already Done)
		 */
		if(other instanceof CollisionArea_MultipleCircs){
			return ((CollisionArea_MultipleCircs) other).checkCollisions(this);
		}
		/* SingleMixed vs. MultMixed (Already Done)
		 */
		else if(other instanceof CollisionArea_MultipleMixed){
			return ((CollisionArea_MultipleMixed) other).checkCollisions(this);
		}
		/* SingleMixed vs. MultRect (Already Done)
		 */
		else if(other instanceof CollisionArea_MultipleRects){
			return ((CollisionArea_MultipleRects) other).checkCollisions(this);
		}
		/* SingleMixed vs. SingleCirc (Already Done)
		 */
		else if(other instanceof CollisionArea_SingleCirc){
			return ((CollisionArea_SingleCirc) other).checkCollisions(this);
		}
		/* SingleMixed vs. SingleMixed
		 */
		else if(other instanceof CollisionArea_SingleMixed){
			// Null pointer check
			if(((CollisionArea_SingleMixed) other).collisionCirc == null
					|| ((CollisionArea_SingleMixed) other).collisionRect == null){
				System.out.println("FATAL ERROR: collisionArea(s) on parameter 'other' SingleMixed object is null!");
				return false;
			}
			
			/*
			 * Collision Check
			 */
			if(collisionRect.overlaps(((CollisionArea_SingleMixed) other).collisionRect)
					|| collisionCirc.overlaps(((CollisionArea_SingleMixed) other).collisionCirc)
					|| Intersector.overlaps(collisionCirc, ((CollisionArea_SingleMixed) other).collisionRect)
					|| Intersector.overlaps(((CollisionArea_SingleMixed) other).collisionCirc, collisionRect)){
				return true;
			}
			
			// If code reaches here, then there was no collision
			return false;
		}
		/*
		 * SingleMixed vs. SingleRect
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
			if(collisionRect.overlaps(((CollisionArea_SingleRect) other).collisionArea)
					|| Intersector.overlaps(collisionCirc, ((CollisionArea_SingleRect) other).collisionArea) ){
				return true;
			}
			
			// Reaching this point means no collision happened
			return false;
		}
		/* This means the collidable object is not a part of the CollisionArea_ classes
		 */
		else{
			System.out.println("FATAL ERROR: Parameter 'other' does not belong to CollisionArea_ classes!");
			return false;
		}
	}


	@Override
	public float getLeftMostEndPoint() {
		return collisionCirc.x < collisionRect.x ? collisionCirc.x : collisionRect.x;
	}

	@Override
	public float getTopMostEndPoint() {
		return collisionCirc.y < collisionRect.y ? collisionCirc.y : collisionRect.y;
	}

	@Override
	public float getRightMostEndPoint() {
		return collisionCirc.x > collisionRect.x ? collisionCirc.x : collisionRect.x;
	}


	@Override
	public float getBotMostEndPoint() {
		return collisionCirc.y > collisionRect.y ? collisionCirc.y : collisionRect.y;
	}
}
