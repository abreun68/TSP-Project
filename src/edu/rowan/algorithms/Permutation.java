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


            generate();
        
    }

    public void generate() {
        int lastItemIndex = cities.size() - 1;
        
        for (int j = 0; j < cities.size(); j++) {
            
            for (int trail = lastItemIndex; trail >= 0; trail--) {
                System.out.println(cities.toString());
                int head = (trail - 1);
                if (head >= 0) {
                    if (cities.get(trail) > cities.get(head)) {
                        swap(trail, (head));
                        //sort(head);
                        
                        
                    }
                }
            }
        }
    }//end of generate()
    
    public void sort(int m) {
        //last item index
        int index = (cities.size() - 1);

        for (int i = m; i <= index; i++) {
            if (cities.get(i) < cities.get(i+1)) {
                swap(i, (i + 1));
            }
        }
    }//end of sort()
    
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
