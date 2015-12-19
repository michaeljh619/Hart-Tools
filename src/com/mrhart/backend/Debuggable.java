package com.mrhart.backend;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * An interface used for debugging collision or touch areas on objects.
 * 
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */
public interface Debuggable {
	/**
	 * Renders wire boxes and circles above collision and touch areas on
	 * objects in game.
	 * 
	 * @param shapes An "begun" ShapeRenderer
	 */
	public void debug(ShapeRenderer shapes);
}
