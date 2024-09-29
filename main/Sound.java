package main;

import java.net.URL;
import javax.sound.sampled.*;

public class Sound {

    // Single instance of Sound class
    private static Sound instance;

    // Audio variables
    private Clip musicClip;
    private URL[] url = new URL[8];
    private Long currentMusicPosition = 0L;

    // Private constructor to prevent instantiation
    private Sound() {
        try {

        url[0] = getClass().getResource("/resources/beep.wav");
        url[1] = getClass().getResource("/resources/gameover 2.wav");
        url[2] = getClass().getResource("/resources/rotation.wav");
        url[3] = getClass().getResource("/resources/touch floor.wav");
        url[4] = getClass().getResource("/resources/touch floor.wav");
        url[5] = getClass().getResource("/resources/piano.wav");
        url[6] = getClass().getResource("/resources/levelup.wav");
        url[7] = getClass().getResource("/resources/levelup 2.wav");

        // Check if resources were found, otherwise throw exception
        for (int i = 0; i < url.length; i++) {
            if (url[i] == null) {
                throw new IllegalArgumentException("Sound resource not found for index: " + i);
            }
        }
    } catch(
    IllegalArgumentException e)

    {
        System.err.println("Error initializing sound resources: " + e.getMessage());
    }
}

    // Public method to provide the single instance of Sound class
    public static synchronized Sound getInstance() {
        if (instance == null) {
            instance = new Sound();
        }
        return instance;
    }

    // Method to play sound
    public void play(int i, boolean music) {
        try {
            if (i < 0 || i >= url.length || url[i] == null){
                throw new IllegalArgumentException("Invalid sound index: " + i);
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(url[i]);
            Clip clip = AudioSystem.getClip();

            if (music) {
                musicClip = clip;
            }

            clip.open(ais);
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });
            ais.close();
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Loop music continuously
    public void loop() {
        if (musicClip != null) {
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    // Pause music
    public void pause() {
        if (musicClip != null && musicClip.isRunning()) {
            currentMusicPosition = musicClip.getMicrosecondPosition();
            musicClip.stop();
        }
    }

    // Resume music
    public void resume() {
        if (musicClip != null) {
            musicClip.setMicrosecondPosition(currentMusicPosition);
            musicClip.start();
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    // Stop music
    public void stop() {
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
        }
    }
}
