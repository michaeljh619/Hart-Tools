package com.mrHart.Handlers.Collisions;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public interface MovableCircle {
	public Circle getCollisionCircle();
	
	public void updateCircle();
	public void resetX();
	public void resetY();
	
	public Vector2 getPosition();
	public Vector2 getVelocity();

}
