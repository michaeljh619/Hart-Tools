package com.mrhart.sprites.background;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mrhart.settings.Settings;
import com.mrhart.sprites.Sprite;

public class Background extends Sprite {
	/*
	 * Instance Variables
	 */
	protected TextureRegion region;
	
	public Background(TextureRegion region){
		super(0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
		this.region = region;
	}

	public Background(int positionX, int positionY, int inWidth, int inHeight,
			TextureRegion region) {
		super(positionX, positionY, inWidth, inHeight);
		this.region = region;
	}

	@Override
	public void render(SpriteBatch batcher, float runtime) {
		batcher.draw(region, position.x, position.y, width, height);
	}

}
