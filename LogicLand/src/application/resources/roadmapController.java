package application.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import application.AccountManager;
import application.Instructor;
import application.Main;
import application.MusicPlayer;
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
	  private ImageView levelTwo;
	  @FXML
	  private ImageView levelThree;
	  @FXML
	  private ImageView levelFour;
	  @FXML
	  private ImageView levelFive;
	  @FXML
	  private ImageView levelSix;
	  @FXML
	  private Text classroom;
	  @FXML
	  Button instructorDashboard;
	  
	  private boolean[] levelLocked = {true, true, true, true, true}; //levels 2-6
		
	  Boolean isTeacher = AccountManager.isAdmin();
	  
	  MultiMediaPlayer audio = new MultiMediaPlayer();
	  
	  
	  
	  
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
			if(levelLocked[0]) {
				return;
			}
			try {
				
				audio.boopPlay();
				sceneSwitcher.switchScene(event, "/application/resources/levels/level2A.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}	
		}
		
		public void levelThree(MouseEvent event) throws IOException {
			if(levelLocked[1]) {
				return;
			}
			try {	
				
				audio.boopPlay();
				sceneSwitcher.switchScene(event, "/application/resources/levels/level3A.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		
		public void levelFour(MouseEvent event) throws IOException {
			if(levelLocked[2]) {
				return;
			}
			try {
				
				audio.boopPlay();
				sceneSwitcher.switchScene(event, "/application/resources/levels/level4A.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		
		public void levelFive(MouseEvent event) throws IOException {
			if(levelLocked[3]) {
				return;
			}
			try {
				
				audio.boopPlay();
				sceneSwitcher.switchScene(event, "/application/resources/levels/level5A.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		
		public void levelSix(MouseEvent event) throws IOException {	
			if(levelLocked[4]) {
				return;
			}
			try {	
				
				audio.boopPlay();
				sceneSwitcher.switchScene(event, "/application/resources/levels/level6A.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		
		public void levelSeven(MouseEvent event) throws IOException {	
			if(AccountManager.getLevelScore(AccountManager.getLevelID(6)) < 75) {
				return;
			}
			try {	
				
				audio.boopPlay();
				sceneSwitcher.switchScene(event, "/application/resources/levels/level7A.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		
		public void levelEight(MouseEvent event) throws IOException {	
			if(AccountManager.getLevelScore(AccountManager.getLevelID(7)) < 75) {
				return;
			}
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
			if(AccountManager.getLevelScore(AccountManager.getLevelID(1)) < 75) {
				levelLocked[0] = true;
				BoxBlur bb2 = new BoxBlur();
				bb2.setWidth(30);
		        bb2.setHeight(30);
		        bb2.setIterations(25);
			    levelTwo.setEffect(bb2);
			} else {
				levelLocked[0] = false;
			}
			if(AccountManager.getLevelScore(AccountManager.getLevelID(2)) < 75) {
				levelLocked[1] = true;
				BoxBlur bb3 = new BoxBlur();
				bb3.setWidth(30);
		        bb3.setHeight(30);
		        bb3.setIterations(25);
			    levelThree.setEffect(bb3);
			} else {
				levelLocked[1] = false;
			}
			if(AccountManager.getLevelScore(AccountManager.getLevelID(3)) < 75) {
				levelLocked[2] = true;
				BoxBlur bb4 = new BoxBlur();
				bb4.setWidth(30);
		        bb4.setHeight(30);
		        bb4.setIterations(25);
			    levelFour.setEffect(bb4);
			} else {
				levelLocked[2] = false;
			}
			if(AccountManager.getLevelScore(AccountManager.getLevelID(4)) < 75) {
				levelLocked[3] = true;
				BoxBlur bb5 = new BoxBlur();
				bb5.setWidth(30);
		        bb5.setHeight(30);
		        bb5.setIterations(25);
			    levelFive.setEffect(bb5);
			} else {
				levelLocked[3] = false;
			}
			if(AccountManager.getLevelScore(AccountManager.getLevelID(5)) < 75) {
				levelLocked[4] = true;
				BoxBlur bb6 = new BoxBlur();
				bb6.setWidth(30);
		        bb6.setHeight(30);
		        bb6.setIterations(25);
			    levelSix.setEffect(bb6);
			} else {
				levelLocked[4] = false;
			}
		    
			
			if(!isTeacher) {
				instructorDashboard.setVisible(false);
			}
			
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
		/*---SANDBOX OPTIONS HOVER EFFECTS---*/
		
		
		
		
		
		// Level1 HOVER
		public void onLevelOneHover() {
			levelOne.setScaleX(1.1);
			levelOne.setScaleY(1.1);
		}
		
		public void offLevelOneHover() {
			levelOne.setScaleX(0.9);
			levelOne.setScaleY(0.9);
		}
		
		
		// Level2 HOVER
		public void onLevelTwoHover() {
			if(!levelLocked[0]) {
				levelTwo.setScaleX(1.1);
				levelTwo.setScaleY(1.1);
			}
		}
		
		public void offLevelTwoHover() {
			if(!levelLocked[0]) {
				levelTwo.setScaleX(0.9);
				levelTwo.setScaleY(0.9);
			}
		}
		
		
		// Level3 HOVER
		public void onLevelThreeHover() {
			if(!levelLocked[1]) {
				levelThree.setScaleX(1.1);
				levelThree.setScaleY(1.1);
			}
		}
		
		public void offLevelThreeHover() {
			if(!levelLocked[1]) {
				levelThree.setScaleX(0.9);
				levelThree.setScaleY(0.9);
			}
		}
		
		// Level4 HOVER
		public void onLevelFourHover() {
			if(!levelLocked[2]) {
				levelFour.setScaleX(1.1);
				levelFour.setScaleY(1.1);
			}
		}
		
		public void offLevelFourHover() {
			if(!levelLocked[2]) {
				levelFour.setScaleX(0.9);
				levelFour.setScaleY(0.9);
			}
		}
		
		// Level5 HOVER
		public void onLevelFiveHover() {
			if(!levelLocked[3]) {
				levelFive.setScaleX(1.1);
				levelFive.setScaleY(1.1);
			}
		}
		
		public void offLevelFiveHover() {
			if(!levelLocked[3]) {
				levelFive.setScaleX(0.9);
				levelFive.setScaleY(0.9);
			}
		}

		// Level6 HOVER
		public void onLevelSixHover() {
			if(!levelLocked[4]) {
				levelSix.setScaleX(1.1);
				levelSix.setScaleY(1.1);
			}
		}
		
		public void offLevelSixHover() {
			if(!levelLocked[4]) {
				levelSix.setScaleX(0.9);
				levelSix.setScaleY(0.9);
			}
		}
		

}
