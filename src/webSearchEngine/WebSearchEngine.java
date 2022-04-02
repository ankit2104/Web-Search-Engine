package webSearchEngine;

import java.io.IOException;
import java.util.Scanner;


//import textprocessing.BoyerMoore;

public class WebSearchEngine {
	
	
	
	/**
	  * Question 1.b & 1.c Started - Implemented BoyerMoore method 
	  *
	  */
	public static void 	AssignmentAnswerOneBoyerMoore(String [] listOfWord, String readAllLines ) 
	{
		
		 int counter = 0;
		int lineoffset =0;
		for(int ct=0;ct<listOfWord.length;ct++)
		{
		counter = 0;
		BoyerMoore boyerMoore = new BoyerMoore(listOfWord[ct].toLowerCase());
		Scanner scannerboyerMoore = new Scanner(readAllLines);
		while (scannerboyerMoore.hasNextLine()) { //iterating each line of the files.
		 String line = scannerboyerMoore.nextLine().toLowerCase();
		 //System.out.println(line);  
		 int offset1 = boyerMoore.search(line); //getting the first occurrence in the line 
		 System.out.print("offset are - "+offset1);
		 lineoffset = offset1;
		 if(offset1!=line.length()) //going to check the offset for the remaining portion of the line
		 {
			 counter++;
			 while(offset1!=line.length())
			 {
				 int pos =offset1+listOfWord[ct].length();
				 line = line.substring(pos);	//getting the remaining substring of the line
				 offset1 = boyerMoore.search(line);			//finding the offset in the substring
				 int currentoffset = listOfWord[ct].length()+ offset1;
				 lineoffset+=currentoffset;
				  
				 if(offset1!=line.length())
				 {
					 counter++;
					 System.out.print(" " +lineoffset);
				 }
			 }
		 }
		 System.out.println("\n");
		  
		}
		scannerboyerMoore.close();
		System.out.println();
		System.out.println("Total number of count using Boyer Moore is " + counter);
	 }
}
	/**
	  * Question 1.b & 1.c Started - Implemented BruteForceMatch method 
	  *
	  */
	
	
	//start the implementation
	
	public static void main(String[] args) throws IOException 
	{
		System.out.println("\n------------Question 1.b Started-----------------\n");
		long totalTimeBoyerMoore =0;
		In Files = new In("src/Hard disk.txt");
		String [] listOfWord = {"Hard" };/*"disk", "hard disk", "hard drive", "hard dist" , "xltpru"};*/
		String readAllLines = Files.readAll();			//reading the file.
		
		long startTimeForBoyerMoore= System.nanoTime();
		AssignmentAnswerOneBoyerMoore(listOfWord, readAllLines ); //calling for the BoyerMoore method to obtain the offset.
		long endTimeForBoyerMoore= System.nanoTime();
		
		
		
		totalTimeBoyerMoore= endTimeForBoyerMoore - startTimeForBoyerMoore;
		
		System.out.println("Total time for Boyer Moore is " + totalTimeBoyerMoore);
		System.out.println("\n------------Question 1.b End-----------------\n");
		
		
	}

}
