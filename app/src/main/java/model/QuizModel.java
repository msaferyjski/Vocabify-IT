package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizModel {
    private List<Question> questions;
    private int currentIndex;
    private int score;

    public QuizModel(QuestionPool pool) {
        this.questions = new ArrayList<>(pool.getQuestions());
        Collections.shuffle(this.questions);
        this.currentIndex = 0;
        this.score = 0;
    }

    public Question getCurrentQuestion() {
        if (currentIndex < questions.size()) {
            return questions.get(currentIndex);
        }
        return null;
    }

    public boolean checkAnswer(String input) {
        Question q = getCurrentQuestion();
        boolean correct = false;
        if (q instanceof TextQuestion tq) {
            correct = tq.getAnswer().equalsIgnoreCase(input.trim());
        } else if (q instanceof MultipleChoiceQuestion mcq) {
            try {
                correct = Integer.parseInt(input) == mcq.getCorrectIndex();
            } catch (NumberFormatException e) {
                correct = false;
            }
        }
        if (correct) score++;
        currentIndex++;
        return correct;
    }

    public boolean isFinished() {
        return currentIndex >= questions.size();
    }

    public int getScore() { return score; }
    public int getTotal() { return questions.size(); }
}