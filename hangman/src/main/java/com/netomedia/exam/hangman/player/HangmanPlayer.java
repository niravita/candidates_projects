package com.netomedia.exam.hangman.player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.netomedia.exam.hangman.model.Letter;
import com.netomedia.exam.hangman.model.ServerResponse;
import com.netomedia.exam.hangman.server.HangmanServer;

public class HangmanPlayer {

    private static HangmanServer server = new HangmanServer();
    private static List<String> dictionary = new ArrayList<String>();
    private static List<Letter> letters = new ArrayList<Letter>();

    private static final String RESOURCES_PATH = "src/main/resources/";
    private static final String DICTIONARY_FILE_NAME = "dictionary.txt";
    private static final String LETTERS_FILE_NAME = "letters.txt";

    private static final String MISSING_LETTER = "_";

    public static void main(String[] args) throws Exception {

        ServerResponse serverResponse = server.startNewGame();
        int wordLength = serverResponse.getHangman().length();

        //load dictionary
        loadFile(RESOURCES_PATH + DICTIONARY_FILE_NAME, dictionary, wordLength);

        //load letters
        final List<String> lettersAsString = new ArrayList<String>();
        loadFile(RESOURCES_PATH + LETTERS_FILE_NAME, lettersAsString, null);
        letters = lettersAsString.stream().map(letter -> new Letter(letter.charAt(0))).collect(Collectors.toList());

        while (serverResponse.getHangman().contains(MISSING_LETTER)) {
            String currentToken = serverResponse.getToken();

            String guess = null;
            Letter mostFrequestLetter = null;
            if (dictionary.size() == 1) {
                guess = dictionary.get(0);
            } else {
                mostFrequestLetter = getMostFrequentLetter();
                guess = mostFrequestLetter.getLetterString();
            }

            System.out.println("Your guess is: " + guess);

            serverResponse = server.guess(currentToken, guess);
            filterWordsFromDict(serverResponse, guess);

            if (guess.length() == 1) {
                letters.remove(mostFrequestLetter);
            }

            letters.stream().forEach(l -> l.zeroCounters());
        }
    }

    public static void filterWordsFromDict(ServerResponse serverResponse, String guess) {
        if (serverResponse.isCorrect()) {
            String hangman = serverResponse.getHangman();
            String hangmanToCheck = hangman.replaceAll("_", ".");
            dictionary.removeIf(word -> !word.matches(hangmanToCheck));
        } else {
            dictionary.removeIf(word -> word.contains(guess));
        }
    }

    public static void loadFile(String fileName, List<String> destination, Integer expectedLength) {
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

    public static Letter getMostFrequentLetter() {

        for (Letter l : letters) {
            for (String word : dictionary) {
                int occurancesOfletterInWord = word.split(l.getLetterString(), -1).length - 1;
                l.incOccurancesInDictionary(occurancesOfletterInWord);

                if (occurancesOfletterInWord > 0) {
                    l.incNumOfWordsContainTheLetter();
                }
            }
        }

        letters.sort(Comparator.comparing(Letter::getWordsContainTheLetter).thenComparing(Letter::getOccurancesInDictionary));
        Collections.reverse(letters);
        return letters.get(0);
    }
}
