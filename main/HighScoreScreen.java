package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HighScoreScreen extends JFrame {
    private TopScoresManager topScoresManager;
    private DefaultTableModel model;

    public HighScoreScreen() {
        setTitle("High Score");
        setSize(600, 500);
        setLayout(new BorderLayout());
        topScoresManager = new TopScoresManager();

        // Title Label
        JLabel titleLabel = new JLabel("High Score", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        add(titleLabel, BorderLayout.NORTH);

        // Table for displaying high scores
        String[] columnNames = {"#", "Name", "Score", "Config"};
        model = new DefaultTableModel(columnNames, 0);

        // Fetch top scores and populate the table
        updateTable();

        JTable scoreTable = new JTable(model);
        scoreTable.setEnabled(false);
        scoreTable.setFont(new Font("Arial", Font.PLAIN, 18));
        scoreTable.setRowHeight(30);
        scoreTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        scoreTable.getTableHeader().setReorderingAllowed(false);

        // Add scroll pane around the table
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel with Back and Clear buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            setVisible(false);
            new MainScreen().setVisible(true);
        });
        bottomPanel.add(backButton, BorderLayout.WEST);

        // Clear High Scores Button
        JButton clearButton = new JButton("Clear High Scores");
        clearButton.addActionListener(e -> {
            int confirmed = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to clear all high scores?",
                    "Clear Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                topScoresManager.clearTopScores();
                updateTable();
            }
        });
        bottomPanel.add(clearButton, BorderLayout.EAST);

        // Author Label
        JLabel authorLabel = new JLabel("Author: Peter Lewis", SwingConstants.CENTER);
        bottomPanel.add(authorLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateTable() {
        model.setRowCount(0);

        List<TopScoresManager.ScoreEntry> topScores = topScoresManager.getTopScores();
        for (int i = 0; i < topScores.size(); i++) {
            TopScoresManager.ScoreEntry entry = topScores.get(i);
            model.addRow(new Object[]{"(" + (i + 1) + ")", entry.getName(), entry.getScore(), "---"});
        }

        // Fill remaining rows if there are less than 10 entries
        for (int i = topScores.size(); i < 10; i++) {
            model.addRow(new Object[]{"(" + (i + 1) + ")", "---", "0", "---"});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HighScoreScreen());
    }
}
