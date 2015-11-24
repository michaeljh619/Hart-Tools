package com.mrhart.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Loads assets used in creating screen effects.
 * 
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 * @deprecated Loading assets should be done through non-static variables,
 *             otherwise issues occur with memory management. Use Mode's
 *             new AssetManager for loading, GameWorld will handle the load.
 */
public class EffectsLoader {
	/*
	 * Named Constants
	 */
	// Directories
	private static final String GRAPHICS_DIR = "graphics/effects/";
	private static final String AUDIO_DIR = "audio/effects/";
	// File Types
	private static final String PNG = ".png";
	// Fade
	private static final String FADE_DIR = "fade/";
	private static final String FADE_FILE = "fade";
	private static final int FADE_TOTAL_FRAMES = 10;
	private static final int FADE_WIDTH = 100;
	private static final int FADE_HEIGHT = 100;
	private static final float FADE_STANDARD_SPEED = 0.06f;
	
	/*
	 * Static Variables
	 */
	private static Texture[] fadeTexture;
	private static TextureRegion[] fadeRegion;
	public static Animation fadeIn, fadeOut;
	
	/**
	 * Loads the fade in and fade out.
	 * 
	 * @param animationSpeed The speed at which the fade should play, a smaller float is a faster speed
	 */
	public static void loadFade(float animationSpeed){
		// Graphics directory
		String graphicsDir = GRAPHICS_DIR + FADE_DIR;
		// Load Texture and Regions
		fadeTexture = new Texture[FADE_TOTAL_FRAMES];
		fadeRegion = Assets.createRegionsFromTextures(fadeTexture,
				graphicsDir, FADE_FILE, PNG, FADE_WIDTH, FADE_HEIGHT, FADE_TOTAL_FRAMES, true);
		// Load Animations
		fadeIn = new Animation(animationSpeed, fadeRegion);
		fadeOut = new Animation(animationSpeed, fadeRegion);
		fadeIn.setPlayMode(PlayMode.NORMAL);
		fadeOut.setPlayMode(PlayMode.REVERSED);
	}
	
	/**
	 * Loads fade at a standard speed.
	 */
	public static void loadFade(){
		loadFade(FADE_STANDARD_SPEED);
	}
	
	/**
	 * Disposes all graphics files used in the fade effect.
	 */
	public static void disposeFade(){
		for(int x = 0; x < FADE_TOTAL_FRAMES; x++){
			fadeTexture[x].dispose();
		}
		fadeTexture = null;
		fadeIn = null;
		fadeOut = null;
		fadeRegion = null;
	}

}
