package view;

import controller.LoginController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginView extends JPanel {

    private final JButton loginBtn;
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginView(LoginController controller) {
        controller.setView(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel title = new JLabel("Login");
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, title.getFont().getSize2D() + 6f));

        JLabel userLabel = new JLabel("Benutzername");
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        usernameField = new JTextField(20);
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, usernameField.getPreferredSize().height));

        JLabel passLabel = new JLabel("Passwort");
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        passwordField = new JPasswordField(20);
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, passwordField.getPreferredSize().height));

        loginBtn = UI.menuButton("Login", "LOGIN", controller);
        JButton registerBtn = UI.menuButton("Registrieren", "REGISTER", controller);

        add(title);
        add(Box.createVerticalStrut(16));
        add(userLabel);
        add(Box.createVerticalStrut(6));
        add(usernameField);
        add(Box.createVerticalStrut(12));
        add(passLabel);
        add(Box.createVerticalStrut(6));
        add(passwordField);
        add(Box.createVerticalStrut(18));
        add(loginBtn);
        add(Box.createVerticalStrut(10));
        add(registerBtn);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    @Override
    public void addNotify() {
        super.addNotify();
        JRootPane root = SwingUtilities.getRootPane(this);
        if (root != null && loginBtn != null) {
            root.setDefaultButton(loginBtn);
        }
    }
}