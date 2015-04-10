package com.mrHart.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.mrHart.State.GameState;

/**
 * Handles all input from the device, whether it be the keyboard or the mouse or the
 * phone's touch screen.
 * 
 * @author Michael James Hart, michaeljh619@yahoo.com
 * @version v1.2
 * @since 12/29/2014
 *
 */
public class InputHandler implements InputProcessor {
	
	// Instance variables
	float convertedX, convertedY;

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	/*****************************************
	 * TouchScreen Functions
	 *****************************************/

	@Override
	public boolean touchDown(int rawX, int rawY, int pointer, int button) {
		// Used to convert touches to game screen resize
		convertedX = convertX(rawX);
		convertedY = convertY(rawY);

		// Processes touches based on GameState
		switch (GameState.currentState) {
		
		}
		
		return true;
	}

	@Override
	public boolean touchUp(int rawX, int rawY, int pointer, int button) {
		// Used to convert touches to game screen resize
		convertedX = convertX(rawX);
		convertedY = convertY(rawY);

		// Processes touches based on GameState
		switch (GameState.currentState) {
		
		}
		
		return true;
	}

	@Override
	public boolean touchDragged(int rawX, int rawY, int pointer) {
		// Used to convert touches to game screen resize
		convertedX = convertX(rawX);
		convertedY = convertY(rawY);

		// Processes touches based on GameState
		switch (GameState.currentState) {
		
		}
		
		return true;
	}
	
	/*****************************************
	 * TouchScreen Functions
	 *****************************************/
	
	

	@Override
	public boolean mouseMoved(int rawX, int rawY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
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
	private float convertX(int inX) {
		float ratio = [TOP CLASS NAME].SCREEN_WIDTH / ((float) Gdx.graphics.getWidth());
		return (float) (inX) * ratio;
	}
	
	/**
	 * Converts the raw screen y into a float that has been multiplied by the
	 * screen ratio.
	 * 
	 * @param inY
	 * @return y
	 */
	private float convertY(int inY) {
		float ratio = [TOP CLASS NAME].SCREEN_HEIGHT / ((float) Gdx.graphics.getHeight());
		return (float) (inY) * ratio;
	}
	
	/*****************************************
	 * Conversion Functions [END]
	 *****************************************/
}