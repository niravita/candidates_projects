package com.netomedia.exam.hangman.model;

public class Letter {
    private char letter;
    public int occurancesInDictionary;
    public int wordsContainTheLetter;

    public Letter(char letter) {
        this.letter = letter;
    }

    public void zeroCounters() {
        occurancesInDictionary = 0;
        wordsContainTheLetter = 0;
    }

    public char getLetterChar() {
        return this.letter;
    }

    public String getLetterString() {
        return String.valueOf(this.letter);
    }

    public int getOccurancesInDictionary() {
        return occurancesInDictionary;
    }

    public void incOccurancesInDictionary(int occurancesInDictionary) {
        this.occurancesInDictionary = this.occurancesInDictionary + occurancesInDictionary;
    }

    public int getWordsContainTheLetter() {
        return wordsContainTheLetter;
    }

    public void incNumOfWordsContainTheLetter() {
        this.wordsContainTheLetter++;
    }

    public char getLetter() {
        return letter;
    }

    @Override
    public String toString() {
        return "Letter [letter=" + letter + ", occurancesInDictionary=" + occurancesInDictionary + ", wordsContainTheLetter=" + wordsContainTheLetter
                + "]";
    }

}
