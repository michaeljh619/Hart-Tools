package com.mrhart.collisions;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

/**
 * A CollisionArea that has an Array of Rectangles.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */

public class CollisionArea_MultipleRects implements CollisionArea {
	public Rectangle[] collisionArea;

	public boolean checkCollisions(CollisionArea other){
		// Check if this object has any null pointers
		if(collisionArea == null){
			System.out.println("FATAL ERROR: collisionArea on calling MultRects object is null!");
			return false;
		}
		
		/*
		 * Start Checking Collisions
		 */
		/* MultRect vs. MultCircs
		 */
		if(other instanceof CollisionArea_MultipleCircs){
			return ((CollisionArea_MultipleCircs) other).checkCollisions(this);
		}
		/* MultRect vs. MultMixed
		 */
		else if(other instanceof CollisionArea_MultipleMixed){
			return ((CollisionArea_MultipleMixed) other).checkCollisions(this);
		}
		/* MultRect vs. MultRect
		 */
		else if(other instanceof CollisionArea_MultipleRects){
			// Null pointer check
			if(((CollisionArea_MultipleRects) other).collisionArea == null){
				System.out.println("FATAL ERROR: collisionArea on parameter 'other' MultRect object is null!");
				return false;
			}
			
			// Check the collision
			for(int x = 0; x < collisionArea.length; x++){
				for(int y = 0; y < ((CollisionArea_MultipleRects) other).collisionArea.length; y++){
					if(collisionArea[x].overlaps(((CollisionArea_MultipleRects) other).collisionArea[y])){
						return true;
					}
				}
			}
			
			// If the code reaches this far, then there were no collisions
			return false;
		}
		/* MultRect vs. SingleCirc
		 */
		else if(other instanceof CollisionArea_SingleCirc){
			// Null Pointer Check
			if(((CollisionArea_SingleCirc) other).collisionArea == null){
				System.out.println("FATAL ERROR: collisionArea on parameter 'other' SingleCirc object is null!");
				return false;
			}
			
			// Check collisions
			for(int x = 0; x < collisionArea.length; x++){
				if(Intersector.overlaps(((CollisionArea_SingleCirc) other).collisionArea, collisionArea[x])){
					return true;
				}
			}
			
			// Reaching this part of the code means there were no collisions
			return false;
		}
		/* MultRect vs. SingleMixed
		 */
		else if(other instanceof CollisionArea_SingleMixed){
			// Null pointer check
			if(((CollisionArea_SingleMixed) other).collisionCirc == null
					|| ((CollisionArea_SingleMixed) other).collisionRect == null){
				System.out.println("FATAL ERROR: collisionArea(s) on parameter 'other' SingleMixed object is null!");
				return false;
			}
			
			// Check collisions
			for(int x = 0; x < collisionArea.length; x++){
				if(collisionArea[x].overlaps(((CollisionArea_SingleMixed) other).collisionRect)){
					return true;
				}
				if(Intersector.overlaps(((CollisionArea_SingleMixed) other).collisionCirc, collisionArea[x])){
					return true;
				}
			}
			
			// Reaching this point means no collisions occurred
			return false;
		}
		else if(other instanceof CollisionArea_SingleRect){
			// Null Pointer Check
			if(((CollisionArea_SingleRect) other).collisionArea == null){
				System.out.println("FATAL ERROR: collisionArea on parameter 'other' SingleRect object is null!");
				return false;
			}
			
			// Check collisions
			for(int x = 0; x < collisionArea.length; x++){
				if(collisionArea[x].overlaps(((CollisionArea_SingleRect) other).collisionArea)){
					return true;
				}
			}
			
			// Reaching this point means there were no collisions
			return false;
		}
		/* Class is not a CollisionArea_ class
		 */
		else{
			System.out.println("FATAL ERROR: Parameter 'other' does not belong to CollisionArea_ classes!");
			return false;
		}
	}

	@Override
	public float getLeftMostEndPoint() {
		float min = collisionArea[0].x;
		float localMin;
		for(int x = 1; x < collisionArea.length; x++){
			localMin = collisionArea[x].x;
			min = localMin < min ? localMin : min;
		}
		return min;
	}

	@Override
	public float getTopMostEndPoint() {
		float min = collisionArea[0].y;
		float localMin;
		for(int x = 1; x < collisionArea.length; x++){
			localMin = collisionArea[x].y;
			min = localMin < min ? localMin : min;
		}
		return min;
	}

	@Override
	public float getRightMostEndPoint() {
		float max = collisionArea[0].x + collisionArea[0].width;
		float localMax;
		for(int x = 1; x < collisionArea.length; x++){
			localMax = collisionArea[x].x;
			max = localMax > max ? localMax : max;
		}
		return max;
	}

	@Override
	public float getBotMostEndPoint() {
		float max = collisionArea[0].y + collisionArea[0].height;
		float localMax;
		for(int x = 1; x < collisionArea.length; x++){
			localMax = collisionArea[x].y + collisionArea[x].height;
			max = localMax > max ? localMax : max;
		}
		return max;
	}
}
