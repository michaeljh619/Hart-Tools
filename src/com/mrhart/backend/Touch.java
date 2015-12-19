package com.mrhart.backend;

import com.badlogic.gdx.Gdx;
import com.mrhart.settings.Settings;


/**
 * Provides support for converting screen touch coordinates to game coordinates.
 * Note: If you choose to use some other screen width and height for your game,
 * 		 make sure you update it in the settings! Otherwise this will throw off
 * 		 the calculations made in this class.
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 *
 */
public class Touch {
	/*****************************************
	 * Conversion Functions
	 *****************************************/
	
	/**
	 * Converts the raw screen x into a float that has been multiplied by the
	 * screen ratio.
	 * 
	 * @version v1.00
	 * @since v2.00
	 * @param inX
	 * @return x
	 */
	public static float convertX(int inX) {
		float ratio = Settings.SCREEN_WIDTH / ((float) Gdx.graphics.getWidth());
		return (float) (inX) * ratio;
	}
	
	/**
	 * Converts the raw screen y into a float that has been multiplied by the
	 * screen ratio.
	 * 
	 * @version v1.00
	 * @since v2.00
	 * @param inY
	 * @return y
	 */
	public static float convertY(int inY) {
		float ratio = Settings.SCREEN_HEIGHT / ((float) Gdx.graphics.getHeight());
		return (float) (inY) * ratio;
	}
	
	/*****************************************
	 * Conversion Functions [END]
	 *****************************************/
}