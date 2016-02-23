package com.mrhart.shapes;

import com.badlogic.gdx.math.Rectangle;

/**
 * Wrapper for LibGDX Rectangle.
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.00
 */
public class Hart_Rectangle extends Rectangle implements Hart_Shape2D{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Hart_Rectangle(){
		super();
	}
	
	public Hart_Rectangle(float x, float y, float width, float height){
		super(x, y, width, height);
	}
	
	@Override
	public boolean contains(float x, float y) {
		// TODO Auto-generated method stub
		return super.contains(x, y);
	}
}
