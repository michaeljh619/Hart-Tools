package com.mrhart.sprites.background;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mrhart.backend.Messages;
import com.mrhart.settings.Settings;


public class ScrollableBackground{
	/*
	 * Named Constants
	 */
	private static final int INDEX_TOP_LEFT = 0;
	private static final int INDEX_TOP_RIGHT = 1;
	private static final int INDEX_BOT_LEFT = 2;
	private static final int INDEX_BOT_RIGHT = 3;
	
	/*
	 * Instance Vars
	 */
	// Scroll directions
	private boolean isScrollableX, isScrollableY;
	// Array of Backgrounds
	private Background[] backgrounds;
	
	/**
	 * Creates a scrollable background with a specified position, dimensions,
	 * and a region.
	 * 
	 * @param positionX
	 * @param positionY
	 * @param width
	 * @param height
	 * @param region
	 * @param isScrollableX
	 * @param isScrollableY
	 */
	public ScrollableBackground(int positionX, int positionY, int width, int height, 
			TextureRegion region, boolean isScrollableX, boolean isScrollableY){
		// Set up boolean scrollables
		this.isScrollableX = isScrollableX;
		this.isScrollableY = isScrollableY;
		// Set up a diagonal scrollable array of backgrounds
		if(isScrollableX && isScrollableY){
			backgrounds = new Background[4];
			backgrounds[INDEX_TOP_LEFT] = new Background(positionX, positionY,
					width, height, region);
			backgrounds[INDEX_TOP_RIGHT] = new Background(positionX + width, positionY,
					width, height, region);
			backgrounds[INDEX_BOT_LEFT] = new Background(positionX, positionY + height,
					width, height, region);
			backgrounds[INDEX_BOT_RIGHT] = new Background(positionX + width, positionY + height,
					width, height, region);
		}
		// Set up a horizontal scrollable array of backgrounds
		else if(isScrollableX && !isScrollableY){
			backgrounds = new Background[2];
			backgrounds[0] = new Background(positionX, positionY,
					width, height, region);
			backgrounds[1] = new Background(positionX + width, positionY,
					width, height, region);
		}
		// Set up a vertical scrollable array of backgrounds
		else if(!isScrollableX && isScrollableY){
			backgrounds = new Background[2];
			backgrounds[0] = new Background(positionX, positionY,
					width, height, region);
			backgrounds[1] = new Background(positionX, positionY + height,
					width, height, region);
		}
		// Both scrollables were false
		else{
			System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE 
				+ "Both isScrollabeX and isScrollableY were false!");
		}
	}
	
	/**
	 * Creates a standard scrollable background at 0, 0 and ScreenWidth and
	 * ScreenHeight.
	 * 
	 * @param region
	 * @param isScrollableX
	 * @param isScrollableY
	 */
	public ScrollableBackground(TextureRegion region, boolean isScrollableX, boolean isScrollableY){
		this(0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT, region,
				isScrollableX, isScrollableY);
	}
	
	public void update(float delta, int cameraOriginX, int cameraOriginY){
		
	}
	
	/**
	 * Renders the backgrounds to the screen.
	 * 
	 * @param batcher
	 * @param runtime
	 */
	public void render(SpriteBatch batcher, float runtime){
		for(int x = 0; x < backgrounds.length; x++){
			backgrounds[x].render(batcher, runtime);
		}
	}
}
