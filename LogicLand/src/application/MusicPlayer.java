package application;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.application.Application;

import java.io.File;

public class MusicPlayer {
    private static MediaPlayer mediaPlayer;
    private static double defaultVolume = 0.3; // Default volume level
    
    
    // link to copyright free music https://uppbeat.io/track/sulyya/mirthaflare
    // Path to the default background music
    private static final String DEFAULT_BACKGROUND_MUSIC = "src/application/resources/sounds/backgroundmusic.wav";
    // Path to the default level music
    private static final String DEFAULT_LEVEL_MUSIC = "src/application/resources/sounds/levelMusic.wav";

    // Play the default background music
    public static void playBackgroundMusic() {
        playMusic(DEFAULT_BACKGROUND_MUSIC);
    }

    // Play the default level music
    public static void playLevelMusic() {
        playMusic(DEFAULT_LEVEL_MUSIC);
    }

    // Play music from the given path
    private static void playMusic(String musicPath) {
        Media media = new Media(new File(musicPath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // loop indefinitely
        mediaPlayer.play();
        mediaPlayer.setVolume(defaultVolume);
   
    }

    // Stop the currently playing music
    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    // Lower the volume to the specified level
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

    // Reset the volume to the default level
    public static void resetVolume() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.setVolume(defaultVolume);
        }
    }
}
