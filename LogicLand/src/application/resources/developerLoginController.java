package application.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.AccountManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * This class is responsible for all actions connected to and available within the developer login page.
 * @author Callum Thompson
 * @author Nicholas Howard
 */
public class developerLoginController implements Initializable{
	
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
		Button back;
		@FXML
		Button login;
		@FXML
		AnchorPane anchorPane;
		
		MultiMediaPlayer audio = new MultiMediaPlayer();
		
		// ----------------Button Functions -----------------------
		
		/**
		 * 
		 * @param event Login button clicked.
		 * @throws Exception Thrown if there is an error finding the roadmap page.
		 * 
		 * This method is responsible for handling the login process of developers.
		 * It will check for a valid username and password. 
		 * It has also been programmed to check if the ID of the user logging in is a valid developer ID.
		 */
		public void login(ActionEvent event) throws Exception {
			String userName = devUsername.getText();
			String userPassword = devPassword.getText();
			if(userName.trim().isEmpty() || userPassword.trim().isEmpty()) {
				status.setText("Input fields cannot be blank!");
				return;
			}
			
			if(AccountManager.verifyLogin(userName, userPassword, true) == false) {
				status.setText("Incorrect username or password.");
				return;
			}
			
			if(AccountManager.getCurrentUser() > 6) {
				status.setText("You are not a developer!");
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
		 * @param event Back button clicked.
		 * @throws IOException Thrown if there is an error finding the mainMenu page.
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

		@Override
		/**
		 * This method is responsible for initializing the developerLogin page.
		 */
		public void initialize(URL arg0, ResourceBundle arg1) {
			

			anchorPane.addEventFilter(KeyEvent.KEY_PRESSED, key ->{
				
				switch(key.getCode()) {
				
				 case ENTER: 
					 login.fire();
					 break;
				
				 case ESCAPE:
					 back.fire();
					 break;
				}
			
			});
			
		}

}
