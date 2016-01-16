package com.mrhart.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mrhart.backend.Debuggable;
import com.mrhart.settings.Settings;

/**
 * GameRenderer is solely responsible for rendering to the screen. No updating
 * whatsoever is to be done here! GameState will tell which function to use to
 * render and GameWorld will do the updating of the objects.
 * 
 * Timers from GameWorld can be accessed in this class since they are protected and in the same
 * package as GameRenderer.
 * 
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.20
 * @since 11/01/2015
 * 
 */

public class GameRenderer {
	/*
	 * Named Constants
	 */
	private static final int LOAD_ICON_X = 700;
	private static final int LOAD_ICON_Y = 400;
	private static final int LOAD_ICON_WIDTH = 50;
	private static final int LOAD_ICON_HEIGHT = 50;
	
	/*
	 *  Instance Variables
	 */
	private GameWorld gameWorld;
	private SpriteBatch batcher;
	private ShapeRenderer shapes;
	private OrthographicCamera camera;
	
	/*****************************************
	 * Main Methods
	 *****************************************/
	
	public GameRenderer(GameWorld inWorld, OrthographicCamera inCamera) {
		gameWorld = inWorld;
		
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(inCamera.combined);

		shapes = new ShapeRenderer();
		shapes.setProjectionMatrix(inCamera.combined);
		
		camera = inCamera;
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

		batcher.setProjectionMatrix(camera.combined);
		// Begin SpriteBatch
		batcher.begin();

		// Enable Transparency
		batcher.enableBlending();
		
		// Renders current mode
		if(gameWorld.currentMode.isFinishedLoading()){
			gameWorld.currentMode.render(batcher, runtime);
		}
		// Render the loading screen instead
		else{
			renderLoadingScreen(batcher, runtime);
		}
			

		batcher.end();
		
		if(gameWorld.currentMode instanceof Debuggable && Settings.DEBUG_ON
				&& gameWorld.currentMode.isFinishedLoading()){
			shapes.setColor(Color.RED);
			shapes.begin(ShapeType.Line);
			((Debuggable) gameWorld.currentMode).debug(shapes);
			shapes.end();
		}
	}
	
	/*****************************************
	 * Main Methods [END]
	 *****************************************/
	
	private void renderLoadingScreen(SpriteBatch batcher, float runtime){
		batcher.draw(gameWorld.loadingIcon.getKeyFrame(runtime), 
				LOAD_ICON_X, LOAD_ICON_Y, LOAD_ICON_WIDTH, LOAD_ICON_HEIGHT);
	}
}
