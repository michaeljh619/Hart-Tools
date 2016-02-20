package com.mrhart.collisions;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

/**
 * A CollisionArea that has an Array of Circles and an Array of Rectangles.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */
public class CollisionArea_MultipleMixed implements CollisionArea{
	public Rectangle[] collisionRects;
	public Circle[] collisionCircs;
	
	public boolean checkCollisions(CollisionArea other){
		// Check if this object has any null pointers
		if(collisionRects == null || collisionCircs == null){
			System.out.println("FATAL ERROR: collisionArea(s) on calling MultMixed object is null!");
			return false;
		}
		
		/*
		 * Now check the actual collisions
		 */
		/* MultMixed vs. MultCirc (Previously Done)
		 */
		if(other instanceof CollisionArea_MultipleCircs){
			return ((CollisionArea_MultipleCircs) other).checkCollisions(this);
		}
		/* MultMixed vs. MultMixed
		 */
		else if(other instanceof CollisionArea_MultipleMixed){
			// Null pointer check
			if(((CollisionArea_MultipleMixed) other).collisionCircs == null
					|| ((CollisionArea_MultipleMixed) other).collisionRects == null){
				System.out.println("FATAL ERROR: collisionArea(s) on parameter 'other' MultMixed object is null!");
				return false;
			}
			
			/*
			 *  Check the collisions
			 */
			// Rect vs. Rect
			for(int x = 0; x < collisionRects.length; x++){
				for(int y = 0; y < ((CollisionArea_MultipleMixed) other).collisionRects.length; y++){
					if(collisionRects[x].overlaps(((CollisionArea_MultipleMixed) other).collisionRects[y])){
						return true;
					}
				}
			}
			// Circ vs. Circ
			for(int x = 0; x < collisionCircs.length; x++){
				for(int y = 0; y < ((CollisionArea_MultipleMixed) other).collisionCircs.length; y++){
					if(collisionCircs[x].overlaps(((CollisionArea_MultipleMixed) other).collisionCircs[y])){
						return true;
					}
				}
			}
			// Rect vs. Circ
			for(int x = 0; x < collisionRects.length; x++){
				for(int y = 0; y < ((CollisionArea_MultipleMixed) other).collisionCircs.length; y++){
					if( Intersector.overlaps(((CollisionArea_MultipleMixed) other).collisionCircs[y], collisionRects[x]) ){
						return true;
					}
				}
			}
			// Circ vs. Rect
			for(int x = 0; x < collisionCircs.length; x++){
				for(int y = 0; y < ((CollisionArea_MultipleMixed) other).collisionRects.length; y++){
					if( Intersector.overlaps(collisionCircs[x], ((CollisionArea_MultipleMixed) other).collisionRects[y])){
						return true;
					}
				}
			}
			
			// Reaching this far means no collisions occurred
			return false;
		}
		/* MultMixed vs. MultRects
		 */
		else if(other instanceof CollisionArea_MultipleRects){
			// Null pointer check
			if(((CollisionArea_MultipleRects) other).collisionArea == null){
				System.out.println("FATAL ERROR: collisionArea on parameter 'other' MultRects object is null!");
				return false;
			}
			
			// Rect vs. Rect
			for(int x = 0; x < collisionRects.length; x++){
				for(int y = 0; y < ((CollisionArea_MultipleRects) other).collisionArea.length; y++){
					if(collisionRects[x].overlaps(((CollisionArea_MultipleRects) other).collisionArea[y])){
						return true;
					}
				}
			}
			// Circ vs. Rect
			for(int x = 0; x < collisionCircs.length; x++){
				for(int y = 0; y < ((CollisionArea_MultipleRects) other).collisionArea.length; y++){
					if(Intersector.overlaps(collisionCircs[x], ((CollisionArea_MultipleRects) other).collisionArea[y])){
						return true;
					}
				}
			}
			
			// Reaching this far means that there were no collisions
			return false;
		}
		/* MultMixed vs. SingleCirc
		 */
		else if(other instanceof CollisionArea_SingleCirc){
			// Null pointer check
			if(((CollisionArea_SingleCirc) other).collisionArea == null){
				System.out.println("FATAL ERROR: collisionArea on parameter 'other' SingleCirc object is null!");
				return false;
			}
			
			// Rect vs. Circ
			for(int x = 0; x < collisionRects.length; x++){
				if(Intersector.overlaps(((CollisionArea_SingleCirc) other).collisionArea, collisionRects[x])){
					return true;
				}
			}
			// Circ vs. Circ
			for(int x = 0; x < collisionCircs.length; x++){
				if(collisionCircs[x].overlaps(((CollisionArea_SingleCirc) other).collisionArea)){
					return true;
				}
			}
			
			// Reaching this far means no collisions occurred
			return false;
		}
		/* MultMixed vs. SingleMixed
		 */
		else if(other instanceof CollisionArea_SingleMixed){
			// Null pointer check
			if(((CollisionArea_SingleMixed) other).collisionRect == null
					|| ((CollisionArea_SingleMixed) other).collisionCirc == null){
				System.out.println("FATAL ERROR: collisionArea(s) on parameter 'other' SingleMixed object is null!");
				return false;
			}
			
			// Rect vs. Mixed
			for(int x = 0; x < collisionRects.length; x++){
				if(collisionRects[x].overlaps(((CollisionArea_SingleMixed) other).collisionRect)){
					return true;
				}
				if(Intersector.overlaps(((CollisionArea_SingleMixed) other).collisionCirc, collisionRects[x])){
					return true;
				}
			}
			// Circ vs. Mixed
			for(int x = 0; x < collisionCircs.length; x++){
				if(collisionCircs[x].overlaps(((CollisionArea_SingleMixed) other).collisionCirc)){
					return true;
				}
				if(Intersector.overlaps(collisionCircs[x], ((CollisionArea_SingleMixed) other).collisionRect)){
					return true;
				}
			}
			
			// Reaching this far means no collisions occurred
			return false;
		}
		/* MultMixed vs SingleRect
		 */
		else if(other instanceof CollisionArea_SingleRect){
			// Null pointer check
			if(((CollisionArea_SingleRect) other).collisionArea == null){
				System.out.println("FATAL ERROR: collisionArea on parameter 'other' SingleRect object is null!");
				return false;
			}
			
			// Rect vs. Rect
			for(int x  = 0; x < collisionRects.length; x++){
				if(collisionRects[x].overlaps(((CollisionArea_SingleRect) other).collisionArea)){
					return true;
				}
			}
			// Circ vs. Rect
			for(int x = 0; x < collisionCircs.length; x++){
				if(Intersector.overlaps(collisionCircs[x], ((CollisionArea_SingleRect) other).collisionArea)){
					return true;
				}
			}
			
			// If the code reaches this far, then there were no collisions
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
		float min = collisionCircs[0].x - collisionCircs[0].radius;
		float localMin;
		for(int x = 1; x < collisionCircs.length; x++){
			localMin = collisionCircs[x].x - collisionCircs[0].radius;
			min = localMin < min ? localMin : min;
		}
		for(int x = 0; x < collisionRects.length; x++){
			localMin = collisionRects[x].x;
			min = localMin < min ? localMin : min;
		}
		return min;
	}

	@Override
	public float getTopMostEndPoint() {
		float min = collisionCircs[0].y - collisionCircs[0].radius;
		float localMin;
		for(int x = 1; x < collisionCircs.length; x++){
			localMin = collisionCircs[x].y - collisionCircs[0].radius;
			min = localMin < min ? localMin : min;
		}
		for(int x = 0; x < collisionRects.length; x++){
			localMin = collisionRects[x].y;
			min = localMin < min ? localMin : min;
		}
		return min;
	}

	@Override
	public float getRightMostEndPoint() {
		float max = collisionCircs[0].x + collisionCircs[0].radius;
		float localMax;
		for(int x = 1; x < collisionCircs.length; x++){
			localMax = collisionCircs[x].x + collisionCircs[0].radius;
			max = localMax > max ? localMax : max;
		}
		for(int x = 0; x < collisionRects.length; x++){
			localMax = collisionRects[x].x + collisionRects[x].width;
			max = localMax > max ? localMax : max;
		}
		return max;
	}

	@Override
	public float getBotMostEndPoint() {
		float max = collisionCircs[0].y + collisionCircs[0].radius;
		float localMax;
		for(int x = 1; x < collisionCircs.length; x++){
			localMax = collisionCircs[x].y + collisionCircs[0].radius;
			max = localMax > max ? localMax : max;
		}
		for(int x = 0; x < collisionRects.length; x++){
			localMax = collisionRects[x].y + collisionRects[x].height;
			max = localMax > max ? localMax : max;
		}
		return max;
	}
}
