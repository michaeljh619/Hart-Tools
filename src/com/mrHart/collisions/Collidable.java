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
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */
public interface Collidable {
	public CollisionArea getCollisionArea();
	
	public void collide(Collidable other);

	public boolean canCollide();
}
