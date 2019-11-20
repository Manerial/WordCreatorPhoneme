package word_analyzer;
import java.util.Iterator;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import utilities.BasicFunctions;

public class WordAnalyzer {
	private static Random	random						= new Random();
	// The sum of letters of analyzed words in the element file
	private Integer			totalLetters				= 0;
	// The total number of analyzed words in the element file
	private Integer			totalAnalyzedWords			= 0;
	// The frequency of a bigrams of letters and the frequency of the next letters that can follow them
	private JSONObject		phonemeAnalysis	= new JSONObject();
	private String analysisFilePath;
	private String resultsFilePath;

	public String getAnalysisFilePath() {
		return analysisFilePath;
	}

	public void setAnalysisFilePath(String analysisFilePath) {
		this.analysisFilePath = analysisFilePath;
	}

	public String getResultsFilePath() {
		return resultsFilePath;
	}

	public void setResultsFilePath(String resultsFilePath) {
		this.resultsFilePath = resultsFilePath;
	}

	public Integer getTotalLetters() {
		return totalLetters;
	}

	public void setTotalLetters(Integer totalLetters) {
		this.totalLetters = totalLetters;
	}

	public void incrementTotalLetters() {
		totalLetters++;
	}

	public Integer getTotalAnalyzedWords() {
		return totalAnalyzedWords;
	}

	public void setTotalAnalyzedWords(Integer totalAnalysedWords) {
		this.totalAnalyzedWords = totalAnalysedWords;
	}

	public void incrementTotalAnalyzedWords() {
		totalAnalyzedWords++;
	}

	public JSONObject getPhonemeAnalysis() {
		return phonemeAnalysis;
	}

	public void setPhonemeAnalysis(JSONObject phonemeAnalysis) {
		this.phonemeAnalysis = phonemeAnalysis;
	}

	/**
	 * Create a new word using the parameters of the analyzer
	 * 
	 * @param begin : If set, will be the beginning of the new word
	 * @return a new word
	 * @throws JSONException : All the JSON exceptions
	 */
	public String createWord(String begin) throws JSONException {
		String newWord = begin;

		if (newWord.isEmpty()) {
			newWord = getWordBeginning();
			if(newWord.length() == 1 || (newWord.length() == 2 && newWord.contains("\u0303"))) {
				return newWord;
			}
		}

		while (true) {
			String lastTwoChars = getTwoLastChar(newWord);
			String nextChar = getNextChar(newWord, lastTwoChars);
			newWord += nextChar;
			if(endOfWord(nextChar)) {
				break;
			}
		}
		return newWord;
	}

	private String getNextChar(String newWord, String lastTwoChars) throws JSONException {
		String nextChar = getNextChar(newWord);
		if(!hasVowel(lastTwoChars)) {
			while(!hasVowel(nextChar)) {
				nextChar = getNextChar(newWord);
			}
		} else if(!hasConsone(lastTwoChars)) {
			while(!hasConsone(nextChar)) {
				nextChar = getNextChar(newWord);
			}
		}
		return nextChar;
	}

	private boolean hasConsone(String lastTwoChars) {
		return lastTwoChars.contains("l") ||
				lastTwoChars.contains("b") ||
				lastTwoChars.contains("p") ||
				lastTwoChars.contains("m") ||
				lastTwoChars.contains("d") ||
				lastTwoChars.contains("t") ||
				lastTwoChars.contains("n") ||
				lastTwoChars.contains("v") ||
				lastTwoChars.contains("f") ||
				lastTwoChars.contains("g") ||
				lastTwoChars.contains("k") ||
				lastTwoChars.contains("z") ||
				lastTwoChars.contains("s") ||
				lastTwoChars.contains("θ") ||
				lastTwoChars.contains("ð") ||
				lastTwoChars.contains("�?") ||
				lastTwoChars.contains("χ") ||
				lastTwoChars.contains("ʒ") ||
				lastTwoChars.contains("ʃ");
	}

	private String getTwoLastChar(String newWord) {
		String lastTwoChars = newWord.substring(newWord.length() - 2, newWord.length());
		if(lastTwoChars.contains("\u0303")) {
			lastTwoChars = newWord.substring(newWord.length() - 3, newWord.length());
		}
		return lastTwoChars;
	}

	private boolean hasVowel(String lastTwoChars) {
		return lastTwoChars.contains("a") ||
				lastTwoChars.contains("ɑ̃") ||
				lastTwoChars.contains("e") ||
				lastTwoChars.contains("i") ||
				lastTwoChars.contains("ɛ") ||
				lastTwoChars.contains("ɛ̃") ||
				lastTwoChars.contains("o") ||
				lastTwoChars.contains("u") ||
				lastTwoChars.contains("y") ||
				lastTwoChars.contains("ɔ̃") ||
				lastTwoChars.contains("ø");
	}

