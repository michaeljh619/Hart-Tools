package com.mrHart.Depth;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Allows for the game renderer to choose the order in which objects should be rendered based on their y
 * position on the screen.
 * 
 * @author Michael
 *
 */
public interface DepthRenderable extends Comparable<DepthRenderable> {
	public float getPositionY();
	
	public void render(SpriteBatch batcher, float runtime);
	
}
