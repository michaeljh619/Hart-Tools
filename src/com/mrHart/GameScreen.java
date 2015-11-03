package com.mrhart;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mrhart.settings.Settings;
import com.mrhart.world.GameRenderer;
import com.mrhart.world.GameWorld;

/**
 * GameScreen is the main control hub for all that goes on in the game world. 
 * Calls the update function from GameWorld and the render function from GameRenderer.
 * Keeps track of runtime and controls resizing, pausing, exiting, etc.
 * 
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 *
 */

public class GameScreen implements Screen {
	private GameWorld gameWorld;
	private GameRenderer gameRenderer;
	
	private OrthographicCamera camera;
	private StretchViewport viewport;
	
	private float runTime = 0;
    
	/*
	 * Camera needs to be passed down to gameRenderer for use in SpriteBatch
	 */
    public GameScreen() {
    	
    	camera = new OrthographicCamera();
    	camera.setToOrtho(true, Settings.SCREEN_WIDTH,
    			Settings.SCREEN_HEIGHT);
    	
    	viewport = new StretchViewport(Settings.SCREEN_WIDTH,
    			Settings.SCREEN_HEIGHT, camera);
    	
    	gameWorld = new GameWorld();
    	gameRenderer = new GameRenderer(gameWorld, camera);
    }

    /**
     * We use delta to consider the fact that CPU speed slows down and speeds
     * up. We must scale everything with a factor of delta to ensure gameplay
     * is smooth!
     * 
     * @param delta
     */
    public void render(float delta) {
    	runTime += delta;
		gameWorld.update(delta);
		gameRenderer.render(runTime);
    }

    /**
     * When resizing, viewport must also be resized to keep the screen
     * consistent with gameplay screen.
     * 
     * @param width int
     * @param height int
     */
    public void resize(int width, int height) {
    	viewport.update(width, height);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {   
    }

    @Override
    public void pause() {       
    }

    @Override
    public void resume() {     
    }

    @Override
    public void dispose() {
    }

}