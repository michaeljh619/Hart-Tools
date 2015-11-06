package com.mrhart.input;

import com.badlogic.gdx.math.Vector2;

/**
 * Used to get the normalized vector scale from a Joystick.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.01
 * @since 11/01/2015
 */
public class JoystickCommands {
	public Vector2 normalized;
	
	public JoystickCommands(float x, float y){
		normalized = new Vector2(x, y);
	}
	
	public void update(float x, float y){
		normalized.set(x, y);
	}
}
