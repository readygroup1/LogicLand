package application.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.AccountManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class discoveriesController implements Initializable{
	
	//---------------Variables----------------------------------------------
	  @FXML
	  Button instructorDashboard;
		
	  Boolean isTeacher = AccountManager.isAdmin();
	
	// ----------------User Dashboard Button Functions -----------------------
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	MultiMediaPlayer audio = new MultiMediaPlayer();
	
	/**
	 * Take user to roadmap page.
	 * 
	 * @param event Roadmap menu bar button clicked.
	 * @throws IOException Thrown if there is an error finding the mainMenu page.
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
	 * Take user to sandbox page.
	 * 
	 * @param event Sandbox menu bar button clicked.
	 * @throws IOException Thrown if there is an error finding the mainMenu page.
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
	 * Take user to highscore page.
	 * 
	 * @param event Highscore menu bar button clicked.
	 * @throws IOException Thrown if there is an error finding the mainMenu page.
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
	 * Take user to discoveries page.
	 * 
	 * @param event Discoveries menu bar button clicked.
	 * @throws IOException Thrown if there is an error finding the mainMenu page.
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
	 * Take user to options page.
	 * 
	 * @param event Options menu bar button clicked.
	 * @throws IOException Thrown if there is an error finding the mainMenu page.
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
	 * Take user to instructorDashboard page.
	 * 
	 * @param event instructorDashboard button clicked.
	 * @throws IOException Thrown if there is an error finding the mainMenu page.
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

	@Override
	/**
	 * This method is responsible for ensuring that the instructor dashboard button is not displayed if the user is not an instructor/admin. 
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		if(!isTeacher) {
			instructorDashboard.setVisible(false);
		}
	}

}
