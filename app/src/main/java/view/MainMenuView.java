package view;

import controller.MainMenuController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainMenuView extends JPanel {

    public MainMenuView(MainMenuController controller) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel title = new JLabel("Hauptmenü");
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, title.getFont().getSize2D() + 6f));

        add(title);
        add(Box.createVerticalStrut(16));

        add(UI.menuButton("Benutzerverwaltung", "USER_MANAGER", controller));
        add(Box.createVerticalStrut(10));
        add(UI.menuButton("Fragenpool", "QUESTION_POOL", controller));
        add(Box.createVerticalStrut(10));
        add(UI.menuButton("Quiz", "QUIZ", controller));
        add(Box.createVerticalStrut(10));
        add(UI.menuButton("Spiel", "GAME", controller));
        add(Box.createVerticalStrut(10));
        add(UI.menuButton("Export", "EXPORT", controller));
        add(Box.createVerticalStrut(18));
        add(UI.menuButton("Logout", "LOGOUT", controller));
    }
}
