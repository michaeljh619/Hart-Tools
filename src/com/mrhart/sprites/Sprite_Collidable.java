package com.mrhart.sprites;

import com.mrhart.collisions.Collidable;
import com.mrhart.collisions.CollisionArea;
import com.mrhart.renderable.RenderableObject;

public abstract class Sprite_Collidable extends Sprite implements Collidable{
	/*
	 * Instance Vars
	 */
	protected CollisionArea collisionArea;

	public Sprite_Collidable(int positionX, int positionY, int width, int height,
			CollisionArea collisionArea){
		this(positionX, positionY, width, height, null, collisionArea);
	}

	public Sprite_Collidable(int positionX, int positionY, int width, int height,
			RenderableObject renderObject, CollisionArea collisionArea) {
		super(positionX, positionY, width, height, renderObject);
		this.collisionArea = collisionArea;
	}

	public Sprite_Collidable(int positionX, int positionY, int width, int height,
			CollisionArea collisionArea, int ID){
		this(positionX, positionY, width, height, null, collisionArea, ID);
	}

	public Sprite_Collidable(int positionX, int positionY, int width, int height,
			RenderableObject renderObject, CollisionArea collisionArea, int ID) {
		super(positionX, positionY, width, height, renderObject, ID);
		this.collisionArea = collisionArea;
	}

	public Sprite_Collidable(int positionX, int positionY, int width, int height,
			CollisionArea collisionArea, boolean topLefted){
		this(positionX, positionY, width, height, null, collisionArea, topLefted);
	}

	public Sprite_Collidable(int positionX, int positionY, int width, int height,
			RenderableObject renderObject, CollisionArea collisionArea, boolean topLefted) {
		super(positionX, positionY, width, height, renderObject, topLefted);
		this.collisionArea = collisionArea;
	}

	public Sprite_Collidable(int positionX, int positionY, int width, int height,
			CollisionArea collisionArea, int ID, boolean topLefted){
		this(positionX, positionY, width, height, null, collisionArea, ID, topLefted);
	}

	public Sprite_Collidable(int positionX, int positionY, int width, int height,
			RenderableObject renderObject, CollisionArea collisionArea, int ID, boolean topLefted) {
		super(positionX, positionY, width, height, renderObject, ID, topLefted);
		this.collisionArea = collisionArea;
	}

	@Override
	public CollisionArea getCollisionArea() {
		return collisionArea;
	}
	
	@Override
	public void update(float delta){
		super.update(delta);
		updateCollisionArea();
	}

	@Override
	public abstract void collide(Collidable other, boolean collidedOnce);

	@Override
	public abstract boolean canCollide();

	@Override
	public abstract void updateCollisionArea();

}
