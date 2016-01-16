package com.mrhart.sprites;

import java.util.Iterator;
import java.util.LinkedList;

public class SpriteHandler_Action_AddSprites implements SpriteHandler_Action {
	/*
	 * Instance Vars
	 */
	private LinkedList<SpritePair> spritePairs;
	
	public SpriteHandler_Action_AddSprites(){
		spritePairs = new LinkedList<SpritePair>();
	}
	
	public void addSprite(Sprite sprite, int index){
		spritePairs.add(new SpritePair(sprite, index));
	}

	@Override
	public void performAction(SpriteHandler spriteHandler) {
		SpritePair current;
		for(Iterator<SpritePair> i = spritePairs.iterator(); i.hasNext();){
			current = i.next();
			spriteHandler.add(current.index, current.sprite);
		}
	}
	
	private class SpritePair{
		protected Sprite sprite;
		protected int index;
		
		public SpritePair(Sprite sprite, int index){
			this.sprite = sprite;
			this.index = index;
		}
	}
}
