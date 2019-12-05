package com.netomedia.exam.hangman.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.netomedia.exam.hangman.model.ServerResponse;
import com.netomedia.exam.hangman.utils.TokenGenerator;

public class HangmanServer {

	private static List<String> dictionary;
	private Random random;
	private final TokenGenerator tokenGenerator;
	private static int attempts = 0;
	private static int failedAttempts = 0;
	private String token = null;
	private String word = null;
	private String hangman = null;
	private static String previousHangman = null;
	private List<String> previousGuesses;

	private static final int MAX_PLAYER_REQUESTS = 26;
	private static final String RESOURCES_PATH = "src/main/resources/";
	private static final String DICTIONARY_FILE_NAME = "dictionary.txt";

	public HangmanServer() {
		random = new Random();
		tokenGenerator = new TokenGenerator();
		previousGuesses = new ArrayList<String>();
		dictionary = new ArrayList<String>();
	}

	public ServerResponse startNewGame() throws Exception {
		// load dictionary
		loadFile(RESOURCES_PATH + DICTIONARY_FILE_NAME, dictionary, null);

		createNewHangman();

		System.out.println("*** Initialized a new Hangman Game! ***");
		return new ServerResponse(generateNewToken(), hangman, null);
	}

	public ServerResponse guess(String token, String guess) throws Exception {
		if (!isTokenValid(token)) {
			System.out.println("The token " + token + " is invalid!");
			return new ServerResponse(generateNewToken(), previousHangman, false);
		}

		if (!hangman.contains("_")) {
			System.out.println(
					"Yay! you won the game in " + attempts + " attempts! (" + failedAttempts + " failedAttempts)");
			new ServerResponse(generateNewToken(), hangman, true, true, failedAttempts);
		}

		if (attempts == MAX_PLAYER_REQUESTS) {
			throw new Exception("Sorry, you lost! You have reached the maximum of 26 attempts");
		}

		if (previousGuesses.contains(guess)) {
			System.out.println("The guess " + guess + " has already been tried out");
			return new ServerResponse(generateNewToken(), previousHangman, false);
		}

		previousGuesses.add(guess);
		attempts++;
		System.out.println("Guess number: " + attempts + " out of: " + MAX_PLAYER_REQUESTS);

		if (word.contains(guess)) {
			// correct guess
			updateHangman(guess);
		} else {
			failedAttempts++;
		}

		if (!hangman.contains("_")) {
			System.out.println(
					"Yay! you won the game in " + attempts + " attempts! (" + failedAttempts + " failedAttempts)");
			return new ServerResponse(generateNewToken(), hangman, true, true, failedAttempts);
		}

		return new ServerResponse(generateNewToken(), hangman, true);
	}

	private void updateHangman(String guess) {
		if (word.equals(guess)) {
			hangman = guess;
			return;
		}

		List<Integer> allWordIndexes = getAllWordIndexes(guess);
		StringBuilder newHangman = new StringBuilder(hangman);
		for (Integer index : allWordIndexes) {
			newHangman.setCharAt(index, guess.charAt(0));
		}

		hangman = newHangman.toString();
	}

	private List<Integer> getAllWordIndexes(String guess) {
		List<Integer> indexes = new ArrayList<Integer>();

		if (guess == null) {
			return indexes;
		}

		for (int index = word.indexOf(guess.charAt(0)); index >= 0; index = word.indexOf(guess, index + 1)) {
			indexes.add(index);
		}

		return indexes;
	}

	private String generateNewToken() {
		setToken(tokenGenerator.nextString());
		return token;
	}

	private void setToken(String token) {
		this.token = token;
	}

	private boolean isTokenValid(String token) {
		return this.token.equals(token);
	}

	private String selectWordFromDictionary() {
		word = dictionary.get(random.nextInt(dictionary.size()));
		return word;
	}

	private void createNewHangman() {
		if (word == null) {
			selectWordFromDictionary();
		}

		hangman = word.replaceAll(".", "_");
	}

	private static void loadFile(String fileName, List<String> destination, Integer expectedLength) {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			List<String> words = stream.collect(Collectors.toList());
			if (expectedLength != null) {
				words = words.stream().filter(w -> w.length() == expectedLength).collect(Collectors.toList());
			}

			words.stream().forEach(w -> destination.add(w));

		} catch (IOException e) {
			System.out.println("An I/O error has occured.");
		}
	}

	public ServerResponse startNewGame(String word) {
		this.previousGuesses.clear();
		attempts = failedAttempts = 0;
		createNewHangman(word);
		System.out.println("*** Initialized a new Hangman Game with a predefined word! ***");
		return new ServerResponse(generateNewToken(), hangman, null);
	}

	private void createNewHangman(String word) {
		this.word = word;
		createNewHangman();
	}
}
