package application.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.AccountManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * This class handles all activities that occur in the instructor dashboard. 
 * Such activities may affect an instructor's class and be connected to other pages in the game.
 * Only teachers and admins have the ability to access this page.
 */
public class instructorDashboard implements Initializable{

	MultiMediaPlayer audio = new MultiMediaPlayer();
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	//FX variables
	@FXML
	Text className;
	@FXML
	Text deleteText;
	@FXML 
	ScrollPane pane;
	@FXML
	ChoiceBox<String> addBox;
	@FXML 
	ChoiceBox<String> removeBox;
	@FXML
	ChoiceBox<String> deleteBox;
	@FXML
	Button addButton;
	@FXML
	Button removeButton;
	@FXML
	Button deletePlayer;
	
	/**
	 * @param url 
	 * @param resourceBundle
	 * 
	 * This method is responsible for loading the corresponding class to the logged-in teacher.
	 * It loads the corresponding class with all students and their scores.
	 * A teacher can add and remove students from their class.
	 * If the user logged in is an admin they can also delete students, which a teacher cannot. 
	 */
	public void initialize(URL url, ResourceBundle resourceBundle) {
		className.setText("Class: "+ AccountManager.getClassName(AccountManager.getCurrentClassID()));
	    VBox contentBox = new VBox(20); // 10 is the spacing between elements
	    ArrayList<String> list = AccountManager.getClassList();
	    for(int i = 0; i < list.size(); i++) {
	        HBox line = new HBox(50);
	        line.setAlignment(Pos.CENTER_LEFT); 

	        String studentName = list.get(i);
	        int userId = AccountManager.getPlayerID(studentName);
	        int score = AccountManager.getHighscore(userId); // Get the high score for the student

	        Label nameLabel = new Label("    "+studentName);
	        nameLabel.setStyle("-fx-font-family: 'Vermin Vibes Regular'; -fx-font-size: 24px; -fx-text-fill: black;");
	        
	        Label scoreLabel = new Label("Score: " + String.valueOf(score) + "    ");
	        scoreLabel.setStyle("-fx-font-family: 'Vermin Vibes Regular'; -fx-font-size: 24px; -fx-text-fill: red;");
	        

	        line.getChildren().addAll(nameLabel, scoreLabel);
	        contentBox.getChildren().add(line);
	    }
	    
	    pane.setContent(contentBox);
	    
	    addBox.getItems().addAll(AccountManager.getAllPlayersExceptInClass());
	    removeBox.getItems().addAll(AccountManager.getClassList());
	    if(AccountManager.isAdmin() && AccountManager.getCurrentUser() <= 6) {
	    	deleteBox.getItems().addAll(AccountManager.getAllPlayers());
		} else {
			deleteBox.setVisible(false);
			deleteBox.setDisable(true);
			deletePlayer.setVisible(false);
			deletePlayer.setDisable(true);
			deleteText.setVisible(false);
			deleteText.setDisable(true);
		}
	}
	
	
	//-------------------Buttons-------------------------------
	
	/**
	 * 
	 * @param event Back button clicked.
	 * @throws IOException Thrown if the roadMap page cannot be found.
	 * 
	 * This method will take a teacher/admin to the roadmap page.
	 */
	public void back(ActionEvent event) throws IOException {			
		try {		
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/roadmap.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	/**
	 * 
	 * @param event Add button clicked.
	 * 
	 * This method will add a student to the teacher's class. 
	 */
	public void addButton(ActionEvent event) {
		String studentName = addBox.getSelectionModel().getSelectedItem();
		if(studentName == null) {
			return;
		} else {
			int studentID = AccountManager.getPlayerID(studentName);
			AccountManager.addPlayerToMyClass(studentID);
			audio.boopPlay();
			// Refresh the page
			try {
				sceneSwitcher.switchScene(event, "/application/resources/instructorDashboard.fxml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param event Remove button clicked.
	 * 
	 * This method will remove a student from a class.
	 * Only teachers and admins can do this.
	 */
	public void removeButton(ActionEvent event) {
		String studentName = removeBox.getSelectionModel().getSelectedItem();
		if(studentName == null) {
			return;
		} else {
			int studentID = AccountManager.getPlayerID(studentName);
			AccountManager.movePlayerToPublicClass(studentID);
			audio.boopPlay();
			// Refresh the page
			try {
				sceneSwitcher.switchScene(event, "/application/resources/instructorDashboard.fxml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param event Delete button clicked.
	 * 
	 * This method will delete a student from the game. 
	 * Only admins have the ability to do this.
	 */
	public void deletePlayer(ActionEvent event) {
		String studentName = deleteBox.getSelectionModel().getSelectedItem();
		if(studentName == null) {
			return;
		} else {
			int studentID = AccountManager.getPlayerID(studentName);
			AccountManager.deletePlayer(studentID);
			audio.boopPlay();
			// Refresh the page
			try {
				sceneSwitcher.switchScene(event, "/application/resources/instructorDashboard.fxml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
