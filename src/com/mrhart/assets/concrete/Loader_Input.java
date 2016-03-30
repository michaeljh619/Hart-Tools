package com.mrhart.assets.concrete;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mrhart.assets.Assets;

public class Loader_Input {
	/*
	 * Named Constants
	 */
	// Directories
	private static final String INPUT_DIR = "graphics/input/";
	// File Names
	private static final String JOYSTICK_DIR = "joystick/";
	private static final String BUTTON_DIR = "button/";
	private static final String STICK_FILE = "stick.png";
	private static final String BACKGROUND_FILE = "background.png";
	private static final String DARK_DIR = "dark/";
	private static final String LIGHT_DIR = "light/";
	private static final String TRANSPARENT_DARK_DIR = "dark_Transparent/";
	private static final String BUTTON_A_FILE = "a.png";
	private static final String BUTTON_B_FILE = "b.png";
	private static final String BUTTON_L_FILE = "l.png";
	private static final String BUTTON_R_FILE = "r.png";
	private static final String BUTTON_X_FILE = "x.png";
	private static final String BUTTON_Y_FILE = "y.png";
//	private static final String BUTTON_ACCEPT_FILE = "accept.png";
//	private static final String BUTTON_CANCEL_FILE = "cancel.png";
	
	

	/*************************************************************************
	 * 							Joystick Dark								 * 
	 *************************************************************************/
	/**
	 * Loads Dark Joystick textures into AssetManager
	 * 
	 * @param assets The AssetManager to load into load queue.
	 */
	public static void loadJoystickDark(AssetManager assets){
		// Directory
		String directory = INPUT_DIR + JOYSTICK_DIR + DARK_DIR;

		// Load into AssetManager
		Assets.loadTexture(assets, directory + BACKGROUND_FILE);
		Assets.loadTexture(assets, directory + STICK_FILE);
	}
	/**
	 * Gets the Dark Joystick Background TextureRegion from the already loaded
	 * Dark background Texture in AssetManager assets.
	 * 
	 * @param assets
	 * @return
	 */
	public static TextureRegion getJoystickDarkBackground(AssetManager assets){
		// Directory
		String directory = INPUT_DIR + JOYSTICK_DIR + DARK_DIR;
		
		return Assets.createRegion(assets, directory + BACKGROUND_FILE);
	}
	/**
	 * Gets the Dark Joystick Background TextureRegion from the already loaded
	 * Dark background Texture in AssetManager assets.
	 * 
	 * @param assets
	 * @return
	 */
	public static TextureRegion getJoystickDarkStick(AssetManager assets){
		// Directory
		String directory = INPUT_DIR + JOYSTICK_DIR + DARK_DIR;
		
		return Assets.createRegion(assets, directory + STICK_FILE);
	}
	
	

	/*************************************************************************
	 * 							Joystick DarkT								 * 
	 *************************************************************************/
	/**
	 * Loads Dark Transparent Joystick textures into AssetManager
	 * 
	 * @param assets The AssetManager to load into load queue.
	 */
	public static void loadJoystickDarkTransparent(AssetManager assets){
		// Directory
		String directory = INPUT_DIR + JOYSTICK_DIR + TRANSPARENT_DARK_DIR;

		// Load into AssetManager
		Assets.loadTexture(assets, directory + BACKGROUND_FILE);
		Assets.loadTexture(assets, directory + STICK_FILE);
	}
	/**
	 * Gets the Dark Transparent Joystick Background TextureRegion from the already loaded
	 * Dark Transparent background Texture in AssetManager assets.
	 * 
	 * @param assets
	 * @return
	 */
	public static TextureRegion getJoystickDarkTransparentBackground(AssetManager assets){
		// Directory
		String directory = INPUT_DIR + JOYSTICK_DIR + TRANSPARENT_DARK_DIR;
		
		return Assets.createRegion(assets, directory + BACKGROUND_FILE);
	}
	/**
	 * Gets the Dark Transparent Joystick Background TextureRegion from the already loaded
	 * Dark Transparent background Texture in AssetManager assets.
	 * 
	 * @param assets
	 * @return
	 */
	public static TextureRegion getJoystickDarkTransparentStick(AssetManager assets){
		// Directory
		String directory = INPUT_DIR + JOYSTICK_DIR + TRANSPARENT_DARK_DIR;
		
		return Assets.createRegion(assets, directory + STICK_FILE);
	}



