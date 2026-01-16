package model;

public class ImageQuestion extends Question {
    private String answer;

    public ImageQuestion(String imageUrl, String answer) {
        super(imageUrl);
        this.answer = answer;
    }

    public String getImageUrl() {
        return questionText;
    }

    @Override
    public String getCorrectAnswer() {
        return answer;
    }

    @Override
    public String toTxtFormat() {
        return "IMAGE|" + questionText + "|" + answer;
    }
}