package com.mrhart.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Assets provides some backend support for all your asset loaders. Having
 * officially moved over to AssetManager, most functions will now take an
 * AssetManager as a parameter.
 *
 * @author Michael James Hart, MrHartGames@yahoo.com
 * @version v2.10
 */
public class Assets {
	
	/**
	 * Slices a Texture into an array of TextureRegions. Given the
	 * numHorizontal and numVertical, this function will slice the Texture into
	 * numH * numV pieces in row major order. Each piece will have dimensions
	 * width = textureWidth/numH | height = textureHeight/numV.
	 * 
	 * @version v1.00
	 * @since v2.10
	 * @param loadedTexture The loaded texture
	 * @param numColumns Number of columns
	 * @param numRows Number of Rows
	 * @return
	 */
	public static TextureRegion[] slice_LTR(Texture loadedTexture,
			int numColumns, int numRows){
		TextureRegion[] returnRegions 
			= new TextureRegion[numColumns*numRows];
		
		int positionX, positionY;
		int regionWidth = loadedTexture.getWidth()/numColumns;
		int regionHeight = loadedTexture.getHeight()/numRows;
		for(int y = 0; y < numRows; y++){
			for(int x = 0; x < numColumns; x++){
				// Get positions
				positionX = x*regionWidth;
				positionY = y*regionHeight;
				// Create the region
				returnRegions[y*numColumns + x] 
						= new TextureRegion(loadedTexture, 
								positionX, positionY, 
								regionWidth, regionHeight);
				returnRegions[y*numColumns + x].flip(false, true);
			}
		}
		
		return returnRegions;
	}

	/**
	 * Creates and returns a TextureRegion array that is composed of a row of 
	 * texture regions from a loaded Texture file.
	 *
	 * @version v1.00
	 * @since v1.00
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
	 * Loads a texture into the AssetManager argument. Uses the Texture-
	 * Parameter arguments in creating the Texture.
	 * 
	 * @version v1.00
	 * @since v2.10
	 * @param assets
	 * @param fileName
	 * @param params
	 */
	public static void loadTexture(AssetManager assets, String fileName,
			TextureParameter params){
		assets.load(fileName, Texture.class, params);
	}
	/**
	 * Loads a texture into the AssetManager argument. Uses a standard set
	 * of TextureParameters.
	 * 
	 * @version v1.00
	 * @since v2.10
	 * @param assets
	 * @param fileName
	 */
	public static void loadTexture(AssetManager assets, String fileName){
		assets.load(fileName, Texture.class, Assets.getStandardParams());
	}
	
	/**
	 * Loads multiple textures that follow the naming convention:
	 *      firstPartOfName + number + lastPartOfName
	 * These textures will be added to the AssetManager's load queue
	 * and be given mag and min filters through this method.
	 * 
	 * Post-Condition: AssetManager assets will now have all the Textures
	 * 				   that you specified in its load queue.
	 * 
	 * @version v1.00
	 * @since v2.10
	 * @param assets The AssetManager to load the textures into.
	 * @param firstPartOfName First part of the file name including the directory
	 * @param lastPartOfName Last part of the file name, usually just the extension
	 * @param numOfObjects How many textures will be loaded
	 * @param startFromZero Whether the naming convention "number" starts from 0 or not
	 */
	public static void loadTextures(AssetManager assets,
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
			
			// Load into the assets
			assets.load(firstPartOfName + currentIndex + lastPartOfName, 
					Texture.class, param);
		}
	}
	
	/**
	 * Creates and returns a TextureRegion that is created from a Texture.
	 * Gets the region encompassing entire Texture file and flips the Texture
	 * accordingly (Y-axis).
	 * 
	 * Pre-Condition: AssetManager assets must have the fileName Texture 
	 * 				  already loaded into RAM.
	 * 
	 * @version v1.00
	 * @since v2.10
	 * @param assets An AssetManager that has loaded the given Texture file name.
	 * @param fileName The Texture file to get the TextureRegion from
	 * @return
	 */
	public static TextureRegion createRegion(AssetManager assets,
			String fileName){
		TextureRegion returnRegion;
		returnRegion = new TextureRegion(assets.get(fileName, Texture.class),
				0, 0, assets.get(fileName, Texture.class).getWidth(), 
				assets.get(fileName, Texture.class).getHeight());
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
	 * @version v1.00
	 * @since v2.10
	 * @param assets The AssetManager to get the textures from.
	 * @param firstPartOfName First part of the file name including the directory
	 * @param lastPartOfName Last part of the file name, usually just the extension
	 * @param numOfObjects How many textures will be loaded
	 * @param startFromZero Whether the naming convention "number" starts from 0 or not
	 */
	public static TextureRegion[] createRegions(
			AssetManager assets, String firstPartOfName, String lastPartOfName, 
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
			loadedTexture = assets.get(firstPartOfName + currentIndex + lastPartOfName,
					Texture.class);
			regions[x] = new TextureRegion(loadedTexture, 0, 0,
					loadedTexture.getWidth(), loadedTexture.getHeight());
			regions[x].flip(false, true);
		}
		
		return regions;
	}
	
	/**
	 * Gets a standard TextureParameter for use in loading textures to an
	 * AssetManager.
	 * 
	 * @version v1.00
	 * @since v2.10
	 * @return Returns a new standard TextureParameter
	 */
	private static TextureParameter getStandardParams(){
		TextureParameter params = new TextureParameter();
		params.magFilter = TextureFilter.Nearest;
		params.minFilter = TextureFilter.Nearest;
		return params;
	}
}
