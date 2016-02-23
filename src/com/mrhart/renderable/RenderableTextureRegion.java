package com.mrhart.renderable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Wrapper for LibGDX TextureRegion.
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.00
 */
public class RenderableTextureRegion extends TextureRegion implements RenderableObject{
	public RenderableTextureRegion(Texture texture, int x, int y, int width, int height){
		super(texture, x, y, width, height);
	}
	
	public RenderableTextureRegion(TextureRegion region){
		super(region);
	}
	
	@Override
	public TextureRegion getTextureRegion(float runtime) {
		// TODO Auto-generated method stub
		return this;
	}
}
