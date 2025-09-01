package com.quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizManager {
    private List<Question> questions;
    private int[] userAnswers; // -1 for unanswered, otherwise 0..3
    private int currentIndex = 0;
    private int timePerQuestionSeconds = 30;

    public QuizManager() {
        this.questions = loadSampleQuestions();
        this.userAnswers = new int[questions.size()];
        for (int i = 0; i < userAnswers.length; i++) userAnswers[i] = -1;
    }

    private List<Question> loadSampleQuestions() {
        // You can replace/extend these with your own questions.
        List<Question> list = new ArrayList<>();
        list.add(new Question("Which of the following is a SQL injection prevention technique?",
                new String[] {"Use prepared statements / parameterized queries", "Store DB credentials in code", "Use string concatenation for queries", "Disable authentication"},
                0));
        list.add(new Question("What does XSS stand for in web security?",
                new String[] {"Cross-Site Scripting", "Cross-Site Spoofing", "XML Site Scripting", "Cross Server Scripting"},
                0));
        list.add(new Question("Which header helps prevent clickjacking?",
                new String[] {"X-Frame-Options", "Content-Security-Policy", "Cache-Control", "Set-Cookie"},
                0));
        list.add(new Question("Which HTTP method should not be used to perform delete operations without authorization?",
                new String[] {"OPTIONS", "GET", "DELETE", "HEAD"},
                2));
        list.add(new Question("Which OWASP risk deals with insecure direct object references?",
                new String[] {"Broken Access Control", "Sensitive Data Exposure", "Security Misconfiguration", "Insecure Deserialization"},
                0));
        return list;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getQuestionCount() {
        return questions.size();
    }

    public Question getCurrentQuestion() {
        return questions.get(currentIndex);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int idx) {
        if (idx < 0 || idx >= questions.size()) throw new IndexOutOfBoundsException("Invalid question index");
        currentIndex = idx;
    }

    public void next() {
        if (currentIndex < questions.size() - 1) currentIndex++;
    }

    public void previous() {
        if (currentIndex > 0) currentIndex--;
    }

    public void setUserAnswer(int questionIndex, int optionIndex) {
        if (questionIndex < 0 || questionIndex >= userAnswers.length) throw new IndexOutOfBoundsException();
        if (optionIndex < -1 || optionIndex > 3) throw new IllegalArgumentException();
        userAnswers[questionIndex] = optionIndex;
    }

    public int getUserAnswer(int questionIndex) {
        return userAnswers[questionIndex];
    }

    public int[] getAllUserAnswers() {
        return userAnswers;
    }

    public int calculateScore() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers[i] == questions.get(i).getCorrectIndex()) score++;
        }
        return score;
    }

    public int getTimePerQuestionSeconds() {
        return timePerQuestionSeconds;
    }

    public void setTimePerQuestionSeconds(int seconds) {
        if (seconds <= 0) throw new IllegalArgumentException("seconds must be positive");
        this.timePerQuestionSeconds = seconds;
    }

    /**
     * Validate before final submit: throw InvalidInputException if any unanswered questions exist.
     */
    public void validateAllAnsweredOrThrow() throws InvalidInputException {
        for (int i = 0; i < userAnswers.length; i++) {
            if (userAnswers[i] == -1) {
                throw new InvalidInputException("There are unanswered questions. Please answer all questions before submitting.");
            }
        }
    }
}
