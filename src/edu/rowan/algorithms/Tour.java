
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

    public void addCity(int location, int x, int y) {
        List<Integer> city = new ArrayList<Integer>();
        city.add(location);
        city.add(x);
        city.add(y);
        container.add(city);
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
            if (city.get(0) == location) {
                x_coord = city.get(1);
            }
        }
        return x_coord;
    }//end of getXCoord()

    public int getYCoord(int location) {
        int y_coord = -1;
        Iterator<List<Integer>> it = container.iterator();
        while (it.hasNext()) {
            List<Integer> city = it.next();
            if (city.get(0) == location) {
                y_coord = city.get(2);
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
        line.append("EDGE_WEIGHT_TYPE: ").append(this.edgeWeighType).append("\n");
        line.append("NODE_COORD_SECTION" + "\n");
        
        Iterator<List<Integer>> it = container.iterator();
        while (it.hasNext()) {
            List<Integer> list = it.next();
            
            int loc = list.get(0);
            int x   = list.get(1);
            int y   = list.get(2);
            
            line.append(loc).append(" ").append(x).append(" ").append(y).append("\n");
        }
        line.append("EOF");
        return line.toString();
    }//end of toString();
}
