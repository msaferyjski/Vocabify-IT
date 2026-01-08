package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class GameView extends JPanel {
    private final JLabel questionLabel;
    private final JLabel wordLabel;
    private final JLabel statusLabel;
    private final JLabel guessedLettersLabel;
    private final JTextField inputField;

    public GameView() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(30, 40, 40, 40));

        // Display area
        JPanel centerPanel = new JPanel(new GridLayout(5, 1, 5, 5));

        questionLabel = new JLabel("Warte auf Spielstart...", SwingConstants.CENTER);
        questionLabel.setFont(new Font("SansSerif", Font.ITALIC, 20));
        questionLabel.setForeground(new Color(70, 70, 70));

        wordLabel = new JLabel("", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Monospaced", Font.BOLD, 36));

        statusLabel = new JLabel("Wähle einen Pool zum Starten", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        guessedLettersLabel = new JLabel("Bereits geraten: ", SwingConstants.CENTER);
        guessedLettersLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(50, 40));
        inputField.setFont(new Font("SansSerif", Font.BOLD, 20));
        inputField.setHorizontalAlignment(JTextField.CENTER);

        // DocumentFilter to restrict input and force uppercase
        ((AbstractDocument) inputField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String result = text.toUpperCase().replaceAll("[^A-ZÄÖÜ]", "");
                if (fb.getDocument().getLength() - length + result.length() <= 1) {
                    super.replace(fb, offset, length, result, attrs);
                }
            }
        });

        centerPanel.add(questionLabel);
        centerPanel.add(wordLabel);
        centerPanel.add(statusLabel);
        centerPanel.add(guessedLettersLabel);

        JPanel inputWrapper = new JPanel();
        inputWrapper.add(new JLabel("Buchstabe: "));
        inputWrapper.add(inputField);
        centerPanel.add(inputWrapper);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton guessBtn = UI.menuButton("Raten", "GUESS", null);
        JButton backBtn = UI.menuButton("Zurück", "BACK", null);

        bottomPanel.add(guessBtn);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void updateDisplay(String question, String maskedWord, int lives, String guessedLetters) {
        questionLabel.setText("Frage: " + question);
        wordLabel.setText(maskedWord);
        statusLabel.setText("Verbleibende Versuche: " + lives);
        guessedLettersLabel.setText("Bereits geraten: " + guessedLetters);
        inputField.setText("");
        inputField.requestFocus();
    }

    public String getGuess() {
        return inputField.getText().trim();
    }
}