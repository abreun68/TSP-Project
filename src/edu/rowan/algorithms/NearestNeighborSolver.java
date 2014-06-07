package edu.rowan.algorithms;

import java.util.ArrayList;

/**
 * This class takes a Tour object and tries, by using a nearest 
 * neighbor algorithm, to compute the shortest tour distance.
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
        
        //TODO: find a better way to initialize this variable.
        costOfBestTourSoFar = 1000000000.00; 
    }

    /**
     * This function returns an array representing a solution for the TSP
     * problem using a nearest neighbor algorithm.
     * @return  An array representing the shortest tour found. 
     */
    public ArrayList<Integer> getShortestTour() {
         
        /**
         * Test every city as the starting location/node. 
         * The shortest tour will be save off in the variable 'bestTourSoFar' 
         * and its cost in the variable 'costOfBestTourSoFar'
         */
        for(int city = 0; city < tour.getDimension(); city++){
            
            //Repetitive Nearest-Neighbor Algorithm (RNNA)

            determineShortestTour(city);
            
        }
        
        printSolution(bestTourSoFar);
        System.out.println("\nTour cost = " + costOfBestTourSoFar);

        return bestTourSoFar;
    }//end of getShortestTour()   
    
    /**
     * This function follows the nearest neighbor strategy to create a tour
     * starting with the specified (argument) location/node.
     * @param node Starting node. 
     */
    private void determineShortestTour(int node){

        int initialNode = node;
        
        /**
         * The tmpSolution arraylist holds the running solution for a particular 
         * recursion. The values in the array, are saved in a way such that, are 
         * compatible with the TSPLIB format. In other words, in the .tsp file all 
         * location/nodes start from number '1'. Because our for...loops 
         * iterate from '0' (zero), we add '1' to the appending value. 
         */
        ArrayList<Integer> tmpSolution = new ArrayList<Integer>();
        
        visitedCities.clear();
        tmpTourCost = 0.0;
        
        tmpSolution.add(node + 1); // Add the starting node to the solution.
        visitedCities.add(node); // Add the starting node to the visited cities.
        
        while (visitedCities.size() < tour.getDimension()) {
           
            node = getNearestNode(node);
            visitedCities.add(node); // Mark this node as visited
            tmpSolution.add(node + 1);  // Add the current node to the solution.
        }
        
        /**
         * Add the distance from the last node visited to the initial node, to 
         * the tour cost, in order to complete the cycle.
         */
        tmpTourCost += adjacencyMatrix[node][initialNode]; 
  
        // Update best-tour-so-far and its cost.
        if (tmpTourCost < costOfBestTourSoFar) {
            costOfBestTourSoFar = tmpTourCost;
            bestTourSoFar = (ArrayList<Integer>) tmpSolution.clone();
        }        
        
    }//end of determineShortestTour()
    
  
    /**
     * This function returns the nearest neighbor from the specified argument.
     * @param currentNode The starting location/node. 
     * @return The location/node number of the nearest neighbor
     */
    private int getNearestNode(int currentNode) {
        double edge = -1.0;
        int nearestNode = -1;

        /**
         * Search for the nearest neighbor starting from the highest 
         * location/node (end-of-array), in order to always select the lowest 
         * node, in the cases where two location/nodes have the same exact 
         * distance. This is our tie breaker strategy.
         */
        for (int i = (tour.getDimension() - 1); i > -1; i--) {
            
            if (isMarkedVisited(i)){
                // This city has already been visited by some other 
                // node/recursion.
                continue;
            }
            
            if (-1 == adjacencyMatrix[currentNode][i]){
                // Same City. Current and destination cities are the same.
                // Same cities are encoded with a -1 in the adjancency matrix.
                continue;
            }
                        
            if ((-1.0 == edge) && (-1 != adjacencyMatrix[currentNode][i])) {
               edge = adjacencyMatrix[currentNode][i];
                // initiliaze this variable with the edge value of the
                // first city, that is not the current city. This solves the 
                // currentNode[0][0] issues, where it is originally set to -1.
            }            
                        
            if ((adjacencyMatrix[currentNode][i] <= edge)) { 
                edge = adjacencyMatrix[currentNode][i];
                nearestNode = i; //save off nearest neighbour.
            }
        }//end of for...loop
        
        // At this point, the tmpTourCost is missing the cost from the last
        // node visited to the beginning node.
        tmpTourCost += adjacencyMatrix[currentNode][nearestNode];
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
     * FOR DEBUGGING PURPOSES. It prints out all the cost for each leg of a 
     * trip (edges).
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
