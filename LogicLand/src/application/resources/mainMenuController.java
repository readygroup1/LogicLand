package application.resources;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.FadeTransition;


public class mainMenuController {
	
	//--------Constants/Resources---------
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	//---------Buttons-------------------
	
	public void newGame(ActionEvent event) throws IOException {			
		try {			
			sceneSwitcher.switchScene(event, "newGame.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void loadGame(ActionEvent event) throws IOException {			
		try {			
			sceneSwitcher.switchScene(event, "login.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void tutorial(ActionEvent event) throws IOException {			
		try {			
			sceneSwitcher.switchScene(event, "tutorial.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}	
	
	public void highScores(ActionEvent event) throws IOException {			
		try {			
			sceneSwitcher.switchScene(event, "mainMenuHighscores.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void quit(ActionEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.close();
		
	}
	
	public void developerLogin(MouseEvent event) throws IOException {
		
		if(state) {
			try {			
				sceneSwitcher.switchScene(event, "developerLogin.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}
		}
		
	}
	
	
	
	
	
	
	
	
	//------HardCoded Circuit ----------
	
		//---------Images------------------
		Image on = new Image(getClass().getResourceAsStream("images/on.png"));
	 	Image off = new Image(getClass().getResourceAsStream("images/off.png")); 	
		Image batteryOn = new Image(getClass().getResourceAsStream("images/batteryOn.png"));
	 	Image batteryOff = new Image(getClass().getResourceAsStream("images/batteryOff.png"));
	 	Image bulbOn = new Image(getClass().getResourceAsStream("images/bulbOn.png"));
	 	Image bulbOff = new Image(getClass().getResourceAsStream("images/bulbOff.png"));
	 	
	 	
		
	   // ----------------Variables -----------------------
		Boolean state = false;
		@FXML
		ImageView onOffButton;
		@FXML
		ImageView battery;
		@FXML
		ImageView bulb;
	
		//---------Functions----------------
		
		public void switchState() {
	 		
	 		if(state) {
	 			onOffButton.setImage(off);
	 	 		battery.setImage(batteryOff);
	 	 		bulb.setImage(bulbOff);
	 	 		state = false;
	 		}
	 		else {
	 			onOffButton.setImage(on);
	 	 		battery.setImage(batteryOn);
	 	 		bulb.setImage(bulbOn);
	 	 		state = true;
	 		}		
	 		
	 	}
	
	

}
