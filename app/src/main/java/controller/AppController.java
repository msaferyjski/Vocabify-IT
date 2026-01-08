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
    }

    public void showMainMenu() {
    }

    public void showUserManager() {
    }

    public void showQuestionPoolManager() {
    }

    public void showQuiz() {
    }

    public void showGame() {
    }

    public void showExportManager() {
    }
}