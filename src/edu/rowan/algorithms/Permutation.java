package edu.rowan.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author nabreu
 */
public class Permutation {

    private ArrayList<Integer> cities;
    private ArrayList<List<Integer>> permutations;

    public Permutation(ArrayList<Integer> cities) {
        permutations = new ArrayList<List<Integer>>();
        permutations.add(cities);
        this.cities = cities;
    }

    public void generate() {
        
        for (int i = 0; i < cities.size(); i++){
            
        }
    }//end of generate()
    
    public double getFactorial(int n) {
        double fact = 1;
        for (int i = 1; i <= n; i++) {
            fact = fact * i;
        }
        return fact;
    }
    
    public void swap(int j, int k){
        Integer tmp = cities.get(j);
        cities.set(j, cities.get(k));
        cities.set(k, tmp);
    }
    
}
