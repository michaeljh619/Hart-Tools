package com.mrhart.sprites;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A pair containing an ArrayList of Sprites and a boolean signifying whether
 * these Sprites are collidable or not.
 * 
 * @author Michael Hart, michaeljh619@yahoo.com
 * @version v1.0
 */
public class SpritePair {
	/*
	 * Instance Vars
	 */
	private ArrayList<Sprite> sprites;
	private boolean isCollidable;
	
	/**
	 * Initializes the isCollidable flag and the Data Structure
	 * 
	 * @param isCollidable
	 */
	public SpritePair(boolean isCollidable){
		this.isCollidable = isCollidable;
		sprites = new ArrayList<Sprite>();
	}
	
	/**
	 * Delegator for Data Structure add().
	 * 
	 * @param newSprite
	 */
	public void add(Sprite newSprite){
		sprites.add(newSprite);
	}
	
	/**
	 * Delegator for Data Structure remove().
	 * 
	 * @param index
	 */
	public void remove(int index){
		sprites.remove(index);
	}
	
	/**
	 * Delegator for Data Structure get().
	 * 
	 * @param index
	 * @return
	 */
	public Sprite get(int index){
		return sprites.get(index);
	}
	
	/**
	 * Delegator for Data Structure size().
	 * 
	 * @return
	 */
	public int size(){
		return sprites.size();
	}
	
	/**
	 * Delegator for Data Structure iterator().
	 * 
	 * @return
	 */
	public Iterator<Sprite> iterator(){
		return sprites.iterator();
	}
	
	/**
	 * Returns isCollidable flag.
	 * 
	 * @return
	 */
	public boolean isCollidable(){
		return isCollidable;
	}
}
