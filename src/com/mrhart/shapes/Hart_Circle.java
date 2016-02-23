package com.mrhart.shapes;

import com.badlogic.gdx.math.Circle;

/**
 * Wrapper for LibGDX Circle
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.00
 */
public class Hart_Circle extends Circle implements Hart_Shape2D {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Hart_Circle(){
		super();
	}

	public Hart_Circle(float x, float y, float radius) {
		super(x, y, radius);
	}

	@Override
	public boolean contains(float x, float y) {
		// TODO Auto-generated method stub
		return super.contains(x, y);
	}
}
