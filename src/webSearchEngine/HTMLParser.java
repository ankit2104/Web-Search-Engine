package webSearchEngine;



import java.util.*;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class HTMLParser {

/**
	 * Method to save the HTML document
	 * 
	 * @param doc
	 * @param weburl

	 */

	public static void saveHTMLDoc(Document doc, String weburl) {
		try {
		
PrintWriter htmlfile = new PrintWriter(new FileWriter(PathFinder.htmlDirectoryPath + doc.title().replace('/', '_') + ".html"));

htmlfile.write(doc.toString());
htmlfile.close();

parseHtml(PathFinder.htmlDirectoryPath + doc.title().replace('/', '_') + ".html", weburl, doc.title().replace('/', '_') + ".txt");

	} catch (Exception e) {

		}

	}
/**
	 * Method to save HTML file to text file
	 * 
	 * @param htmlfile
	 * @param weburl
	 * @param filename

	 */



	public static void parseHtml(String htmlfile, String weburl, String textfile) throws Exception {
		File nfile = new File(htmlfile);
		Document doc = Jsoup.parse(nfile, "UTF-8");
		String data = doc.text().toLowerCase();
		data = weburl + "::" + data;
		PrintWriter filewrite = new PrintWriter(PathFinder.txtDirectoryPath + textfile);
		filewrite.println(data);
		filewrite.close();
	}
}

