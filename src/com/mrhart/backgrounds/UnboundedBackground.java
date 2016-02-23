package com.mrhart.backgrounds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mrhart.backend.CameraDimensions;
import com.mrhart.backend.HartMath;

/**
 * An unbounded background that can be scrolled on with a camera.
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.00
 */
public class UnboundedBackground {
	/*
	 * Named Constants
	 */
	public static final int X_UNBOUNDED = 0;
	public static final int Y_UNBOUNDED = 1;
	
	/*
	 * Instance Vars
	 */
	// BG
	private Background background;
	// Camera
	private CameraDimensions cameraDimensions;
	// Rendering
	private int unboundedDirection;
	
	public UnboundedBackground(Background background, CameraDimensions cameraDimensions,
			int unboundedDirection){
		// Backgrounds
		this.background = background;
		
		// Camera
		this.cameraDimensions = cameraDimensions;
		
		// Direction
		this.unboundedDirection = unboundedDirection;
	}

	/**
	 * Only renders the visible backgrounds to the camera.
	 * 
	 * @param batcher
	 * @param runtime
	 */
	public void render(SpriteBatch batcher, float runtime){
		if(unboundedDirection == X_UNBOUNDED){
			int leftEdge = HartMath.roundDownToMultiple(
					(int) (cameraDimensions.getOrigin().x - cameraDimensions.getWidth()/2), 
					background.width);
			int rightEdge = (int) (cameraDimensions.getOrigin().x + cameraDimensions.getWidth()/2);
			while(leftEdge <= rightEdge){
				batcher.draw(background.renderObject.getTextureRegion(runtime), 
						leftEdge, background.position.y, background.width, background.height);
				leftEdge += background.width;
			}
		}
		else{
			int topEdge = HartMath.roundDownToMultiple(
					(int) (cameraDimensions.getOrigin().y - cameraDimensions.getHeight()/2), 
					background.height);
			int botEdge = (int) (cameraDimensions.getOrigin().y + cameraDimensions.getHeight()/2);
			while(topEdge <= botEdge){
				batcher.draw(background.renderObject.getTextureRegion(runtime), 
						background.position.x, topEdge, background.width, background.height);
				topEdge += background.height;
			}
		}
	}
}
