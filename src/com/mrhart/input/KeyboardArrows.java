package com.mrhart.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.mrhart.enumerations.Direction;

public class KeyboardArrows implements Directionable{
	@Override
	public Direction getDirections4() {
		if(Gdx.input.isKeyPressed(Keys.UP))
			return Direction.UP;
		else if(Gdx.input.isKeyPressed(Keys.RIGHT))
			return Direction.RIGHT;
		else if(Gdx.input.isKeyPressed(Keys.DOWN))
			return Direction.DOWN;
		else if (Gdx.input.isKeyPressed(Keys.LEFT))
			return Direction.LEFT;
		else
			return Direction.NULL;
	}

	@Override
	public Direction getDirections8() {
		if(Gdx.input.isKeyPressed(Keys.UP)
				&& Gdx.input.isKeyPressed(Keys.RIGHT))
			return Direction.UP_RIGHT;
		else if(Gdx.input.isKeyPressed(Keys.DOWN)
				&& Gdx.input.isKeyPressed(Keys.RIGHT))
			return Direction.DOWN_RIGHT;
		else if(Gdx.input.isKeyPressed(Keys.DOWN)
				&& Gdx.input.isKeyPressed(Keys.LEFT))
			return Direction.DOWN_LEFT;
		else if(Gdx.input.isKeyPressed(Keys.UP)
				&& Gdx.input.isKeyPressed(Keys.LEFT))
			return Direction.UP_LEFT;
		else if(Gdx.input.isKeyPressed(Keys.UP))
			return Direction.UP;
		else if(Gdx.input.isKeyPressed(Keys.RIGHT))
			return Direction.RIGHT;
		else if(Gdx.input.isKeyPressed(Keys.DOWN))
			return Direction.DOWN;
		else if(Gdx.input.isKeyPressed(Keys.LEFT))
			return Direction.LEFT;
		else
			return Direction.NULL;
	}

}
