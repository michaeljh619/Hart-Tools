package com.mrhart;

import com.mrhart.backend.Timer;
import com.mrhart.mode.Mode;
import com.mrhart.mode.concrete.Mode_Logo;
import com.mrhart.mode.concrete.Mode_Test_Input;
import com.mrhart.mode.concrete.Mode_Test_Selection;

/**
 * Initializes all the necessary classes and static variables to get the game
 * going.
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.00
 */
public class Initializer {
	// Change this to change your starting Mode
	public static Class<? extends Mode> STARTING_MODE = Mode_Logo.class;
	
	public static void initialize(){
		Timer.initialize();
	}
}
