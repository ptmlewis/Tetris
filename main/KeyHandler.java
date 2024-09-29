package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    // Player 1 controls
    public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed, musicPressed, soundPressed;
    // Player 2 controls (WASD keys)
    public static boolean wPressed, aPressed, sPressed, dPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        // Unused
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Player 1 controls
        if(code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_P){
            pausePressed = !pausePressed;
        }
        if(code == KeyEvent.VK_M) {
            musicPressed = true;
        }
        if (code == KeyEvent.VK_S){
            soundPressed = true;
        }

        // Player 2 controls (WASD)
        if(code == KeyEvent.VK_W) {
            wPressed = true;
        }
        if(code == KeyEvent.VK_A) {
            aPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            sPressed = true;
        }
        if(code == KeyEvent.VK_D) {
            dPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        // Player 1 controls
        if(code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }

        // Player 2 controls (WASD)
        if(code == KeyEvent.VK_W) {
            wPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            aPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            sPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            dPressed = false;
        }
    }
}
