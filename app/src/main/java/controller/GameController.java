package controller;

import model.GameModel;
import model.Question;
import model.QuestionPool;
import view.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class GameController implements ActionListener {
    private final AppController appController;
    private GameView view;
    private GameModel model;

    public GameController(AppController appController) {
        this.appController = appController;
    }

    public void setView(GameView view) {
        this.view = view;
    }

    public void startGameSetup() {
        List<String> pools = QuestionPool.getAvailablePoolNames();
        if (pools.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Keine Pools vorhanden!");
            appController.showMainMenu();
            return;
        }

        String selected = (String) JOptionPane.showInputDialog(view, "Pool für Hangman wählen:",
                "Spielstart", JOptionPane.QUESTION_MESSAGE, null, pools.toArray(), pools.get(0));

        if (selected == null) {
            appController.showMainMenu();
            return;
        }

        try {
            QuestionPool pool = QuestionPool.loadFromFile(selected);
            List<Question> questions = pool.getQuestions();

            if (questions.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Pool ist leer!");
                appController.showMainMenu();
                return;
            }

            // Pick a random question
            Question q = questions.get(new Random().nextInt(questions.size()));
            model = new GameModel(q.getCorrectAnswer(), q.getQuestionText());
            updateView();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Fehler beim Laden.");
        }
    }

    public void handleGuess() {
        if (model == null) return;

        String input = view.getGuess();
        if (input.isEmpty()) return;

        char letter = input.charAt(0);
        model.guessLetter(letter);

        updateView();

        if (model.isWon()) {
            JOptionPane.showMessageDialog(view, "Gewonnen! Das Wort war: " + model.getWordToGuess());
            appController.showMainMenu();
        } else if (model.isLost()) {
            JOptionPane.showMessageDialog(view, "Game Over! Das Wort war: " + model.getWordToGuess());
            appController.showMainMenu();
        }
    }

    private void updateView() {
        if (view != null && model != null) {
            view.updateDisplay(
                    model.getQuestionText(),
                    model.getDisplayWord(),
                    model.getRemainingLives(),
                    model.getGuessedLettersString()
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "GUESS" -> handleGuess();
            case "BACK" -> appController.showMainMenu();
        }
    }
}