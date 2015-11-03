package com.mrhart.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

/**
 * Asset loader is responsible for loading our data into RAM. Load each batch of
 * assets and when the assets are no longer needed, dispose of them to free up
 * more RAM.
 * 
 * Please remember to dispose! This is an important step that I often forget and
 * can lead to using much more memory being used than you actually need. On phones this
 * can become a huge issue.
 * 
 * Since this class uses pure static variables and methods, pulling up the object you need can get a little
 * confusing. Therefore, names should follow the conventions shown by the Logo section.
 * 
 * TextureRegions can be named like this:
 * 		sectionName_tr_name1, sectionName_tr_name2, sectionName_tr_otherName1, ...
 * Sounds can be named like this:
 * 		sectionName_s_name1, sectionName_s_name2, sectionName_s_otherName1, ...
 * Music can be named like this:
 * 		sectionName_m_name1, sectionName_m_name2, sectionName_m_otherName1, ...
 * Or whatever floats your boat :)
 * 
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */

public class AssetLoader {
	// Named Constants
	public static final String GRAPHICS_DIR = "graphics/logo/";
	public static final String AUDIO_DIR = "audio/logo/";
	
	
	
/*****************************************
 * Graphics and Audio Variables
 *****************************************/
	
	/*****************************************
	 * Logo
	 *****************************************/
	private static Texture[] logo_signTexture, logo_underlineTexture;
	private static Texture logo_signatureTexture;
	private static TextureRegion[] logo_signRegion, logo_underlineRegion;
	public static TextureRegion logo_signature;
	public static Animation logo_sign, logo_underline;
	public static Sound logo_signSound, logo_underlineSound, logo_heartSound;
	
	public static final float logo_signSoundVolume = 1.0f;
	public static final float logo_underlineSoundVolume = 1.0f;
	public static final float logo_heartSoundVolume = 0.7f;
	/*****************************************
	 * Logo [END]
	 *****************************************/
	
/*****************************************
 * Graphics and Audio Variables [END]
 *****************************************/
	
	
	
	/*****************************************
	 * Loader Methods
	 *****************************************/

	/**
	 * Loads necessary graphics and audio for the logo
	 */
	public static void loadLogo() {
		String signGraphicsDir = GRAPHICS_DIR + "sign/";
		String underlineGraphicsDir = GRAPHICS_DIR + "underline/";

		// Textures
		logo_signTexture = new Texture[63];
		logo_underlineTexture = new Texture[24];
		logo_signatureTexture = new Texture(Gdx.files.internal(GRAPHICS_DIR + "signature.jpeg"));
		logo_signatureTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		// TextureRegions
		logo_signRegion = Assets.createRegionsFromTextures(logo_signTexture, 
				signGraphicsDir, "", ".jpeg", 800, 500, 63, true);
		logo_underlineRegion = Assets.createRegionsFromTextures(logo_underlineTexture, 
				underlineGraphicsDir, "", ".jpeg", 800, 500, 24, false);
		logo_signature = new TextureRegion(logo_signatureTexture, 0, 0, 800, 500);
		logo_signature.flip(false, true);

		// Animations
		logo_sign = new Animation(0.02f, logo_signRegion);
		logo_underline = new Animation(0.03f, logo_underlineRegion);
		logo_sign.setPlayMode(PlayMode.REVERSED);
		logo_underline.setPlayMode(PlayMode.REVERSED);

		// Sounds
		logo_signSound = Gdx.audio
				.newSound(Gdx.files.internal(AUDIO_DIR + "sign.wav"));
		logo_underlineSound = Gdx.audio
				.newSound(Gdx.files.internal(AUDIO_DIR + "underline.wav"));
		logo_heartSound = Gdx.audio
				.newSound(Gdx.files.internal(AUDIO_DIR + "heart.wav"));
	}
	
	/*****************************************
	 * Loader Methods [END]
	 *****************************************/
	
	
	
	/*****************************************
	 * Dispose Methods
	 *****************************************/
	
	/**
	 * Disposes all Graphics and audio for the Logo Screen
	 */
	public static void disposeLogo() {
		// Graphics
		for(int x = 0; x < logo_signTexture.length; x++){
			logo_signTexture[x].dispose();
		}
		for(int x = 0; x < logo_underlineTexture.length; x++){
			logo_underlineTexture[x].dispose();
		}
		logo_signatureTexture.dispose();
		logo_signTexture = null;
		logo_signRegion = null;
		logo_underlineTexture = null;
		logo_underlineRegion = null;
		logo_sign = null;
		logo_underline = null;
		
		// Audio
		logo_signSound.dispose();
		logo_underlineSound.dispose();
		logo_heartSound.dispose();
		logo_signSound = null;
		logo_heartSound = null;
	}
	
	/*****************************************
	 * Dispose Methods [END]
	 *****************************************/
	
}
