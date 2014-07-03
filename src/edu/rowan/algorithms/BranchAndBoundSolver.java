package edu.rowan.algorithms;

import java.util.ArrayList;

/**
 * This class takes a Tour object and tries, by using a branch-and-bound
 * approach, to compute the shortest tour distance.
 * @author Nacer Abreu and Emmanuel Bonilla
 */
public class BranchAndBoundSolver {

    Tour tour;
    double[][] adjacencyMatrix;
    double[][] lowerboundMatrix;
    double[][] currentTourDistMatrix;

    ArrayList<Integer> bestTour;

    double bestTourDist;

    static final int NODE_A = 1;
    static final int NODE_B = 2;
    
    static final int CHANGE_A = 3;
    static final int CHANGE_B = 4;
    
    static final int CLOSEST_DIST_A = 1;
    static final int CLOSEST_DIST_B = 3;
    
    /**
     * Constructor
     * @param tour 
     */
    public BranchAndBoundSolver(Tour tour) {
        this.tour = tour;
        this.adjacencyMatrix = tour.getAdjacencyMatrix();
        this.lowerboundMatrix = tour.getLowerBoundMatrix();

        currentTourDistMatrix = new double[tour.getDimension()][5];
        bestTour = new ArrayList<Integer>();

        //TODO: find a better way to initialize this variable.
        bestTourDist = 1000000000.00;

        bestTour.add(0);

        calculateSolution(bestTour, tour.getDimension());

        for (int i = 0; i < bestTour.size(); i++) {
            bestTour.set(i, (bestTour.get(i) + 1));
        }
        System.out.println("\n"+"Best Tour: " + bestTour + " Tour Cost: " + bestTourDist);
    }//end of constructor

    /**
     * This function takes a tour and starts calculating the
     * best tour and best distance.  It also calls the
     * branch-and-bound function to help prune out certain tours
     * @param currentTour The current tour so far 
     * @param paths The number of paths still left for this particular tour
     */
    private void calculateSolution(ArrayList<Integer> currentTour, int paths) {
        double costOfCurrentTour = 0;
        paths--;
        if (paths == 0) {
            // All cities accounted for. Complete path!
            for (int i = 0; i < (tour.getDimension() - 1); i++) {

                costOfCurrentTour += adjacencyMatrix[currentTour.get(i)][currentTour.get(i + 1)];
            }
            costOfCurrentTour += adjacencyMatrix[0][currentTour.get(tour.getDimension() - 1)];

            // Update Best Tour and Distance
            if (bestTourDist > costOfCurrentTour) {
                // We save off the best solution so far!
                bestTourDist = costOfCurrentTour;
                bestTour = (ArrayList<Integer>) currentTour.clone();               
            }

        } else {
            // Not a complete path. Still some cities not visited!
            double compLowerBound = 0;
            System.out.println("\n" + "Current tour " + currentTour);
            compLowerBound = ComputeLowerBound(currentTour);
            System.out.println("Tour Lower Bound: " + compLowerBound);

            if (compLowerBound > bestTourDist) {
                // Inferior solution, because the lower bound is greater than 
                // the current best distance, when not all cities have been 
                // visited yet.
                System.out.println("Best Dist: " + bestTourDist);
            } else {
                // The computed lower bound is smaller than the current best 
                // distance, however not all cities have been visited, so we
                // we call this function recursevely via the createChildren()
                // function.
                createChildren(currentTour, paths);
            }
        }//end of else..
    } //end calculateSolution
    
