package controller;

import model.*;
import view.QuizView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class QuizController implements ActionListener {
    private final AppController appController;
    private QuizView view;
    private QuizModel model;
    private final LoginModel db = new LoginModel();

    public QuizController(AppController appController) {
        this.appController = appController;
    }

    public void setView(QuizView view) {
        this.view = view;
    }

    public void startQuizSetup() {
        List<String> pools = QuestionPool.getAvailablePoolNames();
        if (pools.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Keine Pools vorhanden!");
            appController.showMainMenu();
            return;
        }
        String selected = (String) JOptionPane.showInputDialog(view, "Pool wählen:", "Quiz Start",
                JOptionPane.QUESTION_MESSAGE, null, pools.toArray(), pools.get(0));

        if (selected == null) {
            appController.showMainMenu();
            return;
        }

        try {
            model = new QuizModel(QuestionPool.loadFromFile(selected));
            if (model.getTotal() == 0) {
                JOptionPane.showMessageDialog(view, "Dieser Pool ist leer!");
                appController.showMainMenu();
            } else {
                displayNextQuestion();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Fehler beim Laden.");
        }
    }

    private void displayNextQuestion() {
        if (model.isFinished()) {
            JOptionPane.showMessageDialog(view, "Quiz beendet! Score: " + model.getScore() + "/" + model.getTotal());
            appController.showMainMenu();
            return;
        }
        view.showQuestion(model.getCurrentQuestion());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("BACK".equals(e.getActionCommand())) {
            appController.showMainMenu();
        } else if ("CHECK_ANSWER".equals(e.getActionCommand())) {
            String answer = view.getAnswer();
            boolean correct = model.checkAnswer(answer);

            db.updateStats(AppState.getInstance().getCurrentUser(), correct);

            if (correct) {
                JOptionPane.showMessageDialog(view, "Richtig!");
            } else {
                JOptionPane.showMessageDialog(view, "Falsch!");
            }
            displayNextQuestion();
        }
    }
}