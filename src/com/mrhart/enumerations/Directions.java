package com.mrhart.enumerations;

public class Directions {
	public static final int NULL = 0;
	public static final int UP = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 3;
	public static final int LEFT = 4;
	public static final int UP_RIGHT = 5;
	public static final int DOWN_RIGHT = 6;
	public static final int DOWN_LEFT = 7;
	public static final int UP_LEFT = 8;
	
	public static int getUnitX(int direction){
		switch(direction){
		case UP_RIGHT:
		case RIGHT:
		case DOWN_RIGHT:
			return 1;
		case UP_LEFT:
		case LEFT:
		case DOWN_LEFT:
			return -1;
		default:
			return 0;
		}
	}
	public static int getUnitY(int direction){
		switch(direction){
		case DOWN_LEFT:
		case DOWN:
		case DOWN_RIGHT:
			return 1;
		case UP_LEFT:
		case UP:
		case UP_RIGHT:
			return -1;
		default:
			return 0;
		}
	}
}
