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

public class instructorDashboard implements Initializable{

	audioPlayer audio = new audioPlayer();
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	//FX variables
	@FXML
	Text className;
	@FXML 
	ScrollPane pane;
	@FXML
	ChoiceBox<String> addBox;
	@FXML 
	ChoiceBox<String> removeBox;
	@FXML
	Button addButton;
	@FXML
	Button removeButton;
	
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
	}
	
	
	//-------------------Buttons-------------------------------
	public void back(ActionEvent event) throws IOException {			
		try {		
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/roadmap.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
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
}
