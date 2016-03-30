package com.mrhart.mode;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MetaMode extends Mode{

	public MetaMode(ModeBin modeBin, AssetManager assets) {
		super(modeBin, assets);
	}

	@Override
	public Class<? extends Mode> update(float delta) {
		return null;
	}

	@Override
	public void render(SpriteBatch batcher, float runtime) {
		
	}

	@Override
	public void finalize() {
		
	}

	@Override
	public ModeBin getNextModeBin() {
		return modeBin;
	}

}
