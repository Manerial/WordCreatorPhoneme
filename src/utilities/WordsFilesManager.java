package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import word_analyzer.WordAnalyzer;

public class WordsFilesManager {
	private static String RESOURCE_PATH = System.getProperty("user.dir") + "\\resources\\";
	private static String ANALYSIS_FOLDER = RESOURCE_PATH + "analysis\\";
	private static String RESULT_FOLDER = RESOURCE_PATH + "result\\";

	/**
	 * Parse an analysis file to feed an analyzer
	 * 
	 * @param analyzer : The analyzer object to feed with the analysis file data
	 * @throws IOException : All the IO exceptions
	 * @throws JSONException : All the JSON exceptions
	 */
	public static void parseAnalysisFile(WordAnalyzer analyzer) throws JSONException, IOException {
		BufferedReader br = getBufferReader(ANALYSIS_FOLDER + analyzer.getAnalysisFilePath());
		analyzer.setTotalLetters(Integer.parseInt(br.readLine()));
		analyzer.setTotalAnalyzedWords(Integer.parseInt(br.readLine()));
		analyzer.setPhonemeAnalysis(new JSONObject(br.readLine()));
		br.close();
	}

	/**
	 * Save a list of words in a RESULT file
	 * 
	 * @param analyzer : The analyzer that contains the RESULT file name
	 * @param newWordList : The list of words to save
	 * @throws IOException : All the IO exceptions
	 */
	public static void printStringListInFile(WordAnalyzer analyzer, List<String> newWordList) throws IOException {
		PrintWriter resultFile = getPrinterWriter(RESULT_FOLDER + analyzer.getResultsFilePath());
		for (String newWord : newWordList) {
			String newWordPhoneme = newWord.replaceAll("ɑ̃", "an");

			newWordPhoneme = newWordPhoneme.replaceAll("e", "é");
			newWordPhoneme = newWordPhoneme.replaceAll("ɛ̃", "in");
			newWordPhoneme = newWordPhoneme.replaceAll("ɛ", "è");
			
			newWordPhoneme = newWordPhoneme.replaceAll("ɔ̃", "on");
			newWordPhoneme = newWordPhoneme.replaceAll("u", "ou");
			newWordPhoneme = newWordPhoneme.replaceAll("ø", "œ");
			newWordPhoneme = newWordPhoneme.replaceAll("y", "u");
			
			newWordPhoneme = newWordPhoneme.replaceAll("ʁ", "R");
			newWordPhoneme = newWordPhoneme.replaceAll("χ", "r");
			
			newWordPhoneme = newWordPhoneme.replaceAll("ʃ", "ch");
			newWordPhoneme = newWordPhoneme.replaceAll("ʒ", "j");
			
			newWordPhoneme = newWordPhoneme.replaceAll("ð", "TH");
			newWordPhoneme = newWordPhoneme.replaceAll("θ", "th");
			
			resultFile.println(newWordPhoneme + "\t[" + newWord + "]");
		}
		resultFile.close();
	}

	/**
	 * Return a BufferedReader to read a file
	 * 
	 * @param filePath : the file path to read
	 * @return a BufferedReader with the path of the file to read
	 * @throws FileNotFoundException : All the File Not Found exceptions
	 */
	private static BufferedReader getBufferReader(String filePath) throws FileNotFoundException {
		InputStream ips = new FileInputStream(filePath);
		InputStreamReader ipsr = new InputStreamReader(ips, Charset.forName("UTF-8"));
		return new BufferedReader(ipsr);
	}

	/**
	 * Return a BufferedWriter to write into a file
	 * 
	 * @param filePath : the file path to write
	 * @return a BufferedWriter with the path of the file to write
	 * @throws IOException : All the IO exceptions
	 */
	private static PrintWriter getPrinterWriter(String filePath) throws IOException {
		OutputStream ops = new FileOutputStream(filePath);
		OutputStreamWriter opsw = new OutputStreamWriter(ops, Charset.forName("UTF-8"));
		BufferedWriter bw = new BufferedWriter(opsw);
		return new PrintWriter(bw);
	}
}
