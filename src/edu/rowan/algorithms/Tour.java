package edu.rowan.algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class holds all the information related to a tour that was provided by
 * the .tsp file.
 * @author Nacer Abreu & Emmanuel Bonilla
 */
public class Tour {

    private String name;
    private String comment;
    private String type;
    private int dimension;
    private String edgeWeighType;
    private String filename;
    
    //container of cities + (x,y) coordinates
    private ArrayList<List<Double>> allCitiesContainer; 
    
    private boolean inNodesSection;
    private static final int LOCATION = 0;
    private static final int X_COORD = 1;
    private static final int Y_COORD = 2;
    private double [ ] [ ] matrix;

    public Tour() {
        name = "";
        comment = "";
        type = "";
        dimension = 0;
        edgeWeighType = "";
        allCitiesContainer = new ArrayList<List<Double>>();
        inNodesSection = false;
    }

    /**
     * Parses lines that are compatible with the TSPLIB file format.
     * @param line A line from the .tsp file
     */
    public void parseLine(String line) {

        line = line.trim();
        
        if (line.contains("NAME")) {
            String[] split = line.split(":");
            this.name = split[1].trim();
        }

        if (line.contains("COMMENT")) {
            String[] split = line.split(":");
            if (split.length > 1)
            this.comment = split[1].trim();
        }

        if (line.contains("TYPE")) {
            String[] split = line.split(":");
            this.type = split[1].trim();
        }

        if (line.contains("DIMENSION")) {
            String[] split = line.split(":");
            this.dimension = Integer.parseInt(split[1].trim());
            
            // Create adjancency matrix. This matrix will be use to store 
            // the distances between cities.
            matrix = new double [ this.dimension ] [ this.dimension ] ;   
        }

        if (line.contains("EDGE_WEIGHT_TYPE")) {
            String[] split = line.split(":");
            this.edgeWeighType = split[1].trim();
        }

        if (line.contains("EOF")) {
            this.inNodesSection = false;
            populateMatrix();
        }

        if (this.inNodesSection) {
            
            String[] split = line.split("\\s+");
            int location = Integer.parseInt(split[0].trim());
            double x = Double.parseDouble(split[1].trim());
            double y = Double.parseDouble(split[2].trim());
            saveEntry(location, x, y);
        }

        if (line.contains("NODE_COORD_SECTION")) {
            this.inNodesSection = true;
        }
    }//end of parseLine

    
    /**
     * This function saves off the 'NAME' entry of the .tsp file.
     * @param name The NAME entry in the .tsp file.
     */
    public void setName(String name) {
        this.name = name;
    }//end of setName()

    
    /**
     * This function saves off the 'COMMENT' entry of the .tsp file. 
     * @param comment The 'COMMENT' entry of the .tsp file. 
     */
    public void setComment(String comment) {
        this.comment = comment;
    }//end of setComment()

    
    /**
     * This function saves off the 'TYPE' entry of the .tsp file.
     * @param type The 'TYPE' entry of the .tsp file.
     */
    public void setType(String type) {
        this.type = type;
    }//end of setType()

    
    /**
     * This function saves off the 'DIMENSION' entry of the .tsp file.
     * @param dimension The 'DIMENSION' entry of the .tsp file.
     */
    public void setDimension(int dimension) {
        this.dimension = dimension;
    }//end of setDimension()

    
    
    /**
     * This function saves off the 'EDGE_WEIGHT_TYPE' entry of the .tsp file. 
     * @param edgeWeighType The 'EDGE_WEIGHT_TYPE' entry of the .tsp file.
     */
    public void setEdgeWeighType(String edgeWeighType) {
        this.edgeWeighType = edgeWeighType;
    }//end of setEdgeWeighType()

    
    
    /**
     * This function saves off every entry of the .tsp file.
     * @param location The node/city location
     * @param x_coord coordinate for the specified node. 
     * @param y_coord coordinate for the specified node. 
     */
    private void saveEntry(int location, double x_coord, double y_coord) {
        List<Double> city = new ArrayList<Double>();
        city.add((double) location);
        city.add(x_coord);
        city.add(y_coord);
        allCitiesContainer.add(city);
    }//end of saveEntry()

    
    
    /**
     * This function returns an array list of all city locations.
     * @return An array containing all node/cities locations
     */
    public ArrayList<Integer> getCities() {
        ArrayList<Integer> cities = new ArrayList<Integer>();
        Iterator<List<Double>> it = allCitiesContainer.iterator();
        while (it.hasNext()) {
            List<Double> city = it.next();
            cities.add(city.get(LOCATION).intValue());
        }
        return cities;
    }//end of getCities()

    
    /**
     * This function returns a list containing the city location, x and y
     * coordinates.
     * @param location The node/city location to get.
     * @return A list containing the city x/y coordinates
     * @throws NullPointerException
     */
    public List<Double> getCity(int location) throws NullPointerException {
        List<Double> city = null;

        Iterator<List<Double>> it = allCitiesContainer.iterator();
        while (it.hasNext()) {
            city = it.next();
            // The location is ALWAYS an integer. However, in the 
            // allCitiesContainer list, this value is saved off on a field of
            // type double.
            if (Math.round(city.get(LOCATION)) == location) {
                break;
            }
        }//end of while
        if (city == null) {
            throw new NullPointerException("No location: " + location + 
                    " available.");
        }
        return city;
    }

