package controller;

import model.AppState;
import model.LoginModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserManagerController implements ActionListener {
    private final AppController appController;
    private final LoginModel model;

    public UserManagerController(AppController appController) {
        this.appController = appController;
        this.model = new LoginModel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "CHANGE_DATA" -> changeUserData();
            case "DELETE_USER" -> deleteUser();
            case "BACK" -> appController.showMainMenu();
        }
    }

    private void changeUserData() {
        String currentUser = AppState.getInstance().getCurrentUser();
        if (currentUser == null) return;

        String newPassword = JOptionPane.showInputDialog(null,
                "Neues Passwort für " + currentUser + " eingeben:",
                "Passwort ändern", JOptionPane.QUESTION_MESSAGE);

        if (newPassword != null && !newPassword.trim().isEmpty()) {
            if (model.updatePassword(currentUser, newPassword)) {
                JOptionPane.showMessageDialog(null, "Passwort erfolgreich geändert!");
            } else {
                JOptionPane.showMessageDialog(null, "Fehler beim Ändern des Passworts.",
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteUser() {
        String currentUser = AppState.getInstance().getCurrentUser();
        if (currentUser == null) return;

        int confirm = JOptionPane.showConfirmDialog(null,
                "Möchten Sie den Benutzer '" + currentUser + "' wirklich löschen?\nAlle Statistiken gehen verloren.",
                "Benutzer löschen", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (model.deleteUser(currentUser)) {
                JOptionPane.showMessageDialog(null, "Benutzer erfolgreich gelöscht.");
                AppState.getInstance().setCurrentUser(null);
                appController.showLogin();
            } else {
                JOptionPane.showMessageDialog(null, "Fehler beim Löschen des Benutzers.",
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}