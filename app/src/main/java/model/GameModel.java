package model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GameModel {
    private final String wordToGuess;
    private final String questionText;
    private final Set<Character> guessedLetters;
    private int remainingLives;
    private static final int MAX_LIVES = 6;

    public GameModel(String word, String questionText) {
        this.wordToGuess = word.toUpperCase();
        this.questionText = questionText;
        this.guessedLetters = new HashSet<>();
        this.remainingLives = MAX_LIVES;
    }

    public boolean guessLetter(char letter) {
        letter = Character.toUpperCase(letter);
        if (guessedLetters.contains(letter)) return false;

        guessedLetters.add(letter);
        if (wordToGuess.indexOf(letter) == -1) {
            remainingLives--;
            return false;
        }
        return true;
    }

    public String getDisplayWord() {
        StringBuilder display = new StringBuilder();
        for (char c : wordToGuess.toCharArray()) {
            if (Character.isWhitespace(c)) {
                display.append("  ");
            } else if (guessedLetters.contains(c)) {
                display.append(c).append(" ");
            } else {
                display.append("_ ");
            }
        }
        return display.toString().trim();
    }

    public String getGuessedLettersString() {
        return guessedLetters.stream()
                .map(String::valueOf)
                .sorted()
                .collect(Collectors.joining(", "));
    }

    public boolean isWon() {
        for (char c : wordToGuess.toCharArray()) {
            if (!Character.isWhitespace(c) && !guessedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public boolean isLost() {
        return remainingLives <= 0;
    }

    public int getRemainingLives() { return remainingLives; }
    public String getWordToGuess() { return wordToGuess; }
    public String getQuestionText() { return questionText; }
}