    /**
     * This function returns the name of the .tsp file
     * @return String The name of the .tsp file.
     */
    public String getName() {
        return this.name;
    }

    public String getComment() {
        return this.comment;
    }

    public String getType() {
        return this.type;
    }

    /**
     * This function returns the dimensioned specified by the .tsp file. 
     * This is equal to the number of nodes/cities in the .tsp.
     * @return number of locations/nodes
     */
    public int getDimension() {
        return this.dimension;
    }//end of getDimension()

    
    /**
     * This function returns the edge-weigh-type saved off from the .tsp file
     * @return The edge-weigh-type saved off from the .tsp file
     */
    public String getEdgeWeighType() {
        return this.edgeWeighType;
    }//end of getEdgeWeighType()
    
    
    /**
     * This function returns the x-coordinate for the specified node.
     * @param location This is equal to the node or city
     * @return x-coordinate for the specified node.
     */
    public double getXCoord(int location) {
        double x_coord = -1f;
        Iterator<List<Double>> it = allCitiesContainer.iterator();
        while (it.hasNext()) {
            List<Double> city = it.next();
            if (Math.round(city.get(LOCATION)) == location) {
                x_coord = city.get(X_COORD);
                break;
            }
        }
        return x_coord;
    }//end of getXCoord()

    
    /**
     * This function returns the y-coordinate for the specified node.
     * @param location This is equal to the node or city
     * @return y-coordinate for the specified node.
     */
    public double getYCoord(int location) {
        double y_coord = -1;
        Iterator<List<Double>> it = allCitiesContainer.iterator();
        while (it.hasNext()) {
            List<Double> city = it.next();
            if (Math.round(city.get(LOCATION)) == location) {
                y_coord = city.get(Y_COORD);
                break;
            }
        }
        return y_coord;
    }//end of getYCoord()

    
    /**
     * This function formats the solution to the tour. It takes an array 
     * representing the solution and returns this formated string.  
     * @param tour An array containing a tour solution.
     * @return formatted solution
     */
    public String printTour(ArrayList<Integer> tour) {
        StringBuilder line = new StringBuilder();
        line.append("\n");
        line.append("NAME: ").append(this.name).append("\n");
        line.append("TYPE: ").append(this.type).append("\n");
        line.append("DIMENSION: ").append(this.dimension).append("\n");
        line.append("TOUR_SECTION" + "\n");

        Iterator<Integer> it = tour.iterator();
        while (it.hasNext()) {
            Integer location = it.next();
            line.append(location).append("\n");
        }
        line.append("-1");
        line.append("\n\n");
        return line.toString();
    }

    @Override
    public String toString() {
        StringBuilder line = new StringBuilder();
        line.append("\n");
        line.append("NAME: ").append(this.name).append("\n");
        line.append("COMMENT: ").append(this.comment).append("\n");
        line.append("TYPE: TSP ").append(this.type).append("\n");
        line.append("DIMENSION: ").append(this.dimension).append("\n");
        line.append("EDGE_WEIGHT_TYPE: ").append(this.edgeWeighType);
        line.append("\n");
        line.append("NODE_COORD_SECTION" + "\n");

        Iterator<List<Double>> it = allCitiesContainer.iterator();
        while (it.hasNext()) {
            List<Double> city = it.next();
            line.append(city.get(LOCATION)).append(" ");
            line.append(city.get(X_COORD)).append(" ");
            line.append(city.get(Y_COORD)).append("\n");
        }
        line.append("EOF");
        return line.toString();
    }//end of toString();
    
    
    /**
     * This function calculates all the distances between the different 
     * nodes/locations and stores them in the adjacency matrix for later use.
     */
    private void populateMatrix() {
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {

                if (i == j) {
                    // Same city skip
                    matrix[i][j] = -1;
                } else {
                    matrix[i][j] = calculateDistances(i, j);
                }
            }
        }
    }//end of populateMatrix()
    
    
    /**
     * This function calculates the distance between two nodes/locations.
     * @param location1 The starting node/location
     * @param location2 The destination node/location
     * @return The distance between the specified nodes.
     */
    private double calculateDistances(int location1, int location2) {

        double x1 = allCitiesContainer.get(location1).get(X_COORD);
        double y1 = allCitiesContainer.get(location1).get(Y_COORD);

        double x2 = allCitiesContainer.get(location2).get(X_COORD);
        double y2 = allCitiesContainer.get(location2).get(Y_COORD);       
        
              
        // dist((x, y), (a, b)) = √(x - a)² + (y - b)²
        double x = Math.pow((x1 - x2), 2);
        double y = Math.pow((y1 - y2), 2);        
        
        return Math.sqrt( x + y );    
    }//end of calculateDistances()
    
    
    
    /**
     * This functions returns the Adjacency Matrix. This matrix consist of all
     * the calculated distances from one node to another.
     * @return A two dimensional array containing all distances between nodes.
     */
    public double[][] getAdjacencyMatrix(){
        return matrix;
    }//end of getAdjacencyMatrix()
    
    
    /**
     * This functions sets (save off) the .tsp filename used as input  
     * @param filename 
     */
    public void setFilename(String filename){
        this.filename = filename;
    }//end of setFilename()
    
    
    /**
     * This functions returns the name of the .tsp used as input.
     * @return input filename 
     */
    public String getFilename(){
        return filename;
    }//end of getFilename()
    
}
