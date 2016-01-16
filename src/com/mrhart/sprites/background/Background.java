package com.mrhart.sprites.background;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mrhart.renderable.RenderableObject;
import com.mrhart.settings.Settings;
import com.mrhart.sprites.Sprite;

public class Background extends Sprite {
	public Background(RenderableObject rObject){
		this(0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT, rObject);
	}

	public Background(int positionX, int positionY, int inWidth, int inHeight,
			RenderableObject rObject) {
		super(positionX, positionY, inWidth, inHeight, rObject);
	}

	@Override
	public void render(SpriteBatch batcher, float runtime) {
		batcher.draw(super.renderObject.getTextureRegion(runtime), 
				position.x, position.y, width, height);
	}
}
