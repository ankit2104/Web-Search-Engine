package webSearchEngine;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.Pattern;

public class WebSearchEngine {
	
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		WebSearchEngine SearchEngine = new WebSearchEngine();
		String choice = "n";
		System.out.println("\tWelcome to Doodle Search Engine");
			
		do {
		System.out.println("\tselect the option mentioned below \n ");
			System.out.println(" a) Enter 'a' to start Web Creawling from the URL you will pass");
			System.out.println(" b) Enter 'b' for the Web search from static URL (www.w3schools.com)");
			System.out.println(" c) Enter 'c' for Exit ");
			
			String SelectOption = sc.next();

			switch (SelectOption) {
			case "a":
				System.out.println("\n Please enter valid URL for example 'www.abc.com'");
				String URL = sc.next();
				URL = "https://"+URL+"/";
				choice = SearchEngine.searchWord(URL);
				break;

			case "b":
				choice = SearchEngine.searchWord("https://www.w3schools.com/");
				break;

			case "c":
				System.out.println("Exit...");
				choice = "n";
				break;

			default:
				System.out.println("Please enter correct option");
				choice = "y";

			}
		} while (choice.equals("y"));
		
		System.out.println("Developed by: Doodle developers");                 
	}

	private String searchWord(String URL) {
		
		if(!isValid(URL)) {
			 System.out.println("Enterd URL " + URL + " is not valid");
			 System.out.println("Sorry.. Please try again....\n");
			 return "y";
		}
		
		System.out.println("Enterd URL " + URL + " is valid\n");
		
		System.out.println("Web crawling started...");
		WebCrawler.startCrawler(URL, 0); 						
		System.out.println("Web crawling completed...");
		
		System.out.println("\nHTML links are saving in the 'htmlFiles' Folder...");
		System.out.println("HTML links has been saved...");
		System.out.println("\nHTML to Text process has been started...");
		System.out.println("Processing....");
		System.out.println("\nHTMLtoText Completed....");

		
		Hashtable<String, Integer> FileList = new Hashtable<String, Integer>();
		
		String choice = "y";
		do {
			System.out.println("\nSearch Querry:");
					
			System.out.println("\nEnter a word you want to search");
			String wordToSearch = sc.next();
			
			int NumberofWords = 0;
			int TotalNoOfFiles = 0;
			FileList.clear();
			try {
				System.out.println("\nSearching the word in the text file...");
				File filesPath = new File(PathFinder.txtDirectoryPath);

				File[] ListoftxtFilesArray = filesPath.listFiles();

				for (int cnt = 0; cnt < ListoftxtFilesArray.length; cnt++) {

					In filePath = new In(ListoftxtFilesArray[cnt].getAbsolutePath());

					String txtfile = filePath.readAll();
					filePath.close();
					Pattern pattern = Pattern.compile("::");
					String[] fileName = pattern.split(txtfile);
					NumberofWords = SearchWord.WordSearchUsingKMP(txtfile, wordToSearch.toLowerCase(), fileName[0]); // search word in txt files

					if (NumberofWords != 0) {
						FileList.put(fileName[0], NumberofWords);
						TotalNoOfFiles++;
					}
				}

				if(TotalNoOfFiles>0) {
				System.out.println("\nNumber of files which is containing the word : " + wordToSearch + " is : " + TotalNoOfFiles);
				}
				else {
					System.out.println("\n File not found for the word containing : "+ wordToSearch);
					System.out.println("\nSeeing if an alternative word exists...");		
					
					// suggest another word if entered word not found
					SearchWord.suggestAlternativeWord(wordToSearch.toLowerCase()); 
				}
				
				//display the files with respect to the rank based on frequency of word occurrence
				SearchWord.rankFiles(FileList, TotalNoOfFiles); 				   
				

			} catch (Exception e) {
				System.out.println("Exception:" + e);
			}
			System.out.println("\n Do you want return to search another word(y/n)?");
			choice = sc.next();
		} while (choice.equals("y"));
		
		// delete the files created if the user want to start with new URL
		deleteFiles();					
		
		// returns to the main menu or exit the code.
		System.out.println("\n Do you want return to main menu(y/n)?");    
		return sc.next();
	}

	// deletes all files created while crawling and word search.
		private void deleteFiles() {
			File ListofTxtFiles = new File(PathFinder.txtDirectoryPath);
			File[] ListoftxtFilesArray = ListofTxtFiles.listFiles();

			for (int cnt = 0; cnt < ListoftxtFilesArray.length; cnt++) {
				ListoftxtFilesArray[cnt].delete();
			}
			
			File ListofHTMLFiles= new File(PathFinder.htmlDirectoryPath);
			File[] ListofHTMLFilesArray = ListofHTMLFiles.listFiles();

			for (int cnt = 0; cnt < ListofHTMLFilesArray.length; cnt++) {
				
				ListofHTMLFilesArray[cnt].delete();
			}
		}
		
		/**
		 * It will validate the URL typed by user
		 * @param url
		 * @return
		 */
		public static boolean isValid(String url)
	    {
	        /* Try creating a valid URL */
	        try {
	        	System.out.println("Validating the URL...");
	        	URL URLObject = new URL(url);
	            HttpURLConnection connection = (HttpURLConnection) URLObject.openConnection();
	            //Sending the request
	            connection.setRequestMethod("GET");
	            int getresponse = connection.getResponseCode();
	            if(getresponse==200) 
	            {
	            	 return true;
	            }
	            else
	            {
	            	return false;
	            }
	           
	        }
	          
	        // If there was an Exception
	        // while creating URL object
	        catch (Exception e) {
	            return false;
	        }
	    }

}
