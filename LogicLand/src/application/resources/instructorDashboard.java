package application.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.AccountManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class instructorDashboard implements Initializable{

	audioPlayer audio = new audioPlayer();
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	//FX variables
	@FXML
	Text className;
	
	public void initialize(URL url, ResourceBundle resourceBundle) {
		className.setText("Class: "+ AccountManager.getClassName(AccountManager.getCurrentClassID()));
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