    /**
     * This function creates a matrix of the current lower bounds
     * for a given tour
     * @param tourList The current tour so far 
     */
    private double ComputeLowerBound (ArrayList<Integer> tourList) {
    	
    	double finalLowerBound = 0;

        // Setting up lowerbound Matrix
    	for (int i = 0; i < tour.getDimension(); i++){
    		currentTourDistMatrix[i][0] = i;
    		currentTourDistMatrix[i][NODE_A] = lowerboundMatrix[i][CLOSEST_DIST_A];
    		currentTourDistMatrix[i][NODE_B] = lowerboundMatrix[i][CLOSEST_DIST_B];
    		currentTourDistMatrix[i][CHANGE_A] = 0;
    		currentTourDistMatrix[i][CHANGE_B] = 0;
    		finalLowerBound = getLowerBound();
    	}  // end for loop
    	
    	if (tourList.size() > 1) {
    		for (int i = 0; i < (tourList.size()-1); i++) {
    	    	int previousCity = tourList.get(tourList.size()-(i+2));
    	    	int currentCity = tourList.get(tourList.size()-(i+1));

    	    	double distanceBetweenCities = adjacencyMatrix[previousCity][currentCity];

    	    	if ((currentTourDistMatrix[previousCity][CHANGE_A] == 0) &&
    	    		(currentTourDistMatrix[previousCity][NODE_A] == distanceBetweenCities)) {
    	    		currentTourDistMatrix[previousCity][CHANGE_A] = 1;
    	    		currentTourDistMatrix[currentCity][CHANGE_A] = 1;    	    		
    	    	} 
    	    	else if ((currentTourDistMatrix[previousCity][CHANGE_B] == 0) &&
    	    			 (currentTourDistMatrix[previousCity][NODE_B] == distanceBetweenCities)) {
    	    		currentTourDistMatrix[previousCity][CHANGE_B] = 1;
    	    		currentTourDistMatrix[currentCity][CHANGE_B] = 1;
    	    	}
    	    	else if ((currentTourDistMatrix[previousCity][CHANGE_B] == 0) &&
    	    			 (currentTourDistMatrix[previousCity][NODE_B] < distanceBetweenCities)) {
    	    		currentTourDistMatrix[previousCity][NODE_B] = distanceBetweenCities;
    	    		currentTourDistMatrix[previousCity][CHANGE_B] = 1;
    	    		currentTourDistMatrix[currentCity][NODE_B] = distanceBetweenCities;
    	    		currentTourDistMatrix[currentCity][CHANGE_B] = 1;
    	    	}
    	    	else if ((currentTourDistMatrix[previousCity][CHANGE_A] == 0) &&
   	    			 (currentTourDistMatrix[previousCity][NODE_A] < distanceBetweenCities)) {
   	    		currentTourDistMatrix[previousCity][NODE_A] = distanceBetweenCities;
   	    		currentTourDistMatrix[previousCity][CHANGE_A] = 1;
   	    		currentTourDistMatrix[currentCity][NODE_A] = distanceBetweenCities;
   	    		currentTourDistMatrix[currentCity][CHANGE_A] = 1;
    	    	}
   	    		else {
                                
   	    			// System.out.println("No Changes");
   	    		}
   	    	}

   	     	finalLowerBound = getLowerBound();
    	}//end of ..if tourList.size > 1
        
    	return finalLowerBound;
    } //end ComputeLowerBound()
    
    /**
     * This function creates the children of the parent and 
     * then calls calculateSolution to continue to next city.
     * @param parentList The current tour so far 
     * @param numChildren The number of paths still left for this particular tour
     */
    private void createChildren(ArrayList<Integer> parentList, int numChildren) {        
        int childrenMade = 0;

        for (int i = 0; childrenMade < numChildren; i++) {

            if (parentList.contains(i)) {
                // Do not add
            } else {
                parentList.add(i);
                calculateSolution(parentList, numChildren);
                int arraysize = parentList.size() - 1;
                parentList.remove(arraysize);
                childrenMade++;
            }
        }//end of for..loop
    } //end createChildren
    
    /**
     * This function calculates the lower bound for a given tour
     * @return lowerBound/2 The calculated lower bound
     */
    private double getLowerBound() {
        double lowerBound = 0;
        for (int i = 0; i < tour.getDimension(); i++) {
            lowerBound += currentTourDistMatrix[i][NODE_A] + currentTourDistMatrix[i][NODE_B];
        }  // end for loop

        return lowerBound / 2;
    }//end of getLowerBound()

}//end of class
