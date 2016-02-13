package com.mrhart.sprites;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mrhart.backend.Messages;

public class SpriteHandler_Dangles {
	/*
	 * Instance Vars
	 */
	public SpriteNode[] sprites;
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
	public SpriteHandler_Dangles(int numNodes){
		// Error Check
		if(numNodes < 1){
			System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
					+ "nodes length cannot be less than 1.");
		}
		
		
		// Initialize the array of nodes
		sprites = new SpriteNode[numNodes];
		
		// Initialize Sprite Dangles
		for(int x = 0; x < numNodes; x++){
			sprites[x] = new SpriteNode(numNodes);
		}
		
		// Threads
//		threads = new Thread[Settings.THREADS];
//		System.err.println("Max Threads: " + threads.length);
	}
	
	
	
	/**
	 * Updates all sprites in all the nodes, removes any removable sprites.
	 * 
	 * @since v1.0
	 * @version v1.01
	 * @param delta
	 */
	public void update(float delta){
		// This loop checks each individual nodes
		for(int x = 0; x < sprites.length; x++){
			if(sprites[x] instanceof Sprite_Moderator){
				// Update Sprite and get Action
				SpriteHandler_Action action;
				action = ((Sprite_Moderator) sprites[x]).update(delta, null);
				// Perform action if not null
				if(action != null)
					action.performAction(this);
			}
			else{
				sprites[x].update(delta);
			}
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
	 * Adds a new sprite to the specified nodes.
	 * 
	 * @since v1.0
	 * @version v1.0
	 * @param nodesIndex
	 * @param newSprite
	 */
	public void add(int nodeIndex, Sprite newSprite){
		// Error Checking
		if(nodeIndex < 0 || nodeIndex >= sprites.length){
			System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
					+ "Bad index value given in SpriteHandler.add()");
		}
		
		sprites[nodeIndex].add(newSprite);
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
