package application.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import application.AccountManager;
import application.Instructor;
import application.Main;
import application.User;
import javafx.event.ActionEvent;

public class roadmapController  implements Initializable{
	
	// ------------------Variables--------------------------------------------
	  @FXML
	  private Text name;
	  @FXML
	  private Text score;
	  @FXML
	  private ImageView levelOne;
	  @FXML
	  private Text classroom;
	  @FXML
	  Button instructorDashboard;
		
	  Boolean isTeacher = AccountManager.isAdmin();
	  
	  audioPlayer audio = new audioPlayer();
	  
	  public void initialize() {
		  if(!AccountManager.isAdmin()) {
		    User player = new User(AccountManager.getCurrentUser());
		    name.setText(player.getUsername()); 
		    classroom.setText(player.getClassName());
		    score.setText("Score: " + player.getHighScore());
		    
		  } else if(AccountManager.isAdmin()) {
			  Instructor teacher = new Instructor(AccountManager.getCurrentUser());
			    name.setText(teacher.getName()); 
			    classroom.setText(null);
			    score.setText("Class: " + AccountManager.getClassName(AccountManager.db.getClassIDAdmin(AccountManager.getCurrentUser())));
		  }
		  
		  levelOne.setPickOnBounds(true);
	}
	  
	  
	//-----------------Constants & Resources----------------------------------
	  SceneSwitcher sceneSwitcher = new SceneSwitcher();

	
	// ----------------User Dashboard Button Functions -----------------------
	
		
		
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
		
		
		// ----------------Level Selection Functions -----------------------
		
		
		
		
		public void levelOne(MouseEvent event) throws IOException {	
			 try {		
				  audio.boopPlay();
				  sceneSwitcher.switchScene(event, "/application/resources/levels/level1A.fxml");
				}			
				catch(IOException exception) {				
					exception.printStackTrace();				
				}		  
		}
		
		public void levelTwo(MouseEvent event) throws IOException {	
			try {	
				audio.boopPlay();
				sceneSwitcher.switchScene(event, "/application/resources/levels/level2A.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}	
		}
		
		public void levelThree(MouseEvent event) throws IOException {	
			try {	
				audio.boopPlay();
				sceneSwitcher.switchScene(event, "/application/resources/levels/level3A.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		
		public void levelFour(MouseEvent event) throws IOException {	
			try {
				audio.boopPlay();
				sceneSwitcher.switchScene(event, "/application/resources/levels/level4A.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		
		public void levelFive(MouseEvent event) throws IOException {	
			try {
				audio.boopPlay();
				sceneSwitcher.switchScene(event, "/application/resources/levels/level5A.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		
		public void levelSix(MouseEvent event) throws IOException {	
			try {	
				audio.boopPlay();
				sceneSwitcher.switchScene(event, "/application/resources/levels/level6A.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		
		public void levelSeven(MouseEvent event) throws IOException {	
			try {	
				audio.boopPlay();
				sceneSwitcher.switchScene(event, "/application/resources/levels/level7A.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		
		public void levelEight(MouseEvent event) throws IOException {	
			try {	
				audio.boopPlay();
				sceneSwitcher.switchScene(event, "/application/resources/levels/Level8.fxml");
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
