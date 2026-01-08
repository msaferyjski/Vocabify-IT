package model;

import java.io.Serializable;

public abstract class Question implements Serializable {
    protected String questionText;

    public Question(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionText() { return questionText; }

    public abstract String getCorrectAnswer();

    public abstract String toTxtFormat();
}
