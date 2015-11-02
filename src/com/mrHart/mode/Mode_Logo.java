package com.mrhart.mode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mrhart.assets.AssetLoader;
import com.mrhart.assets.EffectsLoader;
import com.mrhart.spacegame.SpaceGame;
import com.mrhart.spacegame.world.GameWorld;
import com.mrhart.state.GameState;
import com.mrhart.tools.Timer;

/**
 * This Mode is in charge of rendering and updating the Mr. Hart logo scene.
 *
 * @author Michael James Hart, mrhartgames@yahoo.com
 * @version v2.00
 * @since 11/01/2015
 */
public class Mode_Logo extends Mode {
	/*
	 * Named Constants
	 */
	private static final int FADE_TIME = 1000;
	private static final int UNDERLINE_TIME = 1500;
	private static final int SIGN_TIME = 2000;
	private static final int HEART_TIME = 1000;
	
	/*
	 * Instance Vars
	 */
	// Timers
	private Timer fadeTimer;
	private Timer signTimer;
	private Timer underlineTimer;
	private Timer heartTimer;
	// States
	private boolean isFadeFinished = false;
	private boolean isSignFinished = false;
	private boolean isUnderlineFinished = false;
	private boolean isHeartFinished = false;
	private boolean isReadyToFinish = false;
	private boolean isFadeRuntimeSetup = false;
	private boolean isSignRuntimeSetup = false;
	private boolean isUnderlineRuntimeSetup = false;
	// Runtime Floats
	private float runtime_fade;
	private float runtime_sign;
	private float runtime_underline;
	
	public Mode_Logo(){
		// Load the logo assets
		AssetLoader.loadLogo();
		// Load the fade effect
		EffectsLoader.loadFade();
		// Setup the State ID
		stateID = GameState.STATE_LOGO;
		/*
		 *  Setup timers
		 */
		// Signature Timers
		heartTimer = new Timer();
		signTimer = new Timer();
		underlineTimer = new Timer();
		// Fade Timers
		fadeTimer = new Timer();
		fadeTimer.initMilliseconds(FADE_TIME);
	}

	@Override
	public int update(float delta) {
		// First start checking the fade timer before doing the sign timers
		if(!isFadeFinished && fadeTimer.isDone()){
			signTimer.initMilliseconds(SIGN_TIME);
			isFadeFinished = true;
			AssetLoader.logo_signSound.play(AssetLoader.logo_signSoundVolume * GameWorld.volume);
		}
		
		// Now check the sign timer
		if(signTimer.isDone()){
			underlineTimer.initMilliseconds(UNDERLINE_TIME);
			AssetLoader.logo_underlineSound.play(AssetLoader.logo_underlineSoundVolume * GameWorld.volume);
			isSignFinished = true;
		}
		
		// Now check the underline timer
		if(underlineTimer.isDone()){
			heartTimer.initMilliseconds(HEART_TIME);
			AssetLoader.logo_heartSound.play(AssetLoader.logo_heartSoundVolume * GameWorld.volume);
			isUnderlineFinished = true;
		}
		
		// Now check the heart timer
		if(heartTimer.isDone()){
			fadeTimer.reset();
			fadeTimer.initMilliseconds(FADE_TIME);
			isFadeRuntimeSetup = false;
			isReadyToFinish = true;
			isHeartFinished = true;
		}
		
		// Now check the underline timer
		if(isReadyToFinish && fadeTimer.isDone()){
			return GameState.STATE_MENU;
		}
		else{
			return GameState.STATE_NULL;
		}
	}

	@Override
	public void render(SpriteBatch batcher, float runtime) {
		// Render the logo to the screen
		if(!isFadeFinished){
			batcher.draw(AssetLoader.logo_sign.getKeyFrames()[AssetLoader.logo_sign.getKeyFrames().length - 1], 
					0, 0, SpaceGame.SCREEN_WIDTH, SpaceGame.SCREEN_HEIGHT);
			batcher.draw(EffectsLoader.fadeIn.getKeyFrame(getFadeRuntime(runtime)), 0, 0,
					SpaceGame.SCREEN_WIDTH, SpaceGame.SCREEN_HEIGHT);
		}
		else if(!isSignFinished){
			batcher.draw(AssetLoader.logo_sign.getKeyFrame(getSignRuntime(runtime)),
					0, 0);
		}
		else if(!isUnderlineFinished){
			batcher.draw(AssetLoader.logo_underline.getKeyFrame(getUnderlineRuntime(runtime)),
					0, 0);
		}
		else if(!isHeartFinished){
			batcher.draw(AssetLoader.logo_signature, 0, 0);
		}
		else{
			batcher.draw(AssetLoader.logo_signature, 0, 0);
			batcher.draw(EffectsLoader.fadeOut.getKeyFrame(getFadeRuntime(runtime)), 0, 0,
					SpaceGame.SCREEN_WIDTH, SpaceGame.SCREEN_HEIGHT);
		}
	}
	
	private float getFadeRuntime(float runtime){
		if(!isFadeRuntimeSetup){
			isFadeRuntimeSetup = true;
			runtime_fade = runtime;
		}
		
		return runtime - runtime_fade;
	}
	
	private float getSignRuntime(float runtime){
		if(!isSignRuntimeSetup){
			isSignRuntimeSetup = true;
			runtime_sign = runtime;
		}
		
		return runtime - runtime_sign;
	}

	private float getUnderlineRuntime(float runtime){
		if(!isUnderlineRuntimeSetup){
			isUnderlineRuntimeSetup = true;
			runtime_underline = runtime;
		}
		
		return runtime - runtime_underline;
	}

}
