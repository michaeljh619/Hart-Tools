package com.mrHart.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	
	
	public static TextureRegion[] getRowRegions(Texture loadedTexture, int numOfRegions, int startingPositionX,
			int staticPositionY, int width, int height, int offsetX){
		// Initialize a new region array with num of regions length
		TextureRegion[] regions = new TextureRegion[numOfRegions];
		// Loop to create regions
		for(int x = 0; x < numOfRegions; x++){
			regions[x] = new TextureRegion(loadedTexture, startingPositionX + (offsetX*x),
					staticPositionY, width, height);
			regions[x].flip(false, true);
		}
		// Return
		return regions;
	}
	
	/**
	 * Creates a TextureRegion array from an array of textures in the same directory. The names of the
	 * Texture files should only differ by a number and start at 1:
	 * 
	 * 				monster1flier.png, monster2flier.png, monster3flier.png
	 * 
	 * This method will insert the numbers acoordingly in the file names.
	 * 
	 * @param textures
	 * @param directory
	 * @param firstPartOfName "monster" from example above
	 * @param lastPartOfName "flier.png" from example above
	 * @param width
	 * @param height
	 * @return
	 */
	public static TextureRegion[] createRegionsFromTextures(Texture[] textures, String directory,
			String firstPartOfName, String lastPartOfName, int width, int height, int numOfObjects){
		textures = new Texture[numOfObjects];
		TextureRegion[] regions = new TextureRegion[numOfObjects];
		
		for(int x = 0; x < regions.length; x++){
			// Textures
			textures[x] = new Texture(Gdx.files.internal(directory + firstPartOfName + (x + 1) + lastPartOfName));
			textures[x].setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

			regions[x] = new TextureRegion(textures[x], 0, 0, width, height);
			regions[x].flip(false, true);
		}
		
		return regions;
	}
}
