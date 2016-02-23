package com.mrhart.sprites;

import com.badlogic.gdx.math.Vector2;
import com.mrhart.collisions.Collidable;
import com.mrhart.collisions.CollisionArea;
import com.mrhart.collisions.Resettable;
import com.mrhart.renderable.RenderableObject;

public abstract class Sprite_Resettable extends Sprite_Collidable implements Resettable{
	/*
	 * Instance Vars
	 */
	protected Vector2 lastPosition;

	public Sprite_Resettable(int positionX, int positionY, int width, int height,
			CollisionArea collisionArea){
		this(positionX, positionY, width, height, null, collisionArea);
	}

	public Sprite_Resettable(int positionX, int positionY, int width, int height,
			CollisionArea collisionArea, int ID){
		this(positionX, positionY, width, height, null, collisionArea, ID);
	}

	public Sprite_Resettable(int positionX, int positionY, int width, int height,
			RenderableObject renderObject, CollisionArea collisionArea) {
		super(positionX, positionY, width, height, renderObject, collisionArea);
		lastPosition = new Vector2(positionX, positionY);
	}

	public Sprite_Resettable(int positionX, int positionY, int width, int height,
			RenderableObject renderObject, CollisionArea collisionArea, int ID) {
		super(positionX, positionY, width, height, renderObject, collisionArea, ID);
		lastPosition = new Vector2(positionX, positionY);
	}
	
	public Sprite_Resettable(int positionX, int positionY, int width, int height,
			CollisionArea collisionArea, boolean topLefted){
		this(positionX, positionY, width, height, null, collisionArea, topLefted);
	}

	public Sprite_Resettable(int positionX, int positionY, int width, int height,
			CollisionArea collisionArea, int ID, boolean topLefted){
		this(positionX, positionY, width, height, null, collisionArea, ID, topLefted);
	}

	public Sprite_Resettable(int positionX, int positionY, int width, int height,
			RenderableObject renderObject, CollisionArea collisionArea, boolean topLefted) {
		super(positionX, positionY, width, height, renderObject, collisionArea, topLefted);
		lastPosition = new Vector2(positionX, positionY);
	}

	public Sprite_Resettable(int positionX, int positionY, int width, int height,
			RenderableObject renderObject, CollisionArea collisionArea, int ID, boolean topLefted) {
		super(positionX, positionY, width, height, renderObject, collisionArea, ID, topLefted);
		lastPosition = new Vector2(positionX, positionY);
	}
	
	
	@Override
	public void update(float delta){
		lastPosition.set(position);
		super.update(delta);
	}

	@Override
	public abstract void collide(Collidable other, boolean collidedOnce);

	@Override
	public abstract boolean canCollide();

	@Override
	public abstract void updateCollisionArea();

	@Override
	public Vector2 getLastPosition() {
		return lastPosition;
	}
}
