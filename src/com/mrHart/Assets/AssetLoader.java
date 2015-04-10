package com.mrHart.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Asset loader is responsible for loading our data into RAM. Load each batch of
 * assets and when the assets are no longer needed, dispose of them to free up
 * more RAM.
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
 * 
 * @author Michael
 * @version v1.2
 * @since 12/27/2014
 */

public class AssetLoader {
	// Named Constants
	public static final String GRAPHICS_DIR = "graphics/";
	public static final String AUDIO_DIR = "audio/";
	
	
	
/*****************************************
 * Graphics and Audio Variables
 *****************************************/
	
	/*****************************************
	 * Logo
	 *****************************************/
	private static Texture logo_Texture1, logo_Texture2, logo_Texture3, logo_Texture4;
	public static TextureRegion logo_tr_screen1, logo_tr_screen2, logo_tr_screen3, logo_tr_screen4;
	public static Sound logo_s_beat;
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
		String graphicsDir = GRAPHICS_DIR + "logo/";
		String audioDir = AUDIO_DIR + "logo/";

		// Textures
		logo_Texture1 = new Texture(Gdx.files.internal(graphicsDir + "1.png"));
		logo_Texture2 = new Texture(Gdx.files.internal(graphicsDir + "2.png"));
		logo_Texture3 = new Texture(Gdx.files.internal(graphicsDir + "3.png"));
		logo_Texture4 = new Texture(Gdx.files.internal(graphicsDir + "4.png"));
		logo_Texture1.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		logo_Texture2.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		logo_Texture3.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		logo_Texture4.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		// TextureRegions
		logo_tr_screen1 = new TextureRegion(logo_Texture1, 0, 0, 800, 480);
		logo_tr_screen1.flip(false, true);
		logo_tr_screen2 = new TextureRegion(logo_Texture2, 0, 0, 800, 480);
		logo_tr_screen2.flip(false, true);
		logo_tr_screen3 = new TextureRegion(logo_Texture3, 0, 0, 800, 480);
		logo_tr_screen3.flip(false, true);
		logo_tr_screen4 = new TextureRegion(logo_Texture4, 0, 0, 800, 480);
		logo_tr_screen4.flip(false, true);

		// Sounds
		logo_s_beat = Gdx.audio
				.newSound(Gdx.files.internal(audioDir + "Beat.wav"));
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
		logo_Texture1.dispose();
		logo_Texture2.dispose();
		logo_Texture3.dispose();
		logo_Texture4.dispose();

		logo_s_beat.dispose();
	}
	
	/*****************************************
	 * Dispose Methods [END]
	 *****************************************/
	
}
