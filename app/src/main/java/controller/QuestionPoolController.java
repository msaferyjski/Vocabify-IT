package controller;

import model.*;
import view.QuestionPoolManagerView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class QuestionPoolController implements ActionListener {
    private final AppController appController;
    private QuestionPoolManagerView view;
    private QuestionPool currentPool;

    public QuestionPoolController(AppController appController) {
        this.appController = appController;
    }

    public void setView(QuestionPoolManagerView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "CREATE_POOL" -> handleCreatePool();
            case "IMPORT_POOL" -> handleImportPool();
            case "EXPORT_POOL" -> handleExportPool();
            case "DELETE_POOL" -> handleDeletePool();
            case "ADD_QUESTION" -> handleAddQuestion();
            case "IMPORT_SINGLE_QUESTION" -> handleImportSingleQuestion();
            case "DELETE_QUESTION" -> handleDeleteQuestion();
            case "BACK" -> appController.showMainMenu();
        }
    }

    public void onPoolSelected(String name) {
        if (name == null || view == null) return;
        try {
            currentPool = QuestionPool.loadFromFile(name);
            view.displayQuestions(currentPool.getQuestions());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(view, "Fehler beim Laden.");
        }
    }

    private void handleCreatePool() {
        if (view == null) return;
        String name = JOptionPane.showInputDialog(view, "Pool Name:");
        if (name != null) {
            try {
                QuestionPool.createPool(name);
                view.refreshPoolList();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "Fehler beim Erstellen.");
            }
        }
    }

    private void handleImportPool() {
        if (view == null) return;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Pool Datei importieren");
        int result = fileChooser.showOpenDialog(view);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                QuestionPool.importPool(fileChooser.getSelectedFile());
                view.refreshPoolList();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "Fehler beim Importieren: " + ex.getMessage());
            }
        }
    }

    private void handleExportPool() {
        if (view == null) return;
        String selected = view.getSelectedPool();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Bitte wählen Sie einen Pool zum Exportieren aus.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Pool Datei exportieren");
        fileChooser.setSelectedFile(new File(selected + ".txt"));
        int result = fileChooser.showSaveDialog(view);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                QuestionPool.exportPool(selected, fileChooser.getSelectedFile());
                JOptionPane.showMessageDialog(view, "Erfolgreich exportiert.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "Fehler beim Exportieren: " + ex.getMessage());
            }
        }
    }

    private void handleDeletePool() {
        if (view == null) return;
        String selected = view.getSelectedPool();
        if (selected != null) {
            QuestionPool.deletePool(selected);
            currentPool = null;
            view.refreshPoolList();
        }
    }

    private void handleAddQuestion() {
        if (view == null || currentPool == null) {
            JOptionPane.showMessageDialog(view, "Bitte wählen Sie zuerst einen Pool aus.");
            return;
        }
        String[] types = {"Text Frage", "Multiple Choice", "Bild Frage"};
        String type = (String) JOptionPane.showInputDialog(view, "Typ:", "Neue Frage", JOptionPane.QUESTION_MESSAGE, null, types, types[0]);
        if (type == null) return;

        try {
            if (type.equals("Text Frage")) {
                String text = JOptionPane.showInputDialog(view, "Fragetext:");
                String ans = JOptionPane.showInputDialog(view, "Antwort:");
                currentPool.addQuestion(new TextQuestion(text, ans));
            } else if (type.equals("Bild Frage")) {
                handleImageQuestionCreation();
            } else {
                String text = JOptionPane.showInputDialog(view, "Fragetext:");
                String opts = JOptionPane.showInputDialog(view, "Optionen (mit ; getrennt):");
                String idx = JOptionPane.showInputDialog(view, "Index der richtigen Antwort (0-n):");
                currentPool.addQuestion(new MultipleChoiceQuestion(text, Arrays.asList(opts.split(";")), Integer.parseInt(idx)));
            }
            currentPool.saveToFile();
            view.displayQuestions(currentPool.getQuestions());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Eingabefehler: " + ex.getMessage());
        }
    }

    private void handleImageQuestionCreation() throws IOException {
        String[] options = {"Web URL eingeben", "Lokales Bild importieren"};
        int choice = JOptionPane.showOptionDialog(view, "Wie möchten Sie das Bild hinzufügen?", "Bildquelle",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        String imagePath = "";
        if (choice == 0) {
            imagePath = JOptionPane.showInputDialog(view, "Bild URL:");
        } else if (choice == 1) {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
                File source = chooser.getSelectedFile();
                File destDir = new File("resources/images");
                if (!destDir.exists()) destDir.mkdirs();
                File destFile = new File(destDir, source.getName());
                Files.copy(source.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                imagePath = "file:" + destFile.getPath();
            }
        }

        if (imagePath != null && !imagePath.isEmpty()) {
            String ans = JOptionPane.showInputDialog(view, "Antwort:");
            currentPool.addQuestion(new ImageQuestion(imagePath, ans));
        }
    }

    private void handleImportSingleQuestion() {
        if (view == null || currentPool == null) {
            JOptionPane.showMessageDialog(view, "Bitte wählen Sie zuerst einen Pool aus.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Datei mit Fragen zum Importieren auswählen");
        int result = fileChooser.showOpenDialog(view);

        if (result == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                String line;
                int count = 0;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] parts = line.split("\\|");
                    if (parts[0].equals("TEXT")) {
                        currentPool.addQuestion(new TextQuestion(parts[1], parts[2]));
                        count++;
                    } else if (parts[0].equals("MC")) {
                        currentPool.addQuestion(new MultipleChoiceQuestion(parts[1], Arrays.asList(parts[3].split(";")), Integer.parseInt(parts[2])));
                        count++;
                    } else if (parts[0].equals("IMAGE")) {
                        currentPool.addQuestion(new ImageQuestion(parts[1], parts[2]));
                        count++;
                    }
                }
                currentPool.saveToFile();
                view.displayQuestions(currentPool.getQuestions());
                JOptionPane.showMessageDialog(view, count + " Frage(n) erfolgreich importiert.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Fehler beim Importieren: " + ex.getMessage());
            }
        }
    }

    private void handleDeleteQuestion() {
        if (view == null) return;
        int idx = view.getSelectedQuestionIndex();
        if (currentPool != null && idx != -1) {
            currentPool.removeQuestion(idx);
            try {
                currentPool.saveToFile();
                view.displayQuestions(currentPool.getQuestions());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "Fehler beim Speichern.");
            }
        }
    }
}