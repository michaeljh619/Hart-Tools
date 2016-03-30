package com.mrhart.mode;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mrhart.assets.concrete.Loader_Meta;

/**
 * MetaMode provides a way to load a regular Mode while still having a Mode
 * that is running. Whenever assets need to be loaded, the MetaMode will be
 * entered and the current Mode will be suspended. The MetaMode will run just
 * like a regular Mode, with the exception that it must load the assets
 * AssetManager and returning true in its update function says that it is done
 * loading assets and can proceed to the next Mode.
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.00
 *
 */
public class MetaMode{
	/*
	 * Named Constants
	 */
	// Loading Icon
	private static final int LOAD_ICON_X = 700;
	private static final int LOAD_ICON_Y = 400;
	private static final int LOAD_ICON_WIDTH = 50;
	private static final int LOAD_ICON_HEIGHT = 50;
	// Load Time
	private static final int LOAD_TIME = 150;
	
	/*
	 * Instance Vars
	 */
	// Mode
	public ModeBin modeBin;
	// Assets
	public AssetManager assets, metaAssets;
	private int loadTime;
	// Graphics
	protected Animation loadingIcon;

	/**
	 * Just like a regular mode's constructor, except we also get the metaAsset
	 * manager, since we use that one to load graphics for the MetaMode. Note
	 * that we also hold the modeBin to the nextMode as well, this can come in
	 * handy for knowing what Mode you came from or resetting cameras, etc.
	 * 
	 * @param modeBin
	 * @param assets
	 * @param metaAssets
	 */
	public MetaMode(ModeBin modeBin, AssetManager assets, AssetManager metaAssets) {
		this.modeBin = modeBin;
		this.assets = assets;
		this.metaAssets = metaAssets;
		loadTime = LOAD_TIME;
	}
	
	public void loadAssets(){
    	Loader_Meta.loadIcon(metaAssets);
	}
	
	public void finalize(){
		loadingIcon = Loader_Meta.getIcon(metaAssets);
	}

	/**
	 * Update works just like in any other mode, except now, returning true
	 * will transition to the next Mode and returning false will stay in the
	 * meta Mode. If you return true, you are telling GameWorld that you are
	 * finished loading all the assets!
	 * 
	 * @param delta
	 * @return
	 */
	public boolean update(float delta) {
		// Load little block of assets
		assets.update(loadTime);
		
		if(assets.getProgress() < 1.0f)
			return false;
		else{
			return true;
		}
	}

	public void render(SpriteBatch batcher, float runtime) {
		batcher.draw(loadingIcon.getKeyFrame(runtime), 
				LOAD_ICON_X, LOAD_ICON_Y, LOAD_ICON_WIDTH, LOAD_ICON_HEIGHT);
	}
}
