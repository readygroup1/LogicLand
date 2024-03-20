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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
	
	public void initialize(URL url, ResourceBundle resourceBundle) {
		className.setText("Class: "+ AccountManager.getClassName(AccountManager.getCurrentClassID()));
	    VBox contentBox = new VBox(20); // 10 is the spacing between elements
	    ArrayList<String> list = AccountManager.getClassList();
	    for(int i = 0; i < list.size(); i++) {
	        HBox line = new HBox(50);
	        line.setAlignment(Pos.CENTER_LEFT); // Ensure the HBox contents align to the center

	        String studentName = list.get(i);
	        int userId = AccountManager.getPlayerID(studentName);
	        int score = AccountManager.getHighscore(userId); // Get the high score for the student

	        Label nameLabel = new Label(studentName);
	        nameLabel.setStyle("-fx-font-family: 'Vermin Vibes Regular'; -fx-font-size: 24px; -fx-text-fill: black;");
	        
	        Label scoreLabel = new Label("Score: " + String.valueOf(score));
	        scoreLabel.setStyle("-fx-font-family: 'Vermin Vibes Regular'; -fx-font-size: 24px; -fx-text-fill: red;");
	        

	        line.getChildren().addAll(nameLabel, scoreLabel);
	        contentBox.getChildren().add(line);
	    }
	    
	    pane.setContent(contentBox);
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
}
