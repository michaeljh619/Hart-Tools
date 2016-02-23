package com.mrhart.assets.loaders;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mrhart.assets.Assets;

public class Loader_Mario {
	// Directories Logo
	public static final String GRAPHICS_DIR = Loader_Master.GRAPHICS_DIR + "sprites/";
	// FIle Name
	private static final String MARIO = GRAPHICS_DIR + "1.png";

	public static void loadMario(AssetManager assets){
		// Load Logo Graphics
		Assets.loadTexture(assets, MARIO);
	}
	
	public static TextureRegion getMario(AssetManager assets){
		return Assets.createRegion(assets, MARIO);
	}
}
