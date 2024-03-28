package application.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.AccountManager;
import application.MusicPlayer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class is responsible for facilitating all actions available in the options page.
 */
public class optionsController implements Initializable {
	
	
	//-----------Constants/Resources---------
		@FXML
		Button instructorDashboard;
		@FXML 
		Text timePlayed;
		
		Boolean isTeacher = AccountManager.isAdmin();
	
		SceneSwitcher sceneSwitcher = new SceneSwitcher();
		
		MultiMediaPlayer audio = new MultiMediaPlayer();
		
	//-------------Button Functions------------------------
		
		@FXML 
		Button quit;
		/**
		 * 
		 * @param event Quit button clicked.
		 * 
		 * This method will close the game window.
		 */
		public void quit(ActionEvent event) {
			audio.boopPlay();
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.close();
			Platform.exit();
	        System.exit(0);
		}
		
		@FXML
		Button logout;
		/**
		 * 
		 * @param event Log Out button clicked.
		 * 
		 * This method will log the user out and return them to the main menu.
		 */
		public void logout(ActionEvent event) {
			try {		
				audio.boopPlay();
				AccountManager.logout();
				sceneSwitcher.switchScene(event, "mainMenu.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
	
	// ----------- User Dashboard Button Functions ---------
	
	/**
	 * 
	 * @param event Roadmap menu bar button clicked.
	 * @throws IOException Thrown if there is an error finding the roadmap page.
	 * 
	 * This method will take the user to the roadmap page.
	 */
	public void roadmap(ActionEvent event) throws IOException {			
		try {	
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/roadmap.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}

	/**
	 * 
	 * @param event Sanbox menu bar button clicked.
	 * @throws IOException Thrown if there is an error finding the sanbox page.
	 * 
	 * This method will take the user to the sandbox page.
	 */
	public void sandbox(ActionEvent event) throws IOException {			
		try {	
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/sandbox.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}

	/**
	 * 
	 * @param event Highscore menu bar button clicked.
	 * @throws IOException Thrown if there is an error finding the highscore page.
	 * 
	 * This method will take the user to the highscore page.
	 */
	public void highscore(ActionEvent event) throws IOException {	
		
			
		try {	
				audio.boopPlay();
				sceneSwitcher.switchScene(event, "/application/resources/highscore.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}
			
	}

	/**
	 * 
	 * @param event Discoveries menu bar button clicked.
	 * @throws IOException Thrown if there is an error finding the discoveries page.
	 * 
	 * This method will take the user to the discoveries page.
	 */
	public void discoveries(ActionEvent event) throws IOException {			
		try {	
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/discoveries.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}

	/**
	 * 
	 * @param event Options menu bar button clicked.
	 * @throws IOException Thrown if there is an error finding the options page.
	 * 
	 * This method will take the user to the options page.
	 */
	public void options(ActionEvent event) throws IOException {	
		
		try {	
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/options.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}

	/**
	 * 
	 * @param event Instructor Dashboard button clicked.
	 * @throws IOException Thrown if there is an error finding the instructorDashboard page.
	 * 
	 * This method will take the user to the instructorDashboard page.
	 */
   public void instructorDashboard(ActionEvent event) throws IOException {	
		
		try {	
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/instructorDashboard.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}

	/**
	 * 
	 * @param event Highscores button clicked.
	 * @throws IOException Thrown if there is an error finding the global highscores page.
	 * 
	 * This method will take the user to the global highscores page.
	 */
   public void gblHighscores(ActionEvent event) throws IOException {	
		
		try {	
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/mainMenuHighscores.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}

	/**
	 * 
	 * @param event Tutorial menu bar button clicked.
	 * @throws IOException Thrown if there is an error finding the tutorial page.
	 * 
	 * This method will take the user to the tutorial page.
	 */
   public void tutorial(ActionEvent event) throws IOException {	
		
		try {	
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/tutorial.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}


	/**
	 * 
	 * This method is responsible for displaying the instructor dashboard button if the user is a teacher.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		if(!isTeacher) {
			instructorDashboard.setVisible(false);
			timePlayed.setVisible(true);
			timePlayed.setDisable(false);
			timePlayed.setText("Time played: " + AccountManager.getTimePlayed() + " minutes");
			
		} else {
			instructorDashboard.setVisible(true);
			timePlayed.setVisible(false);
			timePlayed.setDisable(true);
		}			
	}
	

	/**
	 * 
	 * This method will interact with MultiMediaPlayer class to turn off all music and sound effects throughout the game.
	 */
	public void mute() {
		
		if(MultiMediaPlayer.defaultBoop == 0) {
			MultiMediaPlayer.defaultBoop = 0.2;
			MultiMediaPlayer.defaultVolume = 0.6;
			MusicPlayer.defaultVolume = 0.3;
			MusicPlayer.playBackgroundMusic();
			
			System.out.print("music on");
			
		}
		else{
			MultiMediaPlayer.defaultBoop = 0;
			MultiMediaPlayer.defaultVolume = 0;
			MusicPlayer.defaultVolume = 0;
			MusicPlayer.stopMusic();
			System.out.print("music off");
		}
		
		
		
	}
}
