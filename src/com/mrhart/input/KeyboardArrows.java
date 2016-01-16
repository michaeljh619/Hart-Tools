package com.mrhart.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.mrhart.enumerations.Directions;

public class KeyboardArrows implements Directionable{
	@Override
	public int getDirections4() {
		if(Gdx.input.isKeyPressed(Keys.UP))
			return Directions.UP;
		else if(Gdx.input.isKeyPressed(Keys.RIGHT))
			return Directions.RIGHT;
		else if(Gdx.input.isKeyPressed(Keys.DOWN))
			return Directions.DOWN;
		else if (Gdx.input.isKeyPressed(Keys.LEFT))
			return Directions.LEFT;
		else
			return Directions.NULL;
	}

	@Override
	public int getDirections8() {
		if(Gdx.input.isKeyPressed(Keys.UP)
				&& Gdx.input.isKeyPressed(Keys.RIGHT))
			return Directions.UP_RIGHT;
		else if(Gdx.input.isKeyPressed(Keys.DOWN)
				&& Gdx.input.isKeyPressed(Keys.RIGHT))
			return Directions.DOWN_RIGHT;
		else if(Gdx.input.isKeyPressed(Keys.DOWN)
				&& Gdx.input.isKeyPressed(Keys.LEFT))
			return Directions.DOWN_LEFT;
		else if(Gdx.input.isKeyPressed(Keys.UP)
				&& Gdx.input.isKeyPressed(Keys.LEFT))
			return Directions.UP_LEFT;
		else if(Gdx.input.isKeyPressed(Keys.UP))
			return Directions.UP;
		else if(Gdx.input.isKeyPressed(Keys.RIGHT))
			return Directions.RIGHT;
		else if(Gdx.input.isKeyPressed(Keys.DOWN))
			return Directions.DOWN;
		else if(Gdx.input.isKeyPressed(Keys.LEFT))
			return Directions.LEFT;
		else
			return Directions.NULL;
	}

}
