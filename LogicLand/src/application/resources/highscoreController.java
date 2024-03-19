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

public class highscoreController implements Initializable{
	
	
	// ----------------Variables -----------------------
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
	
	@FXML
	Text className;
	@FXML
	Button instructorDashboard;
		
	Boolean isTeacher = AccountManager.isAdmin();
	
	audioPlayer audio = new audioPlayer();
	
	
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ArrayList<String> top5Names = AccountManager.getTopFiveNames(AccountManager.getCurrentClassID());
		ArrayList<Integer> top5Scores = AccountManager.getTopFiveScores(AccountManager.getCurrentClassID());
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
		if(AccountManager.isAdmin()) {
			className.setText(AccountManager.getClassName(AccountManager.db.getClassIDAdmin(AccountManager.getCurrentUser())));
		} else {
			System.out.println(AccountManager.db.getClassID(AccountManager.getCurrentUser()));
			className.setText(AccountManager.getClassName(AccountManager.db.getClassID(AccountManager.getCurrentUser())));
		}
		
		if(!isTeacher) {
			instructorDashboard.setVisible(false);
		}
		
	}
	
	// ----------------User Dashboard Button Functions -----------------------
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	public void roadmap(ActionEvent event) throws IOException {			
		try {		
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/roadmap.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void sandbox(ActionEvent event) throws IOException {			
		try {	
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/sandbox.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void highscore(ActionEvent event) throws IOException {			
		try {	
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/highscore.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void discoveries(ActionEvent event) throws IOException {			
		try {	
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/discoveries.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void options(ActionEvent event) throws IOException {			
		try {	
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/options.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void instructorDashboard(ActionEvent event) throws IOException {	
		
		try {	
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/instructorDashboard.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}

}
