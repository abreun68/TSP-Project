package edu.rowan.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * This class takes a Tour object and tries by brute force to find out the 
 * shortest tour distance.
 * 
 * @author Nacer Abreu and Emmanuel Bonilla
 */
public class BruteForceSolver {

    private ArrayList<Integer> cities;
    private ArrayList<List<Integer>> permutations;
    private Tour tour;
    
    private ArrayList<Integer> shortestTour;
    private double shortestDistance;
    
    BruteForceSolver(Tour tour) {
        this.tour = tour;
        permutations = new ArrayList<List<Integer>>();
        this.cities = tour.getCities();
        permutations.add(cities);
        shortestTour = new ArrayList<Integer>();
        shortestDistance = 0.0;
        
        
    }

    public void generatePermutations() {
        int lastItemIndex = cities.size() - 1;
        //System.out.println(cities.toString() + " Dist.:" + getTotalDistance(cities));
        shortestDistance = getTotalDistance(cities);
        shortestTour.clear();
        shortestTour.addAll(cities);
        for (int j = 0; j < cities.size(); j++) {

            for (int trail = lastItemIndex; trail >= 0; trail--) {

                int head = (trail - 1);
                //System.out.println(head + " " + trail);
                if (head >= 0) {
                    if (cities.get(trail) > cities.get(head)) {
                        //System.out.println("Choosing head " + cities.get(head) + " is in slot " + head);
                        int lastSlot = lastItemIndex;
                        while (cities.get(head) > cities.get(lastSlot)) {
                            lastSlot--;
                        }
                        //System.out.println("Choosing lastSlot " + cities.get(lastSlot));
                        //System.out.println("Swapping " + cities.get(head) + " with " + cities.get(lastSlot));
                        swap(lastSlot, head);
                        //System.out.println("Calling Sort with " + head);
                        sort(head);
                        //System.out.println("lastItemIndex = " + lastItemIndex);
                        trail = lastItemIndex + 1;
                        // System.out.println(cities.toString() + " Dist.:" + getTotalDistance(cities));
                        if (shortestDistance > getTotalDistance(cities)) {
                            shortestDistance = getTotalDistance(cities);
                            shortestTour.clear();
                            shortestTour.addAll(cities);
                        }
                        
                    }
                }
            }
        }
    }//end of generatePermutations()

    public double getShortestDistance(){
        return shortestDistance;
    }
    
    public ArrayList<Integer> getShortestTour(){
        return shortestTour;
    }
    
    private void sort(int m) {
        //last item index
        int index = (cities.size() - 1);
        int n = m;
        //System.out.println("head = "+ m + "; index = " + index);
        for (int i = m + 1; i < index; i++) {
            for (int j = n + 1; j < index; j++) {
                if (cities.get(j) > cities.get(j + 1)) {
                    swap(j, (j + 1));
                }
            }
        }
    }//end of sort()

    private double getFactorial(int n) {
        double fact = 1;
        for (int i = 1; i <= n; i++) {
            fact = fact * i;
        }
        return fact;
    }//end of getFactorial()

    private void swap(int j, int k) {
        Integer tmp = cities.get(j);
        cities.set(j, cities.get(k));
        cities.set(k, tmp);
    }//end of swap()

    private double getTotalDistance(ArrayList<Integer> cities) {
        double total = 0.0;
        int x1, y1, x2, y2;

        for (int i = 0; i < cities.size(); i++) {
            x1 = tour.getXCoord(cities.get(i));
            y1 = tour.getYCoord(cities.get(i));

            if ((i + 1) < cities.size()) {
                x2 = tour.getXCoord(cities.get(i + 1));
                y2 = tour.getXCoord(cities.get(i + 1));
            } else {
                x2 = tour.getXCoord(cities.get(0));
                y2 = tour.getXCoord(cities.get(0));
            }
            int[] p = {x1, y1};
            int[] q = {x2, y2};

            total += getDistance(p, q);
        }// end for loop

        return total;
    }// end of getTotalDistance()
    
    
    private double getDistance(int[] p, int[] q) {
        double a = 0.0;
        // dist((x, y), (a, b)) = √(x - a)² + (y - b)²
        a = Math.sqrt((Math.pow((p[0] - q[0]), 2)) + (Math.pow((p[1] - q[1]), 2)));
        return a;
    }// end of getDistance()

}//end of class
