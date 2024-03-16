package application.resources;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import application.AccountManager;
import application.Instructor;
import application.Main;
import application.User;
import javafx.event.ActionEvent;

public class roadmapController {
	
	// ------------------Variables--------------------------------------------
	  @FXML
	  private Text name;
	  @FXML
	  private Text score;
	  @FXML
	  private ImageView andLevel;
	  
	  public void initialize() {
		  if(!AccountManager.isAdmin()) {
		    User player = new User(AccountManager.getCurrentUser());
		    name.setText(player.getUsername()); 
		    score.setText(player.getHighScore());
		    
		  } else if(AccountManager.isAdmin()) {
			  Instructor teacher = new Instructor(AccountManager.getCurrentUser());
			    name.setText(teacher.getName()); 
			    score.setText("Class ID: " + teacher.getClassID());
		  }
		  
		  andLevel.setPickOnBounds(true);
	}
	  
	  
	//-----------------Constants & Resources----------------------------------
	  SceneSwitcher sceneSwitcher = new SceneSwitcher();
	  
	//----------------Level Buttons-------------------------------------------
	  
	  public void andLevel(MouseEvent event) throws IOException {
		  
		  try {			
			  sceneSwitcher.switchScene(event, "/application/resources/levels/level1A.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		  
	  }
	
	// ----------------User Dashboard Button Functions -----------------------
	
		
		
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
