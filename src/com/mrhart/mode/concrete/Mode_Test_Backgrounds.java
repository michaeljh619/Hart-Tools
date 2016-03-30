package com.mrhart.mode.concrete;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mrhart.assets.concrete.Loader_Sprite_Backgrounds;
import com.mrhart.backend.CameraDimensions;
import com.mrhart.backgrounds.Background;
import com.mrhart.backgrounds.BoundedBackground;
import com.mrhart.backgrounds.UnboundedBackground;
import com.mrhart.mode.Mode;
import com.mrhart.mode.ModeBin;
import com.mrhart.renderable.RenderableTextureRegion;
import com.mrhart.settings.Settings;

public class Mode_Test_Backgrounds extends Mode {
	/*
	 * Named Constants
	 */
	private static final int U_BACKGROUND = 0;
	private static final int V_BACKGROUND = 1;
	// Change this type to render different backgrounds
	private static int TYPE = V_BACKGROUND;
	
	/*
	 * Instance Vars
	 */
	// Backgrounds
	private UnboundedBackground uBackground;
	private BoundedBackground vBackground;
	// Renderable
	private RenderableTextureRegion[][]regions;
	private RenderableTextureRegion region;
	// Camera
	private CameraDimensions cameraDimensions;
	private OrthographicCamera camera;

	public Mode_Test_Backgrounds(ModeBin modeBin, AssetManager assets) {
		// Super Constructor
		super(modeBin, assets);
		
		// Camera
		this.camera = modeBin.camera;
		cameraDimensions = new CameraDimensions(camera);
	}

	@Override
	public Class<? extends Mode> update(float delta) {
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			camera.translate(-5, 0);
		}
		else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			camera.translate(5, 0);
		}
		
		if(Gdx.input.isKeyPressed(Keys.UP)){
			camera.translate(0, -5);
		}
		else if(Gdx.input.isKeyPressed(Keys.DOWN)){
			camera.translate(0, 5);
		}

		camera.update();
		cameraDimensions.update(camera);
		
		
		return null;
	}

	@Override
	public void render(SpriteBatch batcher, float runtime) {
		if(TYPE == U_BACKGROUND)
			uBackground.render(batcher, runtime);
		else
			vBackground.render(batcher, runtime);
	}

	@Override
	public void finalize() {
		// Vanishable Background
		regions = new RenderableTextureRegion[2][2];
		for(int x = 0; x < regions.length; x++){
			for(int y = 0; y < regions[0].length;y++){
				regions[x][y] = new RenderableTextureRegion(
						Loader_Sprite_Backgrounds.get_Bright1(assets));
			}
		}
		vBackground = new BoundedBackground(regions, 0, 0, 
				Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT, 
				cameraDimensions);
		
		// Unbounded Background
		region = new RenderableTextureRegion(
					Loader_Sprite_Backgrounds.get_Bright1(assets));
		uBackground = new UnboundedBackground(new Background(region), 
				cameraDimensions, UnboundedBackground.X_UNBOUNDED);
	}

	public ModeBin getNextModeBin(){
		return modeBin;
	}

	@Override
	public void loadAssets() {
		// Load assets
		Loader_Sprite_Backgrounds.load_Bright1(assets);
	}
}
