package launcher;
import java.io.*;
import org.json.*;

import word_analyser.WordAnalyserManager;
import word_analyser.WordAnalyzer;

/**
 * 
 * @author Julien HERMENT
 * Version 1.0, 13/03/2019
 * 
 */
public class Launcher {

	/**
	 * Entry point
	 * 
	 * @param args : Not used
	 * @throws JSONException : for JSON error
	 * @throws IOException : for file management errors
	 */
	public static void main(String[] args) throws JSONException, IOException {
		PrintStream out = new PrintStream("log.txt", "UTF-8");
		System.setOut(out);
		String analysisFileName = "analysis_niini.txt";
		String resultsFileName = "new_niini.txt";
		WordAnalyzer wordAnalyzer = new WordAnalyzer();
		wordAnalyzer.setAnalysisFilePath(analysisFileName);
		wordAnalyzer.setResultsFilePath(resultsFileName);
		
		testCreateWordList(wordAnalyzer);
		
		resultsFileName = "new_fran�ais_length_5.txt";
		wordAnalyzer.setResultsFilePath(resultsFileName);
		testCreateWordListFixLength(wordAnalyzer);
	}
	
	public static void testCreateWordList(WordAnalyzer wordAnalyzer) throws JSONException, IOException {
		System.out.println("testCreateWordList");
		WordAnalyserManager wordAnalyserManager = new WordAnalyserManager(wordAnalyzer);
		wordAnalyserManager.createWordList(1000, "");
	}
	
	public static void testCreateWordListFixLength(WordAnalyzer wordAnalyzer) throws JSONException, IOException {
		System.out.println("testCreateWordListFixLength");
		WordAnalyserManager wordAnalyserManager = new WordAnalyserManager(wordAnalyzer);
		wordAnalyserManager.setWordAnalyzer(wordAnalyzer);
		wordAnalyserManager.createWordListFixLength(1000, "", 5);
	}
}