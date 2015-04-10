package com.mrHart.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Bar {
	/******************
	 * Named Constants
	 ******************/
	// Directories
	private static final String UI_DIR = "graphics/UI/";
	// Png's
	private static final String PNG_BAR = "bar.png";
	private static final String PNG_BAR_WHITE = "barWhite.png";
	private static final String PNG_BLUE = "blue.png";
	private static final String PNG_GREEN = "green.png";
	private static final String PNG_ORANGE = "orange.png";
	private static final String PNG_RED = "red.png";
	private static final String PNG_HEART_FULL = "heartFull.png";
	private static final String PNG_COIN = "coin.png";
	// Bar Colors
	public static final int COLOR_BLUE = 1;
	public static final int COLOR_GREEN = 2;
	public static final int COLOR_ORANGE = 3;
	public static final int COLOR_RED = 4;
	// Bar Styles
	public static final int STYLE_BAR_FILL = 1;
	public static final int STYLE_BAR_WHITE = 2;
	// Decorations
	public static final int DECORATION_HEART_FULL = 1;
	private static final int DECORATION_HEART_FULL_WIDTH = 154;
	private static final int DECORATION_HEART_FULL_HEIGHT = 171;
	private static final float DECORATION_HEART_FULL_RATIO_X = 0.45f;
	private static final float DECORATION_HEART_FULL_RATIO_Y = 1.15f;
	private static final float DECORATION_HEART_FULL_OFFSET_X = 0.2f;
	private static final float DECORATION_HEART_FULL_OFFSET_Y = 0.2f;
	public static final int DECORATION_COIN = 2;
	private static final int DECORATION_COIN_WIDTH = 112;
	private static final int DECORATION_COIN_HEIGHT = 117;
	private static final float DECORATION_COIN_RATIO_X = 0.45f;
	private static final float DECORATION_COIN_RATIO_Y = 1.15f;
	private static final float DECORATION_COIN_OFFSET_X = 0.2f;
	private static final float DECORATION_COIN_OFFSET_Y = 0.25f;
	// Decoration Positions
	public static final int DECORATION_LEFT_JUSTIFIED = 1;
	// Font
	private static final float FONT_SCALE = 0.005f;
	private static final float FONT_RATIO_X_1 = 0.51f;
	private static final float FONT_RATIO_X_10 = 0.48f;
	private static final float FONT_RATIO_X_100 = 0.45f;
	private static final float FONT_RATIO_X_1000 = 0.42f;
	private static final float FONT_RATIO_Y = 0.33f;
	// Sizes
	private static final int BAR_WIDTH = 354;
	private static final int BAR_HEIGHT = 86;
	private static final int FILL_WIDTH = 1105;
	private static final int FILL_HEIGHT = 38;
	private static final float FILL_OFFSET_X = .20f;
	private static final float FILL_OFFSET_Y = .27f;
	private static final float FILL_RATIO_X = .74f;
	private static final float FILL_RATIO_Y = .40f;

	/******************
	 * Instance Vars
	 ******************/
	// Vectors
	private Vector2 position;
	// Graphics
	private Texture barTexture;
	private Texture backgroundFillTexture, fillTexture;
	private TextureRegion bar;
	private TextureRegion backgroundFill, fill;
	// Size
	private int width, height;
	// Decoration
	private Texture decorationTexture;
	private TextureRegion decoration;
	private int decWidth, decHeight;
	private float decRatioX, decRatioY;
	private float decOffsetX, decOffsetY;
	// Font
	private BitmapFont font = new BitmapFont(true);
	
	/******************
	 * Constructors
	 ******************/
	
	/**
	 * Creates a standard bar with a fill, has an optional background fill and optional
	 * decoration as well.
	 * 
	 * @param inPosition
	 * @param inWidth
	 * @param inHeight
	 * @param fillColor Color of the fill
	 * @param bgFillColor Optional: Use 0 if you don't want BG fill
	 * @param inDecoration Optional: Use 0 if you don't want a decoration
	 * @param decorationPosition Optional: Use 0 if you don't want a decoration
	 */
	public Bar(Vector2 inPosition, int inWidth, int inHeight, int fillColor, 
			int bgFillColor, int inDecoration, int decorationPosition){
		// Vector
		position = inPosition;
		// Size
		width = inWidth;
		height = inHeight;
		
		// Set up specifications
		setupBar(STYLE_BAR_FILL);
		setupFill(fillColor);
		setupBackgroundFill(bgFillColor);
		setupDecoration(inDecoration, decorationPosition);
	}
	
	/**
	 * Creates a bar that has a white space for number display and an optional decoration.
	 * 
	 * @param inPosition
	 * @param inWidth
	 * @param inHeight
	 * @param inDecoration Optional: Use 0 if you don't want a decoration
	 * @param decorationPosition Optional: Use 0 if you don't want a decoration
	 */
	public Bar(Vector2 inPosition, int inWidth, int inHeight, int inDecoration, int decorationPosition){
		// Vector
		position = inPosition;
		// Size
		width = inWidth;
		height = inHeight;
		// Font
		font.scale(height*FONT_SCALE);
		// Set up specifications
		setupBar(STYLE_BAR_WHITE);
		setupDecoration(inDecoration, decorationPosition);
	}
	
	/******************
	 * Constructors [END]
	 ******************/
	
	/**************************************
	 * 			  Main Methods
	 **************************************/
	
	/**
	 * Render Method for the Fill Bar: Make sure you are using the right one!
	 * 
	 * Renders the bar, the percentage will be the percentage of the bar that should be filled.
	 * Values of percentage should be anywhere from 0.0f to 1.0f, 0.0f being 0% and 1.0f being 100%.
	 * 
	 * @param batcher
	 * @param percentage
	 */
	public void render(SpriteBatch batcher, float percentage){
		// Render Bar
		batcher.draw(bar, position.x, position.y, width, height);
		// Render Background
		if(backgroundFill != null){
			batcher.draw(backgroundFill, position.x + (width*FILL_OFFSET_X),
					position.y + (height*FILL_OFFSET_Y), (width*FILL_RATIO_X), 
					(height*FILL_RATIO_Y));
			
		}
		// Render Fill
		batcher.draw(fill, position.x + (width*FILL_OFFSET_X),
				position.y + (height*FILL_OFFSET_Y), (width*FILL_RATIO_X)*percentage, 
				(height*FILL_RATIO_Y));
		// Render Decoration
		if(decoration != null){
			batcher.draw(decoration, position.x + (width*decOffsetX),
					position.y + (height*decOffsetY), (width*decRatioX), 
					(height*decRatioY));
		}
	}

	/**
	 * Render method for the Number Bar: Make sure you are using the correct one!
	 * 
	 * Renders the bar, the amount will be the number that is displayed in the white area
	 * of the bar.
	 * 
	 * @param batcher
	 * @param amount
	 */
	public void render(SpriteBatch batcher, int amount){
		// Render Bar
		batcher.draw(bar, position.x, position.y, width, height);
		// Render Font
		float ratioX;
		if(amount < 10){
			ratioX = FONT_RATIO_X_1;
		}
		else if(amount < 100){
			ratioX = FONT_RATIO_X_10;
		}
		else if(amount < 1000){
			ratioX = FONT_RATIO_X_100;
		}
		else{
			ratioX = FONT_RATIO_X_1000;
		}
		font.setColor(Color.BLACK);
		font.draw(batcher, Integer.toString(amount), position.x + (width * ratioX), 
				position.y + (height * FONT_RATIO_Y));
		// Render Decoration
		if(decoration != null){
			batcher.draw(decoration, position.x + (width*decOffsetX),
					position.y + (height*decOffsetY), (width*decRatioX), 
					(height*decRatioY));
		}
	}

	/**************************************
	 * 			  Main Methods [END]
	 **************************************/
	
	/**************************************
	 * 	   Constructor Assist Methods
	 **************************************/
	
	/**
	 * Sets up a new style of bar, either filled or a numbered White bar
	 * 
	 * @param barStyle
	 */
	public void setupBar(int barStyle){
		// Set up String
		String barString;
		switch(barStyle){
		case STYLE_BAR_FILL:
			barString = PNG_BAR;
			break;
		case STYLE_BAR_WHITE:
			barString = PNG_BAR_WHITE;
			break;
		default:
			barString = PNG_BAR;
			break;
		}
		// Bar
		barTexture = new Texture(Gdx.files.internal(UI_DIR + barString));
		barTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		bar = new TextureRegion(barTexture, 0, 0, BAR_WIDTH, BAR_HEIGHT);
		bar.flip(false, true);
	}
	
	/**
	 * Sets up the fill of a standard bar
	 * 
	 * @param fillColor
	 */
	public void setupFill(int fillColor){
		// Fill
		String color;
		switch(fillColor){
		case COLOR_BLUE:
			color = PNG_BLUE;
			break;
		case COLOR_RED:
			color = PNG_RED;
			break;
		case COLOR_GREEN:
			color = PNG_GREEN;
			break;
		case COLOR_ORANGE:
			color = PNG_ORANGE;
			break;
		default:
			color = PNG_RED;
			break;
		}
		fillTexture = new Texture(Gdx.files.internal(UI_DIR + color));
		fillTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		fill = new TextureRegion(fillTexture, 0, 0, FILL_WIDTH, FILL_HEIGHT);
		fill.flip(false, true);
	}
	
	/**
	 * Sets up the background fill of a standard bar
	 * 
	 * @param bgFillColor
	 */
	public void setupBackgroundFill(int bgFillColor){
		// Fill
		String color;
		switch(bgFillColor){
		case COLOR_BLUE:
			color = PNG_BLUE;
			break;
		case COLOR_RED:
			color = PNG_RED;
			break;
		case COLOR_GREEN:
			color = PNG_GREEN;
			break;
		case COLOR_ORANGE:
			color = PNG_ORANGE;
			break;
		default:
			color = null;
			break;
		}
		if(color != null){
			backgroundFillTexture = new Texture(Gdx.files.internal(UI_DIR + color));
			backgroundFillTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
			backgroundFill = new TextureRegion(backgroundFillTexture, 0, 0, FILL_WIDTH, FILL_HEIGHT);
			backgroundFill.flip(false, true);
		}
	}
	
	/**
	 * Sets up a new decoration with a style of decoration and the position of the decoration
	 * 
	 * @param inDecoration
	 * @param decorationPosition
	 */
	public void setupDecoration(int inDecoration, int decorationPosition){
		// Decoration
		String deco;
		switch(inDecoration){
		case DECORATION_HEART_FULL:
			deco = PNG_HEART_FULL;
			decWidth = DECORATION_HEART_FULL_WIDTH;
			decHeight = DECORATION_HEART_FULL_HEIGHT;
			decRatioX = DECORATION_HEART_FULL_RATIO_X;
			decRatioY = DECORATION_HEART_FULL_RATIO_Y;
			if(decorationPosition == DECORATION_LEFT_JUSTIFIED){
				decOffsetX = -DECORATION_HEART_FULL_OFFSET_X;
				decOffsetY = -DECORATION_HEART_FULL_OFFSET_Y;
			}
			break;
		case DECORATION_COIN:
			deco = PNG_COIN;
			decWidth = DECORATION_COIN_WIDTH;
			decHeight = DECORATION_COIN_HEIGHT;
			decRatioX = DECORATION_COIN_RATIO_X;
			decRatioY = DECORATION_COIN_RATIO_Y;
			if(decorationPosition == DECORATION_LEFT_JUSTIFIED){
				decOffsetX = -DECORATION_COIN_OFFSET_X;
				decOffsetY = -DECORATION_COIN_OFFSET_Y;
			}
			break;
		default:
			deco = null;
			break;
		}
		if(deco != null){
			decorationTexture = new Texture(Gdx.files.internal(UI_DIR + deco));
			decorationTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
			decoration = new TextureRegion(decorationTexture, 0, 0, decWidth, decHeight);
			decoration.flip(false, true);
		}
	}
	
	/**************************************
	 * 	  Constructor Assist Methods [END]
	 **************************************/
	
	public void scroll(int speed){
		position.x += speed;
	}
	
	/**************************************
	 * 			Getters/Setters
	 **************************************/

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public void setPosition(float x, float y) {
		position.set(x, y);
	}
}
