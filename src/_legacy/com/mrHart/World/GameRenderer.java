package com.mrHart.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mrHart.Assets.AssetLoader;
import com.mrHart.State.GameState;
import com.mrHart.Tools.Timer;

/**
 * GameRenderer is solely responsible for rendering to the screen. No updating
 * whatsoever is to be done here! GameState will tell which function to use to
 * render and GameWorld will do the updating of the objects.
 * 
 * Timers from GameWorld can be accessed in this class since they are protected and in the same
 * package as GameRenderer.
 * 
 * @author Michael James Hart, michaeljh619@yahoo.com
 * @version v1.2
 * @since 9/12/2014
 * 
 */

public class GameRenderer {
	// Instance Variables
	private GameWorld gameWorld;
	private SpriteBatch batcher;
	private ShapeRenderer shapes;
	
	/*****************************************
	 * Main Methods
	 *****************************************/
	
	public GameRenderer(GameWorld inWorld, OrthographicCamera inCamera) {
		gameWorld = inWorld;
		setupLogoTimer();
		
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
	public void render(float delta) {
		switch (GameState.currentState) {
		case GameState.STATE_LOGO:
			renderLogo();
			break;
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
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Begin SpriteBatch
		batcher.begin();

		// Disable Transparency
		batcher.disableBlending();

		if(gameWorld.timerAlpha.isActive()){
			if (!gameWorld.timerAlpha.tasks[0].isFinished()) {
				batcher.draw(AssetLoader.logo_tr_screen1, 0, 0, 800, 500);
			} else if (!gameWorld.timerAlpha.tasks[1].isFinished()) {
				batcher.draw(AssetLoader.logo_tr_screen2, 0, 0, 800, 500);
			} else if (!gameWorld.timerAlpha.tasks[2].isFinished()) {
				batcher.draw(AssetLoader.logo_tr_screen3, 0, 0, 800, 500);
			} else if (!gameWorld.timerAlpha.tasks[3].isFinished()) {
				batcher.draw(AssetLoader.logo_tr_screen4, 0, 0, 800, 500);
			} else if (!gameWorld.timerAlpha.tasks[4].isFinished()) {
				batcher.draw(AssetLoader.logo_tr_screen3, 0, 0, 800, 500);
			} else if (!gameWorld.timerAlpha.tasks[5].isFinished()) {
				batcher.draw(AssetLoader.logo_tr_screen2, 0, 0, 800, 500);
			} else if (!gameWorld.timerAlpha.tasks[6].isFinished()) {
				batcher.draw(AssetLoader.logo_tr_screen1, 0, 0, 800, 500);
			} else if (!gameWorld.timerAlpha.tasks[7].isFinished()) {
				batcher.draw(AssetLoader.logo_tr_screen2, 0, 0, 800, 500);
			} else if (!gameWorld.timerAlpha.tasks[8].isFinished()) {
				batcher.draw(AssetLoader.logo_tr_screen3, 0, 0, 800, 500);
			} else if (!gameWorld.timerAlpha.tasks[9].isFinished()) {
				batcher.draw(AssetLoader.logo_tr_screen4, 0, 0, 800, 500);
			} else if (!gameWorld.timerAlpha.tasks[10].isFinished()) {
				batcher.draw(AssetLoader.logo_tr_screen3, 0, 0, 800, 500);
			} else if (!gameWorld.timerAlpha.tasks[11].isFinished()) {
				batcher.draw(AssetLoader.logo_tr_screen2, 0, 0, 800, 500);
			} else if (!gameWorld.timerAlpha.isDone()) {
				batcher.draw(AssetLoader.logo_tr_screen1, 0, 0, 800, 500);
			} 
		}

		batcher.end();
	}
	
	/**
	 * Sets up all the tasks for the logo timer
	 */
	private void setupLogoTimer(){
		Timer timer = gameWorld.timerAlpha;
		timer.initMilliseconds((long) 4500, "GameRenderer setupLogoTimer");
		
		timer.addTask(Timer.MILLISECONDS, 2000);
		timer.addTask(Timer.MILLISECONDS, 2100);
		timer.addTask(Timer.MILLISECONDS, 2200);
		timer.addTask(Timer.MILLISECONDS, 2300);
		timer.addTask(Timer.MILLISECONDS, 2400);
		timer.addTask(Timer.MILLISECONDS, 2500);
		timer.addTask(Timer.MILLISECONDS, 2600);
		timer.addTask(Timer.MILLISECONDS, 2700);
		timer.addTask(Timer.MILLISECONDS, 2800);
		timer.addTask(Timer.MILLISECONDS, 2900);
		timer.addTask(Timer.MILLISECONDS, 3000);
		timer.addTask(Timer.MILLISECONDS, 3100);
	}
	
	/*****************************************
	 * Logo Methods [END]
	 *****************************************/
	
}
