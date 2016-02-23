package com.mrhart.mode;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mrhart.assets.loaders.Loader_Mario;
import com.mrhart.state.GameState;
import com.test.Mario;

public class Mode_Test_Dot extends Mode {
	Mario mario;

	public Mode_Test_Dot() {
		super(GameState.TEST_DOT);
		Loader_Mario.loadMario(assets);
	}

	@Override
	public int update(float delta) {
		mario.update(delta);
		return 0;
	}

	@Override
	public void render(SpriteBatch batcher, float runtime) {
		mario.render(batcher, runtime);
		
	}

	@Override
	public void finalize() {
		mario = new Mario(Loader_Mario.getMario(assets));
	}

}
