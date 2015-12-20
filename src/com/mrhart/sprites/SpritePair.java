package com.mrhart.sprites;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;

/**
 * A pair containing an ArrayList of Sprites and a boolean signifying whether
 * these Sprites are collidable or not.
 * 
 * @author Michael Hart, michaeljh619@yahoo.com
 * @version v1.0
 */
public class SpritePair implements Runnable{
	/*
	 * Instance Vars
	 */
	private ArrayList<Sprite> sprites;
	private boolean[] adjacencyList;
	
	/**
	 * Initializes the adjacencyList and the Data Structure
	 * 
	 * @param isCollidable
	 */
	public SpritePair(int numDangles){
		sprites = new ArrayList<Sprite>();
		
		// Initialize the adjacency list
		adjacencyList = new boolean[numDangles];
		for(int x = 0; x < numDangles; x++){
			adjacencyList[x]= false;
		}
	}
	
	/**
	 * Adds a collision to this dangle's adjacency list.
	 * 
	 * @param dangle
	 */
	protected void addCollision(int dangle){
		adjacencyList[dangle] = true;
	}
	
	/**
	 * Removes a collision to this dangle's adjacency list.
	 * 
	 * @param dangle
	 */
	protected void removeCollision(int dangle){
		adjacencyList[dangle] = false;
	}
	
	/**
	 * Checks if a collision exists between this dangle and the other dangle.
	 * 
	 * @param dangle
	 * @return
	 */
	public boolean canCollideWith(int dangle){
		return adjacencyList[dangle];
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
		return adjacencyList.length > 0;
	}
	
	public void update(float delta){
		Sprite current;
		// TODO Auto-generated method stub
		for(Iterator<Sprite> i = sprites.iterator(); i.hasNext();){
			// Get the current sprite and update it
			current = i.next();
			current.update(delta);
			// If the current sprite is removable, remove it.
			if(current.isRemovable){
				i.remove();
			}
		}
	}

	@Override
	public void run() {
		update(Gdx.app.getGraphics().getDeltaTime());
	}
}
