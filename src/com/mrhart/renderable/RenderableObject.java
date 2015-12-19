package com.mrhart.renderable;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Allows for polymorphically creating an Animation or a TextureRegion.
 * Child classes will be wrappers for TextureRegions and Animations.
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.00
 */
public interface RenderableObject {
	/**
	 * Returns the TextureRegion to be drawn to the screen.
	 * 
	 * @version v1.00
	 * @since v1.00
	 * @param runtime
	 * @return
	 */
	public TextureRegion getTextureRegion(float runtime);
}
