package com.mrhart.structures;

public abstract class DFA <T> {
	/*
	 * Named Constants
	 */
	
	/*
	 * Instance Vars
	 */
	// 
	private DFA_State<T>[] states;
	// Sigma
	private int currentState;
	private int[] finalStates;
	
	public DFA(DFA_State<T>[] states, int startStateIndex,
			int[] finalStates){
		this.states = states;
		this.currentState = startStateIndex;
		this.finalStates = finalStates;
	}
	
	/**
	 * Updates the DFA by reading the next symbol and then passing that symbol
	 * to the current state. That state's update (transition) function is then
	 * executed with that symbol and a new state is returned and we transition
	 * to that state.
	 * 
	 * @return
	 */
	public boolean update(){
		// Read next symbol
		T nextSymbol = readSymbol();
		// Update state
		currentState = states[currentState].update(nextSymbol);
		
		// Return true if in a Final state, false if not
		if(finalStates != null){
			for(int x = 0; x < finalStates.length; x++){
				if(currentState == finalStates[x])
					return true;
			}
		}
		return false;
	}
	
	public abstract T readSymbol();
	
	public int getCurrentState(){
		return currentState;
	}
}
