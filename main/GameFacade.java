package main;

public class GameFacade {
    public static PlayManager playManager;
    private static Sound music;

    public static void pauseGame() {
        if (playManager != null) {
            playManager.pauseGame();
        } else {
            System.err.println("playManager is null");
        }
    }

    public static void resumeGame() {
        if (playManager != null) {
            playManager.unpauseGame();
        } else {
            System.err.println("playManager is null");
        }
    }

    static void playBackgroundMusic() {
        Sound.getInstance().play(5, true);
        Sound.getInstance().loop();
    }

    public static void stopGame() {
        if (playManager != null) {
            playManager.stopGame();
        } else {
            System.err.println("playManager is null");
        }
    }

    public static void pauseTheMusic(){
        if (playManager != null) {
            playManager.pauseMusic();
        } else {
            System.err.println("playManager is null");
        }
    }
}
