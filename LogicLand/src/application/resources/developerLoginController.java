package application.resources;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class developerLoginController {
	
	//--------Constants/Resources---------
		SceneSwitcher sceneSwitcher = new SceneSwitcher();
		Image lightningOn = new Image(getClass().getResourceAsStream("images/lightningOn.png"));
		
		//---------------Variables--------------------	
		@FXML
		ImageView lightning;
		@FXML
		TextField devUsername;
		@FXML
		PasswordField devPassword;
		@FXML
		Button backButton;
		
		// ----------------Button Functions -----------------------
		public void login(ActionEvent event) throws Exception {
			
			try {
			
				lightning.setImage(lightningOn);		
				sceneSwitcher.fadeSwitchScene(event, "roadmap.fxml");
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
