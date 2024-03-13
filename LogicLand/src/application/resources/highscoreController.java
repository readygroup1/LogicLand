package application.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class highscoreController implements Initializable{
	
	
	// ----------------Variables -----------------------
	@FXML
	Text firstName;
	
	
	
	public void initialize(URL url, ResourceBundle resourceBundle) {
		firstName.setText("Helloooo");
	}
	
	// ----------------User Dashboard Button Functions -----------------------
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	public void roadmap(ActionEvent event) throws IOException {			
		try {			
			sceneSwitcher.switchScene(event, "/application/resources/roadmap.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void sandbox(ActionEvent event) throws IOException {			
		try {			
			sceneSwitcher.switchScene(event, "/application/resources/sandbox.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void highscore(ActionEvent event) throws IOException {			
		try {			
			sceneSwitcher.switchScene(event, "/application/resources/highscore.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void discoveries(ActionEvent event) throws IOException {			
		try {			
			sceneSwitcher.switchScene(event, "/application/resources/discoveries.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void options(ActionEvent event) throws IOException {			
		try {			
			sceneSwitcher.switchScene(event, "/application/resources/options.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}

}
