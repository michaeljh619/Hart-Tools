package com.mrhart.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Loads assets used in UI elements like Joysticks and Buttons.
 * 
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */
public class InputLoader {
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
	private static final String BUTTON_ACCEPT_FILE = "accept.png";
	private static final String BUTTON_CANCEL_FILE = "cancel.png";
	// PNG Dimensions
	private static final int BACKGROUND_SIZE = 160;
	private static final int STICK_WIDTH = 96;
	private static final int STICK_HEIGHT = 100;
	private static final int BUTTON_SIZE = 80;

	/*
	 * Static Vars
	 */
	// Joystick
	private static Texture joystick_background_textureDark;
	private static Texture joystick_background_textureLight;
	private static Texture joystick_background_textureTransparentDark;
	private static Texture joystick_stick_textureDark;
	private static Texture joystick_stick_textureLight;
	private static Texture joystick_stick_textureTransparentDark;
	public static TextureRegion joystick_background_textureRegionDark;
	public static TextureRegion joystick_background_textureRegionLight;
	public static TextureRegion joystick_background_textureRegionTransparentDark;
	public static TextureRegion joystick_stick_textureRegionDark;
	public static TextureRegion joystick_stick_textureRegionLight;
	public static TextureRegion joystick_stick_textureRegionTransparentDark;
	// Button
	private static Texture button_textureDark_A;
	private static Texture button_textureDark_B;
	private static Texture button_textureDarkTransparent_A;
	private static Texture button_textureDarkTransparent_B;
	private static Texture button_textureLight_A;
	private static Texture button_textureLight_B;
	public static TextureRegion button_dark_A;
	public static TextureRegion button_dark_B;
	public static TextureRegion button_darkTransparent_A;
	public static TextureRegion button_darkTransparent_B;
	public static TextureRegion button_light_A;
	public static TextureRegion button_light_B;
	
	/**
	 * Loads graphics for a dark style Joystick
	 */
	public static void loadJoystickDark(){
		String directory = INPUT_DIR + JOYSTICK_DIR + DARK_DIR;

		joystick_background_textureDark = new Texture(Gdx.files.internal(directory
				+ BACKGROUND_FILE));
		joystick_stick_textureDark = new Texture(Gdx.files.internal(directory + STICK_FILE));
		joystick_background_textureDark.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		joystick_stick_textureDark.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		joystick_background_textureRegionDark = new TextureRegion(joystick_background_textureDark, 0, 0,
				BACKGROUND_SIZE, BACKGROUND_SIZE);
		joystick_background_textureRegionDark.flip(false, true);
		joystick_stick_textureRegionDark = new TextureRegion(joystick_stick_textureDark, 0, 0, STICK_WIDTH, STICK_HEIGHT);
		joystick_stick_textureRegionDark.flip(false, true);
	}
	
	/**
	 * Loads graphics for a light style Joystick
	 */	
	public static void loadJoystickLight(){
		String directory = INPUT_DIR + JOYSTICK_DIR + LIGHT_DIR;

		joystick_background_textureLight = new Texture(Gdx.files.internal(directory
				+ BACKGROUND_FILE));
		joystick_stick_textureLight = new Texture(Gdx.files.internal(directory + STICK_FILE));
		joystick_background_textureLight.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		joystick_stick_textureLight.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		joystick_background_textureRegionLight = new TextureRegion(joystick_background_textureLight, 0, 0,
				BACKGROUND_SIZE, BACKGROUND_SIZE);
		joystick_background_textureRegionLight.flip(false, true);
		joystick_stick_textureRegionLight = new TextureRegion(joystick_stick_textureLight, 0, 0, STICK_WIDTH, STICK_HEIGHT);
		joystick_stick_textureRegionLight.flip(false, true);
	}
		
