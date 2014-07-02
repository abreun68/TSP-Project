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

        /**
         *  Variable declaration
         */
        String filename = "";
        Preferences prefsRoot = Preferences.userRoot();
        Preferences myPrefs = prefsRoot
               .node("edu.rowan.alogorithms.preference.staticPreferenceLoader");

        final int DEFAULT = 0;
        final int BRUTEFORCE = 1;
        final int NEAREST = 2;
        final int BRANCHANDBOUND=3;
        int strategy = DEFAULT;
        
        ArrayList<Integer> shortestTour;
        
        
        /** 
         * Parse arguments and switches
         */
        if (0 < args.length) {
            // Accepts the .tsp filename from the command prompt 
            // filename = args[0];
            
            for (int i = 0; i < args.length; i++){
                if ((args[i].equalsIgnoreCase("--BruteForce"))){
                    strategy = BRUTEFORCE;
                }
                else if((args[i].equalsIgnoreCase("--Nearest"))){
                    strategy = NEAREST;
                }
                else if((args[i].equalsIgnoreCase("--BranchAndBound"))){
                    strategy = BRANCHANDBOUND;
                }
                else{
                    filename = args[0];
                }
            }//end of for...loop

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
            scanner.close();
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
            br.close();
        } // end try
        catch (IOException e) {
            System.err.println("Error: " + e);
        }

        
        switch (strategy) {
        //case DEFAULT:    
        case BRUTEFORCE:
                // System.out.println(tour.toString());
        		long startTime = System.currentTimeMillis();
                BruteForceSolver bruteForce = new BruteForceSolver(tour);
                bruteForce.generatePermutations();
                shortestTour = bruteForce.getShortestTour();
                
                String answer = tour.printTour(shortestTour);
                System.out.println(answer);
                System.out.println("Solution :" + bruteForce.getShortestTour()
                      + ", Dist.: " + bruteForce.getShortestDistance() + "\n");
            	long endTime   = System.currentTimeMillis();
            	long totalTime = endTime - startTime;
            	System.out.println("Total time to execute: "+totalTime+" ms");
                break;
              
            case NEAREST:
                NearestNeighborSolver nn = new NearestNeighborSolver(tour);
                shortestTour = nn.getShortestTour();
                String answer2 = tour.printTour(shortestTour);
                System.out.println(answer2);
                break;
            
            case DEFAULT:
            case BRANCHANDBOUND:
            	startTime = System.currentTimeMillis();
            	BranchAndBoundSolver bab = new BranchAndBoundSolver(tour);
            	endTime   = System.currentTimeMillis();
            	totalTime = endTime - startTime;
            	System.out.println("Total time to execute: "+totalTime+" ms");
            	//shortestTour = bab.getShortestTour();
            	//String answer3 = tour.printTour(shortestTour);
            	//System.out.println(answer3);
            	break;
        }//end of switch statement
    }//end of main()
}// end of class TSP
