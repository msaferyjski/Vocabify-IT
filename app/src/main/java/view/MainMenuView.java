package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainMenuView extends JPanel {

    public MainMenuView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel title = new JLabel("Hauptmenü");
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, title.getFont().getSize2D() + 6f));

        add(title);
        add(Box.createVerticalStrut(16));

        add(UI.menuButton("Benutzerverwaltung", "USER_MANAGER", null));
        add(Box.createVerticalStrut(10));
        add(UI.menuButton("Fragenpool", "QUESTION_POOL", null));
        add(Box.createVerticalStrut(10));
        add(UI.menuButton("Quiz", "QUIZ", null));
        add(Box.createVerticalStrut(10));
        add(UI.menuButton("Spiel", "GAME", null));
        add(Box.createVerticalStrut(10));
        add(UI.menuButton("Export", "EXPORT", null));
        add(Box.createVerticalStrut(18));
        add(UI.menuButton("Logout", "LOGOUT", null));
    }
}
