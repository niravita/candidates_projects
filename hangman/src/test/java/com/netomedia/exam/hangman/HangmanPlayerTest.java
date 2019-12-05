package com.netomedia.exam.hangman;

import java.util.ArrayList;

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

		System.out.println(allWords.size());

		HangmanPlayer hangmanPlayer = new HangmanPlayer();

		int failedAttempts = 0;

		for (String word : allWords) {
			failedAttempts += hangmanPlayer.play(word);
		}

		System.out.println("average failedAttempts: " + new Double(failedAttempts / allWords.size()));
	}
}
