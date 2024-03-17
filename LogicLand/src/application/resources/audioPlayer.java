package application.resources;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class audioPlayer {
	// Correct way to format the local file path as a URI
    String audioFile = getClass().getResource("/application/resources/boop.wav").toExternalForm();
    // Creating a media object for the audio
    Media sound = new Media(audioFile);
    // Creating a MediaPlayer with the media
    MediaPlayer boop = new MediaPlayer(sound);
    
    public void boopPlay() {
    	// Playing the audio
        boop.play();
    }
}
