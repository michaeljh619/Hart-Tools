package com.mrhart.backend;

/**
 * A backend class used to calculate simple arithmetic on rotations of objects.
 * Some things to note:
 * 		- Rotations range from 0.0f to <360.0f  (360.0f == 0.0f)
 *		- Rotations start at zero from south pole (Y-axis pointing down) and rotate clockwise
 *
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v2.00
 */
public class Rotation {
	
	/**
	 * Gets the smallest distance between 2 degrees, degree distances can "wrap," meaning
	 * a 355.0f degree and a 5.0f degree will have a smallest distance of 10.0f.
	 *
	 * @version v1.00
	 * @since v2.00
	 * @param rotate1 The first degree
	 * @param rotate2 The second degree
	 * @return The smallest amount between the two degrees
	 */
	public static float getSmallestDistance(float rotate1, float rotate2){
		float dir1, dir2;
		dir1 = rotate1 - rotate2;
		dir2 = rotate2 - rotate1;
		
		if(dir1 < 0)
			dir1 += 360.0f;
		if(dir2 < 0)
			dir2 += 360.0f;
		
		if(dir1 > dir2)
			return dir2;
		else
			return dir1;
	}
		
	/**
	 * Gets the largest distance between 2 degrees, degree distances can "wrap," meaning
	 * a 100.0f degree and a 105.0f degree will have a largest distance of 355.0f.
	 *
	 * @version v1.00
	 * @since v2.00
	 * @param rotate1 The first degree
	 * @param rotate2 The second degree
	 * @return The largest amount between the two degrees
	 */
	public static float getLargestDistance(float rotate1, float rotate2){
		float dir1, dir2;
		dir1 = rotate1 - rotate2;
		dir2 = rotate2 - rotate1;
		
		if(dir1 < 0)
			dir1 += 360.0f;
		if(dir2 < 0)
			dir2 += 360.0f;
		
		if(dir1 < dir2)
			return dir2;
		else
			return dir1;
	}
	
	/**
	 * Adds two degrees together and returns the sum. If the degrees added surpasses 360.0f,
	 * then the degree will "wrap" and start from zero and count up again from there.
	 *
	 * @version v1.00
	 * @since v2.00
	 * @param degree1 First degree to add
	 * @param degree2 Second degree to add
	 * return Total sum of degrees
	 */
	public static float addDegrees(float degree1, float degree2){
		float returnDegrees;
		
		returnDegrees = degree1 + degree2;
		if(returnDegrees >= 360.0f){
			returnDegrees -= 360.0f;
		}
		else if(returnDegrees < 0.0f){
			returnDegrees += 360.0f;
		}
		
		return returnDegrees;
	}
	
	/**
	 * Checks to see if the desired degree is located clockwise to the calling degree.
	 * Moving clockwise increases in degrees.
	 * 
	 * @version v1.00
	 * @since v2.00
	 * @param callingDegree The reference degree
	 * @param desiredDegree The degree we will rotate towards
	 */
	public static boolean isRotationClockwise(float callingDegree, float desiredDegree){
		float subDegrees = callingDegree - desiredDegree;
		if(subDegrees >= 180.0f){
			return true;
		}
		else if(subDegrees >= 0.0f){
			return false;
		}
		else if(subDegrees >= -180.0f){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Checks to see if the desired degree is located counter clockwise to the calling degree.
	 * Moving counter clockwise decreases in degrees.
	 * 
	 * @version v1.00
	 * @since v2.00
	 * @param callingDegree The reference degree
	 * @param desiredDegree The degree we will rotate towards
	 */
	public static boolean isRotationCounterClockwise(float callingDegree, float desiredDegree){
		return !isRotationClockwise(callingDegree, desiredDegree);
	}
	
	/**
	 * A simple test to see if the class performs the arithmetic as expected
	 */
	public static void main(String [] args){
		float degree1 = 195.0f;
		float degree2 = 200.0f;
		
		System.out.println("Add Degrees " + degree1 + " and " + degree2 + " = " + addDegrees(degree1, degree2));
		System.out.println("Rotation distance between " + degree1 + " and " 
				+ degree2 + " = " + getSmallestDistance(degree1, degree2));
		System.out.println("Is " + degree2 + " located clockwise to " + degree1 + "? " + isRotationClockwise(degree1, degree2));
	}
}
