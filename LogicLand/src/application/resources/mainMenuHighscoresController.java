package application.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.AccountManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * @author Callum Thompson
 * @version 1.0
 * 
 * This class is responsible for displaying the top 5 scores of players across all classes in the highscores page accessible from the main menu and options page.
 */
public class mainMenuHighscoresController implements Initializable{
	
	//--------Constants/Resources---------
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	//---------------Variables--------------------	
	@FXML
	Button backButton;
	@FXML
	Text firstName;
	@FXML
	Text secondName;
	@FXML
	Text thirdName;
	@FXML
	Text fourthName;
	@FXML
	Text fifthName;
	
	@FXML
	Text firstScore;
	@FXML
	Text secondScore;
	@FXML
	Text thirdScore;
	@FXML
	Text fourthScore;
	@FXML
	Text fifthScore;
	
	MultiMediaPlayer audio = new MultiMediaPlayer();
	
	/**
	 * This method will gather and display the top 5 names and scores from the AccountManager class.
	 * The names and scores will be shown in assigned text fields.
	 */
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ArrayList<String> top5Names = AccountManager.getTopFiveNames();
		ArrayList<Integer> top5Scores = AccountManager.getTopFiveScores();
		firstName.setText(top5Names.get(0));
		secondName.setText(top5Names.get(1));
		thirdName.setText(top5Names.get(2));
		fourthName.setText(top5Names.get(3));
		fifthName.setText(top5Names.get(4));
		firstScore.setText(top5Scores.get(0).toString());
		secondScore.setText(top5Scores.get(1).toString());
		thirdScore.setText(top5Scores.get(2).toString());
		fourthScore.setText(top5Scores.get(3).toString());
		fifthScore.setText(top5Scores.get(4).toString());
	}
	// ----------------Buttons Functions-----------------------
	
	/**
	 * 
	 * @param event Back button clicked.
	 * @throws IOException Thrown if there is an error finding the mainMenu/options page or with getting the current user.
	 */
	public void back(ActionEvent event) throws IOException {			
		try {		
			audio.boopPlay();
			/* Determine if user is logged in or not */
			if (AccountManager.getCurrentUser() == -1) {
				/* If not, go to mainMenu.fxml */
				sceneSwitcher.switchScene(event, "mainMenu.fxml");
			} else {
				/* Otherwise, go to options.fxml */
				sceneSwitcher.switchScene(event, "options.fxml");
			}
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}

}
