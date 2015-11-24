package com.mrhart.assets.loaders;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mrhart.assets.Assets;

public class Loader_Effects {
	// Directories
	public static final String GRAPHICS_DIR = Loader_Master.GRAPHICS_DIR + "effects/";
//	public static final String AUDIO_DIR = Master.AUDIO_DIR + "effects/";
	// Sub-Directories
	public static final String FADE_GRAPHICS_DIR = GRAPHICS_DIR + "fade/";
//	public static final String FADE_AUDIO_DIR = AUDIO_DIR + "fade/";
	// Files
	public static final String FADE = FADE_GRAPHICS_DIR + "fade";
	// File Settings
	public static final int FADE_NUM_FILES = 10;
	public static final float FADE_STANDARD_SPEED = 0.06f;
	public static final boolean FADE_START_FROM_ZERO = true;
	
	/**
	 * Loads the Fade Textures into assets
	 * 
	 * @param assets
	 */
	public static void loadFade(AssetManager assets){
		Assets.loadTextures(assets, Loader_Effects.FADE, 
				".png", Loader_Effects.FADE_NUM_FILES, 
				Loader_Effects.FADE_START_FROM_ZERO);
	}
	
	public static TextureRegion[] getFadeRegions(AssetManager assets){
		return Assets.createRegions(assets, Loader_Effects.FADE, 
				".png", Loader_Effects.FADE_NUM_FILES, Loader_Effects.FADE_START_FROM_ZERO);
	}
}
