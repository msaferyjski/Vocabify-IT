package controller;

import model.LoginModel;
import view.ExportManagerView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ExportController implements ActionListener {
    private final AppController appController;
    private final LoginModel db = new LoginModel();
    private ExportManagerView view;

    public ExportController(AppController appController) {
        this.appController = appController;
    }

    public void setView(ExportManagerView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "EXPORT_STATS" -> exportStatistics();
            case "BACK" -> appController.showMainMenu();
        }
    }

    private void exportStatistics() {
        List<LoginModel.UserStats> allStats = db.getAllUserStats();

        if (allStats.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Keine Statistiken zum Exportieren vorhanden.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Speicherort für Statistik wählen");
        fileChooser.setSelectedFile(new File("vokabeltrainer_statistik.txt"));

        int userSelection = fileChooser.showSaveDialog(view);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Ensure .txt extension if not present
            if (!fileToSave.getName().toLowerCase().endsWith(".txt")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".txt");
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(fileToSave))) {
                writer.println("VOKABELTRAINER - NUTZERSTATISTIKEN");
                writer.println("===================================");
                writer.println(String.format("%-20s | %-10s | %-10s | %-10s", "Benutzername", "Richtig", "Falsch", "Quote (%)"));
                writer.println("-------------------------------------------------------------------");

                for (LoginModel.UserStats s : allStats) {
                    double total = s.correct() + s.wrong();
                    double quote = (total == 0) ? 0 : (s.correct() / total) * 100;

                    writer.println(String.format("%-20s | %-10d | %-10d | %-10.2f",
                            s.username(), s.correct(), s.wrong(), quote));
                }

                JOptionPane.showMessageDialog(view, "Export erfolgreich abgeschlossen:\n" + fileToSave.getAbsolutePath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "Fehler beim Schreiben der Datei: " + ex.getMessage(),
                        "Export Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
