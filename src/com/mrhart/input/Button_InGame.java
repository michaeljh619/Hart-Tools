package com.mrhart.input;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mrhart.assets.InputLoader;
import com.mrhart.backend.Messages;

public class Button_InGame extends Button {
	/*
	 *  Named Constants
	 */
	// Colors
	public static final int STYLE_DARK = 1;
	public static final int STYLE_LIGHT = 2;
	public static final int STYLE_DARK_TRANSPARENT = 3;
	// Graphic on the Button
	public static final int BUTTON_A = 1;
	public static final int BUTTON_B = 2;
	public static final int BUTTON_L = 3;
	public static final int BUTTON_R = 4;
	public static final int BUTTON_X = 5;
	public static final int BUTTON_Y = 6;
	public static final int BUTTON_ACCEPT = 7;
	public static final int BUTTON_CANCEL = 8;
	
	/*
	 * Instance Vars
	 */
	// Touch Areas
	private Circle circle;
	
	public Button_InGame(Vector2 position, int inRadius, int styleOfButton, int buttonPicture){
		super(position, inRadius);
		
		circle = new Circle(origin.x, origin.y, radius);
		
		switch(styleOfButton){
		case STYLE_DARK:
			if(buttonPicture == BUTTON_A)
				textureRegion = InputLoader.button_dark_A;
			else if(buttonPicture == BUTTON_B)
				textureRegion = InputLoader.button_dark_B;
			else
				System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
						+ "buttonPicture parameter is not a valid input!");
			break;
		case STYLE_LIGHT:
			if(buttonPicture == BUTTON_A)
				textureRegion = InputLoader.button_light_A;
			else if(buttonPicture == BUTTON_B)
				textureRegion = InputLoader.button_light_B;
			else
				System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
						+ "buttonPicture parameter is not a valid input!");
			break;
		case STYLE_DARK_TRANSPARENT:
			if(buttonPicture == BUTTON_A)
				textureRegion = InputLoader.button_darkTransparent_A;
			else if(buttonPicture == BUTTON_B)
				textureRegion = InputLoader.button_darkTransparent_B;
			else
				System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
						+ "buttonPicture parameter is not a valid input!");
			break;
		default:
				System.err.println(Messages.ERROR + Messages.TYPE_BAD_VALUE
						+ "styleOfButton parameter is not a valid input!");
		}
	}
	
	/**
	 * Checks to see if the touch should be registered to the joystick
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isInRange(float x, float y){
		if(circle.contains(x, y))
			return true;
		else
			return false;
	}

	public void debug(ShapeRenderer shapes){
		shapes.circle(circle.x, circle.y, circle.radius);
	}
}
