package application.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.AccountManager;
import application.Main;
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
import javafx.scene.text.Text;

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
		TextField email;
		@FXML
		CheckBox imATeacher;
		@FXML
		ChoiceBox<String> chooseClassName; // Input Field for students only. 
		@FXML
		TextField enterClassName; // Input field for teachers only.
		Boolean isTeacher = false; // Used in imATeacher() to switch between input fields.
		
	public void initialize(URL url, ResourceBundle resourceBundle) {
		chooseClassName.getItems().addAll(AccountManager.getClassrooms());
	}
		
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
	
	public void createGame(ActionEvent event) throws Exception {
		try {
			
			if(!isTeacher) {
				String userName = username.getText();
				String userPassword = password.getText();
				String userEmail = email.getText();
				String userInitials = initials.getText();
				String className = chooseClassName.getSelectionModel().getSelectedItem();
				int classID = AccountManager.getClassID(className);
				
				
				// Error handling cases for text input
				if(userName.trim().isEmpty() || userPassword.trim().isEmpty() || userEmail.trim().isEmpty() || userInitials.trim().isEmpty() || className.trim().isEmpty() || classID == -1) {
					status.setText("Input fields cannot be blank!");
					return;
				}
				if(AccountManager.db.userNameExists(userName)) {
					status.setText("Name is taken!");
					return;
				}
				if(userInitials.length() != 3) {
					status.setText("Initials must be 3 letters!");
					return;
				}
				if(userPassword.length() < 4) {
					status.setText("Password is too short!");
					return;
				}
				
				AccountManager.newPlayerAccount(userName, userInitials, userPassword, userEmail, classID);
			} else {
				String userName = username.getText();
				String userPassword = password.getText();
				String userEmail = email.getText();
				String userInitials = initials.getText();
				String className = enterClassName.getText();
				
				// Error handling cases for text input
				if(userName.trim().isEmpty() || userPassword.trim().isEmpty() || userEmail.trim().isEmpty() || userInitials.trim().isEmpty() || className.trim().isEmpty()) {
					status.setText("Input fields cannot be blank!");
					return;
				}
				if(AccountManager.db.classroomNameExists(className)) {
					status.setText("Class name is taken!");
					return;
				}
				if(userInitials.length() != 3) {
					status.setText("Initials must be 3 letters!");
					return;
				}
				if(userPassword.length() < 4) {
					status.setText("Password is too short!");
					return;
				}
				AccountManager.newAdminAccount(userName, userInitials, userPassword, userEmail, className);
			}
			
			
			bulb.setImage(bulbOn);	
			sceneSwitcher.fadeSwitchScene(event, "/application/resources/roadmap.fxml");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
