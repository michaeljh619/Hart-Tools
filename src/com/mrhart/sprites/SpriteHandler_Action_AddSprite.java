package com.mrhart.sprites;

public class SpriteHandler_Action_AddSprite implements SpriteHandler_Action{
	/*
	 * Instance Vars
	 */
	private Sprite sprite;
	private int index;
	
	public SpriteHandler_Action_AddSprite(Sprite sprite){
		this.sprite = sprite;
	}
	
	public SpriteHandler_Action_AddSprite(Sprite sprite, int index){
		this.sprite = sprite;
		this.index = index;
	}

	public void performAction(SpriteHandler spriteHandler) {
			spriteHandler.add(sprite);
	}

}
