package com.test;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mrhart.enumerations.Directions;
import com.mrhart.input.KeyboardArrows;
import com.mrhart.renderable.RenderableTextureRegion;
import com.mrhart.sprites.Sprite;

public class Mario extends Sprite{
	KeyboardArrows keys;

	public Mario(TextureRegion region) {
		super(100, 100, 50, 50, new RenderableTextureRegion(region));
		keys = new KeyboardArrows();
	}
	
	public void update(float delta){
		int direction = keys.getDirections4();
		switch(direction){
		case Directions.DOWN:
			super.velocity.y = 500;
			break;
		case Directions.UP:
			super.velocity.y = -500;
			break;
		case Directions.LEFT:
			super.velocity.x = -500;
			break;
		case Directions.RIGHT:
			super.velocity.x = 500;
			break;
		default:
			super.velocity.setZero();
			break;
		}
		
		super.update(delta);
	}

}
