package com.mrhart.mode;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mrhart.assets.InputLoader;
import com.mrhart.backend.Debuggable;
import com.mrhart.input.Button_InGame;
import com.mrhart.input.Joystick;
import com.mrhart.input.JoystickCommands;
import com.mrhart.state.GameState;

public class Mode_Test_Input extends Mode implements Debuggable{
	/*
	 * Instance Vars
	 */
	private Joystick joystick;
	private Button_InGame button;
	private JoystickCommands commands;
	
	public Mode_Test_Input(){
		super(GameState.TEST);
		
		InputLoader.loadButtonLight_A();
		InputLoader.loadJoystickLight();
		joystick = new Joystick(new Vector2(200, 300), 100, 50, Joystick.STYLE_LIGHT);
		button = new Button_InGame(new Vector2(600, 300), 100, Button_InGame.STYLE_LIGHT, Button_InGame.BUTTON_A);
		
		commands = joystick.commands;
	}
	
	@Override
	public int update(float delta) {
		joystick.update();
		button.update();
		
		if(button.isTouched()){
			System.err.println("Button is touched");
		}
		if(joystick.isTouched()){
			System.err.println("Joystick Normalized X: " + commands.normalized.x);
			System.err.println("Joystick Normalized Y: " + commands.normalized.y);
		}
		
		return GameState.NULL;
	}

	@Override
	public void render(SpriteBatch batcher, float runtime) {
		joystick.render(batcher);
		button.render(batcher);
	}

	@Override
	public void dispose() {
		
	}
	
	public void debug(ShapeRenderer shapes){
		button.debug(shapes);
	}

	@Override
	public void finalize() {
		
	}
}
