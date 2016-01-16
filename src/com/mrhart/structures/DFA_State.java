package com.mrhart.structures;

public abstract class DFA_State<T> {
	/*
	 * Instance Vars
	 */
	protected int ID;
	
	public DFA_State(int ID){
		this.ID = ID;
	}
	
	/**
	 * Returns an index that belongs to a state in the DFA, this index
	 * is the next state that we will transition to when symbol T is read.
	 * 
	 * @param symbol
	 * @return
	 */
	public abstract int update(T symbol);
	
	public int getID(){
		return ID;
	}
}
