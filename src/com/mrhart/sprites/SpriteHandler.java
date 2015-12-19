package com.mrhart.sprites;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mrhart.backend.Messages;

public class SpriteHandler {
	/*
	 * Instance Vars
	 */
	public SpritePair[] sprites;
	
	/**
	 * Creates an array of SpritePairs equal to the amount of boolean dangles
	 * that the user passes as an argument. If the boolean dangle is true,
	 * that makes the SpritePair dangle collidable. If it is false, the Sprite-
	 * Pair dangle will not be checked for collisions.
	 * 
	 * @param dangles
	 */
	public SpriteHandler(boolean[] dangles){
		// Error Check
		if(dangles == null){
			System.err.println(Messages.ERROR + Messages.TYPE_NULL_POINTER
					+ "dangles is null!");
		}
		if(dangles.length < 1){
			System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
					+ "dangles length cannot be less than 1.");
		}
		
		// Initialize the array of dangles
		sprites = new SpritePair[dangles.length];
		
		// Initialize Sprite Dangles
		for(int x = 0; x < dangles.length; x++){
			sprites[x] = new SpritePair(dangles[x]);
		}
	}
	
	
	
	/*************************************************************************
	 * 						Data Structure Delegators
	 *************************************************************************/
	/**
	 * Updates all sprites in all the dangles, removes any removable sprites.
	 * 
	 * @since v1.0
	 * @version v1.0
	 * @param update
	 */
	public void update(float delta){
		Sprite current;
		// This loop checks each individual dangle
		for(int x = 0; x < sprites.length; x++){
			// This loops checks in reverse order so that sprites can be removed
			// as the sprites are marked removable.
			for(Iterator<Sprite> i = sprites[x].iterator(); i.hasNext();){
				// Get the current sprite and update it
				current = i.next();
				current.update(delta);
				// If the current sprite is removable, remove it.
				if(current.isRemovable){
					i.remove();
				}
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
}
