package com.mrhart.input;

import com.badlogic.gdx.math.Vector2;

/**
 * Used to get the normalized vector scale from a Joystick.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */
public class JoystickCommands {
	public Vector2 scale;
	
	public JoystickCommands(float x, float y){
		scale = new Vector2(x, y);
	}
	
	public void update(float x, float y){
		scale.set(x, y);
	}
}
