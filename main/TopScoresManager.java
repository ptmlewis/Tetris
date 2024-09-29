package main;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TopScoresManager {
    private static final String SCORE_FILE = "top_scores.json";
    private static final int MAX_SCORES = 10;
    private List<ScoreEntry> topScores;

    public TopScoresManager() {
        topScores = loadTopScores();
    }

    // Load the top scores from the JSON file
    private List<ScoreEntry> loadTopScores() {
        List<ScoreEntry> scores = new ArrayList<>();
        try {
            if (Files.exists(Paths.get(SCORE_FILE))) {
                String content = new String(Files.readAllBytes(Paths.get(SCORE_FILE)));
                JSONArray jsonArray = new JSONArray(content);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject scoreObj = jsonArray.getJSONObject(i);
                    String name = scoreObj.getString("name");
                    int score = scoreObj.getInt("score");
                    scores.add(new ScoreEntry(name, score));
                }
            } else {
                System.out.println("No existing top scores file found. Starting fresh.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }

    // Save the top scores to the JSON file
    private void saveTopScores() {
        JSONArray jsonArray = new JSONArray();
        for (ScoreEntry score : topScores) {
            JSONObject scoreObj = new JSONObject();
            scoreObj.put("name", score.getName());
            scoreObj.put("score", score.getScore());
            jsonArray.put(scoreObj);
        }
        try (FileWriter file = new FileWriter(SCORE_FILE)) {
            file.write(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add a new score and update the top 10 scores
    public void addScore(String name, int score) {
        try {
            // Validate input: Ensure name is not null or empty
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Player name cannot be null or empty.");
            }

            // Validate input: Ensure the score is a positive number
            if (score < 0) {
                throw new IllegalArgumentException("Score must be a positive integer.");
            }

            if (score > 0) {
                topScores.add(new ScoreEntry(name, score));

                // Sort scores in descending order
                topScores.sort(Comparator.comparingInt(ScoreEntry::getScore).reversed());

                // Keep only the top 10 scores
                if (topScores.size() > MAX_SCORES) {
                    topScores.remove(topScores.size() - 1);  // Remove lowest score
                }

                // Save the updated top scores to the file
                saveTopScores();
            }
        } catch (IllegalArgumentException e) {
            // Handle input validation errors
            JOptionPane.showMessageDialog(null, "Invalid input: " + e.getMessage(),
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            // Handle any other unexpected errors
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An unexpected error occurred while adding the score.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Retrieve the top scores for display
    public List<ScoreEntry> getTopScores() {
        return topScores;
    }

    // Clear all top scores
    public void clearTopScores() {
        topScores.clear();
        saveTopScores();
    }

    public int getLowestScore() {
        if (topScores.isEmpty()) {
            return 0; // If no scores exist, return 0
        }
        // Return the score of the last entry in the sorted list, which is the lowest score
        return topScores.get(topScores.size() - 1).getScore();
    }
    // Display the top scores in a dialog
    public void displayTopScores() {
        StringBuilder scoreText = new StringBuilder("Top Scores:\n");
        for (ScoreEntry score : topScores) {
            scoreText.append(score.getName()).append(": ").append(score.getScore()).append("\n");
        }
        JOptionPane.showMessageDialog(null, scoreText.toString(), "Top Scores", JOptionPane.INFORMATION_MESSAGE);
    }

    public static class ScoreEntry {
        private final String name;
        private final int score;

        public ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
    }
}
