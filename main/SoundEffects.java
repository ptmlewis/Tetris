// SoundFactory.java
package main;

public class SoundEffects{
    public static Sound createSound(String type) {
        Sound sound = Sound.getInstance();
        switch (type) {
            case "gameover":
                sound.play(1, false);
                break;
            case "rotation":
                sound.play(2, false);
                break;
            case "levelup":
                sound.play(6, false);
                break;
            case "levelup 2":
                sound.play(6, false);
                break;
            case "touch_floor":
                sound.play(3, false);
                break;
            case "line_deleted":
                sound.play(0, false);
                break;
            default:
                throw new IllegalArgumentException("Unknown sound type: " + type);
        }
        return sound;
    }
}