	/**
	 * Loads graphics for a transparent dark style Joystick
	 */
	public static void loadJoystickTransparentDark(){
		String directory = INPUT_DIR + JOYSTICK_DIR + TRANSPARENT_DARK_DIR;

		joystick_background_textureTransparentDark = new Texture(Gdx.files.internal(directory
				+ BACKGROUND_FILE));
		joystick_stick_textureTransparentDark = new Texture(Gdx.files.internal(directory + STICK_FILE));
		joystick_background_textureTransparentDark.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		joystick_stick_textureTransparentDark.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		joystick_background_textureRegionTransparentDark = new TextureRegion(joystick_background_textureTransparentDark, 0, 0,
				BACKGROUND_SIZE, BACKGROUND_SIZE);
		joystick_background_textureRegionTransparentDark.flip(false, true);
		joystick_stick_textureRegionTransparentDark = new TextureRegion(joystick_stick_textureTransparentDark, 0, 0, STICK_WIDTH, STICK_HEIGHT);
		joystick_stick_textureRegionTransparentDark.flip(false, true);
	}
		
	/**
	 * Loads graphics for a dark style 'A' button
	 */
	public static void loadButtonDark_A(){
		String directory = INPUT_DIR + BUTTON_DIR + DARK_DIR;

		button_textureDark_A = new Texture(Gdx.files.internal(directory
				+ BUTTON_A_FILE));
		button_textureDark_A.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		button_dark_A = new TextureRegion(button_textureDark_A, 0, 0, BUTTON_SIZE, BUTTON_SIZE);
		button_dark_A.flip(false, true);
	}
		
	/**
	 * Loads graphics for a dark style 'B' button
	 */
	
	public static void loadButtonDark_B(){
		String directory = INPUT_DIR + BUTTON_DIR + DARK_DIR;

		button_textureDark_B = new Texture(Gdx.files.internal(directory
				+ BUTTON_B_FILE));
		button_textureDark_B.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		button_dark_B = new TextureRegion(button_textureDark_B, 0, 0, BUTTON_SIZE, BUTTON_SIZE);
		button_dark_B.flip(false, true);
	}
		
	/**
	 * Loads graphics for a transparent dark style 'A' button
	 */
	
	public static void loadButtonDarkTransparent_A(){
		String directory = INPUT_DIR + BUTTON_DIR + TRANSPARENT_DARK_DIR;

		button_textureDarkTransparent_A = new Texture(Gdx.files.internal(directory
				+ BUTTON_A_FILE));
		button_textureDarkTransparent_A.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		button_darkTransparent_A = new TextureRegion(button_textureDarkTransparent_A, 0, 0, BUTTON_SIZE, BUTTON_SIZE);
		button_darkTransparent_A.flip(false, true);
	}
		
	/**
	 * Loads graphics for a transparent dark style 'B' button
	 */
	
	public static void loadButtonDarkTransparent_B(){
		String directory = INPUT_DIR + BUTTON_DIR + TRANSPARENT_DARK_DIR;

		button_textureDarkTransparent_B = new Texture(Gdx.files.internal(directory
				+ BUTTON_B_FILE));
		button_textureDarkTransparent_B.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		button_darkTransparent_B = new TextureRegion(button_textureDarkTransparent_B, 0, 0, BUTTON_SIZE, BUTTON_SIZE);
		button_darkTransparent_B.flip(false, true);
	}
		
	/**
	 * Loads graphics for a light style 'A' button
	 */
	
	public static void loadButtonLight_A(){
		String directory = INPUT_DIR + BUTTON_DIR + LIGHT_DIR;

		button_textureLight_A = new Texture(Gdx.files.internal(directory
				+ BUTTON_A_FILE));
		button_textureLight_A.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		button_light_A = new TextureRegion(button_textureLight_A, 0, 0, BUTTON_SIZE, BUTTON_SIZE);
		button_light_A.flip(false, true);
	}
		
	/**
	 * Loads graphics for a light style 'B' button
	 */
	
	public static void loadButtonLight_B(){
		String directory = INPUT_DIR + BUTTON_DIR + LIGHT_DIR;

		button_textureLight_B = new Texture(Gdx.files.internal(directory
				+ BUTTON_B_FILE));
		button_textureLight_B.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		button_light_B = new TextureRegion(button_textureLight_B, 0, 0, BUTTON_SIZE, BUTTON_SIZE);
		button_light_B.flip(false, true);
	}
}
