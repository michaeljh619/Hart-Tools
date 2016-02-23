package com.mrhart.backgrounds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mrhart.renderable.RenderableObject;
import com.mrhart.settings.Settings;

public class Background{
	public Vector2 position;
	public int width, height;
	protected RenderableObject renderObject;
	
	public Background(RenderableObject rObject){
		this(0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT, rObject);
	}

	public Background(int positionX, int positionY, int inWidth, int inHeight,
			RenderableObject rObject){
		position = new Vector2(positionX, positionY);
		this.width = inWidth;
		this.height = inHeight;
		renderObject = rObject;
	}

	public void render(SpriteBatch batcher, float runtime) {
		if(renderObject != null)
			batcher.draw(renderObject.getTextureRegion(runtime), 
				position.x, position.y, width, height);
	}
}
