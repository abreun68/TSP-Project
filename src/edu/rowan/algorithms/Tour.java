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
    private ArrayList<List<Double>> container;
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
        container = new ArrayList<List<Double>>();
        boolean inNodesSection = false;
    }

    /**
     * Parses lines that complaint with the TSPLIB file format.
     *
     * @param line
     */
    public void parseLine(String line) {

        if (line.contains("NAME")) {
            String[] split = line.split(":");
            this.name = split[1].trim();
        }

        if (line.contains("COMMENT")) {
            String[] split = line.split(":");
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
            System.out.println("Done!");
        }

        if (this.inNodesSection) {
            String[] split = line.split("\\s+");
            int location = Integer.parseInt(split[0].trim());
            double x = Double.parseDouble(split[1].trim());
            double y = Double.parseDouble(split[2].trim());
            addCity(location, x, y);
        }

        if (line.contains("NODE_COORD_SECTION")) {
            this.inNodesSection = true;
        }

    }//end of parseLine

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public void setEdgeWeighType(String edgeWeighType) {
        this.edgeWeighType = edgeWeighType;
    }

    public void addCity(int location, double x_coord, double y_coord) {
        List<Double> city = new ArrayList<Double>();
        city.add((double) location);
        city.add(x_coord);
        city.add(y_coord);
        container.add(city);
    }

    /**
     * This function returns an array list of all city locations.
     *
     * @return
     */
    public ArrayList<Integer> getCities() {
        ArrayList<Integer> cities = new ArrayList<Integer>();
        Iterator<List<Double>> it = container.iterator();
        while (it.hasNext()) {
            List<Double> city = it.next();
            cities.add(city.get(LOCATION).intValue());
        }
        return cities;
    }

    /**
     * This function returns a list containing the city location, x and y
     * coordinates.
     *
     * @param location The specific city location to get.
     * @return
     * @throws NullPointerException
     */
    public List<Double> getCity(int location) throws NullPointerException {
        List<Double> city = null;

        Iterator<List<Double>> it = container.iterator();
        while (it.hasNext()) {
            city = it.next();
            // The location is ALWAYS an integer. However, the 'city' container
            // stores doubles. 
            if (Math.round(city.get(LOCATION)) == location) {
                break;
            }
        }//end of while
        if (city == null) {
            throw new NullPointerException("No location: " + location + " available.");
        }
        return city;
    }

    /**
     * This function returns the name of the .tsp file
     *
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
     * This function returns the dimensioned specified by the .tsp file
     *
     * @return
     */
    public int getDimension() {
        return this.dimension;
    }

    public String getEdgeWeighType() {
        return this.edgeWeighType;
    }

    public double getXCoord(int location) {
        double x_coord = -1f;
        Iterator<List<Double>> it = container.iterator();
        while (it.hasNext()) {
            List<Double> city = it.next();
            if (Math.round(city.get(LOCATION)) == location) {
                x_coord = city.get(X_COORD);
                break;
            }
        }
        return x_coord;
    }//end of getXCoord()

    public double getYCoord(int location) {
        double y_coord = -1;
        Iterator<List<Double>> it = container.iterator();
        while (it.hasNext()) {
            List<Double> city = it.next();
            if (Math.round(city.get(LOCATION)) == location) {
                y_coord = city.get(Y_COORD);
                break;
            }
        }
        return y_coord;
    }//end of getYCoord()

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

        Iterator<List<Double>> it = container.iterator();
        while (it.hasNext()) {
            List<Double> city = it.next();
            line.append(city.get(LOCATION)).append(" ");
            line.append(city.get(X_COORD)).append(" ");
            line.append(city.get(Y_COORD)).append("\n");
        }
        line.append("EOF");
        return line.toString();
    }//end of toString();
    
    
    private void populateMatrix() {
        
        for (int i = 0; i < this.dimension; i++){
            for (int j = 0; j < this.dimension; j++){
             
                if ( i == j ){
                    // Same city skip
                    matrix[i][j] = -1;
                } else{
                    matrix[i][j] = calculateDistances(i, j);
                }
            }            
        }
        
        
    }//end of populateMatrix()
    
    
    private double calculateDistances(int location1, int location2) {

        double x1 = container.get(location1).get(X_COORD);
        double y1 = container.get(location1).get(Y_COORD);

        double x2 = container.get(location2).get(X_COORD);
        double y2 = container.get(location2).get(Y_COORD);       
        
              
        // dist((x, y), (a, b)) = √(x - a)² + (y - b)²
        double distance  = 0.0;
        double x = Math.pow((x1 - x2), 2);
        double y = Math.pow((y1 - y2), 2);
        distance = Math.sqrt( x + y );
        
        return distance;       
    }
    
    
}
