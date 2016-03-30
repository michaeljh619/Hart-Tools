package com.mrhart.enumerations;

public enum Direction {
	// Enumeration
	UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT, NULL;
	
	public static int getUnitX(Direction d){
		switch(d){
		case UP_RIGHT: case RIGHT: case DOWN_RIGHT:
			return 1;
		case DOWN_LEFT: case LEFT: case UP_LEFT:
			return -1;
		default:
			return 0;
		}
	}
	public static int getUnitY(Direction d){
		switch(d){
		case DOWN_LEFT: case DOWN: case DOWN_RIGHT:
			return 1;
		case UP_LEFT: case UP: case UP_RIGHT:
			return -1;
		default:
			return 0;
		}
	}
}
