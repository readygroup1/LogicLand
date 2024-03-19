package application.resources;

import java.io.IOException;

import javafx.event.ActionEvent;

public class instructorDashboard {

	audioPlayer audio = new audioPlayer();
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	
	
	
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
