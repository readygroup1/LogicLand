package application.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.AccountManager;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;


public class mainMenuController implements Initializable{
	
	//--------Constants/Resources---------
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	//---------Buttons-------------------
	@FXML 
	Button startNewGame;
	public void newGame(ActionEvent event) throws IOException {	
		audio.boopPlay();
		transitionScene("newGame.fxml", event);	
	}
	
	@FXML
	Button loadGame;
	public void loadGame(ActionEvent event) throws IOException {	
		audio.boopPlay();
		transitionScene("login.fxml", event);	
	}
	
	@FXML 
	Button tutorial;
	public void tutorial(ActionEvent event) throws IOException {	
		audio.boopPlay();
		transitionScene("tutorial.fxml", event);			
	}	
	
	@FXML
	Button highScores;
	public void highScores(ActionEvent event) throws IOException {	
		audio.boopPlay();
		transitionScene("mainMenuHighscores.fxml", event);	
	}
	
	@FXML
	Button quit;
	public void quit(ActionEvent event) {
		audio.boopPlay();
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.close();
		
	}
	
	public void developerLogin(MouseEvent event) throws IOException {
		
		if(state) {
			audio.boopPlay();
			transitionScene("developerLogin.fxml", event);
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
		@FXML
		Line line;
		@FXML
		AnchorPane outerAnchorPane;
		@FXML
		AnchorPane anchorPane;
		MultiMediaPlayer audio = new MultiMediaPlayer();
		int buttonArrayIndex = -1;
	
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
		
		public void transitionScene(String sceneName, ActionEvent event) {
			startNewGame.setVisible(false);
			loadGame.setVisible(false);
			tutorial.setVisible(false);
			highScores.setVisible(false);
			quit.setVisible(false);
			onOffButton.setVisible(false);
			battery.setVisible(false);
			bulb.setVisible(false);
			line.setVisible(false);
			try {
				Node currentNode = ((Node)event.getSource()).getScene().getRoot();

		        double scaleX = 4; // zoom factor
		        double scaleY = 4;

		        // Calculate the focus point for zooming slightly lower than center
		        double focusX = currentNode.getBoundsInParent().getWidth() / 2;
		        double focusY = currentNode.getBoundsInParent().getHeight() / 2 + currentNode.getBoundsInParent().getHeight() * 0.1; // Slightly lower

		        // Setup scale transition
		        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1.5), currentNode);
		        scaleTransition.setToX(scaleX);
		        scaleTransition.setToY(scaleY);

		        // Calculate translation to keep the focus area visible
		        double initialWidth = currentNode.getBoundsInParent().getWidth();
		        double initialHeight = currentNode.getBoundsInParent().getHeight();
		        double deltaX = focusX - initialWidth / 2;
		        double deltaY = focusY - initialHeight / 2;
		        double targetTranslateX = deltaX * (1 - scaleX);
		        double targetTranslateY = deltaY * (1 - scaleY);

		        // Setup translate transition
		        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1.5), currentNode);
		        translateTransition.setToX(targetTranslateX);
		        translateTransition.setToY(targetTranslateY);

		        // Combine both transitions
		        ParallelTransition parallelTransition = new ParallelTransition(currentNode, scaleTransition, translateTransition);

		        parallelTransition.setOnFinished(e -> {
		            // After zooming, switch the scene
		            try {
		                sceneSwitcher.switchScene(event, sceneName);
		            } catch (IOException ioException) {
		                ioException.printStackTrace();
		            }
		        });

		        // Start the combined transition
		        parallelTransition.play();
		        
		    } catch(Exception exception) {
		        exception.printStackTrace();
		    }
		}
	
		public void transitionScene(String sceneName, MouseEvent event) {
			startNewGame.setVisible(false);
			loadGame.setVisible(false);
			tutorial.setVisible(false);
			highScores.setVisible(false);
			quit.setVisible(false);
			onOffButton.setVisible(false);
			battery.setVisible(false);
			bulb.setVisible(false);
			line.setVisible(false);
			try {
				Node currentNode = ((Node)event.getSource()).getScene().getRoot();

		        double scaleX = 4; // zoom factor
		        double scaleY = 4;

		        // Calculate the focus point for zooming slightly lower than center
		        double focusX = currentNode.getBoundsInParent().getWidth() / 2;
		        double focusY = currentNode.getBoundsInParent().getHeight() / 2 + currentNode.getBoundsInParent().getHeight() * 0.1; // Slightly lower

		        // Setup scale transition
		        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1.5), currentNode);
		        scaleTransition.setToX(scaleX);
		        scaleTransition.setToY(scaleY);

		        // Calculate translation to keep the focus area visible
		        double initialWidth = currentNode.getBoundsInParent().getWidth();
		        double initialHeight = currentNode.getBoundsInParent().getHeight();
		        double deltaX = focusX - initialWidth / 2;
		        double deltaY = focusY - initialHeight / 2;
		        double targetTranslateX = deltaX * (1 - scaleX);
		        double targetTranslateY = deltaY * (1 - scaleY);

		        // Setup translate transition
		        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1.5), currentNode);
		        translateTransition.setToX(targetTranslateX);
		        translateTransition.setToY(targetTranslateY);

		        // Combine both transitions
		        ParallelTransition parallelTransition = new ParallelTransition(currentNode, scaleTransition, translateTransition);

		        parallelTransition.setOnFinished(e -> {
		            // After zooming, switch the scene
		            try {
		                sceneSwitcher.switchScene(event, sceneName);
		            } catch (IOException ioException) {
		                ioException.printStackTrace();
		            }
		        });

		        // Start the combined transition
		        parallelTransition.play();
		        
		    } catch(Exception exception) {
		        exception.printStackTrace();
		    }
		}
		
		public void takeFocus() {
			outerAnchorPane.requestFocus();
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			Button buttonArray[] = {startNewGame, loadGame, tutorial, highScores, quit};
			
			outerAnchorPane.setFocusTraversable(true);
			anchorPane.setFocusTraversable(true);
			
			outerAnchorPane.addEventFilter(KeyEvent.KEY_PRESSED, key ->{
				outerAnchorPane.requestFocus();
				switch(key.getCode()) {
				
				 case ENTER: 
					 buttonArray[buttonArrayIndex].fire();
					 key.consume();
					 break;
				
				 case DOWN:
					 buttonArrayIndex++;
					 buttonArrayIndex =  buttonArrayIndex%5;
					 buttonArray[buttonArrayIndex].requestFocus();
					 key.consume();
					 break;
					 
				 case UP:
					 buttonArrayIndex --;
					 if(buttonArrayIndex < 0) {
						 buttonArrayIndex += 5;					  
					 }
					 buttonArray[buttonArrayIndex].requestFocus();
					 key.consume();
					 break;
					 }

			
			});
			
			
		}
	

}
