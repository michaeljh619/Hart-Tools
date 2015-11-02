package com.mrHart.Input;

public class GamePad {
	// Named Constants
	public static final int STYLE_DARK = 1;
	public static final int STYLE_LIGHT = 2;
	public static final int STYLE_DARK_TRANSPARENT = 3;
	
	public static final String DIR_DARK = "graphics/gamepad/dark/";
	public static final String DIR_LIGHT = "graphics/gamepad/light/";
	public static final String DIR_DARK_TRANSPARENT = "graphics/gamepad/dark_Transparent/";
	
	
	/**
	 * Gets the directory of the particular style
	 * 
	 * @param style
	 * @return
	 */
	public static String getDir(int style){
		switch(style){
		
		case STYLE_DARK:
			return DIR_DARK;
			break;
		case STYLE_LIGHT:
			return DIR_LIGHT;
			break;
		case STYLE_DARK_TRANSPARENT:
			return DIR_DARK_TRANSPARENT;
			break;
		}
		
		return "";
	}
	
}