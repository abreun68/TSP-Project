/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.rowan.algorithms;

/**
 *
 * @author Nacer Abreu
 */
public class Euclidean {

    public static double distance(int[] p, int[] q) {
        double a = 0.0;
        // dist((x, y), (a, b)) = √(x - a)² + (y - b)²
        // dist((2, -1), (-2, 2))
        a = Math.sqrt((Math.pow((p[0] - q[0]), 2)) + (Math.pow((p[1] - q[1]), 2)));

        return a;
    }

}
