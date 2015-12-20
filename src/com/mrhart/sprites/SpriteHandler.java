package com.mrhart.sprites;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mrhart.backend.Messages;

public class SpriteHandler {
	/*
	 * Instance Vars
	 */
	public SpritePair[] sprites;
//	private Thread[] threads;
//	private final boolean useThreadedUpdate = false;
	
	/**
	 * Creates an array of SpritePairs equal to the amount of dangles that
	 * the user passes in. Each SpritePair will take its adjacency list
	 * in its constructor. So dangles[0][1] = true, will be saying that dangle 0 can
	 * collide with dangle1.
	 * 
	 * @param dangles
	 */
	public SpriteHandler(int numDangles){
		// Error Check
		if(numDangles < 1){
			System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
					+ "dangles length cannot be less than 1.");
		}
		
		
		// Initialize the array of dangles
		sprites = new SpritePair[numDangles];
		
		// Initialize Sprite Dangles
		for(int x = 0; x < numDangles; x++){
			sprites[x] = new SpritePair(numDangles);
		}
		
		// Threads
//		threads = new Thread[Settings.THREADS];
//		System.err.println("Max Threads: " + threads.length);
	}
	
	
	
	/**
	 * Updates all sprites in all the dangles, removes any removable sprites.
	 * 
	 * @since v1.0
	 * @version v1.0
	 * @param update
	 */
	public void update(float delta){
		// This loop checks each individual dangle
		for(int x = 0; x < sprites.length;){
			// Normal Update
//			if(!useThreadedUpdate){
				sprites[x].update(delta);
				x++;
//			}
//			// Threaded Update
//			else{
//				for(int index = x; (index-x) < threads.length; index++){
//					// So we don't fall off sprites array
//					if(index >= sprites.length)
//						break;
//					
//					threads[index-x] = new Thread(sprites[index]);
//					threads[index-x].start();
//				}
//				
//				x += threads.length;
//			}
		}
	}
	
	public void render(SpriteBatch batcher, float runtime){
		for(int x = 0; x < sprites.length; x++){
			for(Iterator<Sprite> i = sprites[x].iterator(); i.hasNext();){
				i.next().render(batcher, runtime);
			}
		}
	}
	
	
	
	/*************************************************************************
	 * 						Data Structure Delegators
	 *************************************************************************/
	/**
	 * Adds a new sprite to the specified dangle.
	 * 
	 * @since v1.0
	 * @version v1.0
	 * @param dangleIndex
	 * @param newSprite
	 */
	public void add(int dangleIndex, Sprite newSprite){
		// Error Checking
		if(dangleIndex < 0 || dangleIndex >= sprites.length){
			System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
					+ "Bad index value given in SpriteHandler.add()");
		}
		
		sprites[dangleIndex].add(newSprite);
	}
	/*************************************************************************
	 * 						Data Structure Delegators
	 *************************************************************************/
	
	
	
	/*************************************************************************
	 * 						Adjacency List Operations
	 *************************************************************************/
	public void addCollision(int dangleIndex1, int dangleIndex2){
		if(dangleIndex1 > sprites.length 
				|| dangleIndex2 > sprites.length
				|| dangleIndex1 < 0 || dangleIndex2 < 0){
			System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
					+ "dangleIndex 1 or 2 was out of range!");
		}
		else{
			sprites[dangleIndex1].addCollision(dangleIndex2);
			sprites[dangleIndex2].addCollision(dangleIndex1);
		}
	}
	public void addCollision(int dangleIndex1, int[] dangleIndexes){
		if(dangleIndex1 > sprites.length 
				|| dangleIndex1 < 0){
			System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
					+ "dangleIndex 1 was out of range!");
		}
		else if(dangleIndexes == null){
			System.err.println(Messages.ERROR + Messages.TYPE_NULL_POINTER
					+ " dangleIndexes was null!");
		}
		else{
			for(int x = 0; x < dangleIndexes.length; x++){
				sprites[dangleIndex1].addCollision(dangleIndexes[x]);
				sprites[dangleIndexes[x]].addCollision(dangleIndex1);
			}
		}
	}
	public void addAllCollisions(){
		for(int x = 0; x < sprites.length; x++){
			for(int y = 0; y < sprites.length; y++){
				sprites[x].addCollision(y);
			}
		}
	}

	public void removeCollision(int dangleIndex1, int dangleIndex2){
		if(dangleIndex1 > sprites.length 
				|| dangleIndex2 > sprites.length
				|| dangleIndex1 < 0 || dangleIndex2 < 0){
			System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
					+ "dangleIndex 1 or 2 was out of range!");
		}
		else{
			sprites[dangleIndex1].removeCollision(dangleIndex2);
			sprites[dangleIndex2].removeCollision(dangleIndex1);
		}
	}
	public void removeCollision(int dangleIndex1, int[] dangleIndexes){
		if(dangleIndex1 > sprites.length 
				|| dangleIndex1 < 0){
			System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
					+ "dangleIndex 1 was out of range!");
		}
		else if(dangleIndexes == null){
			System.err.println(Messages.ERROR + Messages.TYPE_NULL_POINTER
					+ " dangleIndexes was null!");
		}
		else{
			for(int x = 0; x < dangleIndexes.length; x++){
				sprites[dangleIndex1].removeCollision(dangleIndexes[x]);
				sprites[dangleIndexes[x]].removeCollision(dangleIndex1);
			}
		}
	}
	public void removeAllCollisions(){
		for(int x = 0; x < sprites.length; x++){
			for(int y = 0; y < sprites.length; y++){
				sprites[x].removeCollision(y);
			}
		}
	}
}
