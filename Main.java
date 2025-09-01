package com.quiz;

import javax.swing.*;

/**
 * Entry point
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuizManager manager = new QuizManager();
            // optional: set a custom time per question (seconds)
            manager.setTimePerQuestionSeconds(30); // 30 seconds per question
            new QuizFrame(manager);
        });
    }
}
