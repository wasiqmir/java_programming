package com.quiz;

/**
 * Custom exception used when user tries to submit when some questions are unanswered
 * or other invalid inputs occur.
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
