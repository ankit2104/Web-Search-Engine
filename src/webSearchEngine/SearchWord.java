package webSearchEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchWord {
	static ArrayList<String> key = new ArrayList<String>();
	static HashMap<String, Integer> numbers = new HashMap<String, Integer>();
		
		// suggest alternative word in case we don't get the word searched. 
		public static void suggestAlternativeWord(String wordToBeSearched) {
			String line = " ";
			String regexpattern = "[a-z0-9]+";
			int fileNumber = 0;

			// Create a regex.Pattern object
			Pattern patternobj = Pattern.compile(regexpattern);
			
			// Now create regex.matcher object
			Matcher matcherobj = patternobj.matcher(line);
			
			// get access to the list of files from the directory path provided in PathFinder class
			File directory = new File(Path.txtDirectoryPath);
			File[] fileList = directory.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				try {
					
					// call findWord function to get word from the list of text files.
					findWord(fileList[i], fileNumber, matcherobj, wordToBeSearched);
					fileNumber++;
				} catch (FileNotFoundException err) {
					err.printStackTrace();
				}
			}
			
			// How much distance the word can be edited to find similar words
			Integer allowedDistance = 1; 
			boolean matchFound = false;

			int i = 0;
			for (Map.Entry entry : numbers.entrySet()) {
				if (allowedDistance == entry.getValue()) {
					
					i++;
					
					if(i==1)
					System.out.println("Did you mean? ");
					
					System.out.print("(" + i + ") " + entry.getKey() + "\n");
					matchFound = true;
				}
			}
			if (!matchFound)
				System.out.println("Entered word cannot be resolved....");
		}

		// finds strings with similar pattern and calls edit distance() on those strings
		public static void findWord(File srcFile, int fileNumber, Matcher match, String str)
				throws FileNotFoundException, ArrayIndexOutOfBoundsException {
			try {
				BufferedReader bufferObject = new BufferedReader(new FileReader(srcFile));
				String line = null;

				while ((line = bufferObject.readLine()) != null) {
					match.reset(line);
					while (match.find()) {
						key.add(match.group());
					}
				}

				bufferObject.close();
				for (int l = 0; l < key.size(); l++) {
					numbers.put(key.get(l), editDistanceAlgo(str.toLowerCase(), key.get(l).toLowerCase()));
				}
			} catch (Exception e) {
				System.out.println("Exception:" + e);
			}

		}
		
		// edit distance algorithm implementation
		public static int editDistanceAlgo(String word1, String word2) {
			int length1 = word1.length();
			int length2 = word2.length();

			int[][] my_array = new int[length1 + 1][length2 + 1];

			for (int i = 0; i <= length1; i++) {
				my_array[i][0] = i;
			}

			for (int j = 0; j <= length2; j++) {
				my_array[0][j] = j;
			}

			for (int i = 0; i < length1; i++) {
				char c1 = word1.charAt(i);
				for (int j = 0; j < length2; j++) {
					char c2 = word2.charAt(j);

					if (c1 == c2) {
						my_array[i + 1][j + 1] = my_array[i][j];
					} else {
						int replace = my_array[i][j] + 1;
						int insert = my_array[i][j + 1] + 1;
						int delete = my_array[i + 1][j] + 1;

						int min = replace > insert ? insert : replace;
						min = delete > min ? min : delete;
						my_array[i + 1][j + 1] = min;
					}
				}
			}

			return my_array[length1][length2];
		}

}
