package com.mrhart.backgrounds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mrhart.backend.CameraDimensions;
import com.mrhart.renderable.RenderableObject;

public class BoundedBackground{
	/*
	 * Instance Vars
	 */
	// Finite Background
	protected Background[][] backgrounds;
	protected Rectangle[][] backgroundRects;
	protected Rectangle cameraRect;
	// Infinite Background
	private CameraDimensions cameraDimensions;
	
	
	
	
	/*************************************************************************
	 * 							Constructors
	 *************************************************************************/
	public BoundedBackground(RenderableObject[][] backgrounds, 
			int topLeftX, int topLeftY, int width, int height,
			CameraDimensions cameraDimensions){
		
		// Backgrounds
		this.backgrounds = new Background[backgrounds.length][backgrounds[0].length];
		backgroundRects = new Rectangle[backgrounds.length][backgrounds[0].length];
		for(int x = 0; x < this.backgrounds.length; x++){
			for(int y = 0; y < this.backgrounds[0].length; y++){
				this.backgrounds[x][y] 
						= new Background(
								x*width, y*height, 
								width, height, 
								backgrounds[x][y]);
				backgroundRects[x][y] 
						= new Rectangle(x*width, y*height, 
								width, height);
			}
		}
		
		// Camera
		this.cameraDimensions = cameraDimensions;
		cameraRect = new Rectangle();
		setCameraRect();
	}
	
	
	
	/*************************************************************************
	 * 							Main Methods
	 *************************************************************************/
	public void render(SpriteBatch batcher, float runtime){
		setCameraRect();
		for(int x = 0; x < backgrounds.length; x++){
			for(int y = 0; y < backgrounds[0].length; y++){
				if(cameraRect.overlaps(backgroundRects[x][y])){
					backgrounds[x][y].render(batcher, runtime);
				}
			}
		}
	}
	
	
	
	/*************************************************************************
	 * 							Helper Methods
	 *************************************************************************/
	private void setCameraRect(){
		cameraRect.setWidth(cameraDimensions.getWidth());
		cameraRect.setHeight(cameraDimensions.getHeight());
		cameraRect.setCenter(cameraDimensions.getOrigin());
	}
}
