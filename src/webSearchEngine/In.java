package webSearchEngine;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Scanner;

public final class In {
	
	private Scanner scanner;
	
    // assume Unicode UTF-8 encoding
    private static final String CHARSET_NAME = "UTF-8";

    // assume language = English, country = US for consistency with System.out.
    private static final Locale LOCALE = Locale.US;

    // the default token separator; we maintain the invariant that this value 
    // is held by the scanner's delimiter between calls
    private static final Pattern WHITESPACE_PATTERN
        = Pattern.compile("\\p{javaWhitespace}+");

    // makes whitespace characters significant 
    private static final Pattern EMPTY_PATTERN
        = Pattern.compile("");

    // used to read the entire input. source:
    // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
    private static final Pattern EVERYTHING_PATTERN
        = Pattern.compile("\\A");

	 /**
     * Create an input stream from a filename or web page name.
     */
    public In(String s) {
        try {
            // first try to read file from local file system
            File file = new File(s);
            if (file.exists()) {
                scanner = new Scanner(file, CHARSET_NAME);
                scanner.useLocale(LOCALE);
                return;
            }

            // next try for files included in jar
            URL url = getClass().getResource(s);

            // or URL from web
            if (url == null) { url = new URL(s); }

            URLConnection site = url.openConnection();

            // in order to set User-Agent, replace above line with these two
            // HttpURLConnection site = (HttpURLConnection) url.openConnection();
            // site.addRequestProperty("User-Agent", "Mozilla/4.76");

            InputStream is     = site.getInputStream();
            scanner            = new Scanner(new BufferedInputStream(is), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        }
        catch (IOException ioe) {
            System.err.println("Could not open " + s);
        }
    }
    
    
    /**
     * Read and return the remainder of the input as a string.
     */
    public String readAll() {
        if (!scanner.hasNextLine())
            return "";

        String result = scanner.useDelimiter(EVERYTHING_PATTERN).next();
        // not that important to reset delimeter, since now scanner is empty
        scanner.useDelimiter(WHITESPACE_PATTERN); // but let's do it anyway
        return result;
    }

	
}
