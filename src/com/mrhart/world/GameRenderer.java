package com.mrhart.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mrhart.assets.AssetLoader;
import com.mrhart.backend.Debuggable;
import com.mrhart.state.GameState;
import com.mrhart.tools.Timer;

/**
 * GameRenderer is solely responsible for rendering to the screen. No updating
 * whatsoever is to be done here! GameState will tell which function to use to
 * render and GameWorld will do the updating of the objects.
 * 
 * Timers from GameWorld can be accessed in this class since they are protected and in the same
 * package as GameRenderer.
 * 
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.0
 * @since 11/01/2015
 * 
 */

public class GameRenderer {
	/*
	 * Named Constants
	 */
	private final static boolean DEBUG_ON = true;
	
	// Instance Variables
	private GameWorld gameWorld;
	private SpriteBatch batcher;
	private ShapeRenderer shapes;
	
	/*****************************************
	 * Main Methods
	 *****************************************/
	
	public GameRenderer(GameWorld inWorld, OrthographicCamera inCamera) {
		gameWorld = inWorld;
		
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(inCamera.combined);

		shapes = new ShapeRenderer();
		shapes.setProjectionMatrix(inCamera.combined);
	}

	/**
	 * Render is the control hub for rendering objects to the screen. It is only
	 * to be used as a control method to call helper methods from the
	 * GameRenderer class.
	 * 
	 * @param delta
	 */
	@SuppressWarnings("unused")
	public void render(float runtime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Begin SpriteBatch
		batcher.begin();

		// Enable Transparency
		batcher.enableBlending();
		
		// Renders current mode
		gameWorld.currentMode.render(batcher, runtime);

		batcher.end();
		
		if(gameWorld.currentMode instanceof Debuggable && DEBUG_ON){
			shapes.setColor(Color.RED);
			shapes.begin(ShapeType.Line);
			((Debuggable) gameWorld.currentMode).debug(shapes);
			shapes.end();
		}
	}
	
	/*****************************************
	 * Main Methods [END]
	 *****************************************/
	
	
	
	/*****************************************
	 * Logo Methods
	 *****************************************/
	
	/**
	 * Renders the Mr. Hart logo
	 */
	private void renderLogo() {
	}
	
	/*****************************************
	 * Logo Methods [END]
	 *****************************************/
	
}
