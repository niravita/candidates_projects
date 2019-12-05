package com.netomedia.exam.hangman;

import java.util.ArrayList;
import java.util.Collections;

import com.netomedia.exam.hangman.player.HangmanPlayer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class HangmanPlayerTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public HangmanPlayerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(HangmanPlayerTest.class);
	}

	/**
	 * Rigourous Test :-)
	 * 
	 * @throws Exception
	 */
	public void testApp() throws Exception {

		ArrayList<String> allWords = new ArrayList<String>();

		HangmanPlayer.loadFile(HangmanPlayer.RESOURCES_PATH + HangmanPlayer.DICTIONARY_FILE_NAME, allWords, null);

		Collections.shuffle(allWords);

		HangmanPlayer hangmanPlayer = new HangmanPlayer();

		int failedAttempts = 0;

		int TESTED_WORDS_COUNT = allWords.size();
		System.out.println("Calculating average error for all words... (estimate time 4 hours)");
		for (int i = 0; i < TESTED_WORDS_COUNT; i++) {
			if (i % 1000 == 0 && i!=0) {
				System.out.println("Processed " + i + "\\" + TESTED_WORDS_COUNT + " words...");
			}
			failedAttempts += hangmanPlayer.play(allWords.get(i));
		}
		Double avrgFailedAttempts = (double) (new Double(failedAttempts) / new Double(TESTED_WORDS_COUNT));
		System.out.println("average failedAttempts: " + avrgFailedAttempts);
	}
}
