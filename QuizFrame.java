package com.quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizFrame extends JFrame {
    private QuizManager manager;
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup bg;
    private JButton prevBtn, nextBtn, submitBtn;
    private JLabel timerLabel;
    private Timer swingTimer; // javax.swing.Timer
    private int remainingSeconds;

    public QuizFrame(QuizManager manager) {
        super("Online Quiz System - (OWASP TOP 10 themed sample)");
        this.manager = manager;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 350);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        initComponents();
        loadQuestionToUI();
        startTimerForCurrentQuestion();

        this.setVisible(true);
    }

    private void initComponents() {
        // Top: question text and timer
        JPanel topPanel = new JPanel(new BorderLayout());
        questionLabel = new JLabel("", SwingConstants.LEFT);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        topPanel.add(questionLabel, BorderLayout.CENTER);

        timerLabel = new JLabel("", SwingConstants.RIGHT);
        timerLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
        timerLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        topPanel.add(timerLabel, BorderLayout.EAST);

        this.add(topPanel, BorderLayout.NORTH);

        // Center: options
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(4, 1, 4, 4));
        optionButtons = new JRadioButton[4];
        bg = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setActionCommand(String.valueOf(i));
            optionButtons[i].setFont(new Font("SansSerif", Font.PLAIN, 14));
            bg.add(optionButtons[i]);
            center.add(optionButtons[i]);
        }
        this.add(center, BorderLayout.CENTER);

        // Bottom: navigation buttons
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        prevBtn = new JButton("Previous");
        nextBtn = new JButton("Next");
        submitBtn = new JButton("Submit Quiz");

        prevBtn.addActionListener(e -> onPrevious());
        nextBtn.addActionListener(e -> onNext());
        submitBtn.addActionListener(e -> onSubmit());

        bottom.add(prevBtn);
        bottom.add(nextBtn);
        bottom.add(submitBtn);

        this.add(bottom, BorderLayout.SOUTH);
    }

    private void loadQuestionToUI() {
        int idx = manager.getCurrentIndex();
        Question q = manager.getCurrentQuestion();
        questionLabel.setText(String.format("Q%d. %s", idx + 1, q.getQuestionText()));
        String[] opts = q.getOptions();
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(opts[i]);
        }
        // Restore previous selection if any
        int prevAns = manager.getUserAnswer(idx);
        bg.clearSelection();
        if (prevAns >= 0 && prevAns <= 3) {
            optionButtons[prevAns].setSelected(true);
        }
        // Update prev/next button enable states
        prevBtn.setEnabled(idx > 0);
        nextBtn.setEnabled(idx < manager.getQuestionCount() - 1);
    }

    private void recordCurrentSelection() {
        int idx = manager.getCurrentIndex();
        ButtonModel sel = bg.getSelection();
        if (sel == null) {
            manager.setUserAnswer(idx, -1);
        } else {
            int selected = Integer.parseInt(sel.getActionCommand());
            manager.setUserAnswer(idx, selected);
        }
    }

    private void startTimerForCurrentQuestion() {
        if (swingTimer != null && swingTimer.isRunning()) swingTimer.stop();
        remainingSeconds = manager.getTimePerQuestionSeconds();

        updateTimerLabel();

        swingTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingSeconds--;
                updateTimerLabel();
                if (remainingSeconds <= 0) {
                    swingTimer.stop();
                    // Time up for current question -> auto-record unanswered (if none) and go next
                    recordCurrentSelection(); // if user selected at last sec, it's recorded
                    JOptionPane.showMessageDialog(QuizFrame.this,
                            "Time up for this question. Moving to next question.",
                            "Time Out", JOptionPane.INFORMATION_MESSAGE);
                    if (manager.getCurrentIndex() < manager.getQuestionCount() - 1) {
                        manager.next();
                        loadQuestionToUI();
                        startTimerForCurrentQuestion();
                    } else {
                        // last question timed out -> auto submit or ask user
                        int option = JOptionPane.showConfirmDialog(QuizFrame.this,
                                "Last question timed out. Do you want to submit the quiz now?",
                                "Submit?", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            onSubmit();
                        } else {
                            // restart timer for last question
                            startTimerForCurrentQuestion();
                        }
                    }
                }
            }
        });
        swingTimer.start();
    }

    private void updateTimerLabel() {
        timerLabel.setText(String.format("Time left: %02d:%02d",
                remainingSeconds / 60, remainingSeconds % 60));
    }

    private void onNext() {
        try {
            // record current selection before moving
            recordCurrentSelection();
            if (manager.getCurrentIndex() < manager.getQuestionCount() - 1) {
                manager.next();
                loadQuestionToUI();
                startTimerForCurrentQuestion();
            }
        } catch (Exception ex) {
            // general safety catch
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onPrevious() {
        try {
            recordCurrentSelection();
            if (manager.getCurrentIndex() > 0) {
                manager.previous();
                loadQuestionToUI();
                startTimerForCurrentQuestion();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onSubmit() {
        try {
            // record current selection for last view
            recordCurrentSelection();

            // Validate: if any unanswered questions -> raise InvalidInputException
            manager.validateAllAnsweredOrThrow();

            // If validation passed, stop timer and show result
            if (swingTimer != null && swingTimer.isRunning()) swingTimer.stop();
            int score = manager.calculateScore();
            int total = manager.getQuestionCount();
            double percent = (score * 100.0) / total;

            String message = String.format("Your Score: %d/%d (%.2f%%)\nWould you like to review answers?", score, total, percent);
            int option = JOptionPane.showConfirmDialog(this, message, "Quiz Result", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                showReviewDialog();
            } else {
                // exit or restart
                int again = JOptionPane.showConfirmDialog(this, "Do you want to take the quiz again?", "Restart?", JOptionPane.YES_NO_OPTION);
                if (again == JOptionPane.YES_OPTION) {
                    restartQuiz();
                } else {
                    System.exit(0);
                }
            }

        } catch (InvalidInputException iiex) {
            JOptionPane.showMessageDialog(this, iiex.getMessage(), "Unanswered Questions", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error during submit: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showReviewDialog() {
        StringBuilder sb = new StringBuilder();
        int[] user = manager.getAllUserAnswers();
        for (int i = 0; i < manager.getQuestionCount(); i++) {
            Question q = manager.getQuestions().get(i);
            sb.append(String.format("Q%d: %s\n", i + 1, q.getQuestionText()));
            for (int j = 0; j < 4; j++) {
                String opt = q.getOptions()[j];
                String marker = "";
                if (j == q.getCorrectIndex()) marker += " (Correct)";
                if (user[i] == j) marker += " (Your Answer)";
                sb.append(String.format("    %d) %s%s\n", j + 1, opt, marker));
            }
            sb.append("\n");
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        area.setCaretPosition(0);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(this, scroll, "Review Answers", JOptionPane.INFORMATION_MESSAGE);

        int again = JOptionPane.showConfirmDialog(this, "Do you want to take the quiz again?", "Restart?", JOptionPane.YES_NO_OPTION);
        if (again == JOptionPane.YES_OPTION) {
            restartQuiz();
        } else {
            System.exit(0);
        }
    }

    private void restartQuiz() {
        // reset manager state
        manager = new QuizManager();
        // replace UI manager reference and reload
        loadQuestionToUI();
        startTimerForCurrentQuestion();
    }
}
