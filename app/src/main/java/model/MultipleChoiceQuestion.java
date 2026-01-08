package model;

import java.util.List;

public class MultipleChoiceQuestion extends Question {
    private List<String> options;
    private int correctIndex;

    public MultipleChoiceQuestion(String text, List<String> options, int correctIndex) {
        super(text);
        this.options = options;
        this.correctIndex = correctIndex;
    }

    public List<String> getOptions() { return options; }
    public int getCorrectIndex() { return correctIndex; }

    @Override
    public String getCorrectAnswer() {
        if (correctIndex >= 0 && correctIndex < options.size()) {
            return options.get(correctIndex);
        }
        return "";
    }

    @Override
    public String toTxtFormat() {
        return "MC|" + questionText + "|" + correctIndex + "|" + String.join(";", options);
    }
}
