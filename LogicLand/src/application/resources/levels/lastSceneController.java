package application.resources.levels;

import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;

import application.AccountManager;
import application.MusicPlayer;
import application.resources.SceneSwitcher;
import application.resources.audioPlayer;
import application.resources.sandboxController;
import application.resources.gates.andController;
import application.resources.gates.batteryController;
import application.resources.gates.bulbController;
import application.resources.gates.gateObject;
import application.resources.gates.nandController;
import application.resources.gates.norController;
import application.resources.gates.notController;
import application.resources.gates.orController;
import application.resources.gates.xorController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class lastSceneController extends sandboxController implements Initializable {

	// ----------------Variables ---------------------------------------
		public Boolean deleteState = false;
		
		@FXML
		Pane circuitBoardPane;
		@FXML
		Button back;
		
		
		@FXML
		Label title;
		
		
		
		audioPlayer audio = new audioPlayer();
		
		
		
		
		
		
		// ---------------- Button Functions -----------------------
		
		SceneSwitcher sceneSwitcher = new SceneSwitcher();
		
		public void back(ActionEvent event) throws IOException {			
			audio.boopPlay();
			//play level music
			MusicPlayer.stopMusic();
			MusicPlayer.playBackgroundMusic();
			try {			
				sceneSwitcher.switchScene(event, "/application/resources/roadmap.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		
		
		
		/**
	     * Switches the scene to the previous level view when the "Previous Level" button is pressed.
	     * 
	     *
	     * @param event The ActionEvent triggered by clicking the "Previous Level" button.
	     * @throws IOException If an I/O exception occurs during scene switching.
	     */
	    public void previousLevel(ActionEvent event) throws IOException {
	    	audio.boopPlay();
	    	//play level music
			MusicPlayer.stopMusic();
			MusicPlayer.playBackgroundMusic();
	    	try {
	            String previousLevelFXML = "/application/resources/levels/level6B.fxml";
	            sceneSwitcher.switchScene(event, previousLevelFXML);
	            audio.boopPlay();
	        } catch (IOException exception) {
	            exception.printStackTrace();
	        }
	    }
}		