
package edu.rowan.algorithms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Nacer Abreu
 */
public class TSP {
// C:\Users\nabreu\Desktop\Algorithms\inputs\inputs\brute10.tsp

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String filename;

        if (0 < args.length) {
            // Accepts the .tsp filename from the command prompt 
            filename = args[0];

        } else {
            // Prompt the user for the .tsp filename.
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the file name: ");
            System.out.flush();
            filename = scanner.nextLine();
        }

        Tour tour = new Tour();
        try {
            String readLine = "";
            BufferedReader br = new BufferedReader(new FileReader(filename));
            // Read-in the .tsp file line-by-line
            while ((readLine = br.readLine()) != null) {
                tour.parseLine(readLine);
            } // end while 
        } // end try
        catch (IOException e) {
            System.err.println("Error: " + e);
        }

        System.out.println(tour.toString());
        System.out.println(tour.getXCoord(3));

    }//end of main()
}
