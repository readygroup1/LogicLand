package application.resources;



import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * This class is responsible for playing the "boop" sound effect on a clicked button. 
 * 
 * @author
 */
public class audioPlayer {
	// Correct way to format the local file path as a URI
    String audioFile = getClass().getResource("/application/resources/sounds/boop.wav").toExternalForm();
    String errorSoundFile = getClass().getResource("/application/resources/sounds/error.wav").toExternalForm();
    // Creating a media object for the audio
    Media sound = new Media(audioFile);
    Media errorSound = new Media(errorSoundFile);
    // Creating a MediaPlayer with the media
    MediaPlayer boop = new MediaPlayer(sound);
    
    /**
     * Play "boop" sound.
     */
    public void boopPlay() {
    	// Playing the audio
        boop.play();
    }
   
    public void errorPlay() {
    	MediaPlayer error = new MediaPlayer(errorSound);
    	error.play();
    }
}
