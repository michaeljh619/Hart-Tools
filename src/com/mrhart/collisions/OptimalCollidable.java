package com.mrhart.collisions;

/**
 * An interface used in optimizing collisions. A class that implements this should have
 * a single circle CollisionArea that updates. This circle should wrap around all of this
 * object's other collisionAreas. When these conditions are met, it allows for the Collision
 * handler to check this one circle before checking multiple collision areas inside, that way
 * collision checks can be kept to a minimum.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */
public interface OptimalCollidable {
	public CollisionArea_SingleCirc getOptimalCollisionArea();
}
