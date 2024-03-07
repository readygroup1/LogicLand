package application.resources;

import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class loginController {	

	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	Image bulbOn = new Image(getClass().getResourceAsStream("images/bulbOn.png"));
	
//---------------Variables--------------------	
	@FXML
	ImageView bulb;
	TextField username;
	PasswordField password;
	
	// ----------------Buttons -----------------------
	public void login(ActionEvent event) throws Exception {
		
		bulb.setImage(bulbOn);		
		sceneSwitcher.fadeSwitchScene(event, "/application/resources/roadmap.fxml");
		
//		
	}

}
