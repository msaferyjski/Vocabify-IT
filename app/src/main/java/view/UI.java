package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UI {
    public static JButton menuButton(String text, String command, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));
        button.setActionCommand(command);
        button.addActionListener(listener);
        return button;
    }
}