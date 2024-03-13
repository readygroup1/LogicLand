package application.resources;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import application.User;
import javafx.event.ActionEvent;

public class roadmapController {
		
	  @FXML
	  private Label name;
	  @FXML
	  private Label score;
	  
	  public void initialize() {
		    User player = new User("1");
		    name.setText(player.getUsername()); 
		    score.setText(player.getHighScore());
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
