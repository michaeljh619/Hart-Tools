package com.mrHart.Handlers.Collisions;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface MovableRect {
	public Rectangle getCollisionBox();
	
	public void updateRect();
	public void resetX();
	public void resetY();
	
	public Vector2 getPosition();
	public Vector2 getVelocity();
}
