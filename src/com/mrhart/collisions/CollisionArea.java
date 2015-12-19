package com.mrhart.collisions;

/**
 * Used as an interface to create different types of CollisionAreas. All objects of this type should
 * be able to check collisions with other CollisionAreas.
 *
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v2.00
 */
public interface CollisionArea {
	/**
	 * Checks whether this CollisionArea is colliding with the other
	 * CollisionArea, returns a boolean signifying this condition.
	 * 
	 * @version v1.00
	 * @since v2.00
	 * @param other
	 * @return
	 */
	public boolean checkCollisions(CollisionArea other);
}
