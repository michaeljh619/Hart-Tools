package com.mrhart.mode;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * A struct for holding objects from one Mode to another. By default, ModeBin
 * has certain objects like the camera and the last mode. However, if you
 * extend a ModeBin, you may send more objects and primitive types to the next
 * mode as well; for example, high scores, sprites, etc.
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.00
 */
public class ModeBin {
	/*
	 * Instance Vars
	 */
	public OrthographicCamera camera;
	public Class<? extends Mode> lastMode;
	public float volume;
	
	/**
	 * GameWorld will automatically send you the camera and the last mode.
	 * Volume needs to updated by you.
	 * 
	 * @version v1.00
	 * @since v1.00
	 */
	public ModeBin(){
		volume = 1.0f;
	}
}
