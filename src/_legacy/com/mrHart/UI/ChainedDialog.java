package com.mrHart.UI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Wrapper class for Dialog, allows to seamlessly stream through a group of dialogs.
 * 
 * @author Michael James Hart
 *
 */
public class ChainedDialog {
	// Instance Vars
	public Dialog[] array;
	private int currentDialogIndex = 0;
	public boolean isFinished = false;
	
	/**
	 * Creates a dialog array with the number of array that you specify.
	 * 
	 * @param numberOfDialogs Number of dialogs in the array
	 */
	public ChainedDialog(int numberOfDialogs){
		array = new Dialog[numberOfDialogs];
	}
	
	/**
	 * Updates the current dialog
	 */
	public void update(){
		if(currentDialogIndex < array.length){
			if(array[currentDialogIndex].isFinished){
				currentDialogIndex++;
			}
			else{
				array[currentDialogIndex].update();
			}
		}
		else{
			isFinished = true;
		}
	}
	
	/**
	 * Renders current dialog to the screen
	 * 
	 * @param batcher
	 * @param runtime
	 */
	public void render(SpriteBatch batcher, float runtime, int cameraPositionX){
		if(currentDialogIndex < array.length){
			array[currentDialogIndex].render(batcher, runtime, cameraPositionX);
		}
	}
}