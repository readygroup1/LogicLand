package application.resources;

import java.io.IOException;

import javafx.event.ActionEvent;

public class loginController {
	
	
// ----------------Buttons -----------------------
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	public void login(ActionEvent event) throws IOException {
		
		try {
		
			sceneSwitcher.switchScene(event, "/application/resources/roadmap.fxml");
		}
		
		catch(IOException exception) {
			
			exception.printStackTrace(); 
			
		}
	}

}
