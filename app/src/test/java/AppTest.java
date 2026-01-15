import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private QuestionPool testPool;

    @BeforeEach
    void setUp() {
        // Prepare a fresh pool for each test
        testPool = new QuestionPool("TestPool");
        testPool.addQuestion(new TextQuestion("What is Apple in German?", "Apfel"));
        testPool.addQuestion(new MultipleChoiceQuestion("Capital of Germany?",
                Arrays.asList("Berlin", "Munich", "Hamburg"), 0));
    }

    @Test
    void testGameModelLogic() {
        GameModel game = new GameModel("Java", "Programming Language");

        // wordToGuess should be the uppercase input without extra spaces
        assertEquals("JAVA", game.getWordToGuess());
        // displayWord should have underscores separated by spaces
        assertEquals("_ _ _ _", game.getDisplayWord());

        // Test correct guess
        assertTrue(game.guessLetter('j'));
        assertEquals("J _ _ _", game.getDisplayWord());

        // Test case insensitivity and multiple occurrences (A)
        assertTrue(game.guessLetter('A'));
        assertEquals("J A _ A", game.getDisplayWord());

        // Test wrong guess
        assertFalse(game.guessLetter('z'));
        assertEquals(5, game.getRemainingLives()); // Initial 6 - 1

        // Test win condition
        game.guessLetter('v');
        assertTrue(game.isWon());
        assertFalse(game.isLost());
    }

    @Test
    void testQuizModelProgression() {
        QuizModel quiz = new QuizModel(testPool);

        assertEquals(2, quiz.getTotal());
        assertFalse(quiz.isFinished());

        Question first = quiz.getCurrentQuestion();
        assertNotNull(first);

        if (first instanceof TextQuestion) {
            assertTrue(quiz.checkAnswer("Apfel"));
        } else if (first instanceof MultipleChoiceQuestion) {
            assertTrue(quiz.checkAnswer("0")); // The correct index for Berlin
        }

        assertEquals(1, quiz.getScore());
        assertFalse(quiz.isFinished());

        // Answer the second question incorrectly to finish the quiz
        quiz.checkAnswer("wrong input");
        assertTrue(quiz.isFinished());
        assertEquals(1, quiz.getScore());
    }

    @Test
    void testQuestionFormats() {
        // Verify TextQuestion serialization format
        TextQuestion tq = new TextQuestion("Hello", "Hallo");
        assertEquals("TEXT|Hello|Hallo", tq.toTxtFormat());
        assertEquals("Hallo", tq.getCorrectAnswer());

        // Verify MultipleChoiceQuestion serialization format
        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion("Which is blue?", 
                Arrays.asList("Red", "Blue", "Green"), 1);
        assertEquals("MC|Which is blue?|1|Red;Blue;Green", mcq.toTxtFormat());
        assertEquals("Blue", mcq.getCorrectAnswer());
    }

    @Test
    void testQuestionPoolManagement() {
        QuestionPool pool = new QuestionPool("ManagementTest");
        TextQuestion q = new TextQuestion("Q", "A");
        
        pool.addQuestion(q);
        assertEquals(1, pool.getQuestions().size());
        
        pool.removeQuestion(0);
        assertTrue(pool.getQuestions().isEmpty());
    }
}