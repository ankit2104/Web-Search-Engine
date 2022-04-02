package webSearchEngine;

public class BoyerMoore {
	
    private final int Radix;     // the radix
    private int[] WordstoSkiparray;     // the bad-character WordstoSkip array

    //private char[] stringPatternsterns;  // store the stringPatternstern as a character array
    private String stringPatterns;      // or as a string

    // stringPatternstern provided as a string
    public BoyerMoore(String stringPatterns) {
        this.Radix = 256;
        this.stringPatterns = stringPatterns;

        // position of WordstoSkiparraymost occurrence of c in the stringPatternstern
        WordstoSkiparray = new int[Radix];
        for (int counter = 0; counter < Radix; counter++)
            WordstoSkiparray[counter] = -1;
        for (int cnt = 0; cnt < stringPatterns.length(); cnt++)
            WordstoSkiparray[stringPatterns.charAt(cnt)] = cnt;
    }
    
    // return offset of first match; N if no match
    public int search(String txtLine) {
        int PatternLength = stringPatterns.length();
        int TextLineLength = txtLine.length();
        int WordstoSkip;
        for (int i = 0; i <= TextLineLength - PatternLength; i += WordstoSkip) {
            WordstoSkip = 0;
            for (int j = PatternLength-1; j >= 0; j--) {
                if (stringPatterns.charAt(j) != txtLine.charAt(i+j)) {
                    WordstoSkip = Math.max(1, j - WordstoSkiparray[txtLine.charAt(i+j)]);
                    break;
                }
            }
            if (WordstoSkip == 0) return i;    // found
        }
        return TextLineLength;                       // not found
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
