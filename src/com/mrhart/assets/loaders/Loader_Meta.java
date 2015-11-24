package com.mrhart.assets.loaders;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mrhart.assets.Assets;

public class Loader_Meta {
	// Directories
	public static final String GRAPHICS_DIR = Loader_Master.GRAPHICS_DIR + "meta/";
	// Files
	public static final String LOAD_ICON = GRAPHICS_DIR + "loadingIcon.png";
	// Settings
	private static final float LOAD_FRAME_SPEED = 0.1f;
	
	public static void loadIcon(AssetManager assets){
		Assets.loadTexture(assets, LOAD_ICON);
	}
	public static Animation getIcon(AssetManager assets){
		TextureRegion[] regions = Assets.slice_LTR(assets.get(LOAD_ICON, Texture.class), 5, 1);
		
		Animation animation = new Animation(LOAD_FRAME_SPEED, regions);
		animation.setPlayMode(PlayMode.LOOP);
		
		return animation;
	}
}
