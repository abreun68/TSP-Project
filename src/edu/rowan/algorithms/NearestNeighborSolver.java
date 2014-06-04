

package edu.rowan.algorithms;

import java.util.ArrayList;

/**
 *
 * @author Nacer Abreu and Emmanuel Bonilla
 */
public class NearestNeighborSolver {

    final Tour tour;
    double[][] adjacencyMatrix;
    
    ArrayList<Integer> visitedCities = new ArrayList<Integer>();
    ArrayList<Integer> solution = new ArrayList<Integer>();
    double tourCost;
    
    ArrayList<Integer> bestTourSoFar = new ArrayList<Integer>();
    double costOfBestTourSoFar = 1000000000.00;

    
    public NearestNeighborSolver(Tour tour) {
    
        this.tour = tour;
        this.adjacencyMatrix = tour.getAdjacencyMatrix();
        tourCost = 0.0;
    }

    /**
     * This function recursively visit every node specified in the .tsp file
     * by always choosing the nearest node.
     * @return  An array representing the shortest tour. The solution.
     */
    ArrayList<Integer> getShortestTour() {

        int node = 0; //Start Node
    
        
        for(int i = 0; i < tour.getDimension(); i++){
            determineShortestTour(i);
            System.out.println("Tour cost = " + tourCost);
            System.out.println("=========================\n");
        }
        
        
        System.out.println("\nBest cost = " + costOfBestTourSoFar);
        System.out.println(bestTourSoFar);
        return bestTourSoFar;
    }//end of getShortestTour()   
    
    
    private void determineShortestTour(int node){
        solution.clear();
        visitedCities.clear();
        tourCost = 0.0;
        
        processInitialNode(node);

        while (visitedCities.size() < tour.getDimension()) {
            System.out.println("Node: " + (node + 1));
            node = getNearestNode(node);
            visitedCities.add(node);
            solution.add(node + 1);
            System.out.println("\n");
        }
        
        processFinalNode(node);        
        
    }
    
    /**
     * This function process the very first node that will be process.
     * @param node Initial node
     */
    private void processInitialNode(int node) {
        solution.add(node + 1); // Add the starting node to the solution array.
        visitedCities.add(node); // Add the starting node to the visited cities
    }
    
    private void processFinalNode(int node) {

        tourCost += adjacencyMatrix[node][0];
        
        System.out.println("Node: " + (node + 1));
        System.out.println("Nearest Node: [initial node]" + "; Edge: " + adjacencyMatrix[node][0]);
        //System.out.println(solution);
        
        if( tourCost < costOfBestTourSoFar ){
            costOfBestTourSoFar = tourCost;
            bestTourSoFar = (ArrayList<Integer>) solution.clone();
        }
        
        
    }//end of processFinalNode()

    private int getNearestNode(int node) {
        double edge = -1.0;
        int nearestNode = -1;

        for (int i = (tour.getDimension() - 1); i > -1; i--) {

            
            if (isMarkedVisited(i)){
                // This city has already been visited by some node.
                continue;
            }
            
            if (-1 == adjacencyMatrix[node][i]){
                // Same city. Current and destination cities are the same.
                continue;
            }
                        
            if ((-1.0 == edge) && (-1 != adjacencyMatrix[node][i])) {
               edge = adjacencyMatrix[node][i];
                // initiliaze this variable with the edge value of the
                // first city, that is not the current city. This solves the 
                // node[0][0] issues, where it is originally set to -1.
            }            
            
            
            if ((adjacencyMatrix[node][i] <= edge)) { 
                edge = adjacencyMatrix[node][i];
                System.out.println("Nearest Node: " + (i + 1)  + "; Edge: " + edge);
                nearestNode = i; //save off nearest neighbour.
            }
        }//end of for...loop
        
        tourCost += adjacencyMatrix[node][nearestNode];
        return nearestNode;
    }//end of getNearestNode()  
    
    
    
    /**
     * Test if the node argument was already visited.
     * @param node Node to be checked against the visited cities array.
     * @return True, if the node was already visited; otherwise, false.
     */
    private boolean isMarkedVisited(int node) {
        boolean found = false;

        for (int i = 0; i < visitedCities.size(); i++) {
            if (node == visitedCities.get(i)) {
                found = true;
                break;
            }
        }
        return found;
    }//end of isMarkedVisited();
    
}//end of class
