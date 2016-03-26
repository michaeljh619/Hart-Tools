package com.mrhart.mode;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mrhart.backend.Debuggable;
import com.mrhart.backend.Permute;

public class Mode_Test_TSP extends Mode implements Debuggable {
	private static final int SHIFT_X = 50;
	private static final int SHIFT_Y = 50;
	private static final float SCALE = 20.0f;
	private static final int FILE_NUMBER = 1;
	private static final String FILE_NAME = "misc/TSP/" + FILE_NUMBER + ".txt";
	
	/*
	 * Instance Vars
	 */
	// Points
	private Vector2[] points;
	private int numPoints;
	// Permutations
	private ArrayList<Integer []> permutations = new ArrayList<Integer []>();
	// Best Solution
	private int[] bestOrder;
	private int bestOrderIndex;
	private float bestDist;
	// Permutations
	private int currentPermIndex = 0;
	// Stop
	private boolean done = false;

	public Mode_Test_TSP(ModeBin modeBin, AssetManager assets) {
		// Initialize Top Level Mode
		super(modeBin, assets);
		// Open File and read it
		FileHandle handle = Gdx.files.internal(FILE_NAME);
		String[] lines = handle.readString().split("\\r?\\n");
		// Initialize Points
		numPoints = lines.length;
		points = new Vector2[numPoints];
		// Create your array of vertices here
		for(int i = 0; i < numPoints; i++){
			// Get the coordinates from the line
			String[] coordinates = lines[i].split(" ");
			int x = ((int) SCALE*Integer.parseInt(coordinates[0])) + SHIFT_X;
			int y = ((int) SCALE*Integer.parseInt(coordinates[1])) + SHIFT_Y;
			points[i] = new Vector2(x, y);
		}
		// Initialize Best Solution
		bestOrder = new int[numPoints];
		for(int i = 0; i < numPoints; i++){
			bestOrder[i] = i;
		}
		bestDist = Float.MAX_VALUE;
		// Generate permutations
		Permute.permute(bestOrder, permutations);
		System.err.println("Number of permutations: " + permutations.size());
		// Debug
//		printPoints();
	}

	@Override
	public void debug(ShapeRenderer shapes) {
		for(int i = 0; i < bestOrder.length; i++){
			// If this is the last vertex
			if(i == (bestOrder.length - 1)){
				shapes.line(points[bestOrder[i]], points[bestOrder[0]]);
			}
			else{
				shapes.line(points[bestOrder[i]], points[bestOrder[i+1]]);
			}
		}
		for(int i = 0; i < bestOrder.length; i++){
			shapes.setColor(Color.GREEN);
			shapes.circle(points[bestOrder[i]].x, points[bestOrder[i]].y, 2);
		}
	}

	@Override
	public Class update(float delta) {
		// Calculates current permutation
		/*
		 * If statement: occurs every cycle
		 * While statement: computes all in one cycle
		 */
		while(currentPermIndex < permutations.size() && !done){
			// Get current order
			Integer[] currentOrder = permutations.get(currentPermIndex);
			// Get current TSP distance in this order
			float currentDist = 0.0f;
			for(int i = 0; i < currentOrder.length; i++){
				// If this is the last vertex
				if(i == (currentOrder.length - 1)){
					currentDist += points[currentOrder[i]].dst(points[currentOrder[0]]);
				}
				else{
					currentDist += points[currentOrder[i]].dst(points[currentOrder[i+1]]);
				}
			}
			// Decide if this is a better ordering
//			System.err.println("Best So Far: " + bestDist);
//			System.err.println("Current: " + currentDist);
			if(currentDist < bestDist){ // currentDist < bestDist
				bestDist = currentDist;
				bestOrderIndex = currentPermIndex;
				for(int i = 0; i < bestOrder.length; i++){
					bestOrder[i] = currentOrder[i];
				}
			}
			// Update for the next permutation
			currentPermIndex++;
		}
		// All permutations are finished, print a finished message
		if(!done && currentPermIndex >= permutations.size()){
			done = true;
			System.err.println("Analysis complete!");
			System.err.print("Best Order:");
			for(int i = 0; i < points.length; i++){
				Vector2 point = points[bestOrder[i]];
				System.err.print(" (" + ((int)((point.x - SHIFT_X)/SCALE)) 
						+ ", " + ((int)((point.y - SHIFT_Y)/SCALE)) + ")");
			}
			System.err.print("\n");
			System.err.println("Best Distance: " + bestDist/SCALE);
		}
		return null;
	}

	@Override
	public void render(SpriteBatch batcher, float runtime) {
	}

	@Override
	public void finalize() {
	}

	private void printPoints(){
		System.err.println("-- Total Points --");
		for(int i = 0; i < numPoints; i++){
			// Get the coordinates from the line
			float x = points[i].x;
			float y = points[i].y;
			System.err.println("Point " + i + ": (" + x + ", " + y + ")");
		}
		
	}

	@Override
	public ModeBin getNextModeBin() {
		// TODO Auto-generated method stub
		return null;
	}
}