	private String getNextChar(String newWord) throws JSONException {
		String nextChar = "";
		JSONObject nextPossibilities = getNextPossibilities(newWord);
		int randomNextCharRank = getRandomCharRank(nextPossibilities);
		int sumOfPreviousCharRank = 0;
		Iterator<?> iteratorNextChar = nextPossibilities.keys();
		while (!BasicFunctions.rankFound(sumOfPreviousCharRank, randomNextCharRank) && iteratorNextChar.hasNext()) {
			nextChar = iteratorNextChar.next().toString();
			sumOfPreviousCharRank += nextPossibilities.getInt(nextChar);
		}
		return nextChar;
	}

	/**
	 * Get a random character rank in all the availables trigrams
	 * 
	 * @param possibilities : all the availables trigrams
	 * @return the random rank of a character
	 * @throws JSONException : All the JSON exceptions
	 */
	private int getRandomCharRank(JSONObject possibilities) throws JSONException {
		int sumOfTrigramsFrequency = getSumOfPossibilitiesFrequency(possibilities);
		return random.nextInt(sumOfTrigramsFrequency) + 1;
	}

	/**
	 * Check if the character is a word end (empty)
	 * 
	 * @param character : the character to check
	 * @return true if the character is a word end
	 */
	private boolean endOfWord(String character) {
		return character.equals("");
	}

	/**
	 * Get all the next possibles trigrams that match the end of a word
	 * 
	 * @param word : The word to use
	 * @return the next possibles trigrams that can match the end of the word
	 * @throws JSONException : All the JSON exceptions
	 */
	private JSONObject getNextPossibilities(String word) throws JSONException {
		String lastLetter = word.substring(word.length() - 1, word.length());
		if(lastLetter.equals("\u0303")) {
			lastLetter = word.substring(word.length() - 2, word.length());
		}
		JSONObject nextTrigrams = phonemeAnalysis.getJSONObject(lastLetter);
		return nextTrigrams;
	}

	/**
	 * Get the two first letters of a word
	 * 
	 * @return The two first letters of a word
	 * @throws JSONException : All the JSON exceptions
	 */
	private String getWordBeginning() throws JSONException {
		int rankToFind = getRandomMonogramRank();
		int sumOfPreviousPhonemeFrequency = 0;
		String firstPhoneme = "";
		String secondPhoneme = "";
		JSONObject phonemeNextPossibilities = new JSONObject();

		// Get first letter
		Iterator<?> iteratorPhoneme = phonemeAnalysis.keys();
		while (!BasicFunctions.rankFound(sumOfPreviousPhonemeFrequency, rankToFind) && iteratorPhoneme.hasNext()) {
			firstPhoneme = iteratorPhoneme.next().toString();
			phonemeNextPossibilities = phonemeAnalysis.getJSONObject(firstPhoneme);
			sumOfPreviousPhonemeFrequency += getSumOfPossibilitiesFrequency(phonemeNextPossibilities);
		}

		sumOfPreviousPhonemeFrequency -= getSumOfPossibilitiesFrequency(phonemeNextPossibilities);

		//Get second letter
		Iterator<?> iteratorSecondLetter = phonemeNextPossibilities.keys();
		while (!BasicFunctions.rankFound(sumOfPreviousPhonemeFrequency, rankToFind) && iteratorSecondLetter.hasNext()) {
			secondPhoneme = iteratorSecondLetter.next().toString();
			sumOfPreviousPhonemeFrequency += phonemeNextPossibilities.getInt(secondPhoneme);
		}

		return firstPhoneme + secondPhoneme;
	}

	/**
	 * Get a random number that symbolizes the rank of a monogram
	 * (bigram of 1 letter)
	 * 
	 * @return the random rank of a monogram
	 */
	private int getRandomMonogramRank() {
		return random.nextInt(totalAnalyzedWords) + 1;
	}

	/**
	 * Get the number of appearance of a bigram
	 * 
	 * @param possibilities : The bigram we need to get the frequency
	 * @return The frequency of appearance of a bigram
	 * @throws JSONException : All the JSON exceptions
	 */
	private int getSumOfPossibilitiesFrequency(JSONObject possibilities) throws JSONException {
		int sumFrequencies = 0;
		Iterator<?> nextPossibilities = possibilities.keys();
		while (nextPossibilities.hasNext()) {
			String nextLetter = nextPossibilities.next().toString();
			sumFrequencies += possibilities.getInt(nextLetter);
		}
		return sumFrequencies;
	}
}