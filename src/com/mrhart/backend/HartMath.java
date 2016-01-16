package com.mrhart.backend;

import com.badlogic.gdx.math.Vector2;

// TODO: Documentation
public class HartMath {
	
	public static int roundDownToMultiple(int number, int multiple){
		// Error Check
		if(multiple <= 0){
			System.err.println(Messages.WARNING + Messages.TYPE_BAD_VALUE 
					+ "value 'multiple' is <= 0! May get unexpected results!");
		}
		
		// Guarantee positivity
		int positiveNumber = number;
		boolean wasChanged = false;
		if(positiveNumber < 0){
			positiveNumber = -positiveNumber;
			wasChanged = true;
		}
		// Guarantee positivity
		int positiveMultiple = multiple;
		if(positiveMultiple < 0)
			positiveMultiple = -positiveMultiple;
		// Find the difference and return the rounded down multiple.
		int mod = positiveNumber % positiveMultiple;
		if(wasChanged)
			mod = positiveMultiple - mod;
		
		return number - mod;
	}
	public static int roundUpToMultiple(int number, int multiple){
		return roundDownToMultiple(number, multiple) + multiple;
	}
	
	public static int rowMajorIndex(Vector2 vector, int width){
		return (int) (vector.y*width + vector.x);
	}
	public static int rowMajorIndex(int x, int y, int width){
		return (int) (y*width + x);
	}
	public static int rowMajorIndexTo_Y(int rowMajorIndex, int width){
		return rowMajorIndex/width;
	}
	public static int rowMajorIndexTo_X(int rowMajorIndex, int width){
		return rowMajorIndex%width;
	}
}
