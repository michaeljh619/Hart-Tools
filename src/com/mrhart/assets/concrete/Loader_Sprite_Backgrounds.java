package com.mrhart.assets.concrete;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mrhart.assets.Assets;

public class Loader_Sprite_Backgrounds {
	/*
	 * Named Constants
	 */
	// Directories
	private static final String BACKGROUND_DIR = "graphics/sprites/backgrounds/";
	// Sub-Directories
	private static final String BRIGHT_DIR = BACKGROUND_DIR + "bright/";
	// File Names
	private static final String FILE_BRIGHT_1 = BRIGHT_DIR + "1.png";

	/*************************************************************************
	 * 							Bright Backgrounds							 * 
	 *************************************************************************/
	/**
	 * Loads Bright BG 1 into AssetManager
	 * 
	 * @param assets The AssetManager to load into load queue.
	 */
	public static void load_Bright1(AssetManager assets){
		// Load into AssetManager
		Assets.loadTexture(assets, FILE_BRIGHT_1);
	}
	/**
	 * Gets Bright BG 1 TextureRegion from the AssetManager
	 * 
	 * @param assets
	 * @return
	 */
	public static TextureRegion get_Bright1(AssetManager assets){
		return Assets.createRegion(assets, FILE_BRIGHT_1);
	}
}
