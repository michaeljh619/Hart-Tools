package com.mrhart.collisions;

/**
 * Interface used for creating objects that can collide with each other. It must have some
 * CollisionArea object that is updating and returnable in the getCollisionArea function.
 * It must be able to collide with other Collidable objects and must have a canCollide
 * function that checks if it is able to collide at that moment.
 * 
 * Note: In your collide function, you will need to implement what happens with your collision.
 *		 For example, if it's a character who is colliding with a bullet, you need to check that
 *		 parameter "other" is a bullet and then proceed with whatever action you want. Likewise,
 *		 the bullet will call its collision and do whatever it needs to do as well. Make sure you
 *		 don't double update with this! As in both the bullet and the character take health from the
 *		 character.
 *
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */
public interface Collidable {
	/**
	 * Returns the CollisionArea that this Collidable object is using to
	 * encompass its collision area. Must be non-null.
	 * 
	 * @version v1.00
	 * @since v2.00
	 * @return
	 */
	public CollisionArea getCollisionArea();
	
	/**
	 * Called when two objects collide with each other. At this point the dev
	 * should check what class type the other object is and update this Sprite
	 * accordingly. Do not modify the "other" Collidable object, that object
	 * will also have its Collide method called and you should update that
	 * object in its collide method.
	 * 
	 * @version v1.00
	 * @since v2.00
	 * @param other
	 * @param collidedOnce
	 */
	public void collide(Collidable other, boolean collidedOnce);

	/**
	 * Returns the canCollide flag; if it is false, then this Collidable
	 * object will not be checked for collisions.
	 * 
	 * @return
	 */
	public boolean canCollide();
	
	/**
	 * Updates the CollisionArea. Should be called in your update method after
	 * doing the vector position update. Will also be used in determining
	 * collisions in the CollisionHandler.
	 */
	public void updateCollisionArea();
}
