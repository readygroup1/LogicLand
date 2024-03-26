package application.resources;



import java.time.Duration;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * This class is responsible for playing the "boop" sound effect on a clicked button. 
 * 
 * @author
 */
public class MultiMediaPlayer {
	// Correct way to format the local file path as a URI
    String audioFile = getClass().getResource("/application/resources/sounds/boop.wav").toExternalForm();
    String errorSoundFile = getClass().getResource("/application/resources/sounds/error.wav").toExternalForm();
    String newGate = getClass().getResource("/application/resources/sounds/newGate2.wav").toExternalForm();
    String newWire = getClass().getResource("/application/resources/sounds/newWire.wav").toExternalForm();
    
    
    // Creating a media object for the audio
    Media sound = new Media(audioFile);
    Media errorSound = new Media(errorSoundFile);
    
    // Creating media object for gate audio
    Media gateSound = new Media(newGate);
    Media wireSound = new Media(newWire);
    
    // Creating a MediaPlayer with the media
    MediaPlayer boop = new MediaPlayer(sound);
    
    
    String videoDemo1A = getClass().getResource("/application/resources/1Aclip.mp4").toExternalForm();
    Media video1A = new Media(videoDemo1A);
    String videoDemo1B = getClass().getResource("/application/resources/1Bclip.mp4").toExternalForm();
    Media video1B = new Media(videoDemo1B);
   
    static public double defaultBoop = 0.2;
    static public double defaultVolume = 0.9;
    
    /**
     * Play "boop" sound.
     */
    public void boopPlay() {
    	// Playing the audio
        boop.play();
        boop.setVolume(defaultBoop);
    }
   
    public void errorPlay() {
    	MediaPlayer error = new MediaPlayer(errorSound);
    	error.play();
    	error.setVolume(defaultBoop);
    }
    
    public void gatePlay() {
    	MediaPlayer gate = new MediaPlayer(gateSound);
    	gate.play();
    	gate.setVolume(defaultVolume);
    }
    
    public void wirePlay() {
    	MediaPlayer wire = new MediaPlayer(wireSound);
    	wire.play();
    	wire.setVolume(defaultVolume);
    }
    public void videoDemoPlay(int mode) {
    	MediaPlayer mediaPlayer;
		MediaView mediaView;
    	if(mode == 0) {
    		mediaPlayer = new MediaPlayer(video1A);
    		mediaView = new MediaView(mediaPlayer);
    	} else {
    		mediaPlayer = new MediaPlayer(video1B);
    		mediaView = new MediaView(mediaPlayer);
    	}
        
        // Set the dialog dimensions
        final double dialogWidth = 800;
        final double dialogHeight = 450;

        // Preserve the aspect ratio without filling the entire space
        mediaView.setPreserveRatio(true);
        
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Level 1 Demo");
        dialog.getDialogPane().setPrefSize(dialogWidth, dialogHeight); // Set the preferred size of the dialog
        
        // Since the MediaView's size adjustments are based on the media's inherent size,
        // which might only be known once it's ready, consider setting fit dimensions upon media readiness.
        mediaPlayer.setOnReady(() -> {
            // Calculate the maximum possible size of the MediaView within the dialog while preserving the video's aspect ratio
            double mediaWidth = mediaPlayer.getMedia().getWidth();
            double mediaHeight = mediaPlayer.getMedia().getHeight();
            double aspectRatio = mediaWidth / mediaHeight;
            
            double fitHeight = dialogHeight;
            double fitWidth = dialogHeight * aspectRatio;
            if (fitWidth > dialogWidth) {
                // If width exceeds dialog width after initial calculation, recalculate based on width.
                fitWidth = dialogWidth;
                fitHeight = dialogWidth / aspectRatio;
            }
            
            mediaView.setFitWidth(fitWidth);
            mediaView.setFitHeight(fitHeight);
        });

        dialog.getDialogPane().setContent(mediaView); // Add the MediaView to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL); // Add a Cancel button

        mediaPlayer.setOnEndOfMedia(() -> {
            // Loop the video by seeking to the start
            Platform.runLater(() -> mediaPlayer.seek(javafx.util.Duration.ZERO));
        });
        
        // Play the video when the dialog is shown
        dialog.setOnShown(event -> mediaPlayer.play());


        // Stop the video when the dialog is closed
        dialog.setOnCloseRequest(event -> mediaPlayer.stop());

        // Show the dialog and wait for it to be closed
        Platform.runLater(dialog::showAndWait);
    }
    
    
    public void setDefaultVolume(double volume) {
        if (volume < 0.0) {
            volume = 0.0;
        } else if (volume > 1.0) {
            volume = 1.0;
        }
        
        defaultVolume = volume;
    }
    
    public void setBoopVolume(double volume) {
        if (volume < 0.0) {
            volume = 0.0;
        } else if (volume > 1.0) {
            volume = 1.0;
        }
        
        defaultVolume = volume;
    }
    
        
        
    
    
    

    
    
}
