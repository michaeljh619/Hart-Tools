package com.mrhart.state;

@SuppressWarnings("rawtypes")
/**
 * Handles updates and transitions of states
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.00
 *
 * @param <Type>
 */
public abstract class AbstractStateHandler<Type> {
	/*
	 * Instance Vars
	 */
	// States
	protected AbstractState[] states;
	public AbstractState currentState;
	public AbstractState lastState;
	
	public AbstractStateHandler(AbstractState[] states, int startState){
		// Initialize Data Structure
		this.states = states;
		// Set current state to start state
		currentState = states[startState];
	}
	
	public void update(Type o){
		// Get the last state
		lastState = currentState;
		
		// Implemented by child, decides what the next state is
		currentState = transition(o);
		
		// Perform Mealy actions
		if(lastState != currentState){
			lastState.performExitAction();
			currentState.performEnterAction();
		}
		
		// Update current state
		currentState.update();
	}
	
	public abstract AbstractState transition(Type o);
}
