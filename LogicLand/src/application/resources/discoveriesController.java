package application.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.AccountManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class discoveriesController implements Initializable{
	
	//---------------Variables----------------------------------------------
	  @FXML
	  Button instructorDashboard;
		
	  Boolean isTeacher = AccountManager.isAdmin();
	
	// ----------------User Dashboard Button Functions -----------------------
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	audioPlayer audio = new audioPlayer();
	
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
			instructorDashboard.setVisible(false);
		}
		
		
		
	}

}
