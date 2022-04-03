package webSearchEngine;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.Pattern;

public class WebSearchEngine {
	
	private static Scanner sc = new Scanner(System.in);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		WebSearchEngine SearchEngine = new WebSearchEngine();
		String choice = "n";
		System.out.println("\n*************************************************************************\n");
		System.out.println("                       Welcome to Web Search Engine                      ");
		System.out.println("\n*************************************************************************\n");
		System.out.println("                               Team Members                              \n");
		System.out.println("                 Asad Mehmood, Ankit Kunwar, Anuroop Sreepuram,                 ");
		System.out.println("            		Fazil Mahesania, Jijo Sabu Johns              ");
		System.out.println("\n*************************************************************************\n");
		do {
		System.out.println("                    select the option mentioned below                   \n ");
		System.out.println("*************************************************************************\n");
			System.out.println(" 1) Enter 1 for the Web search from the URL you will pass");
			System.out.println(" 2) Enter 2 for the Web search from static URL (https://www.javatpoint.com/)");
			System.out.println(" 3) Enter 3 for Exit ");
			System.out.println("\n*************************************************************************\n");
			int SelectOption = sc.nextInt();

			switch (SelectOption) {
			case 1:
				System.out.println("\n Please enter valid URL for example 'www.xyz.com'");
				String URL = sc.next();
				URL = "https://"+URL+"/";
				choice = SearchEngine.searchWord(URL);
				break;

			case 2:
				choice = SearchEngine.searchWord("https://www.javatpoint.com/");
				break;

			case 3:
				System.out.println("Exit...");
				choice = "n";
				break;

			default:
				System.out.println("Please enter correct option");
				choice = "y";

			}
		} while (choice.equals("y"));

		System.out.println("\n*************************************************************************\n");
		System.out.println("               :) THANK YOU FOR USING WEB SEARCH ENGINE :)               ");
		System.out.println("\n*************************************************************************\n");
	}

	private String searchWord(String URL) {
		
		if(!isValid(URL)) {
			 System.out.println("Enterd URL " + URL + " isn't valid");
			 System.out.println("Please try again....\n");
			 return "y";
		}
		
		System.out.println("Enterd URL " + URL + " is valid\n");
		
		System.out.println("Crawling Started...");
		WebCrawler.startCrawler(URL, 0); 						//crawling the URL
		System.out.println("Crawling Compelted...");

		// Hash table is used instead of Hash Map as it don't allow null value in insertion
		Hashtable<String, Integer> FileList = new Hashtable<String, Integer>();
		
		String choice = "y";
		do {
			System.out.println("\n*************************************************************************");
			System.out.println("\n                     Enter a word you want to search                     ");
			System.out.println("\n*************************************************************************");
			String wordToSearch = sc.next();
			System.out.println("\n*************************************************************************");
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
				System.out.println("\nTotal count of files which is containing the word : " + wordToSearch + " is : " + TotalNoOfFiles);
				}else {
					System.out.println("\n File not found for the word containing : "+ wordToSearch);
					SearchWord.suggestAlternativeWord(wordToSearch.toLowerCase()); // suggest another word if entered word not found
				}

				SearchWord.rankFiles(FileList, TotalNoOfFiles); 				   //rank the files based on frequency of word count
				

			} catch (Exception e) {
				System.out.println("Exception:" + e);
			}
			System.out.println("\n Do you want return to search another word(y/n)?");
			choice = sc.next();
		} while (choice.equals("y"));
		
		deleteFiles();					// delete the files created if the user do not want to search any other words and want to start with new URL
		
		System.out.println("\n Do you want return to main menu(y/n)?");   // returns to the main menu to choose from new/static URL or exit the code. 
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
		 * It will validate URL entered by user with DNS
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
