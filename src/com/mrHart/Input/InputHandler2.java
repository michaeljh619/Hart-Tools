package com.mrHart.Input;

import com.badlogic.gdx.Gdx;

public class InputHandler2 {
	// Objects
	GameWorld gameWorld;
	public static Joystick joystick;
	public static Button button;
	

	public InputHandler2(){
		
		Joystick.initialize();
		joystick = new Joystick(GamePad.STYLE_DARK, 8, 120, 650, 100);
		button = new Button(GamePad.STYLE_DARK, Button.BUTTON_A, 380, 650, 50);
	}
	
	/**
	 * Updates all inputs
	 */
	public void update(){
		joystick.update();
	}
	
	/*****************************************
	 * Conversion Functions
	 *****************************************/
	
	/**
	 * Converts the raw screen x into a float that has been multiplied by the
	 * screen ratio.
	 * 
	 * @param inX
	 * @return x
	 */
	public static float convertX(int inX) {
		float ratio = PypGame.SCREEN_WIDTH / ((float) Gdx.graphics.getWidth());
		return (float) (inX) * ratio;
	}
	
	/**
	 * Converts the raw screen y into a float that has been multiplied by the
	 * screen ratio.
	 * 
	 * @param inY
	 * @return y
	 */
	public static float convertY(int inY) {
		float ratio = PypGame.SCREEN_HEIGHT / ((float) Gdx.graphics.getHeight());
		return (float) (inY) * ratio;
	}
	
	/*****************************************
	 * Conversion Functions [END]
	 *****************************************/
}
