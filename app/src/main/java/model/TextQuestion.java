package model;

public class TextQuestion extends Question {
    private String answer;

    public TextQuestion(String text, String answer) {
        super(text);
        this.answer = answer;
    }

    public String getAnswer() { return answer; }

    @Override
    public String getCorrectAnswer() {
        return answer;
    }

    @Override
    public String toTxtFormat() {
        return "TEXT|" + questionText + "|" + answer;
    }
}