	/*************************************************************************
	 * 							Joystick Light								 * 
	 *************************************************************************/
	/**
	 * Loads Light Joystick textures into AssetManager
	 * 
	 * @param assets The AssetManager to load into load queue.
	 */
	public static void loadJoystickLight(AssetManager assets){
		// Directory
		String directory = INPUT_DIR + JOYSTICK_DIR + LIGHT_DIR;

		// Load into AssetManager
		Assets.loadTexture(assets, directory + BACKGROUND_FILE);
		Assets.loadTexture(assets, directory + STICK_FILE);
	}
	/**
	 * Gets the Light Joystick Background TextureRegion from the already loaded
	 * Light background Texture in AssetManager assets.
	 * 
	 * @param assets
	 * @return
	 */
	public static TextureRegion getJoystickLightBackground(AssetManager assets){
		// Directory
		String directory = INPUT_DIR + JOYSTICK_DIR + LIGHT_DIR;
		return Assets.createRegion(assets, directory + BACKGROUND_FILE);
	}
	/**
	 * Gets the Light Joystick Background TextureRegion from the already loaded
	 * Light background Texture in AssetManager assets.
	 * 
	 * @param assets
	 * @return
	 */
	public static TextureRegion getJoystickLightStick(AssetManager assets){
		// Directory
		String directory = INPUT_DIR + JOYSTICK_DIR + LIGHT_DIR;
		return Assets.createRegion(assets, directory + STICK_FILE);
	}
	
	
	
	/*************************************************************************
	 * 								Buttons								     * 
	 *************************************************************************/
	/**
	 * Loads graphics for a dark style 'A' button
	 */
	public static void loadButtonDark_A(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + DARK_DIR;
		Assets.loadTexture(assets, directory + BUTTON_A_FILE);
	}
	public static TextureRegion getButtonDark_A(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + DARK_DIR;
		return Assets.createRegion(assets, directory + BUTTON_A_FILE);
	}
		
	/**
	 * Loads graphics for a dark style 'B' button
	 */
	public static void loadButtonDark_B(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + DARK_DIR;
		Assets.loadTexture(assets, directory + BUTTON_B_FILE);
	}
	public static TextureRegion getButtonDark_B(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + DARK_DIR;
		return Assets.createRegion(assets, directory + BUTTON_B_FILE);
	}
		
	/**
	 * Loads graphics for a dark style 'X' button
	 */
	public static void loadButtonDark_X(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + DARK_DIR;
		Assets.loadTexture(assets, directory + BUTTON_X_FILE);
	}
	public static TextureRegion getButtonDark_X(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + DARK_DIR;
		return Assets.createRegion(assets, directory + BUTTON_X_FILE);
	}
		
	/**
	 * Loads graphics for a dark style 'Y' button
	 */
	public static void loadButtonDark_Y(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + DARK_DIR;
		Assets.loadTexture(assets, directory + BUTTON_Y_FILE);
	}
	public static TextureRegion getButtonDark_Y(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + DARK_DIR;
		return Assets.createRegion(assets, directory + BUTTON_Y_FILE);
	}
		
	/**
	 * Loads graphics for a dark style 'R' button
	 */
	public static void loadButtonDark_R(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + DARK_DIR;
		Assets.loadTexture(assets, directory + BUTTON_R_FILE);
	}
	public static TextureRegion getButtonDark_R(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + DARK_DIR;
		return Assets.createRegion(assets, directory + BUTTON_R_FILE);
	}
		
	/**
	 * Loads graphics for a dark style 'L' button
	 */
	public static void loadButtonDark_L(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + DARK_DIR;
		Assets.loadTexture(assets, directory + BUTTON_L_FILE);
	}
	public static TextureRegion getButtonDark_L(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + DARK_DIR;
		return Assets.createRegion(assets, directory + BUTTON_L_FILE);
	}
		
	/**
	 * Loads graphics for a transparent dark style 'A' button
	 */
	public static void loadButtonDarkTransparent_A(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + TRANSPARENT_DARK_DIR;
		Assets.loadTexture(assets, directory + BUTTON_A_FILE);
	}
	public static TextureRegion getButtonDarkTransparent_A(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + TRANSPARENT_DARK_DIR;
		return Assets.createRegion(assets, directory + BUTTON_A_FILE);
	}
		
	/**
	 * Loads graphics for a transparent dark style 'B' button
	 */
	
	public static void loadButtonDarkTransparent_B(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + TRANSPARENT_DARK_DIR;
		Assets.loadTexture(assets, directory + BUTTON_B_FILE);
	}
	public static TextureRegion getButtonDarkTransparent_B(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + TRANSPARENT_DARK_DIR;
		return Assets.createRegion(assets, directory + BUTTON_B_FILE);
	}
		
	/**
	 * Loads graphics for a light style 'A' button
	 */
	public static void loadButtonLight_A(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + LIGHT_DIR;
		Assets.loadTexture(assets, directory + BUTTON_A_FILE);
	}
	public static TextureRegion getButtonLight_A(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + LIGHT_DIR;
		return Assets.createRegion(assets, directory + BUTTON_A_FILE);
	}
		
	/**
	 * Loads graphics for a light style 'B' button
	 */
	public static void loadButtonLight_B(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + LIGHT_DIR;
		Assets.loadTexture(assets, directory + BUTTON_B_FILE);
	}
	public static TextureRegion getButtonLight_B(AssetManager assets){
		String directory = INPUT_DIR + BUTTON_DIR + LIGHT_DIR;
		return Assets.createRegion(assets, directory + BUTTON_B_FILE);
	}
}
