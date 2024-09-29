package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigurationScreen extends JFrame {
    private JSlider fieldWidthSlider;
    private JSlider fieldHeightSlider;
    private JSlider gameLevelSlider;
    private JCheckBox musicToggle;
    private JCheckBox soundEffectsToggle;
    private JCheckBox aiPlayToggle;
    private JCheckBox extendModeToggle;
    private JButton backButton;

    public static int fieldWidth = 10;
    public static int fieldHeight = 20;
    public static boolean isMusicOn = true;
    public static boolean isSoundEffectsOn = true;
    public static boolean isExtendModeOn = false;
    public static int gameLevel = 1;

    public ConfigurationScreen() {
        setTitle("Configuration");
        setSize(300, 400);
        setLayout(new GridLayout(0, 1));

        // Field Width Slider
        fieldWidthSlider = new JSlider(10, 30, fieldWidth);
        fieldWidthSlider.setMajorTickSpacing(5);
        fieldWidthSlider.setPaintTicks(true);
        fieldWidthSlider.setPaintLabels(true);
        fieldWidthSlider.addChangeListener(e -> {
            fieldWidth = fieldWidthSlider.getValue();
        });
        add(new JLabel("Field Width:"));
        add(fieldWidthSlider);

        // Field Height Slider
        fieldHeightSlider = new JSlider(20, 40, fieldHeight);
        fieldHeightSlider.setMajorTickSpacing(5);
        fieldHeightSlider.setPaintTicks(true);
        fieldHeightSlider.setPaintLabels(true);
        fieldHeightSlider.addChangeListener(e -> {
            fieldHeight = fieldHeightSlider.getValue();
        });
        add(new JLabel("Field Height:"));
        add(fieldHeightSlider);

        // Game Level Slider
        gameLevelSlider = new JSlider(1, 10, gameLevel);
        gameLevelSlider.setMajorTickSpacing(1);
        gameLevelSlider.setPaintTicks(true);
        gameLevelSlider.setPaintLabels(true);
        gameLevelSlider.addChangeListener(e -> {
            gameLevel = gameLevelSlider.getValue();
        });
        add(new JLabel("Game Level:"));
        add(gameLevelSlider);

        // Music Toggle
        musicToggle = new JCheckBox("Music On/Off");
        musicToggle.setSelected(isMusicOn);
        musicToggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isMusicOn = musicToggle.isSelected();
            }
        });
        add(musicToggle);

        // Sound Effects Toggle
        soundEffectsToggle = new JCheckBox("Sound Effects On/Off");
        soundEffectsToggle.setSelected(isSoundEffectsOn);
        soundEffectsToggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isSoundEffectsOn = soundEffectsToggle.isSelected();
            }
        });
        add(soundEffectsToggle);

        // AI Play Toggle
        aiPlayToggle = new JCheckBox("AI Play");
        add(aiPlayToggle);

        // Extend Mode Toggle
        extendModeToggle = new JCheckBox("Extend Mode");
        extendModeToggle.setSelected(isExtendModeOn);
        extendModeToggle.addItemListener(e -> isExtendModeOn = extendModeToggle.isSelected());
        add(extendModeToggle);

        // Back Button
        backButton = new JButton("Back");
        backButton.addActionListener((ActionEvent e) -> {
            setVisible(false);
            new MainScreen().setVisible(true);
        });
        add(backButton);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static boolean getExtendMode() {
        return isExtendModeOn;
    }
}
