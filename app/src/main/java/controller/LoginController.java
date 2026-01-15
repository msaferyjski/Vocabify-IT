package controller;

import model.AppState;
import model.LoginModel;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {
    private final AppController appController;
    private final LoginModel model;
    private LoginView view;

    public LoginController(AppController appController) {
        this.appController = appController;
        this.model = new LoginModel();
    }

    public void setView(LoginView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = view.getUsername();
        String pass = view.getPassword();

        switch (e.getActionCommand()) {
            case "LOGIN" -> {
                if (model.authenticate(user, pass)) {
                    AppState.getInstance().setCurrentUser(user);
                    appController.showMainMenu();
                } else {
                    JOptionPane.showMessageDialog(view, "Ungültige Anmeldedaten!", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
            case "REGISTER" -> {
                if (model.register(user, pass)) {
                    JOptionPane.showMessageDialog(view, "Erfolgreich registriert! Sie können sich nun einloggen.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(view, "Registrierung fehlgeschlagen (Benutzername vergeben?).", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
