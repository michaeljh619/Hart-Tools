package com.mrhart.collisions;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

/**
 * A CollisionArea that has an Array of Circles.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */
public class CollisionArea_MultipleCircs implements CollisionArea {
	public Circle[] collisionArea;
	
	public boolean checkCollisions(CollisionArea other){
		// Do null pointer check to begin with
		if(collisionArea == null){
			System.out.println("FATAL ERROR: collisionArea on calling MultCirc object is null!");
			return false;
		}
		
		/*
		 * Check each possible collision
		 */
		/* Check MultCirc vs. MultCirc collision
		 */
		if(other instanceof CollisionArea_MultipleCircs){
			// Do null pointer check to begin with
			if(((CollisionArea_MultipleCircs) other).collisionArea == null){
				System.out.println("FATAL ERROR: collisionArea on parameter 'other' MultCirc object is null!");
				return false;
			}
			// Actually check the collisions
			for(int x = 0; x < collisionArea.length; x++){
				for(int y = 0; y < ((CollisionArea_MultipleCircs) other).collisionArea.length; y++){
					if(   collisionArea[x].overlaps( ( (CollisionArea_MultipleCircs) other).collisionArea[y] )   ){
						return true;
					}
				}
			}
			// Reaching this far means no collision occurred
			return false;
		}
		/* Check MultCirc vs. MultMixed collision
		 */
		else if(other instanceof CollisionArea_MultipleMixed){
			// Do null pointer check
			if(((CollisionArea_MultipleMixed) other).collisionCircs == null
					|| ((CollisionArea_MultipleMixed) other).collisionRects == null){
				System.out.println("FATAL ERROR: collisionArea(s) on parameter 'other' MultMixed object is null!");
				return false;
			}
			// Check this objects circs with other's circs collisions
			for(int x = 0; x < collisionArea.length; x++){
				for(int y = 0; y < ((CollisionArea_MultipleMixed) other).collisionCircs.length; y++){
					if(   collisionArea[x].overlaps( ( (CollisionArea_MultipleMixed) other).collisionCircs[y] )   ){
						return true;
					}
				}
			}
			// Check this objects circs with other's rects collisions
			for(int x = 0; x < collisionArea.length; x++){
				for(int y = 0; y < ((CollisionArea_MultipleMixed) other).collisionRects.length; y++){
					if(   Intersector.overlaps(collisionArea[x], ((CollisionArea_MultipleMixed) other).collisionRects[y])   ){
						return true;
					}
				}
			}
			// Reaching this far means there were no collisions
			return false;
		}
		/* Check MultCirc vs. MultRects
		 */
		else if(other instanceof CollisionArea_MultipleRects){
			// Do null pointer check
			if(((CollisionArea_MultipleRects) other).collisionArea == null){
				System.out.println("FATAL ERROR: collisionArea on parameter 'other' MultRect object is null!");
				return false;
			}
			// Check this object's circs with other's rects collisionArea
			for(int x = 0; x < collisionArea.length; x++){
				for(int y = 0; y < ((CollisionArea_MultipleRects) other).collisionArea.length; y++){
					if(   Intersector.overlaps(collisionArea[x], ((CollisionArea_MultipleRects) other).collisionArea[y])   ){
						return true;
					}
				}
			}
			// Reaching this far means no collisions occurred
			return false;
		}
		/* Check MultCirc vs. SingleCirc
		 */
		else if(other instanceof CollisionArea_SingleCirc){
			// Null pointer check
			if(((CollisionArea_SingleCirc) other).collisionArea == null){
				System.out.println("FATAL ERROR: collisionArea on parameter 'other' SingleCirc object is null!");
				return false;
			}
			// Check the collisions
			for(int x = 0; x < collisionArea.length; x++){
				if(collisionArea[x].overlaps(((CollisionArea_SingleCirc) other).collisionArea)){
					return true;
				}
			}
			// Reaching this far means there were no collisions
			return false;
		}
		/* MultCirc vs. SingleMixed
		 */
		else if(other instanceof CollisionArea_SingleMixed){
			// Null Pointer Check
			if(((CollisionArea_SingleMixed) other).collisionCirc == null
					|| ((CollisionArea_SingleMixed) other).collisionRect == null){
				System.out.println("FATAL ERROR: collisionArea(s) on parameter 'other' SingleMixed object is null!");
				return false;
			}
			// Check the collision
			for(int x = 0; x < collisionArea.length; x++){
				if(collisionArea[x].overlaps(((CollisionArea_SingleMixed) other).collisionCirc)
						|| Intersector.overlaps(collisionArea[x], ((CollisionArea_SingleMixed) other).collisionRect)){
					return true;
				}
			}
			// Reaching this far means there were no collisions
			return false;
		}
		/* MultCirc vs. SingleRect
		 */
		else if(other instanceof CollisionArea_SingleRect){
			// Null Pointer Check
			if(((CollisionArea_SingleRect) other).collisionArea == null){
				System.out.println("FATAL ERROR: collisionArea on parameter 'other' SingleRect object is null!");
				return false;
			}
			// Check the collision
			for(int x = 0; x < collisionArea.length; x++){
				if(Intersector.overlaps(collisionArea[x], ((CollisionArea_SingleRect) other).collisionArea)){
					return true;
				}
			}
			// Reaching this far means there were no collisions
			return false;
		}
		/* This means the object passed was collidable but was not one of the CollisionArea_ classes
		 */
		else{
			System.out.println("FATAL ERROR: Parameter 'other' does not belong to CollisionArea_ classes!");
			return false;
		}
	}
}
