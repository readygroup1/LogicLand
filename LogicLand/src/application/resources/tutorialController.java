package application.resources;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class tutorialController {
	
	//--------Constants/Resources---------
	
		SceneSwitcher sceneSwitcher = new SceneSwitcher();
		
	//---------------Variables--------------------	
		@FXML
		Button backButton;
		
		audioPlayer audio = new audioPlayer();
		
	//---------Button Functions-------------------
	
	public void back(ActionEvent event) throws IOException {			
		try {			
			audio.boopPlay();
			/* Determine if user is logged in or not */
			/* If not, go to mainMenu.fxml */
			sceneSwitcher.switchScene(event, "mainMenu.fxml");
			/* Otherwise, go to options.fxml */
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}

}
