package main;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen(3000); // 3 seconds duration
        splash.showSplash();

        SwingUtilities.invokeLater(() -> {
            new MainScreen().setVisible(true);
        });

    }
}