package com.mrhart.collisions;

/**
 * An interface used in optimizing collisions. A class that implements this should have
 * a single circle CollisionArea that updates. This circle should wrap around all of this
 * object's other collisionAreas. When these conditions are met, it allows for the Collision
 * handler to check this one circle before checking multiple collision areas inside, that way
 * collision checks can be kept to a minimum.
 *
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */
public interface OptimalCollidable extends Collidable{
	/**
	 * Returns the optimal collision area. This single circle should wrap
	 * around all the other collision areas that this object has.
	 * 
	 * @version v1.00
	 * @since v2.00
	 * @return
	 */
	public CollisionArea_SingleCirc getOptimalCollisionArea();
	
	/**
	 * Updates the positioning of the optimal collision area, should be called
	 * after the position update of the Sprite.
	 */
	public void updateOptimalCollisionArea();
}
