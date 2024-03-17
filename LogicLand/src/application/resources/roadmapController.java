package application.resources;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import application.AccountManager;
import application.Instructor;
import application.Main;
import application.User;
import javafx.event.ActionEvent;

public class roadmapController {
		
	  @FXML
	  private Text name;
	  @FXML
	  private Text score;
	  
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
		
		
		// ----------------Level Selection Functions -----------------------
		
		
		
		
		public void levelOne(ActionEvent event) throws IOException {	
			try {			
				sceneSwitcher.switchScene(event, "/application/resources/Level1.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		
		public void levelTwo(ActionEvent event) throws IOException {	
			try {			
				sceneSwitcher.switchScene(event, "/application/resources/Level2.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}	
		}
		
		public void levelThree(ActionEvent event) throws IOException {	
			try {			
				sceneSwitcher.switchScene(event, "/application/resources/Level3.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		
		public void levelFour(ActionEvent event) throws IOException {	
			try {			
				sceneSwitcher.switchScene(event, "/application/resources/Level4.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		
		public void levelFive(ActionEvent event) throws IOException {	
			try {			
				sceneSwitcher.switchScene(event, "/application/resources/Level5.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		
		public void levelSix(ActionEvent event) throws IOException {	
			try {			
				sceneSwitcher.switchScene(event, "/application/resources/Level6.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		
		public void levelSeven(ActionEvent event) throws IOException {	
			try {			
				sceneSwitcher.switchScene(event, "/application/resources/Level7.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		
		public void levelEight(ActionEvent event) throws IOException {	
			try {			
				sceneSwitcher.switchScene(event, "/application/resources/Level8.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}


}
