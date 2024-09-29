package main;

import mino.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.Key;
import java.util.ArrayList;
import java.util.Random;

import main.GamePanel;

public class PlayManager {
    private JFrame getGameWindow;
    private GamePanel gamePanel;
    private boolean isGameRunning = true;


    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    Mino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();

    public static int dropInterval; //Mino drop speed
    boolean gameOver;

    // Score
    int level;
    int initial_level = level;
    int lines;
    int score;

    private static boolean musicPaused = false;

    private JFrame gameWindow;

    public void pauseGame() {
        if (KeyHandler.pausePressed == true) {
            GamePanel.music.pause();
        }
    }

    public void pauseMusic() {
        if (musicPaused) {
            GamePanel.music.resume();
        } else {
            GamePanel.music.pause();
        }
        musicPaused = !musicPaused;
    }

    public void unpauseGame(){
        if (KeyHandler.pausePressed == false){
            GamePanel.music.resume();
            gamePanel.requestFocusInWindow();
        }
    }

    public void playSoundEffect(String soundType) {
        if (ConfigurationScreen.isSoundEffectsOn) {
            SoundEffects.createSound(soundType);
        }
    }


    public PlayManager(JFrame gameWindow, GamePanel gamePanel) {
        this.gameWindow = gameWindow;
        this.gamePanel = gamePanel;

        left_x = (GamePanel.WIDTH/2) - (WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        // Set game level and drop interval based on level config
        this.level = ConfigurationScreen.gameLevel;
        setDropSpeed(level);

        MINO_START_X = left_x + + (WIDTH/2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXTMINO_X = right_x + 175;
        NEXTMINO_Y = top_y+ 500;

        staticBlocks.clear();
        gameOver = false;

        currentMino = pickMino();
        currentMino.setXY(MINO_START_X,MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //pauseGame();
                GameFacade.pauseGame();
                int confirmed = JOptionPane.showConfirmDialog(
                        gameWindow,
                        "Are you sure you want to exit the game?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmed == JOptionPane.YES_OPTION) {
                    stopGame();
                    GamePanel.music.stop();
                    clearGameState();
                    gameWindow.dispose();
                    new MainScreen().setVisible(true);
                } else {
                    //unpauseGame();
                    GameFacade.resumeGame();
                    gamePanel.requestFocusInWindow();
                }
            }
        });
        gameWindow.add(backButton,BorderLayout.SOUTH);
    }

