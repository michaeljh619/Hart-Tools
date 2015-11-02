package com.mrHart.Depth;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DepthRenderer {
	// Instance Variables
	private DepthRenderable[] objects;
	private ArrayList<DepthRenderable> arrayList;
	private int maxObjects;
	
	/**
	 * Creates a new Depth Object holder that will hold a maximum of depth renderable
	 * objects.
	 * 
	 * @param max The max amount of objects that can be held
	 */
	public DepthRenderer(int max, ArrayList<DepthRenderable> inArrayList){
		maxObjects = max;
		objects = new DepthRenderable[maxObjects];
		arrayList = inArrayList;
	}
	
	public void render(SpriteBatch batcher, float runtime){
		for(int x = 0; x < maxObjects; x++){
			if(x >= arrayList.size()){
				objects[x] = null;
			}
			else{
				objects[x] = arrayList.get(x);
			}
		}
		
		// Sort the arraylist before rendering
		Collections.sort(arrayList);
		
		for(int x = 0; x < arrayList.size(); x++){
			arrayList.get(x).render(batcher, runtime);
		}
	}
}
