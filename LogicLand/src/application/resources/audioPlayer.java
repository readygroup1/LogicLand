package application.resources;

import java.time.Duration;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class audioPlayer {
	// Correct way to format the local file path as a URI
    String audioFile = getClass().getResource("/application/resources/boop.wav").toExternalForm();
    String errorSoundFile = getClass().getResource("/application/resources/error.wav").toExternalForm();
    // Creating a media object for the audio
    Media sound = new Media(audioFile);
    Media errorSound = new Media(errorSoundFile);
    // Creating a MediaPlayer with the media
    MediaPlayer boop = new MediaPlayer(sound);
    
    
    public void boopPlay() {
    	// Playing the audio
        boop.play();
    }
    
    public void errorPlay() {
    	MediaPlayer error = new MediaPlayer(errorSound);
    	error.play();
    }
}
