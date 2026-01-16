package view;

import controller.QuizController;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class QuizView extends JPanel {
    private final JLabel questionLabel;
    private final JPanel inputPanel;
    private final QuizController controller;

    public QuizView(QuizController controller) {
        this.controller = controller;

        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(30, 30, 30, 30));

        questionLabel = new JLabel("Warten auf Start...", SwingConstants.CENTER);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(questionLabel, BorderLayout.NORTH);

        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        add(inputPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(UI.menuButton("Antwort prüfen", "CHECK_ANSWER", controller));
        bottomPanel.add(UI.menuButton("Beenden", "BACK", controller));
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void showQuestion(Question q) {
        inputPanel.removeAll();

        if (q instanceof ImageQuestion imgQ) {
            questionLabel.setText("Benenne das abgebildete Objekt:");
            try {
                ImageIcon icon = new ImageIcon(new URL(imgQ.getImageUrl()));
                Image img = icon.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(img));
                imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                inputPanel.add(imageLabel);
            } catch (Exception e) {
                JLabel errorLabel = new JLabel("Bild konnte nicht geladen werden.");
                errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                inputPanel.add(errorLabel);
            }
            addTextField();
        } else if (q instanceof TextQuestion) {
            questionLabel.setText(q.getQuestionText());
            addTextField();
        } else if (q instanceof MultipleChoiceQuestion mcq) {
            questionLabel.setText(q.getQuestionText());
            ButtonGroup group = new ButtonGroup();
            for (int i = 0; i < mcq.getOptions().size(); i++) {
                JRadioButton rb = new JRadioButton(mcq.getOptions().get(i));
                rb.setActionCommand(String.valueOf(i));
                rb.setName("MC_OPTION");
                group.add(rb);
                inputPanel.add(rb);
            }
        }

        revalidate();
        repaint();
    }

    private void addTextField() {
        inputPanel.add(Box.createVerticalStrut(15));
        JTextField field = new JTextField();
        field.setName("ANSWER_FIELD");
        field.setMaximumSize(new Dimension(400, 30));
        inputPanel.add(field);
    }

    public String getAnswer() {
        for (Component c : inputPanel.getComponents()) {
            if (c instanceof JTextField tf && "ANSWER_FIELD".equals(tf.getName())) {
                return tf.getText();
            }
            if (c instanceof JRadioButton rb && rb.isSelected()) {
                return rb.getActionCommand();
            }
        }
        return "";
    }
}