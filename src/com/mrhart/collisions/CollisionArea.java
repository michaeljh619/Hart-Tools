package com.mrhart.collisions;

/**
 * Used as an interface to create different types of CollisionAreas. All objects of this type should
 * be able to check collisions with other CollisionAreas.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */
public interface CollisionArea {
	public boolean checkCollisions(CollisionArea other);
}
