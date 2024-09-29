
package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame {

    public MainScreen() {

        setTitle("Tetris Game Main Screen");

        int width = 800;
        int height = 600;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        Color backgroundColor = new Color(211, 211, 211);  // Light Grey

        mainPanel.setBackground(backgroundColor);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(backgroundColor);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));

        JLabel titleLabel = new JLabel("Main Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(backgroundColor);


        titlePanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4,1,10,10));
        buttonPanel.setBackground(backgroundColor);

        buttonPanel.setPreferredSize(new Dimension(200,200));

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(100,300,100,300));

        JButton playButton = new JButton("Play");
        JButton configButton = new JButton("Configuration");
        JButton highScoresButton = new JButton("High Scores");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(playButton);
        buttonPanel.add(configButton);
        buttonPanel.add(highScoresButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame window = new JFrame("Tetris");

                if (ConfigurationScreen.getExtendMode()){
                    window.setLayout(new GridLayout(1,2));

                    GamePanel player1Panel = new GamePanel(window);
                    GamePanel player2Panel = new GamePanel(window);

                    window.add(player1Panel);
                    window.add(player2Panel);

                    player1Panel.launchGame();
                    player2Panel.launchGame();

                } else {
                    // 1 Player Mode
                    GamePanel gp = new GamePanel(window);
                    window.add(gp);
                    gp.launchGame();
                }
                window.pack();
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setResizable(false);
                window.setLocationRelativeTo(null);
                window.setVisible(true);

                MainScreen.this.setVisible(false);

            }
        });

        configButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                new ConfigurationScreen().setVisible(true);;
            }
        });

        highScoresButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new HighScoreScreen().setVisible(true);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(
                        MainScreen.this,
                        "Are you sure you want to exit the game?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmed == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        getContentPane().add(mainPanel);

        setVisible(true);
    }
}
