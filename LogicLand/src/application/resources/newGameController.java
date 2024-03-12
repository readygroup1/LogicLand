package application.resources;

import java.io.IOException;

import application.AccountManager;
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
			AccountManager account = new AccountManager();
			Image bulbOn = new Image(getClass().getResourceAsStream("images/bulbOn.png"));
			Image bulbOff = new Image(getClass().getResourceAsStream("images/bulbOff.png"));
			Image batteryOn = new Image(getClass().getResourceAsStream("images/batteryOn.png"));
			Image batteryOff = new Image(getClass().getResourceAsStream("images/batteryOff.png"));
			
			
	//---------------Variables--------------------
		@FXML
		ImageView image;
		@FXML
		Button backButton;
		@FXML
		Button createGame;
		@FXML
		TextField initals;
		@FXML
		TextField username;
		@FXML
		PasswordField password;
		@FXML
		TextField email;
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
			image.setImage(bulbOff);
			chooseClassName.setVisible(true);
			enterClassName.setVisible(false);
			isTeacher = false;
		}
		
		else {
			image.setImage(batteryOff);
			chooseClassName.setVisible(false);
			enterClassName.setVisible(true);
			isTeacher = true;			
		}		
	}
	
	public void createGame(ActionEvent event) throws Exception {
		
		try {
			if(!isTeacher) {
				String userName = username.getText();
				String userPassword = password.getText();
				String userEmail = email.getText();
				String userInitals = initals.getText();
				int classID = 1;
				//classID = (int) chooseClassName.getSelectionModel().getSelectedItem();
				
				if(userName.equals("") || userPassword.equals("") || userEmail.equals("") || userInitals.equals("") || classID == -1) {
					System.out.println("There is an error in one of the input fields");
					return;
				}
				account.newPlayerAccount(userName, userInitals, userPassword, userEmail, classID);
			}
			bulb.setImage(bulbOn);		
			
			if(isTeacher) {
				image.setImage(batteryOn);
			}
			
			else {
				image.setImage(bulbOn);
			}	
					
			sceneSwitcher.fadeSwitchScene(event, "/application/resources/roadmap.fxml");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
