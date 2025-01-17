package application.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.AccountManager;
import application.GameSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


/**
 * This class controls the login page. It handles username and password verification in the login page, 
 * as well as handling setting teacher status for other classes.
 * 
 * @author Callum Thompson
 * @author Nicholas Howard
 * 
 */
public class loginController implements Initializable {	

	//--------Constants/Resources---------
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	Image bulbOn = new Image(getClass().getResourceAsStream("images/bulbOn.png"));
	
	//---------------Variables--------------------	
	@FXML
	AnchorPane anchorPane;
	@FXML
	Text status;
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
	
	MultiMediaPlayer audio = new MultiMediaPlayer();
	
	
	// ----------------Button Functions -----------------------
	
	/**
	 * 
	 * @param event Occurs when the user clicks the login button.
	 * @throws Exception Thrown when there is an error with the login action.
	 */
	public void login(ActionEvent event) throws Exception {
		String userName = username.getText();
		String userPassword = password.getText();
		if(userName.trim().isEmpty() || userPassword.trim().isEmpty()) {
			status.setText("Input fields cannot be blank!");
			audio.errorPlay();
			return;
		}
		
		if(AccountManager.verifyLogin(userName, userPassword, isTeacher) == false) {
			status.setText("Incorrect username or password");
			audio.errorPlay();
			return;
		}
		
		try {
		
			bulb.setImage(bulbOn);	
			audio.boopPlay();
			GameSession game = new GameSession(AccountManager.getCurrentUser());
			game.startTracking();
			
			sceneSwitcher.fadeSwitchScene(event, "/application/resources/roadmap.fxml");
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param event Occurs when the back button is clicked.
	 * @throws IOException Thrown when the mainMenu page cannot be found.
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
	
	/**
	 * 
	 * @param event Occurs when the "I am a Teacher" checkbox is checked.
	 */
	public void imATeacher(ActionEvent event) {
		if(isTeacher) {
			isTeacher = false;
		} else {
			isTeacher = true;
		}
	}



	@Override
	/**
	 * This method handles the startup process of the login page.
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		anchorPane.addEventFilter(KeyEvent.KEY_PRESSED, key ->{
			
			switch(key.getCode()) {
			
			 case ENTER: 
				 login.fire();
				 break;
			
			 case ESCAPE:
				 backButton.fire();
				 break;
			}
		
		});
	}
	
	
}
