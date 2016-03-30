package com.mrhart.mode.concrete;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mrhart.assets.concrete.Loader_Effects;
import com.mrhart.assets.concrete.Loader_Logo;
import com.mrhart.backend.Timer;
import com.mrhart.mode.Mode;
import com.mrhart.mode.ModeBin;
import com.mrhart.settings.Settings;
import com.mrhart.settings.Settings_Timer;
import com.mrhart.world.GameWorld;

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
	// Times
	private static final int FADE_TIME = 1000;
	private static final int UNDERLINE_TIME = 1500;
	private static final int SIGN_TIME = 2000;
	private static final int HEART_TIME = 1000;
	private static final int GLOBAL_RESET_TIME = 5;
	// Volumes
	private static final float logo_signSoundVolume = 1.0f;
	private static final float logo_underlineSoundVolume = 1.0f;
	private static final float logo_heartSoundVolume = 0.7f;
	
	/*
	 * Instance Vars
	 */
	//Graphics
	private TextureRegion[] logo_signRegion;
	private TextureRegion[] logo_underlineRegion;
	private TextureRegion[] fadeRegion;
	private TextureRegion logo_signature;
	private Animation logo_sign, logo_underline;
	private Animation fadeIn, fadeOut;
	// Audio
	private Sound logo_signSound, logo_heartSound, logo_underlineSound;
	// Timers
	private Timer fadeTimer;
	private Timer signTimer;
	private Timer underlineTimer;
	private Timer heartTimer;
	private Timer globalResetTimer;
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
	
	public Mode_Logo(ModeBin modeBin, AssetManager assets){
		// Set up top level constructor
		super(modeBin, assets);
		
		/*
		 * Load assets
		 */
		// Load Logo Graphics
		Loader_Logo.loadLogo(assets);
		// Load Fade Graphics
		Loader_Effects.loadFade(assets);
		
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
		// Global Reset
		globalResetTimer = new Timer(Settings_Timer.TIMER_ID_SYSTEM);
		globalResetTimer.initMilliseconds(GLOBAL_RESET_TIME);
		Timer.freezeTimers(Settings_Timer.TIMER_ID_DEFAULT);
	}

	@Override
	public Class<? extends Mode> update(float delta) {
		// Wait for the global reset of loading assets to complete
		if(globalResetTimer.isDone()){
			Timer.unfreezeTimers(Settings_Timer.TIMER_ID_DEFAULT);
		}
		
		// First start checking the fade timer before doing the sign timers
		if(!isFadeFinished && fadeTimer.isDone()){
			signTimer.initMilliseconds(SIGN_TIME);
			isFadeFinished = true;
			logo_signSound.play(logo_signSoundVolume * GameWorld.volume);
		}
		
		// Now check the sign timer
		if(signTimer.isDone()){
			underlineTimer.initMilliseconds(UNDERLINE_TIME);
			logo_underlineSound.play(logo_underlineSoundVolume * GameWorld.volume);
			isSignFinished = true;
		}
		
		// Now check the underline timer
		if(underlineTimer.isDone()){
			heartTimer.initMilliseconds(HEART_TIME);
			logo_heartSound.play(logo_heartSoundVolume * GameWorld.volume);
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
			return Mode_Logo.class;
		}
		else{
			return null;
		}
	}

	@Override
	public void render(SpriteBatch batcher, float runtime) {
		// Render the logo to the screen after global reset is finished
		if(Timer.isTimerFrozen(Settings_Timer.TIMER_ID_DEFAULT)){
			// Do nothing, just want to wait till the global reset is finished
		}
		else if(!isFadeFinished){
			batcher.draw(logo_sign.getKeyFrames()[logo_sign.getKeyFrames().length - 1], 
					0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
			batcher.draw(fadeIn.getKeyFrame(getFadeRuntime(runtime)), 0, 0,
					Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
		}
		else if(!isSignFinished){
			batcher.draw(logo_sign.getKeyFrame(getSignRuntime(runtime)),
					0, 0);
		}
		else if(!isUnderlineFinished){
			batcher.draw(logo_underline.getKeyFrame(getUnderlineRuntime(runtime)),
					0, 0);
		}
		else if(!isHeartFinished){
			batcher.draw(logo_signature, 0, 0);
		}
		else{
			batcher.draw(logo_signature, 0, 0);
			batcher.draw(fadeOut.getKeyFrame(getFadeRuntime(runtime)), 0, 0,
					Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
		}
	}
	
	public void finalize(){
		/*
		 *  TextureRegions
		 */
		// Logo
		logo_signRegion = Loader_Logo.getSignRegions(assets);
		logo_underlineRegion = Loader_Logo.getUnderlineRegions(assets);
		logo_signature = Loader_Logo.getSignatureRegion(assets);
		// Fade
		fadeRegion = Loader_Effects.getFadeRegions(assets);
		
		/*
		 *  Animations
		 */
		// Logo
		logo_sign = new Animation(0.02f, logo_signRegion);
		logo_underline = new Animation(0.03f, logo_underlineRegion);
		logo_sign.setPlayMode(PlayMode.REVERSED);
		logo_underline.setPlayMode(PlayMode.REVERSED);
		// Fade
		fadeIn = new Animation(Loader_Effects.FADE_STANDARD_SPEED, fadeRegion);
		fadeOut = new Animation(Loader_Effects.FADE_STANDARD_SPEED, fadeRegion);
		fadeIn.setPlayMode(PlayMode.NORMAL);
		fadeOut.setPlayMode(PlayMode.REVERSED);
		
		/*
		 *  Sounds
		 */
		logo_signSound = Loader_Logo.getSignSound(assets);
		logo_heartSound = Loader_Logo.getHeartSound(assets);
		logo_underlineSound = Loader_Logo.getUnderlineSound(assets);
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
	
	@Override
	public ModeBin getNextModeBin() {
		// TODO Auto-generated method stub
		return modeBin;
	}

}
