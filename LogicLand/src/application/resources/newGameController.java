package application.resources;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class newGameController {
	
	//--------Constants/Resources---------
	
			SceneSwitcher sceneSwitcher = new SceneSwitcher();
			Image bulbOn = new Image(getClass().getResourceAsStream("images/bulbOn.png"));
			
	//---------------Variables--------------------
		@FXML
		ImageView bulb;
		@FXML
		Button backButton;
		@FXML
		Button createGame;
		@FXML
		TextField name;
		@FXML
		TextField initals;
		@FXML
		TextField username;
		@FXML
		PasswordField password;
		@FXML
		CheckBox imATeacher;
		@FXML
		ChoiceBox chooseClassName; // Input Field for students only. 
		@FXML
		TextField enterClassName; // Input field for teachers only.
		Boolean isTeacher = false; // Used in imATeacher() to switch between input fields.
		
		
		
	//---------Button Functions-------------------
	
	public void back(ActionEvent event) throws IOException {			
		try {			
			sceneSwitcher.switchScene(event, "mainMenu.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	
	// Function for imATeacher checkbox that switches between student and teacher class input fields.
	public void imATeacher(ActionEvent event) {
		
		if(isTeacher) {
			chooseClassName.setVisible(true);
			enterClassName.setVisible(false);
			isTeacher = false;
		}
		
		else {
			chooseClassName.setVisible(false);
			enterClassName.setVisible(true);
			isTeacher = true;			
		}		
	}
	
	public void createGame(ActionEvent event) throws Exception {
		
		try {
		
			bulb.setImage(bulbOn);		
			sceneSwitcher.fadeSwitchScene(event, "/application/resources/roadmap.fxml");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
