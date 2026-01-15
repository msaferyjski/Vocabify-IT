package view;

import controller.QuizController;
import model.MultipleChoiceQuestion;
import model.Question;
import model.TextQuestion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

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
        questionLabel.setText(q.getQuestionText());
        inputPanel.removeAll();

        if (q instanceof TextQuestion) {
            JTextField field = new JTextField();
            field.setName("ANSWER_FIELD");
            field.setMaximumSize(new Dimension(400, 30));
            inputPanel.add(Box.createVerticalGlue());
            inputPanel.add(field);
            inputPanel.add(Box.createVerticalGlue());
        } else if (q instanceof MultipleChoiceQuestion mcq) {
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