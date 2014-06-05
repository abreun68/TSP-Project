package edu.rowan.algorithms;

import java.util.ArrayList;

/**
 * This class takes a Tour object and tries by using a nearest 
 * neighbor  to compute the shortest tour distance.
 * @author Nacer Abreu and Emmanuel Bonilla
 */
public class NearestNeighborSolver {

    final Tour tour;
    final double[][] adjacencyMatrix;
    
    ArrayList<Integer> visitedCities;
    ArrayList<Integer> bestTourSoFar;
    
    double tmpTourCost;
    double costOfBestTourSoFar;

    
    public NearestNeighborSolver(Tour tour) {
    
        this.tour = tour;
        this.adjacencyMatrix = tour.getAdjacencyMatrix();
        tmpTourCost = 0.0;
        visitedCities = new ArrayList<Integer>();
        bestTourSoFar = new ArrayList<Integer>();
        costOfBestTourSoFar = 1000000000.00; //TODO: find a better way to init.
    }

    /**
     * This function recursively visit every node specified in the .tsp file
     * by always choosing the nearest node.
     * @return  An array representing the shortest tour. The tmpSolution.
     */
    ArrayList<Integer> getShortestTour() {
         
        // Test every city as the starting location/node.
        for(int i = 0; i < tour.getDimension(); i++){
            
            determineShortestTour(i);
            
        }
        
        printSolution(bestTourSoFar);
        System.out.println("\nTour cost = " + costOfBestTourSoFar);

        return bestTourSoFar;
    }//end of getShortestTour()   
    
    /**
     * This function follows the nearest neighbor strategy to create a tour
     * starting with the specified (argument) location/node.
     * @param node Starting node
     */
    private void determineShortestTour(int node){

        int initialNode = node;
        ArrayList<Integer> tmpSolution = new ArrayList<Integer>();
        
        visitedCities.clear();
        tmpTourCost = 0.0;
        
        tmpSolution.add(node + 1); // Add the starting node to the tmpSolution array.
        visitedCities.add(node); // Add the starting node to the visited cities
        
        while (visitedCities.size() < tour.getDimension()) {
           
            node = getNearestNode(node);
            visitedCities.add(node);
            tmpSolution.add(node + 1);  // The first node should be 
                                        // '1' instead of zero          
        }
        
        // Add the distance from the last node to the initial node to the tour 
        // cost.
        tmpTourCost += adjacencyMatrix[node][initialNode]; 
  
        // Update best-tour-so-far and its cost.
        if (tmpTourCost < costOfBestTourSoFar) {
            costOfBestTourSoFar = tmpTourCost;
            bestTourSoFar = (ArrayList<Integer>) tmpSolution.clone();
        }        
        
    }//end of determineShortestTour()
    
  
    /**
     * This function returns the nearest neighbor from the specified node 
     * argument.
     * @param node The starting location/node. 
     * @return The location/node number of the nearest neighbor
     */
    private int getNearestNode(int node) {
        double edge = -1.0;
        int nearestNode = -1;

        // Search for the nearest neighbor starting from the higest 
        // location/node, so that we pick the lowest location/node as the tie 
        // breaker when two location/nodes are at the same exact distance.
        for (int i = (tour.getDimension() - 1); i > -1; i--) {
            
            if (isMarkedVisited(i)){
                // This city has already been visited by some node.
                continue;
            }
            
            if (-1 == adjacencyMatrix[node][i]){
                // Same city. Current and destination cities are the same.
                // Same cities are encoded with a -1 in the adjancency matrix.
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
                nearestNode = i; //save off nearest neighbour.
            }
        }//end of for...loop
        
        tmpTourCost += adjacencyMatrix[node][nearestNode];
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
    
    /**
     * This function is basically used for debugging purposes. It prints out all
     * tour with the different cost for each leg of a trip (edges).
     * @param tmpSolution The array tmpSolution
     */
    private void printSolution(ArrayList<Integer> solution) {
        double cost = 0.0;
        double edge = 0.0;
        int currentNode = -1;
        int nextNode = -1;
            
        for (int i = 1; i < solution.size(); i++) {
            currentNode = solution.get(i - 1);
            nextNode = solution.get(i);           
            
            edge = adjacencyMatrix[currentNode -1][nextNode - 1];
            cost += edge;
            
            System.out.println("Node: " + currentNode);            
            System.out.println("Nearest Node: " + nextNode  + "; Edge: " + edge);
            System.out.println();
        }//end of for loop.
        
        // The following ties the end node back to the initial
        currentNode = nextNode;
        nextNode = solution.get(0);
        edge = adjacencyMatrix[currentNode - 1][nextNode - 1];
        cost += edge;      
        
        System.out.println("Node: " + currentNode);
        System.out.println("Nearest Node: " + nextNode + "; Edge: " + edge);
        
    }//end of printSolution()
}//end of class
