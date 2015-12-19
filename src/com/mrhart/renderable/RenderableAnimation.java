package com.mrhart.renderable;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Wrapper for LibGDX Animation.
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.00
 */
public class RenderableAnimation implements RenderableObject{
	public Animation animation;
	
	public RenderableAnimation(Animation animation){
		this.animation = animation;
	}
	
	@Override
	public TextureRegion getTextureRegion(float runtime) {
		return animation.getKeyFrame(runtime);
	}
}
