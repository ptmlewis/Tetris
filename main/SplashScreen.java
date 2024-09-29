package main;
import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {
    private int duration;

    public SplashScreen(int duration) {
        this.duration = duration;
    }

    public void showSplash() {
        JPanel content = (JPanel)getContentPane();
        content.setBackground(Color.white);

        int width = 450;
        int height = 300;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        JLabel label = new JLabel(new ImageIcon("src/resources/splash-image.png"));
        JLabel copyrt = new JLabel("Created by Peter Lewis | Course Code: 2805ICT", JLabel.CENTER);
        copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        content.add(label, BorderLayout.CENTER);
        content.add(copyrt, BorderLayout.SOUTH);
        Color customColor = new Color(156, 20, 20, 255);
        content.setBorder(BorderFactory.createLineBorder(customColor, 10));

        setVisible(true);

        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(false);
    }
}
