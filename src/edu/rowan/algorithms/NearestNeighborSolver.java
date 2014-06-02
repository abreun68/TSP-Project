

package edu.rowan.algorithms;

import java.util.ArrayList;

/**
 *
 * @author Nacer Abreu and Emmanuel Bonilla
 */
public class NearestNeighborSolver {

    private final Tour tour;
    double[][] adjacencyMatrix;

    ArrayList<Integer> visitedCities = new ArrayList<Integer>();
    ArrayList<Integer> solution = new ArrayList<Integer>();
    double tourCost;
    
    public NearestNeighborSolver(Tour tour) {
    
        this.tour = tour;
        this.adjacencyMatrix = tour.getAdjacencyMatrix();
        tourCost = 0.0;
    }

    /**
     * This function recursively visit every node specified in the .tsp file
     * by always choosing the nearest node.
     * @return  An array representing the shortest tour, the solution.
     */
    ArrayList<Integer> getShortestTour() {

        // The following "println"  represent the output headers.
        // TODO: move these print statements to a more logical place.
        System.out.println("NAME : " + tour.getFilename());
        System.out.println("TYPE : " + tour.getType());
        System.out.println("DIMENSION : " + tour.getDimension());
        System.out.println("TOUR_SECTION");

        int node = 0; //Start Node
        processInitialNode(node);

        while (visitedCities.size() < tour.getDimension()) {
            node = getNearestNode(node);
        }

        System.out.println("-1");
        return solution;
    }//end of getShortestTour()   
    
    
    /**
     * This function process the very first node that will be process.
     * @param node Initial node
     */
    private void processInitialNode(int node) {
        solution.add(node); // Add the starting node to the solution array.
        visitedCities.add(node); // Add the starting node to the visited cities
        System.out.println(node + 1);
    }

    private int getNearestNode(int node) {
        double edge = 0.0;
        int nearestNode = -1;

        for (int i = 0; i < tour.getDimension(); i++) {

            
            if (visited(i)){
                // This city has already been visited by some node.
                continue;
            }
            
            if (-1 == adjacencyMatrix[node][i]){
                // Same city.
                continue;
            }

            if ((0.0 == edge) && (-1 != adjacencyMatrix[node][i])) {
                edge = adjacencyMatrix[node][i];
                // initiliaze this variable with the edge value of the
                // first city, that is not the current city. This solves the 
                // node[0][0] issues, where it is originally set to -1.
            }            
            
            
            if ((adjacencyMatrix[node][i] <= edge)) { 
                edge = adjacencyMatrix[node][i];
//                System.out.println("Edge :" + edge);
                nearestNode = i; //save off nearest neighbour.
            }
        }//end of for...loop
        
        tourCost += adjacencyMatrix[node][nearestNode];
        visitedCities.add(nearestNode);
        System.out.println(nearestNode + 1);
        solution.add(nearestNode);
        return nearestNode;
    }//end of getNearestNode()  
    
    
    
    /**
     * Test if the node argument was already visited.
     * @param node Node to be checked against the visited cities array.
     * @return True, if the node was already visited; otherwise, false.
     */
    private boolean visited(int node) {
        boolean found = false;

        for (int i = 0; i < visitedCities.size(); i++) {
            if (node == visitedCities.get(i)) {
                found = true;
                break;
            }
        }
        return found;
    }//end of visited();
    
}//end of class
