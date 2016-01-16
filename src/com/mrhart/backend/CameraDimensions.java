package com.mrhart.backend;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class CameraDimensions {
	/*
	 * Instance Vars
	 */
	private Vector2 origin;
	private int width, height;
	
	public CameraDimensions(OrthographicCamera camera){
		this.origin = new Vector2(camera.position.x, camera.position.y);
		this.width = (int) camera.viewportWidth;
		this.height = (int) camera.viewportHeight;
		System.err.println(origin.x + " " + origin.y + " " + width + " " + height);
	}
	
	public CameraDimensions(Vector2 origin, int width, int height){
		this.origin = origin;
		this.width = width;
		this.height = height;
	}
	
	public void update(OrthographicCamera camera){
		origin.set(camera.position.x, camera.position.y);
		width = (int) camera.viewportWidth;
		height = (int) camera.viewportHeight;
	}

	public Vector2 getOrigin() {
		return origin;
	}

	public void setOrigin(Vector2 origin) {
		this.origin = origin;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
