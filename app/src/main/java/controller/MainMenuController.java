package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController implements ActionListener {
    private final AppController appController;

    public MainMenuController(AppController appController) {
        this.appController = appController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "USER_MANAGER" -> appController.showUserManager();
            case "QUESTION_POOL" -> appController.showQuestionPoolManager();
            case "QUIZ" -> appController.showQuiz();
            case "GAME" -> appController.showGame();
            case "EXPORT" -> appController.showExportManager();
            case "LOGOUT" -> appController.showLogin();
        }
    }
}
