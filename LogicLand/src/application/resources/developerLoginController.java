package application.resources;

import java.io.IOException;

import application.AccountManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class developerLoginController {
	
	//--------Constants/Resources---------
		SceneSwitcher sceneSwitcher = new SceneSwitcher();
		Image lightningOn = new Image(getClass().getResourceAsStream("images/lightningOn.png"));
		
		//---------------Variables--------------------
		@FXML
		Text status;
		
		@FXML
		ImageView lightning;
		@FXML
		TextField devUsername;
		@FXML
		PasswordField devPassword;
		@FXML
		Button backButton;
		
		audioPlayer audio = new audioPlayer();
		
		// ----------------Button Functions -----------------------
		public void login(ActionEvent event) throws Exception {
			String userName = devUsername.getText();
			String userPassword = devPassword.getText();
			if(userName.trim().isEmpty() || userPassword.trim().isEmpty()) {
				status.setText("Input fields cannot be blank!");
				return;
			}
			
			if(AccountManager.verifyLogin(userName, userPassword, true) == false) {
				status.setText("Incorrect username or password");
				return;
			}
			
			if(AccountManager.getCurrentUser() > 6) {
				status.setText("You arent a developer!");
				return;
			}
		
			try {
				lightning.setImage(lightningOn);
				audio.boopPlay();
				sceneSwitcher.fadeSwitchScene(event, "roadmap.fxml");
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Take user back to main menu from developer login page.
		 * 
		 * @param event
		 * @throws IOException
		 */
		public void back(ActionEvent event) throws IOException {			
			try {			
				audio.boopPlay();
				sceneSwitcher.switchScene(event, "mainMenu.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}

}
