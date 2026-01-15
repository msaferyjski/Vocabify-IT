package view;

import controller.ExportController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ExportManagerView extends JPanel {
    private final ExportController controller;

    public ExportManagerView(ExportController controller) {
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel title = new JLabel("Export & Statistik");
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));

        JLabel description = new JLabel("Wählen Sie eine Aktion aus, um Ihre Daten zu sichern.");
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(title);
        add(Box.createVerticalStrut(10));
        add(description);
        add(Box.createVerticalStrut(25));

        JButton exportBtn = UI.menuButton("Statistik als Text exportieren", "EXPORT_STATS", controller);
        exportBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(exportBtn);

        add(Box.createVerticalStrut(15));

        JButton backBtn = UI.menuButton("Zurück zum Hauptmenü", "BACK", controller);
        backBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(backBtn);

        // Add glue to push everything to the top
        add(Box.createVerticalGlue());
    }
}
