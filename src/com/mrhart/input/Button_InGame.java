package com.mrhart.input;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Button_InGame extends Button {
	/*
	 * Instance Vars
	 */
	// Touch Areas
	private Circle circle;
	
	public Button_InGame(Vector2 position, int inRadius){
		super(position, inRadius);
		circle = new Circle(origin.x, origin.y, radius);
	}
	
	/**
	 * Checks to see if the touch should be registered to the joystick
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isInRange(float x, float y){
		if(circle.contains(x, y))
			return true;
		else
			return false;
	}

	public void debug(ShapeRenderer shapes){
		shapes.circle(circle.x, circle.y, circle.radius);
	}
}
