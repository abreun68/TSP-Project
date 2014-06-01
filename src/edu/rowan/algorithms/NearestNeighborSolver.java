

package edu.rowan.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nasser
 */
public class NearestNeighborSolver {

    private Tour tour;
    double[][] adjacencyMatrix;
    int dimension;
    ArrayList<Integer> visitedCities = new ArrayList<Integer>();
    ArrayList<Integer> solution = new ArrayList<Integer>();
    double tourCost;
    
    public NearestNeighborSolver(Tour tour) {
    
        this.tour = tour;
        this.adjacencyMatrix = tour.getAdjacencyMatrix();
        this.dimension = tour.getDimension();
        tourCost = 0.0;
    }

    ArrayList<Integer> getShortestTour() {

        System.out.println("NAME : " + tour.getFilename());
        System.out.println("TYPE : " + tour.getType());
        System.out.println("DIMENSION : " + tour.getDimension());
        System.out.println("TOUR_SECTION");

        int node = 0; //Start Node
        solution.add(node);
        visitedCities.add(node);
        System.out.println(node + 1);
        
        while (visitedCities.size() < this.dimension ) {
            
            node = getNearestNode(node);

        }

        System.out.println("-1");
        return solution;
    }   
    

    private int getNearestNode(int node) {
        double edge = 0.0;
        int nearestNode = -1;

                
        for (int i = 0; i < this.dimension; i++) {

            
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
                System.out.println("Edge :" + edge);
                nearestNode = i; //save off nearest neighbour.
            }
        }//end of for...loop
        
        tourCost += adjacencyMatrix[node][nearestNode];
        visitedCities.add(nearestNode);
        System.out.println(nearestNode + 1);
        solution.add(nearestNode);
        return nearestNode;
    }    

    private boolean visited(int node) {
        boolean found = false;

        for (int i = 0; i < visitedCities.size(); i++) {
            if(node == visitedCities.get(i)){
               found = true; 
               break;
            }
        }
        return found;
    }
}//end of class
