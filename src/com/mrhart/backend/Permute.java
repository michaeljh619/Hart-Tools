package com.mrhart.backend;

import java.util.ArrayList;
import java.util.List;

public class Permute{
    private static void recPermute(List<Integer> arr, int k, ArrayList<Integer []> builtList){
        for(int i = k; i < arr.size(); i++){
            java.util.Collections.swap(arr, i, k);
            recPermute(arr, k+1, builtList);
            java.util.Collections.swap(arr, k, i);
        }
        if (k == arr.size() -1){
        	Integer [] newPerm = new Integer[arr.size()];
        	builtList.add(arr.toArray(newPerm));
        }
    }
    public static void permute(int[] numbers, ArrayList<Integer []> builtList){
    	List<Integer> intList = new ArrayList<Integer>();
        for (int index = 0; index < numbers.length; index++)
        {
            intList.add(numbers[index]);
        }
        recPermute(intList, 0, builtList);
    }
}
