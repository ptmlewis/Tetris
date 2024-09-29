package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static boolean isSoundOn;
    final int FPS = 75;
    Thread gameThread;
    PlayManager pm;
    public static Sound music = Sound.getInstance();
    public static Sound se = Sound.getInstance();

    private boolean isMusicOn = ConfigurationScreen.isMusicOn;

    public GamePanel(JFrame gameWindow){
        isSoundOn = ConfigurationScreen.isSoundEffectsOn;
        //Panel Settings
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.gray);
        this.setLayout(null);
        //implement KeyListener
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);
        this.requestFocusInWindow();

        pm = new PlayManager(gameWindow,this);
    }

    public void startMusic(){
        if (isMusicOn){
            GameFacade.playBackgroundMusic();
        }
    }

    public void launchGame(){
        gameThread = new Thread(this);
        gameThread.start();
        startMusic();
    }

    public void playSoundEffect(int soundEffectIndex) {
        if (isSoundOn) {
            GamePanel.se.play(soundEffectIndex, false);
        }
    }

    @Override
    public void run() {
        //Game Loop
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) /drawInterval;
            lastTime = currentTime;

            if (delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }
    private void update(){
        if (KeyHandler.pausePressed == false && pm.gameOver == false){
            pm.update();
        }

        if (KeyHandler.musicPressed){
            isMusicOn = !isMusicOn;
            if(isMusicOn) {
                GameFacade.playBackgroundMusic();
            } else {
                GamePanel.music.stop();
            }
            KeyHandler.musicPressed = false;
        }

        if (KeyHandler.soundPressed) {
            isSoundOn = !isSoundOn;
            KeyHandler.soundPressed = false;
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        pm.draw(g2);

        // Display current status of Music and Sound
        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.drawString("Music: " + (isMusicOn ? "On" : "Off"), 10, 30);
        g2.drawString("Sound: " + (isSoundOn ? "On" : "Off"), 10, 60);
    }
}