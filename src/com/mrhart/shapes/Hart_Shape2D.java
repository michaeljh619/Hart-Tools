package com.mrhart.shapes;

/**
 * An interface for 2-dimensional regions.
 * 
 * Note: LibGDX uses a marker interface for their Shape2D. Thus I can't
 * 		 directly call contains on Shape2D objects without downcasting.
 * 		 The Hart_Shapes are just wrappers for LibGDX shapes so that this
 * 		 whole mess can be avoided. 
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.00
 */
public interface Hart_Shape2D {
	/**
	 * Calls the respective LibGDX shape's contains method.
	 * 
	 * @version v1.00
	 * @since v1.00
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean contains(float x, float y);
}
