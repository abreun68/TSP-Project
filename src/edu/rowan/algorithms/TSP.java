package edu.rowan.algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.prefs.Preferences;

/**
 * Entry point for the application. It creates a instance of a Tour object and
 * fills it with the information from the .tsp file.
 *
 * @author Nacer Abreu & Emmanuel Bonilla
 */
public class TSP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String filename;
        Preferences prefsRoot = Preferences.userRoot();
        Preferences myPrefs = prefsRoot
                .node("edu.rowan.alogorithms.preference.staticPreferenceLoader");

        if (0 < args.length) {
            // Accepts the .tsp filename from the command prompt 
            filename = args[0];

        } else {
            
            // Prompt the user for the .tsp filename.
            Scanner scanner = new Scanner(System.in);            
            System.out.println("Default File: [" + myPrefs.get("lastFile", "") + "]");
            System.out.print("Enter the file name: ");
            System.out.flush();
            filename = scanner.nextLine();
            
            // The following logic is purely for convenience. It allows me to 
            // enter the filename only once while testing.  
            if (filename.length() == 0){
                filename = myPrefs.get("lastFile", "");
            }else{
                myPrefs.put("lastFile",filename);
            }
                        
        }

        Tour tour = new Tour();
        tour.setFilename(filename);
        
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

        
//        // System.out.println(tour.toString());
//        BruteForceSolver bruteForce = new BruteForceSolver(tour);
//        bruteForce.generatePermutations();
//        ArrayList<Integer> shortestTour = bruteForce.getShortestTour();
//        String answer = tour.printTour(shortestTour);
//        System.out.println(answer);
//        System.out.println("Solution :" + bruteForce.getShortestTour() + 
//                ", Dist.: " + bruteForce.getShortestDistance() + "\n");
//        
        
        NearestNeighborSolver nn = new NearestNeighborSolver(tour);  
        ArrayList<Integer> shortestTour = nn.getShortestTour();        

    }
}