    // set the Tetromino drop speed based on game level
    public void setDropSpeed(int level){
        dropInterval = Math.max(1,60-(level*5));
    }
    public void stopGame(){
        if (gamePanel.gameThread != null){
            gamePanel.gameThread.interrupt();
            try {
                gamePanel.gameThread.join(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            if (gamePanel.gameThread.isAlive()){
                System.err.println(" ");
            }
            gamePanel.gameThread = null;
            GamePanel.music.stop();
        }
    }

    public void clearGameState() {
        staticBlocks.clear();
        gameOver = false;
        KeyHandler.pausePressed = false;
    }

    private Mino pickMino() {

        Mino mino = null;
        int i = new Random().nextInt(7);

        switch(i) {
            case 0: mino = new Mino_L1();break;
            case 1: mino = new Mino_L2();break;
            case 2: mino = new Mino_Square();break;
            case 3: mino = new Mino_Bar();break;
            case 4: mino = new Mino_T();break;
            case 5: mino = new Mino_Z1();break;
            case 6: mino = new Mino_Z2();break;
        }
        return mino;
    }
    public void update(){

        if (currentMino.active == false){

            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y){

                gameOver = true;
                GamePanel.music.stop();
                if (GamePanel.isSoundOn) {
                    playSoundEffect("gameover");
                }
                checkGameOver();
            }

            currentMino.deactivating = false;

            currentMino = nextMino;
            currentMino.setXY(MINO_START_X,MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXTMINO_X,NEXTMINO_Y);

            checkDelete();
        }
        else {
            currentMino.update();
        }
        currentMino.update();
    }

    private void checkDelete(){

        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int lineCount = 0;

        while (x < right_x && y < bottom_y){

            for (int i = 0; i < staticBlocks.size(); i++ ){
                if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y){
                    blockCount++;
                }
            }
            x += Block.SIZE;

            if(x == right_x) {

                if (blockCount == 12){

                    for (int i = staticBlocks.size()-1; i > -1; i--){
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                            if (GamePanel.isSoundOn){
                                GamePanel.se.play(0,false);
                            }
                        }
                    }

                    lineCount++;
                    lines++;
                    // Drop Speed
                    // if the line score hits a certain number, increase the drop speed
                    // 1 is the fastest
                    if (lines % 10 == 0 && dropInterval > 1){

                        level++;
                        if (GamePanel.isSoundOn) {
                            GamePanel.se.play(6, false);
                            GamePanel.se.play(7, false);
                            playSoundEffect("levelup");
                            playSoundEffect("levelup 2");
                        }
                        if (dropInterval > 10){
                            dropInterval -= 10;
                        }
                        else {
                            dropInterval -= 1;
                        }
                    }

                    for (int i = 0; i < staticBlocks.size(); i++){
                        if (staticBlocks.get(i).y < y){
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }
                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }

        // Add score
        if (lineCount > 0) {
            switch (lineCount) {
                case 1:
                    score += 100;
                    break;
                case 2:
                    score += 300;
                    break;
                case 3:
                    score += 600;
                    break;
                case 4:
                    score += 1000;
                    break;
                default:
                    // No bonus for more than 4 lines erased at once
                    break;
            }
        }

    }
    public void checkGameOver() {
        if (gameOver) {
            TopScoresManager topScoresManager = new TopScoresManager();
            int lowestTopScore = topScoresManager.getLowestScore();

            // Check if the score qualifies for the top 10 list
            if (score > lowestTopScore || topScoresManager.getTopScores().size() < 10) {
                // Prompt for player name if it's a top score
                String playerName = JOptionPane.showInputDialog("Congrats! Enter your name:");
                // Add the player's score if the name is valid
                if (playerName != null && !playerName.trim().isEmpty()) {
                    topScoresManager.addScore(playerName, score);
                }
            }
            // Display the updated top scores
            topScoresManager.displayTopScores();
        }
    }

    public void draw(Graphics2D g2){

        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4, top_y-4,WIDTH+8,HEIGHT+8);

        //Draw Next Mino Frame
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x,y,200,200);
        g2.setFont(new Font("Arial", Font.PLAIN,30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT",x+60,y+60);

        // Draw Score Frame
        g2.drawRect(x, top_y,250,250);
        x += 20;
        g2.setFont(new Font("Arial", Font.PLAIN, 22));
        y = top_y + 50;
        g2.drawString("PLAYER: HUMAN", x, y); y+= 40;
        g2.drawString("INITIAL LEVEL: " + initial_level, x, y); y+= 40;
        g2.drawString("CURRENT LEVEL: " + level, x, y); y+= 40;
        g2.drawString("LINES ERASED: " + lines, x, y); y+= 40;
        g2.drawString("SCORE: " + score, x, y);



        // Draw the next mino
        if (currentMino != null){
            currentMino.draw(g2);
        }

        // Draw the next mino
        nextMino.draw(g2);

        for(int i = 0; i < staticBlocks.size(); i++){
            staticBlocks.get(i).draw(g2);
        }
        g2.setColor(Color.black);
        g2.setFont(g2.getFont().deriveFont(50f));

        //GAME OVER DISPLAY
        if (gameOver) {
            x = left_x + 25;
            y = top_y + 325;
            g2.drawString("GAME OVER",x,y);
        }

        if (KeyHandler.musicPressed){
            GameFacade.pauseTheMusic();
        }

        // PAUSE DISPLAY
        else if(KeyHandler.pausePressed){
            x = left_x + 75;
            y = top_y + 320;
            g2.drawString("PAUSED",x,y);

            g2.setFont(g2.getFont().deriveFont(20f));
            g2.drawString("Press 'P' to unpause game",x-25,y+30);
        }

    }

}