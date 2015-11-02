package com.mrHart.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * A class used to create background windows for UI. Essentially, you will be creating a window and
 * the class will take care of making sure the window displays properly. Ensure that windows are disposed
 * after they are created! As multiple windows can be created, this class will be using static texture
 * regions and texture files, which will be automagically loaded upon constructor calls.
 * 
 * @author Michael James Hart, michaeljh619@yahoo.com
 * @version v1.0
 * @since 06/24/2015
 *
 */
public class Window {
	/*
	 * Named Constants
	 */
	private static final String UI_DIR = "graphics/UI/";
	
	/*
	 * Static Vars
	 */
	private static int numWindows = 0;
	private static Texture botTexture, botLeftTexture, botRightTexture,
								topTexture, topLeftTexture, topRightTexture,
								midTexture, midLeftTexture, midRightTexture;
	private static TextureRegion bot, botLeft, botRight,
								top, topLeft, topRight,
								mid, midLeft, midRight;
	
	/*
	 * Instance Vars
	 */
	// Size
	protected int width, height;
	// Vectors
	protected Vector2 position;
	
	/**
	 * Creates a New Window of said width and height
	 * 
	 * @param inWidth
	 * @param inHeight
	 */
	public Window(int topLeftX, int topLeftY, int inWidth, int inHeight){
		width = inWidth;
		height = inHeight;
		position = new Vector2(topLeftX, topLeftY);
		
		// Decide whether to load textures or not
		numWindows++;
		if(numWindows == 1){
			setupGraphics();
		}
	}
	
	public void render(SpriteBatch batcher){
		// Draw Top Row
		batcher.draw(topLeft, position.x, position.y, width/3, height/3);
		batcher.draw(top, position.x + (width/3), position.y, width/3, height/3);
		batcher.draw(topRight, position.x + (2*(width/3)), position.y, width/3, height/3);
		// Draw Mid Row
		batcher.draw(midLeft, position.x, position.y + (height/3) - 2, width/3, height/3);
		batcher.draw(mid, position.x + (width/3), position.y + (height/3) - 2, width/3, height/3);
		batcher.draw(midRight, position.x + (2*(width/3)), position.y + (height/3) - 2, width/3, height/3);
		// Draw Bot Row
		batcher.draw(botLeft, position.x, position.y + (2*(height/3)) - 4, width/3, height/3);
		batcher.draw(bot, position.x + (width/3), position.y + (2*(height/3)) - 4, width/3, height/3);
		batcher.draw(botRight, position.x + (2*(width/3)), position.y + (2*(height/3)) - 4, width/3, height/3);
	}
	
	public void render(SpriteBatch batcher, int offsetX, int offsetY){
		// Draw Top Row
		batcher.draw(topLeft, position.x + offsetX, position.y + offsetY, width/3, height/3);
		batcher.draw(top, position.x + offsetX + (width/3), position.y + offsetY, width/3, height/3);
		batcher.draw(topRight, position.x + offsetX + (2*(width/3)), position.y + offsetY, width/3, height/3);
		// Draw Mid Row
		batcher.draw(midLeft, position.x + offsetX, position.y + offsetY + (height/3) - 2, width/3, height/3);
		batcher.draw(mid, position.x + offsetX + (width/3), position.y + offsetY + (height/3) - 2, width/3, height/3);
		batcher.draw(midRight, position.x + offsetX + (2*(width/3)), position.y + offsetY + (height/3) - 2, width/3, height/3);
		// Draw Bot Row
		batcher.draw(botLeft, position.x + offsetX, position.y + offsetY + (2*(height/3)) - 4, width/3, height/3);
		batcher.draw(bot, position.x + offsetX + (width/3), position.y + offsetY + (2*(height/3)) - 4, width/3, height/3);
		batcher.draw(botRight, position.x + offsetX + (2*(width/3)), position.y + offsetY + (2*(height/3)) - 4, width/3, height/3);
	}
	
	/**
	 * Helper method to load Textures
	 */
	private void setupGraphics(){
		/*
		 * Textures
		 */
		// Bottom Row
		botTexture = new Texture(Gdx.files.internal(UI_DIR + "windowBot.png"));
		botTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		botLeftTexture = new Texture(Gdx.files.internal(UI_DIR + "windowBotLeft.png"));
		botLeftTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		botRightTexture = new Texture(Gdx.files.internal(UI_DIR + "windowBotRight.png"));
		botRightTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		// Top Row
		topTexture = new Texture(Gdx.files.internal(UI_DIR + "windowTop.png"));
		topTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		topLeftTexture = new Texture(Gdx.files.internal(UI_DIR + "windowTopLeft.png"));
		topLeftTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		topRightTexture = new Texture(Gdx.files.internal(UI_DIR + "windowTopRight.png"));
		topRightTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		// Mid Row
		midTexture = new Texture(Gdx.files.internal(UI_DIR + "windowMid.png"));
		midTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		midLeftTexture = new Texture(Gdx.files.internal(UI_DIR + "windowMidLeft.png"));
		midLeftTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		midRightTexture = new Texture(Gdx.files.internal(UI_DIR + "windowMidRight.png"));
		midRightTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		/*
		 * TextureRegions
		 */
		// Bottom Row
		bot = new TextureRegion(botTexture, 0, 0, 122, 64);
		bot.flip(false, true);
		botLeft = new TextureRegion(botLeftTexture, 0, 0, 122, 64);
		botLeft.flip(false, true);
		botRight = new TextureRegion(botRightTexture, 0, 0, 122, 64);
		botRight.flip(false, true);
		// Top Row
		top = new TextureRegion(topTexture, 0, 0, 122, 93);
		top.flip(false, true);
		topLeft = new TextureRegion(topLeftTexture, 0, 0, 122, 93);
		topLeft.flip(false, true);
		topRight = new TextureRegion(topRightTexture, 0, 0, 122, 93);
		topRight.flip(false, true);
		// Mid Row
		mid = new TextureRegion(midTexture, 0, 0, 122, 64);
		mid.flip(false, true);
		midLeft = new TextureRegion(midLeftTexture, 0, 0, 122, 64);
		midLeft.flip(false, true);
		midRight = new TextureRegion(midRightTexture, 0, 0, 122, 64);
		midRight.flip(false, true);
	}
	
	public void dispose(){
		numWindows--;
		// Check to see if graphics should be disposed
		if(numWindows == 0){
			botTexture.dispose(); 
			botLeftTexture.dispose(); 
			botRightTexture.dispose();
			topTexture.dispose(); 
			topLeftTexture.dispose(); 
			topRightTexture.dispose();
			midTexture.dispose(); 
			midLeftTexture.dispose(); 
			midRightTexture.dispose();
		}
	}
	
}
