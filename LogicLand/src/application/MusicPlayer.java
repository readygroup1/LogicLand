package application;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.application.Application;

import java.io.File;



/**
 * The MusicPlayer class provides functionality to play background music and level music in a game.
 * It uses the JavaFX MediaPlayer class to play the music files.
 * 
 * @version 1.0
 * @since 1.0
 * @author Andres Pedreros
 */
public class MusicPlayer {
    private static MediaPlayer mediaPlayer;
    public static double defaultVolume = 0.3; // Default volume level
    
    /** Path to the default background music*/ 
    private static final String DEFAULT_BACKGROUND_MUSIC = "/application/resources/sounds/backgroundMusic.wav";
    /** Path to the default level music */
    private static final String DEFAULT_LEVEL_MUSIC = "/application/resources/sounds/levelMusic.wav";

    /**
     * Plays the default background music.
     */
    public static void playBackgroundMusic() {
        playMusic(DEFAULT_BACKGROUND_MUSIC);
    }

    /**
     * Plays the default level music.
     */
    public static void playLevelMusic() {
        playMusic(DEFAULT_LEVEL_MUSIC);
    }

    /**
     * Plays music from the given path.
     *
     * @param musicPath The path to the music file.
     */
    private static void playMusic(String musicPath) {
        Media media = new Media(MusicPlayer.class.getResource(musicPath).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // loop indefinitely
        mediaPlayer.play();
        mediaPlayer.setVolume(defaultVolume);
    }

    /**
     * Stops the currently playing music.
     */
    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    /**
     * Lowers the volume to the specified level.
     *
     * @param volume The volume level to set. Should be between 0 and 1.
     */
    public static void lowerVolume(double volume) {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            if (volume >= 0 && volume <= 1) {
                mediaPlayer.setVolume(volume);
            } else {
                System.err.println("Invalid volume level. Volume should be between 0 and 1.");
            }
        } else {
            System.err.println("Media player is not initialized or not playing.");
        }
    }

    /**
     * Resets the volume to the default level.
     */
    public static void resetVolume() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.setVolume(0.3);
        }
    }
}
