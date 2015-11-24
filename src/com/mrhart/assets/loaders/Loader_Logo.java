package com.mrhart.assets.loaders;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mrhart.assets.Assets;

public class Loader_Logo {
	// Directories Logo
	public static final String GRAPHICS_DIR = Loader_Master.GRAPHICS_DIR + "logo/";
	public static final String AUDIO_DIR = Loader_Master.AUDIO_DIR + "logo/";
	// Files
	public static final String SIGNATURE = GRAPHICS_DIR + "signature.jpeg";
	public static final String SOUND_SIGN = AUDIO_DIR + "sign.wav";
	public static final String SOUND_UNDERLINE = AUDIO_DIR + "underline.wav";
	public static final String SOUND_HEART = AUDIO_DIR + "heart.wav";
	
	/**
	 * Loads all the logo assets into the AssetManager's load queue.
	 * 
	 * @param assets
	 */
	public static void loadLogo(AssetManager assets){
		// Load Logo Graphics
		Assets.loadTexture(assets, Loader_Logo.SIGNATURE);
		Assets.loadTextures(assets,
				Loader_Logo.GRAPHICS_DIR + "sign/", 
				".jpeg", 63, true);
		Assets.loadTextures(assets,
				Loader_Logo.GRAPHICS_DIR + "underline/", 
				".jpeg", 24, false);
		// Load Logo Sounds
		assets.load(Loader_Logo.SOUND_SIGN,
				Sound.class);
		assets.load(Loader_Logo.SOUND_UNDERLINE,
				Sound.class);
		assets.load(Loader_Logo.SOUND_HEART,
				Sound.class);
	}
	
	public static TextureRegion[] getSignRegions(AssetManager assets){
		return Assets.createRegions(assets, 
				Loader_Logo.GRAPHICS_DIR + "sign/", ".jpeg", 63, true);
	}
	
	public static TextureRegion[] getUnderlineRegions(AssetManager assets){
		return Assets.createRegions(assets, 
				Loader_Logo.GRAPHICS_DIR + "underline/", ".jpeg", 24, false);
	}
	
	public static TextureRegion getSignatureRegion(AssetManager assets){
		return Assets.createRegion(assets, Loader_Logo.SIGNATURE);
	}
	
	public static Sound getSignSound(AssetManager assets){
		return assets.get(Loader_Logo.SOUND_SIGN, Sound.class);
		
	}
	
	public static Sound getUnderlineSound(AssetManager assets){
		return assets.get(Loader_Logo.SOUND_UNDERLINE, Sound.class);
	}
	
	public static Sound getHeartSound(AssetManager assets){
		return assets.get(Loader_Logo.SOUND_HEART, Sound.class);
		
	}
}
