package com.mrhart.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Assets provides some backend support for all your asset loaders. While rudimentary
 * at the moment, more functionality can be added in the future for easier loads.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
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
}
