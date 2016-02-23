package com.mrhart.structures;

import java.util.ArrayList;
import java.util.Collections;

public class SortedArrayList<T extends Comparable<T>> extends ArrayList<T>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public boolean add(T t){
		int index = binarySearch(t);
		index = linearSearch(index, t);
		super.add(index, t);
		return true;
	}
	
	public void add(int index, T t){
		System.err.println("This method violates the SortedArrayList sorted insertion, "
				+ "use add(T t) instead.");
	}
	
	public void sort(){
		Collections.sort(this);
	}
	
	/**
	 * Performs a binary search and finds an index that may be 1 off of the insert index.
	 * Use linear search afterwards with the returned index to get the correct insertion index.
	 * If an element in the ArrayList is found to be exactly equal to this element, it will not
	 * be inserted and the index that will be returned is -1.
	 * 
	 * @return An index that is 1 off of the actual insertion index.
	 */
	private int binarySearch(T other){
		if(super.size() == 0){
			return 0;
		}
		
		int index = super.size()/2;
		int jumpDistance = super.size()/4;
		int comparison;
		while(jumpDistance > 1){
			comparison = other.compareTo(super.get(index));
			if(comparison == 1){
				index += jumpDistance;
			}
			else if(comparison == -1){
				index -= jumpDistance;
			}
			else{
				return index;
			}
			jumpDistance /= 2;
		}
		return index;
	}
	
	private int linearSearch(int startIndex, T t){
		int comparison;
		int nextComparison;
		if(super.size() == 0)
			return 0;
		while(true){
			comparison = t.compareTo(super.get(startIndex));
			// Less than
			if(comparison == -1){
				// Don't fall off the edge!
				if(startIndex == 0)
					return 0;
				// Check if it should go in between
				nextComparison = t.compareTo(super.get(startIndex - 1));
				if(nextComparison >= 0){
					return startIndex;
				}
				else{
					startIndex--;
				}
			}
			else if(comparison == 1){
				// Don't fall off the edge!
				if(startIndex == (super.size() - 1))
					return super.size();
				// Check if it should go in between
				nextComparison = t.compareTo(super.get(startIndex + 1));
				if(nextComparison <= 0){
					return startIndex + 1;
				}
				else{
					startIndex++;
				}
			}
			else{
				return startIndex;
			}
		}
	}
	
	public static void main(String[] args){
		SortedArrayList<Integer> list = new SortedArrayList<Integer>();
		list.add(5);
		System.err.println(list);
		list.add(0);
		System.err.println(list);
		list.add(3);
		System.err.println(list);
		list.add(7);
		System.err.println(list);
		list.add(1);
		System.err.println(list);
		list.add(20);
		System.err.println(list);
		list.add(4);
		System.err.println(list);
		list.add(-20);
		System.err.println(list);
		list.add(3);
		System.err.println(list);
		list.add(20);
		System.err.println(list);
	}
}
