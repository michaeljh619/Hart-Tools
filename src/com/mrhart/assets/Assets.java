package com.mrhart.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Assets provides some backend support for all your asset loaders. While rudimentary
 * at the moment, more functionality can be added in the future for easier loads.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.10
 */
public class Assets {

	/**
	 * Creates and returns a TextureRegion array that parses a row of texture regions
	 * from a loaded Texture file.
	 *
	 * @param loadedTexture A texture that has been loaded already
	 * @param numOfRegions The number of frames in the row
	 * @param startingPositionX Where x starts, usually at zero; will be updated by "width"
	 * 							for each new frame
	 * @param staticPositionY The unchanging y position, starting from the top of the frame
	 * @param width The width of each frame
	 * @param height The height of each frame
	 * @return A loaded TextureRegion array, ready to use for an Animation
	 */
	public static TextureRegion[] getRowRegions(Texture loadedTexture,
			int numOfRegions, int startingPositionX, int staticPositionY,
			int width, int height) {
		// Initialize a new region array with num of regions length
		TextureRegion[] regions = new TextureRegion[numOfRegions];
		// Loop to create regions
		for (int x = 0; x < numOfRegions; x++) {
			regions[x] = new TextureRegion(loadedTexture, startingPositionX
					+ (width * x), staticPositionY, width, height);
			regions[x].flip(false, true);
		}
		// Return
		return regions;
	}

	/**
	 * Creates a TextureRegion array from an array of textures in the same
	 * directory. The names of the Texture files should only differ by a number
	 * and start at either 1 or 0:
	 * 
	 * monster1flier.png, monster2flier.png, monster3flier.png
	 *		OR
	 * monster0flier.png, monster1flier.png, monster2flier.png
	 * 
	 * This function will insert the numbers accordingly in the file names.
	 * 
	 * @param textures An initialized Texture array, if not initialized, you will crash!
	 * @param directory The directory starting in the assets folder, e.g. "graphics/sprites/monsters/"
	 * @param firstPartOfName "monster" from example above
	 * @param lastPartOfName "flier.png" from example above
	 * @param width Width of the graphics file
	 * @param height Height of the graphics files
	 * @param numOfObjects The number of files you will be loading
	 * @param startFromZero Whether or not the file name should start from zero.
	 * @return A TextureRegion array with the loaded textures ready to be used for an Animation
	 * @deprecated Dev should not be manually managing textures! This method was designed
	 *             for use with static Textures, which leads to huge memory leaks. Use 
	 *             loadTextureArray
	 */
	public static TextureRegion[] createRegionsFromTextures(Texture[] textures,
			String directory, String firstPartOfName, String lastPartOfName,
			int width, int height, int numOfObjects, boolean startFromZero) {
		TextureRegion[] regions = new TextureRegion[numOfObjects];

		if (startFromZero) {
			for (int x = 0; x < regions.length; x++) {
				// Textures
				textures[x] = new Texture(Gdx.files.internal(directory
						+ firstPartOfName + x + lastPartOfName));
				textures[x].setFilter(TextureFilter.Nearest,
						TextureFilter.Nearest);

				regions[x] = new TextureRegion(textures[x], 0, 0, width, height);
				regions[x].flip(false, true);
			}
		}
		else{
			for (int x = 0; x < regions.length; x++) {
				// Textures
				textures[x] = new Texture(Gdx.files.internal(directory
						+ firstPartOfName + (x + 1) + lastPartOfName));
				textures[x].setFilter(TextureFilter.Nearest,
						TextureFilter.Nearest);

				regions[x] = new TextureRegion(textures[x], 0, 0, width, height);
				regions[x].flip(false, true);
			}
		}

		return regions;
	}
	
	public static void loadTexture(AssetManager manager, String fileName,
			TextureParameter params){
		manager.load(fileName, Texture.class, params);
	}
	
	/**
	 * Loads multiple textures that follow the naming convention:
	 *      firstPartOfName + number + lastPartOfName
	 * These textures will be added to the AssetManager's load queue
	 * and be given mag and min filters through this method.
	 * 
	 * Post-Condition: AssetManager manager will now have all the Textures
	 * 				   that you specified in its load queue.
	 * 
	 * @param manager The AssetManager to load the textures into.
	 * @param firstPartOfName First part of the file name including the directory
	 * @param lastPartOfName Last part of the file name, usually just the extension
	 * @param numOfObjects How many textures will be loaded
	 * @param startFromZero Whether the naming convention "number" starts from 0 or not
	 * 
	 * @since v2.10
	 */
	public static void loadTextures(AssetManager manager,
			String firstPartOfName, String lastPartOfName, int numOfObjects,
			boolean startFromZero){
		// Set up filters params
		TextureParameter param = new TextureParameter();
		param.magFilter = TextureFilter.Nearest;
		param.minFilter = TextureFilter.Nearest;
		
		// Run through loop to add texture to load queue of AssetManager
		int currentIndex;
		for(int x = 0; x < numOfObjects; x++){
			// Get the correct index
			if(startFromZero){
				currentIndex = x;
			}
			else{
				currentIndex = x + 1;
			}
			
			// Load into the manager
			manager.load(firstPartOfName + currentIndex + lastPartOfName, 
					Texture.class, param);
		}
	}
	
	/**
	 * Creates and returns a TextureRegion that is created from a Texture.
	 * Gets the region encompassing entire Texture file and flips the Texture
	 * accordingly (Y-axis).
	 * 
	 * Pre-Condition: AssetManager manager must have the fileName Texture 
	 * 				  already loaded into RAM.
	 * 
	 * @param manager An AssetManager that has loaded the given Texture file name.
	 * @param fileName The Texture file to get the TextureRegion from
	 * @return
	 */
	public static TextureRegion createRegion(AssetManager manager,
			String fileName){
		TextureRegion returnRegion;
		returnRegion = new TextureRegion(manager.get(fileName, Texture.class),
				0, 0, manager.get(fileName, Texture.class).getWidth(), 
				manager.get(fileName, Texture.class).getHeight());
		returnRegion.flip(false, true);
		return returnRegion;
	}
	
	/**
	 * Gets an array of Regions from loaded Textures in an AssetManager.
	 * 
	 * Pre-Condition: Textures should have been initialized with the method
	 *                loadMultipleTextures and the Textures must already be
	 *                finished loading.
	 * 
	 * @param manager The AssetManager to get the textures from.
	 * @param firstPartOfName First part of the file name including the directory
	 * @param lastPartOfName Last part of the file name, usually just the extension
	 * @param numOfObjects How many textures will be loaded
	 * @param startFromZero Whether the naming convention "number" starts from 0 or not
	 * 
	 * @since v2.10
	 */
	public static TextureRegion[] createRegions(
			AssetManager manager, String firstPartOfName, String lastPartOfName, 
			int numOfObjects, boolean startFromZero){
		// Create an array of TextureRegions for return
		TextureRegion[] regions = new TextureRegion[numOfObjects];
		
		// Run through loop to create each TextureRegion based
		// on all the loaded textures
		int currentIndex;
		Texture loadedTexture;
		for(int x = 0; x < numOfObjects; x++){
			// Get the correct index
			if(startFromZero){
				currentIndex = x;
			}
			else{
				currentIndex = x + 1;
			}
			
			// Get the loaded Texture
			loadedTexture = manager.get(firstPartOfName + currentIndex + lastPartOfName,
					Texture.class);
			regions[x] = new TextureRegion(loadedTexture, 0, 0,
					loadedTexture.getWidth(), loadedTexture.getHeight());
			regions[x].flip(false, true);
		}
		
		return regions;
	}
}
