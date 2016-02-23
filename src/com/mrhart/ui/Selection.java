package com.mrhart.ui;

import com.badlogic.gdx.math.Vector2;
import com.mrhart.backend.HartMath;
import com.mrhart.enumerations.Directions;
import com.mrhart.input.Directionable;
import com.mrhart.input.Joystick;
import com.mrhart.settings.Settings_Timer;
import com.mrhart.structures.DFA;
import com.mrhart.structures.DFA_State;
import com.mrhart.tools.Timer;

// TODO: Testing & Documentation
public class Selection {
	/*
	 * Named Constants
	 */
	// Defaults
	private static final int HOLD_WAIT = 500;
	private static final int HOLD_INTERVAL = 50;
	
	/*
	 * Instance Vars
	 */
	// State
	private DFA_Sel dfa;
	private boolean[][] nodes;
	// Possible Inputs
	private Directionable input;

	/*
	 *  Settings
	 */
	// Jumpable: Whether directions can jump null gaps
	private boolean isJumpable = false;
	// Wrappable: Whether falling off the end of the array wraps around
	private boolean isWrappable = true;
	// Holdable: When holding the input in a direction, whether the selection
	// 			 should keep scrolling.
	private boolean isHoldable = true;
	private boolean justPressed = false;
	private Timer holdWaitTimer = new Timer(Settings_Timer.TIMER_ID_SYSTEM);
	private Timer holdIntervalTimer = new Timer(Settings_Timer.TIMER_ID_SYSTEM);
	private int holdWait = HOLD_WAIT;
	private int holdInterval = HOLD_INTERVAL;
	private boolean holdWaitFinished = false;
	// Directions4: If true, use 4 directions, else use 8 directions.
	private boolean directions4 = true;
	
	public Selection(Directionable input, boolean[][] nodes,
			int startIndexX, int startIndexY){
		this.input = input;
		this.nodes = nodes;
		
		DFA_Sel_State[] states = generateStates(nodes);
		dfa = new DFA_Sel(states, 
				HartMath.rowMajorIndex(
						new Vector2(startIndexX, startIndexY), nodes.length), 
				null);
	}
	public Selection(Directionable input, int xSelection, int ySelection,
			int startIndexX, int startIndexY){
		this(input, createSelection(xSelection, ySelection), 
				startIndexY, startIndexY);
	}
	private static boolean[][] createSelection(int xSelection, int ySelection){
		boolean[][] tempNodes = new boolean[xSelection][ySelection];
		for(int x = 0; x < xSelection; x++){
			for(int y = 0; y < ySelection; y++){
				tempNodes[x][y] = true;
			}
		}
		return tempNodes;
	}
	private DFA_Sel_State[] generateStates(boolean[][] nodes){
		// Create Objects
		DFA_Sel_State[] tempStates = new DFA_Sel_State[nodes.length*nodes[0].length];
		int RMI;
		// Create the nodes
		for(int x = 0; x < nodes.length; x++){
			for(int y = 0; y < nodes[0].length; y++){
				if(nodes[x][y] == true){
					RMI = HartMath.rowMajorIndex(x, y, nodes.length);
					tempStates[RMI]= new DFA_Sel_State(RMI, nodes.length, nodes[0].length, nodes);
				}
			}
		}
		
		return tempStates;
	}
	
	public void update(){
		dfa.update();
	}
	public int getCurrentStateX(){
		return HartMath.rowMajorIndexTo_X(dfa.getCurrentState(), nodes.length);
	}
	public int getCurrentStateY(){
		return HartMath.rowMajorIndexTo_Y(dfa.getCurrentState(), nodes.length);
	}
	
	public boolean[][] getNodes(){
		return nodes;
	}
	public void setJumpable(boolean isJumpable){
		this.isJumpable = isJumpable;
	}
	public void setWrappable(boolean isWrappable){
		this.isWrappable = isWrappable;
	}
	public void setHoldable(int holdWait, int holdInterval){
		isHoldable = true;
		this.holdWait = holdWait;
		this.holdInterval = holdInterval;
	}
	public void setHoldable(boolean isHoldable){
		this.isHoldable = isHoldable;
	}
	public void set4Directions(){
		directions4 = true;
	}
	public void set8Directions(){
		directions4 = false;
	}
	
	/*************************************************************************
	 * 									DFA
	 *************************************************************************/
	private class DFA_Sel extends DFA<Integer>{

		public DFA_Sel(DFA_State<Integer>[] states, int startStateIndex,
				int[] finalStates) {
			super(states, startStateIndex, finalStates);
		}

		@Override
		public Integer readSymbol() {
			// Get the current direction
			int direction;
			if(directions4)
				direction = input.getDirections4();
			else
				direction = input.getDirections8();
			
			// Hold
			if(isHoldable && direction != Directions.NULL){
				if(!holdWaitFinished){
					if(!holdWaitTimer.isActive()){
						holdWaitTimer.initMilliseconds(holdWait);
					}
					else if(holdWaitTimer.isDone()){
						holdWaitFinished = true;
						holdIntervalTimer.initMilliseconds(holdInterval);
					}
					else{
						return Directions.NULL;
					}
				}
				else{
					if(holdIntervalTimer.isDone()){
						holdIntervalTimer.initMilliseconds(holdInterval);
					}
					else{
						return Directions.NULL;
					}
				}
			}
			else if(isHoldable && direction == Directions.NULL){
				holdWaitFinished = false;
				holdWaitTimer.reset();
				holdIntervalTimer.reset();
			}
			else if(!isHoldable && direction == Directions.NULL){
				justPressed = false;
			}
			else{
				if(justPressed)
					return Directions.NULL;
				else
					justPressed = true;
			}
			
			return direction;
		}
	}
	private class DFA_Sel_State extends DFA_State<Integer>{
		private int width, height;
		private boolean[][] nodes;
		
		public DFA_Sel_State(int ID, int width, int height, boolean[][] nodes){
			super(ID);
			this.width = width;
			this.height = height;
			this.nodes = nodes;
		}
		
		@Override
		public int update(Integer symbol) {
			int x, y;
			// No Input
			if(symbol == Directions.NULL){
				return this.ID;
			}
			// Calculate location
			x = HartMath.rowMajorIndexTo_X(ID, width);
			y = HartMath.rowMajorIndexTo_Y(ID, width);
			int incX = Directions.getUnitX(symbol);
			int incY = Directions.getUnitY(symbol);
			
			do{
				x += incX;
				y += incY;
				if(isWrappable){
					x = normalize(x, width);
					y = normalize(y, height);
				}
				else{
					if(x >= width || x < 0 || y >= height || y < 0)
						return this.ID;
				}
			}while(nodes[x][y] == false && isJumpable);
			
			if(!isJumpable && nodes[x][y] == false){
					return this.ID;
			}
			
			return HartMath.rowMajorIndex(x, y, width);
		}
		
		private int normalize(int pos, int length){
			if(pos >= length)
				return pos - length;
			else if(pos < 0)
				return pos + length;
			else
				return pos;
		}
	}

}
