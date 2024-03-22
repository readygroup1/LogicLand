package application.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.AccountManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class optionsController implements Initializable {
	
	
	//-----------Constants/Resources---------
		@FXML
		Button instructorDashboard;
		@FXML 
		Text timePlayed;
		
		Boolean isTeacher = AccountManager.isAdmin();
	
		SceneSwitcher sceneSwitcher = new SceneSwitcher();
		
		audioPlayer audio = new audioPlayer();
		
	//-------------Button Functions------------------------
		
		@FXML 
		Button quit;
		public void quit(ActionEvent event) {
			audio.boopPlay();
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.close();
		}
		
		@FXML
		Button logout;
		public void logout(ActionEvent event) {
			try {		
				audio.boopPlay();
				sceneSwitcher.switchScene(event, "mainMenu.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
	
	// ----------- User Dashboard Button Functions ---------
	
	
	
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		if(!isTeacher) {
			instructorDashboard.setOpacity(0.25);
			instructorDashboard.setDisable(true);
			timePlayed.setText("Time played: " + AccountManager.getTimePlayed() + " minutes");
			
		} else {
			timePlayed.setVisible(false);
			timePlayed.setDisable(true);
		}
		
	}
}
