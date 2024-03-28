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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * @author Callum Thompson
 * 
 * This class is responsible for gathering and storing a users information in the newGame page.
 * A user must enter a name, initial, password, email, and class & teacher information.
 */
public class newGameController implements Initializable{
	
	//--------Constants/Resources---------
	
			SceneSwitcher sceneSwitcher = new SceneSwitcher();
			Image bulbOn = new Image(getClass().getResourceAsStream("images/bulbOn.png"));
			Image bulbOff = new Image(getClass().getResourceAsStream("images/bulbOff.png"));
			Image student = new Image(getClass().getResourceAsStream("images/student.png"));
			Image teacher = new Image(getClass().getResourceAsStream("images/teacher.png"));
			
			
	//---------------Variables--------------------
		@FXML
		Text status;
		@FXML
		ImageView userIcon;
		@FXML
		ImageView bulb;
		@FXML
		Button backButton;
		@FXML
		Button createGame;
		@FXML
		TextField initials;
		@FXML
		TextField username;
		@FXML
		PasswordField password;
		@FXML
		PasswordField confirmPassword;
		@FXML
		TextField email;
		@FXML
		CheckBox imATeacher;
		@FXML
		ChoiceBox<String> chooseClassName; // Input Field for students only. 
		@FXML
		TextField enterClassName; // Input field for teachers only.
		@FXML
		AnchorPane anchorPane;
		Boolean isTeacher = false; // Used in imATeacher() to switch between input fields.
		
		MultiMediaPlayer audio = new MultiMediaPlayer();
		
	/**
	 * This method is responsible for intitializing the newGame page.
	 */
	public void initialize(URL url, ResourceBundle resourceBundle) {
		chooseClassName.getItems().addAll(AccountManager.getClassrooms());
		
		anchorPane.addEventFilter(KeyEvent.KEY_PRESSED, key ->{
			
			switch(key.getCode()) {
			
			 case ENTER: 
				 createGame.fire();
				 break;
			
			 case ESCAPE:
				 backButton.fire();
				 break;
			}
		
		});
	}
		
	//---------Button Functions-------------------
	
	/**
	 * 
	 * @param event Back button clicked.
	 * @throws IOException Thrown is there is an error finding the mainMenu page.
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
	 * @param event I am a Teacher checkbox checked.
	 * 
	 * This method will check for whether or not the I am a Teacher checkbox is checked.
	 * If it is, the user will then be able to name a corresponding class to create.
	 * Otherwise, this is not an option for a regular user.
	 */
	public void imATeacher(ActionEvent event) {
		if(isTeacher) {
			userIcon.setImage(student);
			chooseClassName.setVisible(true);
			enterClassName.setVisible(false);
			isTeacher = false;
			// This lines the bulb up with the head
			bulb.setX(bulb.getX()-15);
		}
		
		else {
			userIcon.setImage(teacher);
			chooseClassName.setVisible(false);
			enterClassName.setVisible(true);
			isTeacher = true;
			bulb.setX(bulb.getX()+15);
		}		
	}
	
	/**
	 * 
	 * @param event Create Game button clicked.
	 * @throws Exception Thrown if there is an error with the user input or with finding the roadmap page.
	 * 
	 * This method will verify the user input and store it with AccountManager if it is valid input.
	 * The user must enter a name.
	 * The user must enter a three letter initial.
	 * The user must enter a password at least four characters in length and confirm it.
	 * The user must select a class to belong to.
	 * A teacher must name a class to create.
	 */
	public void createGame(ActionEvent event) throws Exception {
		try {
			String userName = username.getText();
			String userPassword = password.getText();
			String userConfirmPassword = confirmPassword.getText();
			String userEmail = email.getText();
			String userInitials = initials.getText();
			
			if(!isTeacher) {
				String className = chooseClassName.getSelectionModel().getSelectedItem();
				if(className == null) {
					className = "";
				}
				int classID = AccountManager.getClassID(className);
				if(userName.contains("'") || userInitials.contains("'") || userPassword.contains("'") || userEmail.contains("'")) {
					status.setText("Names cannot have ' in them");
					return;
				}
				// Error handling cases for text input
				if(userName.trim().isEmpty() || userPassword.trim().isEmpty() || userConfirmPassword.trim().isEmpty() || userEmail.trim().isEmpty() || userInitials.trim().isEmpty() || className.trim().isEmpty() || classID == -1) {
					audio.errorPlay();
					status.setText("Input fields cannot be blank!");
					return;
				}
				if(AccountManager.db.userNameExists(userName)) {
					audio.errorPlay();
					status.setText("Name is taken!");
					return;
				}
				if(userInitials.length() != 3) {
					audio.errorPlay();
					status.setText("Initials must be 3 letters!");
					return;
				}
				if(userPassword.length() < 4) {
					audio.errorPlay();
					status.setText("Password is too short!");
					return;
				}
				if(!userConfirmPassword.equals(userPassword)) {
					audio.errorPlay();
					status.setText("Password confirmation does not match!");
					return;
				}
				AccountManager.newPlayerAccount(userName, userInitials, userPassword, userEmail, classID);
				GameSession game = new GameSession(AccountManager.getCurrentUser());
				game.startTracking();
			} else {
				String className = enterClassName.getText();
				if(className.contains("'") || userName.contains("'") || userInitials.contains("'") || userPassword.contains("'") || userEmail.contains("'")) {
					status.setText("Names cannot have ' in them");
					return;
				}
				// Error handling cases for text input
				if(userName.trim().isEmpty() || userPassword.trim().isEmpty() || userConfirmPassword.trim().isEmpty() || userEmail.trim().isEmpty() || userInitials.trim().isEmpty() || className.trim().isEmpty()) {
					audio.errorPlay();
					status.setText("Input fields cannot be blank!");
					return;
				}
				if(AccountManager.db.classroomNameExists(className)) {
					audio.errorPlay();
					status.setText("Class name is taken!");
					return;
				}
				if(className.length() > 15) {
					audio.errorPlay();
					status.setText("Class name is too long");
				}
				if(userInitials.length() != 3) {
					audio.errorPlay();
					status.setText("Initials must be 3 letters!");
					return;
				}
				if(userPassword.length() < 4) {
					audio.errorPlay();
					status.setText("Password is too short!");
					return;
				}
				if(!userConfirmPassword.equals(userPassword)) {
					audio.errorPlay();
					status.setText("Password confirmation does not match!");
					return;
				}
				AccountManager.newAdminAccount(userName, userInitials, userPassword, userEmail, className);
			}
			
			
			
			bulb.setImage(bulbOn);	
			audio.boopPlay();
			sceneSwitcher.fadeSwitchScene(event, "/application/resources/roadmap.fxml");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
