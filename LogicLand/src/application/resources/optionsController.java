package application.resources;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class optionsController {
	
	
	//-----------Constants/Resources---------
	
		SceneSwitcher sceneSwitcher = new SceneSwitcher();
		
	//-------------Button Functions------------------------
		
		public void quit(ActionEvent event) {
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.close();
			
		}
	
	// ----------- User Dashboard Button Functions ---------
	
	
	
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
