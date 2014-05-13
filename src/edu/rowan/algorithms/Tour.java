
package edu.rowan.algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author nabreu
 */
public class Tour {

    private String name;
    private String comment;
    private String type;
    private int dimension;
    private String edgeWeighType;
    private ArrayList<List<Integer>> container;
    private boolean inNodesSection;
    private static final int LOCATION = 0;
    private static final int X_COORD  = 1;
    private static final int Y_COORD  = 2;
    
    public Tour() {
        name = "";
        comment = "";
        type = "";
        dimension = 0;
        edgeWeighType = "";
        container = new ArrayList<List<Integer>>();
        boolean inNodesSection = false;
    }

    /**
     * Parses lines that complaint with the TSPLIB file format.
     * @param line 
     */
    public void parseLine(String line) {

        if (line.contains("NAME:")) {
            String[] split = line.split(":");
            this.name = split[1].trim();
        }

        if (line.contains("COMMENT:")) {
            String[] split = line.split(":");
            this.comment = split[1].trim();
        }

        if (line.contains("TYPE:")) {
            String[] split = line.split(":");
            this.type = split[1].trim();
        }

        if (line.contains("DIMENSION:")) {
            String[] split = line.split(":");
            this.dimension = Integer.parseInt(split[1].trim());
        }

        if (line.contains("EDGE_WEIGHT_TYPE:")) {
            String[] split = line.split(":");
            this.edgeWeighType = split[1].trim();
        }

        if (line.contains("EOF")) {
            this.inNodesSection = false;
        }

        if (this.inNodesSection) {
            String[] split = line.split(" ");
            int location = Integer.parseInt(split[0].trim());
            int x = Integer.parseInt(split[1].trim());
            int y = Integer.parseInt(split[2].trim());
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

    public void addCity(int location, int x_coord, int y_coord) {
        List<Integer> city = new ArrayList<Integer>();
        city.add(location);
        city.add(x_coord);
        city.add(y_coord);
        container.add(city);
    }

    /**
     * This function returns an array list of all city locations.
     * @return 
     */
    public ArrayList<Integer> getCities() {
        ArrayList<Integer> cities = new ArrayList<Integer>();
        Iterator<List<Integer>> it = container.iterator();
        while (it.hasNext()) {
            List<Integer> city = it.next();
            cities.add(city.get(LOCATION));
        }
        return cities;
    }
    
    /**
     * This function returns a list containing the city location, x and y 
     * coordinates.  
     * @param location The specific city location to get.
     * @return
     * @throws NullPointerException 
     */
    public List<Integer> getCity(int location) throws NullPointerException {
        List<Integer> city = null;
        
        Iterator<List<Integer>> it = container.iterator();
        while (it.hasNext()) {
            city = it.next();
            if (city.get(LOCATION) == location) {
                break;
            }
        }//end of while
        if(city == null){
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
     * @return 
     */
    public int getDimension() {
        return this.dimension;
    }

    public String getEdgeWeighType() {
        return this.edgeWeighType;
    }

    public int getXCoord(int location) {
        int x_coord = -1;
        Iterator<List<Integer>> it = container.iterator();
        while (it.hasNext()) {
            List<Integer> city = it.next();
            if (city.get(LOCATION) == location) {
                x_coord = city.get(X_COORD);
                break;
            }
        }
        return x_coord;
    }//end of getXCoord()

    public int getYCoord(int location) {
        int y_coord = -1;
        Iterator<List<Integer>> it = container.iterator();
        while (it.hasNext()) {
            List<Integer> city = it.next();
            if (city.get(LOCATION) == location) {
                y_coord = city.get(Y_COORD);
                break;
            }
        }
        return y_coord;
    }//end of getYCoord()

    @Override
    public String toString() {
        StringBuilder line = new StringBuilder();
        line.append("NAME: ").append(this.name).append("\n");
        line.append("COMMENT: ").append(this.comment).append("\n");
        line.append("TYPE: TSP ").append(this.type).append("\n");
        line.append("DIMENSION: ").append(this.dimension).append("\n");
        line.append("EDGE_WEIGHT_TYPE: ").append(this.edgeWeighType);
        line.append("\n");
        line.append("NODE_COORD_SECTION" + "\n");
        
        Iterator<List<Integer>> it = container.iterator();
        while (it.hasNext()) {
            List<Integer> city = it.next();            
            line.append(city.get(LOCATION)).append(" ");
            line.append(city.get(X_COORD)).append(" ");
            line.append(city.get(Y_COORD)).append("\n");
        }
        line.append("EOF");
        return line.toString();
    }//end of toString();
}
