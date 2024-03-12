package application.resources;

import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class loginController {	

	//--------Constants/Resources---------
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	Image bulbOn = new Image(getClass().getResourceAsStream("images/bulbOn.png"));
	
	//---------------Variables--------------------	
	@FXML
	ImageView bulb;
	@FXML
	TextField username;
	@FXML
	PasswordField password;
	@FXML
	Button backButton;
	
	// ----------------Button Functions -----------------------
	public void login(ActionEvent event) throws Exception {
		
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
	
	
}
