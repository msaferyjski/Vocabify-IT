package view;

import controller.QuestionPoolController;
import model.QuestionPool;
import model.Question;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class QuestionPoolManagerView extends JPanel {
    private JList<String> poolList;
    private DefaultListModel<String> poolListModel;
    private JList<String> questionList;
    private DefaultListModel<String> questionListModel;

    public QuestionPoolManagerView(QuestionPoolController controller) {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Fragenpoolverwaltung");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 0));

        poolListModel = new DefaultListModel<>();
        poolList = new JList<>(poolListModel);
        poolList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        poolList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                controller.onPoolSelected(poolList.getSelectedValue());
            }
        });

        JScrollPane poolScroll = new JScrollPane(poolList);
        poolScroll.setBorder(BorderFactory.createTitledBorder("Pools"));
        centerPanel.add(poolScroll);

        questionListModel = new DefaultListModel<>();
        questionList = new JList<>(questionListModel);
        JScrollPane questionScroll = new JScrollPane(questionList);
        questionScroll.setBorder(BorderFactory.createTitledBorder("Fragen im Pool"));
        centerPanel.add(questionScroll);

        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setPreferredSize(new Dimension(180, 0));

        buttonPanel.add(UI.menuButton("Pool erstellen", "CREATE_POOL", controller));
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(UI.menuButton("Pool importieren", "IMPORT_POOL", controller));
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(UI.menuButton("Pool exportieren", "EXPORT_POOL", controller));
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(UI.menuButton("Pool löschen", "DELETE_POOL", controller));
        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(new JSeparator());
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(UI.menuButton("Frage hinzufügen", "ADD_QUESTION", controller));
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(UI.menuButton("Frage löschen", "DELETE_QUESTION", controller));
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(UI.menuButton("Zurück", "BACK", controller));
        add(buttonPanel, BorderLayout.EAST);

        refreshPoolList();
    }

    public void refreshPoolList() {
        poolListModel.clear();
        QuestionPool.getAvailablePoolNames().forEach(poolListModel::addElement);
        questionListModel.clear();
    }

    public void displayQuestions(List<Question> questions) {
        questionListModel.clear();
        if (questions != null) {
            questions.forEach(q -> questionListModel.addElement(q.getQuestionText()));
        }
    }

    public String getSelectedPool() { return poolList.getSelectedValue(); }
    public int getSelectedQuestionIndex() { return questionList.getSelectedIndex(); }
}