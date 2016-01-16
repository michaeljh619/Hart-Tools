package com.mrhart.state;

/**
 * A class for representing states.
 * 
 * @author Michael Hart, MrHartGames@yahoo.com
 * @version v1.00
 */
public abstract class AbstractState<Type> {
	/*
	 * Instance Vars
	 */
	// ID
	protected int ID;
	// Object for updating
	protected Type object;
	
	/**
	 * Specifies the ID for this state
	 * 
	 * @param ID
	 */
	public AbstractState(Type object, int ID){
		this.ID = ID;
		this.object = object;
	}
	
	public AbstractState(Type object){
		this(object, -1);
	}
	
	/**
	 * This function is called when entering this state.
	 */
	public abstract void performEnterAction();
	
	/**
	 * Update Method
	 */
	public abstract void update();
	
	/**
	 * This function is called when exitting this state.
	 */
	public abstract void performExitAction();
	
	/**
	 * Gets the ID for this state
	 * 
	 * @return ID
	 */
	public int getID(){
		return ID;
	}
}
