package view;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UserManagerView extends JPanel {

    public UserManagerView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel title = new JLabel("Benutzerverwaltung");
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, title.getFont().getSize2D() + 6f));

        add(title);
        add(Box.createVerticalStrut(16));

        add(UI.menuButton("Benutzerdaten ändern", "CHANGE_DATA", null));
        add(Box.createVerticalStrut(10));

        add(UI.menuButton("Benutzer löschen", "DELETE_USER", null));
        add(Box.createVerticalStrut(18));

        add(UI.menuButton("Zurück", "BACK", null));
    }
}
