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
    double[][] currentTourDistMatrix = new double[6][3];
    
    ArrayList<Integer> visitedCities;
    ArrayList<Integer> cities;
    ArrayList<Integer> bestTour;
    ArrayList<Integer> tourSoFar;

    double tmpTourCost;
    double costOfBestTourSoFar;
    double bestTourDist;
    double edge;
    
    int NodeA = 1;
    int NodeB = 2;
    int	closestPointA = 0;
    int closestDistA =1;
    int closestPointB = 2;
    int closestDistB = 3;
    
	public BranchAndBoundSolver(Tour tour) {
        this.tour = tour;
        this.adjacencyMatrix = tour.getAdjacencyMatrix();
        this.lowerboundMatrix = tour.getLowerBoundMatrix();
        this.cities = tour.getCities();
        

        tmpTourCost = 0.0;
        visitedCities = new ArrayList<Integer>();
        bestTour = new ArrayList<Integer>();
        
        //TODO: find a better way to initialize this variable.
        costOfBestTourSoFar = 1000000000.00; 
        bestTourDist = costOfBestTourSoFar;
        
        //generatePermutation();
        
		//test();
        bestTour.add(0);
		tsp(bestTour, tour.getDimension());
		//tsp(bestTour, 3);
		System.out.println("Best Tour: "+bestTour+" Tour Cost: "+bestTourDist);
	}

    private void test (){
    	double compLowerBound = 0;
    	System.out.println(cities);
    	for (int i = 0; i < 6; i++){
    		compLowerBound = ComputeLowerBound(i);
    		System.out.println("\nStep "+(i+1));
    		System.out.println("   City: "+(cities.get(i))+" Lower Bound: "+compLowerBound);
    		//System.out.println("Current Tour Dist Matrix: ["+i+"] "+(int)currentTourDistMatrix[i][0]
    		//		+ " "+currentTourDistMatrix[i][1]+" "+currentTourDistMatrix[i][2]);
    	}
    	//System.out.println("\n");
/*    	for (int i = 0; i < 6; i++){
    		System.out.println("Current Tour Dist Matrix: ["+i+"] "+(int)currentTourDistMatrix[i][0]
    				+ " "+currentTourDistMatrix[i][NodeA]+" "+currentTourDistMatrix[i][NodeB]);
    	} */
    	double costOfCurrentTour=0;
    	for (int i = 0; i < (tour.getDimension()-1); i++){
    		costOfCurrentTour += adjacencyMatrix[(cities.get(i)-1)][(cities.get(i+1)-1)];
    	}
    	// Add last node to close loop
    	//System.out.println(cities.get(tour.getDimension()-1));
    	//System.out.println(adjacencyMatrix[0][5]);
    	costOfCurrentTour += adjacencyMatrix[0][(cities.get(tour.getDimension()-1)-1)];
    	System.out.println("costOfCurrentTour: "+costOfCurrentTour);
    	System.out.println("bestTourDist: "+bestTourDist);
    	System.out.println(bestTour+"\n");
    	
    	if (costOfCurrentTour < bestTourDist){
    		bestTourDist = costOfCurrentTour;
    		//System.out.println("Changing bestTour");
    		bestTour = (ArrayList<Integer>)cities.clone();
    		//System.out.println(bestTour+"\n");
    	}
    }
    
    private int tsp (ArrayList<Integer> currentTour, int paths) {
    	double costOfCurrentTour =0;
		//System.out.println("Path "+paths);
    	paths--;
    	if (paths == 0) {
    		System.out.println("Return tour "+currentTour);
    		// Calculate cost of the current tour
    		for (int i = 0; i < (tour.getDimension()-1); i++){
        		System.out.println("City "+(currentTour.get(i))+" "+(currentTour.get(i+1)));
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
    		System.out.println("Current tour "+currentTour);
    		System.out.println("Number of children "+paths);
    		createChildren(currentTour,paths);

    		return 1;
    	}
    } //end tsp
    
    private double ComputeLowerBound (int node) {
    	
    	double lowerBound = 0;
    	double finalLowerBound = 0;
    	if (node == 0){
    		for (int i = 0; i < tour.getDimension(); i++){
    			lowerBound += lowerboundMatrix[i][closestDistA] + lowerboundMatrix[i][closestDistB];
    			currentTourDistMatrix[i][0] = cities.get(i);
    			currentTourDistMatrix[i][NodeA] = lowerboundMatrix[cities.get(i)-1][closestDistA];
    			currentTourDistMatrix[i][NodeB] = lowerboundMatrix[cities.get(i)-1][closestDistB];
    		}  // end for loop
    		finalLowerBound = getLowerBound()/2;
    	} 
    	else {
    		//System.out.println("\ncities.get(node): node = "+node+" city = "+cities.get(node));
    		//System.out.println("PointA: "+(lowerboundMatrix[(cities.get(node)-1)][closestPointA]+1));
    		//System.out.println("PointB: "+(lowerboundMatrix[(cities.get(node)-1)][closestPointB]+1));
    		//System.out.println("cities.get(node-1): "+cities.get(node-1));
    		if (cities.get(node-1)==(lowerboundMatrix[(cities.get(node)-1)][closestPointA]+1) ||
    			cities.get(node-1)==(lowerboundMatrix[(cities.get(node)-1)][closestPointB]+1)){
    			//System.out.println("Don't change Matrix");    			
    		} 
    		else {
    			if (node == 1){
    				//System.out.println("Entering node == 1");
    				//System.out.println("Changing NodeB to "+adjacencyMatrix[(cities.get(node)-1)][(cities.get(node-1)-1)]);
    				//System.out.println("Changing NodeB to "+adjacencyMatrix[(cities.get(node)-1)][(cities.get(node-1)-1)]);
    				currentTourDistMatrix[node-1][NodeB] = adjacencyMatrix[(cities.get(node)-1)][(cities.get(node-1)-1)];
    				currentTourDistMatrix[node][NodeB] = adjacencyMatrix[(cities.get(node)-1)][(cities.get(node-1)-1)];
    			}
    			else {
    				//System.out.println("Entering not == 1");
    				currentTourDistMatrix[node-1][NodeA] = adjacencyMatrix[(cities.get(node)-1)][(cities.get(node-1)-1)];
    				currentTourDistMatrix[node][NodeB] = adjacencyMatrix[(cities.get(node)-1)][(cities.get(node-1)-1)];
    			}

    			lowerBound = getLowerBound();
    			
        		finalLowerBound = lowerBound/2;
    		}
    		finalLowerBound = getLowerBound()/2;
    		//System.out.println("cities.get(node): "+cities.get(node));
    		//System.out.println("lbMatrixA: "+(lowerboundMatrix[(cities.get(node)-1)][closestPointA]+1));
    		//System.out.println("lbMatrixB: "+(lowerboundMatrix[(cities.get(node)-1)][closestPointB]+1));

    		//Complete circuit
			currentTourDistMatrix[0][NodeA] = adjacencyMatrix[(cities.get(node)-1)][0];
			currentTourDistMatrix[node][NodeA] = adjacencyMatrix[(cities.get(node)-1)][0];

    		for (int i = node; i >= 0; i--){
    			//System.out.println(node+"   * "+cities.get(i)+" & "+cities.get(i)+" *");
    			 
    			//lowerBound += adjacencyMatrix[i][i+1];
    		}
    		//finalLowerBound = lowerBound;
    	}// end else of node=0
    	//System.out.println("Lower Bound = " + finalLowerBound);
      // Prints out the lower bound Matrix  	
/*    	for (int i =0; i < 6; i++){
            	System.out.println("City " + (i+1) + ": " + 
            			((int)lowerboundMatrix[i][0]+1) + "-" + lowerboundMatrix[i][1] + " " +
            			((int)lowerboundMatrix[i][2]+1) + "-" + lowerboundMatrix[i][3]);
    	}    	
*/
    	return finalLowerBound;
    } //end ComputeLowerBound
    
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
    }
    
    private double getLowerBound() {
    	double lowerBound = 0;
		for (int i = 0; i < tour.getDimension(); i++){
			lowerBound += currentTourDistMatrix[i][NodeA] + currentTourDistMatrix[i][NodeB];
		}  // end for loop
    	
		return lowerBound;
    }

}//end of class
