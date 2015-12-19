package com.mrhart.shapes;

import com.badlogic.gdx.math.Circle;

/**
 * Wrapper for LibGDX Circle
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.00
 */
public class Hart_Circle implements Hart_Shape2D {
	public Circle circle;

	public Hart_Circle(Circle circle){
		this.circle = circle;
	}
	
	@Override
	public boolean contains(float x, float y) {
		// TODO Auto-generated method stub
		return circle.contains(x, y);
	}
}
