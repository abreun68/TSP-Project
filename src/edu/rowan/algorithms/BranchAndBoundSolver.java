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
    ArrayList<Integer> tourSoFar;

    double tmpTourCost;
    double costOfBestTourSoFar;
    double bestTourDist;
    double edge;
    
    int NodeA = 1;
    int NodeB = 2;
    int ChangeA = 3;
    int ChangeB = 4;
    int	closestPointA = 0;
    int closestDistA =1;
    int closestPointB = 2;
    int closestDistB = 3;
    
	public BranchAndBoundSolver(Tour tour) {
        this.tour = tour;
        this.adjacencyMatrix = tour.getAdjacencyMatrix();
        this.lowerboundMatrix = tour.getLowerBoundMatrix();
        
        currentTourDistMatrix  = new double[tour.getDimension()][5];

        tmpTourCost = 0.0;
        bestTour = new ArrayList<Integer>();
        
        //TODO: find a better way to initialize this variable.
        costOfBestTourSoFar = 1000000000.00; 
        bestTourDist = costOfBestTourSoFar;
        
        bestTour.add(0);
		tsp(bestTour, tour.getDimension());

		for (int i = 0; i < bestTour.size(); i++) {
			bestTour.set(i, (bestTour.get(i)+1));
		}
		System.out.println("Best Tour: "+bestTour+" Tour Cost: "+bestTourDist);
	}

    /**
     * This function takes a tour and starts calculating the
     * best tour and best distance.  It also calls the
     * branch-and-bound function to help prune out certain tours
     * @param currentTour The current tour so far 
     * @param paths The number of paths still left for this particular tour
     */
    private int tsp (ArrayList<Integer> currentTour, int paths) {
    	double costOfCurrentTour =0;
		//System.out.println("Path "+paths);
    	paths--;
    	if (paths == 0) {
    		System.out.println("Return tour "+currentTour);
    		// Calculate cost of the current tour
    		for (int i = 0; i < (tour.getDimension()-1); i++){
        		//System.out.println("City "+(currentTour.get(i))+" "+(currentTour.get(i+1)));
        		costOfCurrentTour += adjacencyMatrix[currentTour.get(i)][currentTour.get(i+1)];
        	}
    		costOfCurrentTour += adjacencyMatrix[0][currentTour.get(tour.getDimension()-1)];
        	
        	System.out.println("bestTourDist: "+bestTourDist+" currentDist: "+costOfCurrentTour);
        	// Update Best Tour and Distance
        	if (bestTourDist > costOfCurrentTour) {
        		bestTourDist = costOfCurrentTour;
        		bestTour = (ArrayList<Integer>)currentTour.clone();
        	}

    		System.out.println("End of tour");
    		return 1;
    	} 
    	else {
    		double compLowerBound = 0;
    		System.out.println("Current tour "+currentTour);
    		System.out.println("Number of children "+paths);
    		compLowerBound = ComputeLowerBound(currentTour);
    		System.out.println("Lower Bound: "+compLowerBound);
    		System.out.println("Best Dist: "+bestTourDist);
    		if (compLowerBound > bestTourDist)
    			return 1;
    		createChildren(currentTour,paths);

    		return 1;
    	}
    } //end tsp
    
    /**
     * This function creates a matrix of the current lower bounds
     * for a given tour
     * @param tourList The current tour so far 
     */
    private double ComputeLowerBound (ArrayList<Integer> tourList) {
    	
    	double finalLowerBound = 0;
    	System.out.println("ComputeLowerBound on "+tourList);

    	// Print out the initial Lower Bound Matrix
/*    	for (int i =0; i < tour.getDimension(); i++){
    		System.out.println("City " + (i+1) + ": " + 
    				((int)lowerboundMatrix[i][0]+1) + "-" + lowerboundMatrix[i][1] + " " +
    				((int)lowerboundMatrix[i][2]+1) + "-" + lowerboundMatrix[i][3]);
    	}   
*/    	 	
    		//Create initial lower bound for all cities
    	System.out.println("Setting up lowerboundMatrix");
    	for (int i = 0; i < tour.getDimension(); i++){
    		currentTourDistMatrix[i][0] = i;
    		currentTourDistMatrix[i][NodeA] = lowerboundMatrix[i][closestDistA];
    		currentTourDistMatrix[i][NodeB] = lowerboundMatrix[i][closestDistB];
    		currentTourDistMatrix[i][ChangeA] = 0;
    		currentTourDistMatrix[i][ChangeB] = 0;
    		finalLowerBound = getLowerBound();
    	}  // end for loop
    	
    	if (tourList.size() > 1) {
    		for (int i = 0; i < (tourList.size()-1); i++) {
    	    	int previousCity = tourList.get(tourList.size()-(i+2));
    	    	int currentCity = tourList.get(tourList.size()-(i+1));

    	    	//System.out.println("Previous City: "+ previousCity);
    	    	//System.out.println("Current City: "+ currentCity);
    	    	//System.out.println("PointA: "+(lowerboundMatrix[tourList.get(tourList.size()-(i+1))][closestPointA]+1));
    	    	//System.out.println("PointB: "+(lowerboundMatrix[tourList.get(tourList.size()-(i+1))][closestPointB]+1));
    	    	double distanceBetweenCities = adjacencyMatrix[previousCity][currentCity];

    	    	if ((currentTourDistMatrix[previousCity][ChangeA] == 0) &&
    	    		(currentTourDistMatrix[previousCity][NodeA] == distanceBetweenCities)) {
    	    		currentTourDistMatrix[previousCity][ChangeA] = 1;
    	    		currentTourDistMatrix[currentCity][ChangeA] = 1;    	    		
    	    	} 
    	    	else if ((currentTourDistMatrix[previousCity][ChangeB] == 0) &&
    	    			 (currentTourDistMatrix[previousCity][NodeB] == distanceBetweenCities)) {
    	    		currentTourDistMatrix[previousCity][ChangeB] = 1;
    	    		currentTourDistMatrix[currentCity][ChangeB] = 1;
    	    	}
    	    	else if ((currentTourDistMatrix[previousCity][ChangeB] == 0) &&
    	    			 (currentTourDistMatrix[previousCity][NodeB] < distanceBetweenCities)) {
    	    		currentTourDistMatrix[previousCity][NodeB] = distanceBetweenCities;
    	    		currentTourDistMatrix[previousCity][ChangeB] = 1;
    	    		currentTourDistMatrix[currentCity][NodeB] = distanceBetweenCities;
    	    		currentTourDistMatrix[currentCity][ChangeB] = 1;
    	    	}
    	    	else if ((currentTourDistMatrix[previousCity][ChangeA] == 0) &&
   	    			 (currentTourDistMatrix[previousCity][NodeA] < distanceBetweenCities)) {
   	    		currentTourDistMatrix[previousCity][NodeA] = distanceBetweenCities;
   	    		currentTourDistMatrix[previousCity][ChangeA] = 1;
   	    		currentTourDistMatrix[currentCity][NodeA] = distanceBetweenCities;
   	    		currentTourDistMatrix[currentCity][ChangeA] = 1;
    	    	}
   	    		else {
   	    			System.out.println("No Changes");
   	    		}
   	    	}

    		//Print out the Current Lower Bound Tour Matrix 
        	/*for (int i =0; i < tour.getDimension(); i++){
        		System.out.println("City " + (i+1) + ": " + 
        				(currentTourDistMatrix[i][NodeA]) +" "+
        				(currentTourDistMatrix[i][NodeB]));
        	} */   	

   	     	finalLowerBound = getLowerBound();
    	}
    	return finalLowerBound;
    } //end ComputeLowerBound
    
    /**
     * This function creates the children of the parent and 
     * then calls tsp to continue to next city.
     * @param parentList The current tour so far 
     * @param numChildren The number of paths still left for this particular tour
     */
    private void createChildren(ArrayList<Integer> parentList, int numChildren) {
    	System.out.println("Enter createChildren");
    	int childrenMade = 0;
    	//System.out.println("parent "+ parentList.get(0));
    	for (int i = 0; childrenMade < numChildren; i++){
    		//if (parentList.get(i) != NULL){
    		//System.out.println("parent: "+parentList.get(i));
    		if (parentList.contains(i)) {
    			System.out.println("Do not add");
    		}
    		else {
    			parentList.add(i);
    	    	System.out.println("New parent list: "+parentList);
    	    	tsp(parentList, numChildren);
    	    	System.out.println("Remove i = "+i);
    	    	int arraysize = parentList.size()-1;
    	    	parentList.remove(arraysize);
    	    	childrenMade++;
    		}
    		//}
    	}
    	System.out.println("Leaving createChildren");
    } //end createChildren
    
    /**
     * This function calculates the lower bound for a given tour
     * @return lowerBound/2 The calculated lower bound
     */
    private double getLowerBound() {
    	double lowerBound = 0;
    	//adjacencyMatrix[city][i];
		for (int i = 0; i < tour.getDimension(); i++){
			lowerBound += currentTourDistMatrix[i][NodeA] + currentTourDistMatrix[i][NodeB];
		}  // end for loop
    	
		return lowerBound/2;
    }

}//end of class
