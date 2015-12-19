package com.mrhart.collisions;

import com.badlogic.gdx.math.Vector2;

/**
 * A Sprite that implements Resettable has the ability to reset to its last
 * good non-colliding position (or the position in the last CPU cycle). This
 * allows for simple collision handling use with the CollisionHandler class.
 * 
 * IMPORTANT: If a Sprite implements this class, update the lastPosition
 * vector to equal position vector before you do position updates. This way the
 * lastPosition vector is always one cycle behind the position vector.
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v2.00
 */
public interface Resettable extends Collidable {
	/**
	 * Returns the last position that the Sprite was in.
	 * 
	 * @return
	 */
	public Vector2 getLastPosition();
	/**
	 * Getter for velocity.
	 * 
	 * @return
	 */
	public Vector2 getVelocity();
	/**
	 * Setter for velocity.
	 * 
	 * @param x
	 * @param y
	 */
	public void setVelocity(float x, float y);
	/**
	 * Getter for position.
	 * 
	 * @return
	 */
	public Vector2 getPosition();
	/**
	 * Setter for position.
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y);
}
