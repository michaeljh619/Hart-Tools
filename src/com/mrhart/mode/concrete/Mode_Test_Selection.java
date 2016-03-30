package com.mrhart.mode.concrete;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mrhart.assets.concrete.Loader_Sprite_Backgrounds;
import com.mrhart.input.KeyboardArrows;
import com.mrhart.mode.Mode;
import com.mrhart.mode.ModeBin;
import com.mrhart.renderable.RenderableObject;
import com.mrhart.renderable.RenderableTextureRegion;
import com.mrhart.settings.Settings;
import com.mrhart.sprites.Sprite;
import com.mrhart.ui.Selection;

public class Mode_Test_Selection extends Mode{
	/*
	 * Named Constants
	 */
	private static final int X = 6;
	private static final int Y = 4;
	
	/*
	 * Instance Vars
	 */
	private Selection selection;
	private KeyboardArrows keys;
	private Box[][] boxes;
	private int x, y;

	public Mode_Test_Selection(ModeBin modeBin, AssetManager assets) {
		super(modeBin, assets);
		keys = new KeyboardArrows();
		selection = new Selection(keys, X, Y, 0, 0);
		// Selection Settings
		selection.set4Directions();
		selection.setWrappable(false);
		selection.setJumpable(false);
		// Load assets
		Loader_Sprite_Backgrounds.load_Bright1(assets);
	}

	@Override
	public Class<? extends Mode> update(float delta) {
		selection.update();
		x = selection.getCurrentStateX();
		y = selection.getCurrentStateY();
		return null;
	}

	@Override
	public void render(SpriteBatch batcher, float runtime) {
		boxes[x][y].render(batcher, runtime);
	}

	@Override
	public void finalize() {
		boxes = new Box[X][Y];
		for(int x = 0; x < X; x++){
			for(int y = 0; y < Y; y++){
				boxes[x][y] = new Box(0, 0,
						Settings.SCREEN_WIDTH/X,
						Settings.SCREEN_HEIGHT/Y,
						new RenderableTextureRegion(
								Loader_Sprite_Backgrounds.get_Bright1(assets)));
				boxes[x][y].setRenderPosition(
						x*(Settings.SCREEN_WIDTH/X),
						y*(Settings.SCREEN_HEIGHT/Y));
			}
		}
	}

	private class Box extends Sprite{

		public Box(int positionX, int positionY, int width, int height,
				RenderableObject renderObject) {
			super(positionX, positionY, width, height, renderObject);
			// TODO Auto-generated constructor stub
		}
	}

	@Override
	public ModeBin getNextModeBin() {
		// TODO Auto-generated method stub
		return modeBin;
	}
}
