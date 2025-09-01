package com.quiz;

public class Question {
    private String questionText;
    private String[] options; // length should be 4
    private int correctIndex; // 0..3

    public Question(String questionText, String[] options, int correctIndex) {
        if (options == null || options.length != 4) {
            throw new IllegalArgumentException("Options must be an array of 4 strings.");
        }
        if (correctIndex < 0 || correctIndex > 3) {
            throw new IllegalArgumentException("correctIndex must be between 0 and 3.");
        }
        this.questionText = questionText;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }
} 
