package com.mrhart.renderable;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Wrapper for LibGDX Animation.
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.00
 */
public class RenderableAnimation extends Animation implements RenderableObject{
	
	public RenderableAnimation(float frameDuration, Array<? extends TextureRegion> regions, PlayMode playMode){
		super(frameDuration, regions, playMode);
	}
	
	@Override
	public TextureRegion getTextureRegion(float runtime) {
		return getKeyFrame(runtime);
	}
}
