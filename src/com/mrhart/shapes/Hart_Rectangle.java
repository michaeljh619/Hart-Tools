package com.mrhart.shapes;

import com.badlogic.gdx.math.Rectangle;

/**
 * Wrapper for LibGDX Rectangle.
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.00
 */
public class Hart_Rectangle implements Hart_Shape2D{
	public Rectangle rect;

	public Hart_Rectangle(Rectangle rect){
		this.rect = rect;
	}
	
	@Override
	public boolean contains(float x, float y) {
		// TODO Auto-generated method stub
		return rect.contains(x, y);
	}
}
