package controller;

import view.*;

import javax.swing.*;

public class AppController {

    private final BaseFrame frame;

    public AppController() {
        frame = new BaseFrame("Vokabeltrainer");
        showLogin();
    }

    private void setView(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.setView(panel);
        frame.revalidate();
        frame.repaint();
    }

    public void showLogin() {
        setView(new LoginView(new LoginController(this)));
    }

    public void showMainMenu() {
        setView(new MainMenuView(new MainMenuController(this)));
    }

    public void showUserManager() {
        setView(new UserManagerView(new UserManagerController(this)));
    }

    public void showQuestionPoolManager() {
        QuestionPoolController controller = new QuestionPoolController(this);
        QuestionPoolManagerView view = new QuestionPoolManagerView(controller);
        controller.setView(view);
        setView(view);
    }

    public void showQuiz() {
        QuizController controller = new QuizController(this);
        QuizView view = new QuizView(controller);
        controller.setView(view);
        setView(view);
        controller.startQuizSetup();
    }

    public void showGame() {
        GameController controller = new GameController(this);
        GameView view = new GameView(controller);
        controller.setView(view);
        setView(view);
        controller.startGameSetup();
    }

    public void showExportManager() {
        ExportController controller = new ExportController(this);
        ExportManagerView view = new ExportManagerView(controller);
        controller.setView(view);
        setView(view);
    }
}