package com.mrhart.mode;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class ModeBin {
	protected OrthographicCamera camera;
	protected Class lastMode;
	
	public ModeBin(OrthographicCamera camera){
		this.camera = camera;
	}
	
	/**
	 * This method is used in GameWorld, any calls to this method
	 * will be overwritten when GameWorld creates the next Mode.
	 * 
	 * @param lastMode
	 */
	public void setLastMode(Class lastMode){
		this.lastMode = lastMode;
	}
	
	public Class getLastMode(){
		return lastMode;
	}
}
