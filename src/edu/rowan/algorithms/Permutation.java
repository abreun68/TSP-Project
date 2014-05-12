
package edu.rowan.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author nabreu
 */


public class Permutation {

    private ArrayList<Integer> list;

    public Permutation(ArrayList<Integer> list) {

        this.list = list;
    }

    public void add(int pos) {
        this.list.add(pos);        
    }
}
