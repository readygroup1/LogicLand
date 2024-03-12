package application.resources;

import java.io.IOException;

import application.AccountManager;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class loginController {	

	//--------Constants/Resources---------
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	Image bulbOn = new Image(getClass().getResourceAsStream("images/bulbOn.png"));
	AccountManager account = new AccountManager();
	
	//---------------Variables--------------------	
	@FXML
	ImageView bulb;
	@FXML
	TextField username;
	@FXML
	Button login;
	@FXML
	PasswordField password;
	@FXML
	Button backButton;
	@FXML
	CheckBox imATeacher;
	Boolean isTeacher = false; // Used in imATeacher() to switch between input fields.
	
	// ----------------Button Functions -----------------------
	public void login(ActionEvent event) throws Exception {
		String userName = username.getText();;
		String userPassword = password.getText();
		if(userName.equals("") || userPassword.equals("")) {
			System.out.println("Error in one of the input fields");
			return;
		}
		
		if(account.verifyLogin(userName, userPassword, isTeacher) == false) {
			System.out.println("Incorrect username or password");
			return;
		}
		
		try {
		
			bulb.setImage(bulbOn);		
			sceneSwitcher.fadeSwitchScene(event, "/application/resources/roadmap.fxml");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void back(ActionEvent event) throws IOException {			
		try {			
			sceneSwitcher.switchScene(event, "mainMenu.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void imATeacher(ActionEvent event) {
		if(isTeacher) {
			isTeacher = false;
		} else {
			isTeacher = true;
		}
	}
	
	
}
