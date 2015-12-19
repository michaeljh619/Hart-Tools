package com.mrhart.renderable;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Wrapper for LibGDX TextureRegion.
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.00
 */
public class RenderableTextureRegion implements RenderableObject{
	public TextureRegion textureRegion;
	
	public RenderableTextureRegion(TextureRegion region){
		this.textureRegion = region;
	}
	
	@Override
	public TextureRegion getTextureRegion(float runtime) {
		// TODO Auto-generated method stub
		return textureRegion;
	}
}
