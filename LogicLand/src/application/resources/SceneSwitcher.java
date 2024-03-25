/** This class was created to switch between difference scenes within the same window.
 * 
 * @author Nicholas Howard
 * @category 
 * @version 1.0
 *
 * Parts of this code, especially pertaining to the function chains used to retrieve an open window from an event object,
 * were inspired by / borrowed from a tutorial "JavaFX Switch Scenes" found here https://www.youtube.com/watch?v=hcM-R-YOKkQ */





package application.resources;


import java.io.IOException;
import java.util.EventObject;

import application.AccountManager;
import javafx.scene.Node;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneSwitcher {	
	
		private Stage stage;
		private Scene scene;
		private Parent root;
		
		public SceneSwitcher() {
			
		}		
		/** This is a function used to switch the current scene in a window.
		 * It takes an event as a parameter, which is generated when a user click a button to move to a new screen.
		 * It also takes a string which is a location of the fxml file of the new screen.
		 * It switches the current window that is open to the new screen and returns nothing 
		 * 
		 * @param EventObject An event of the current scene.
		 * @param String The file location of the next scene.
		 * @throws IOException if file of next scene, or any of its dependencies, cannot be found. 
		 * */
		public void switchScene(EventObject event, String fxml) throws IOException {
			try {
				if(fxml.equals("/application/resources/sandbox.fxml")) {
					AccountManager.setInSandbox(true);
				} else {
					AccountManager.setInSandbox(false);
				}
				// An analogy for how this works
				// root is like a script or stage directions
				// scene is like actors acting out the script
				// stage is where it would happen, the window
				  
				  // This loads the new fxml into the root variable
				  root = FXMLLoader.load(getClass().getResource(fxml));
				  // This sets the value of stage as the window that is currently open for the user. It takes the input event, and gets the window it came from.
				  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				  //This makes a scene that is composed of the fxml we previously loaded
				  scene = new Scene(root);
				  // This sets the stage with out new scene
				  stage.setScene(scene);
				  // This removes the default highlighting of a single button. Comment it out to see what I mean. 
				  scene.getRoot().requestFocus();
				  //This displays out new scene on the window
				  stage.show();
			}
			
			catch(Exception e){
				
				 e.printStackTrace();
			}
				
		}
		/** This is a function very similar to sceneSwitch, which is used to switch the current scene in a window, but with a transition effect.
		 * It takes an event as a parameter, which is generated when a user click a button to move to a new screen.
		 * It also takes a string which is a location of the fxml file of the new screen.
		 * It switches the current window that is open to the new screen and returns nothing 
		 * 
		 * @param EventObject An event of the current scene.
		 * @param String The file location of the next scene.
		 * @throws IOException if file of next scene, or any of its dependencies, cannot be found. 
		 * */
		public void fadeSwitchScene(ActionEvent event, String fxml) {
			try {
				  
				  root = FXMLLoader.load(getClass().getResource(fxml));
				  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		    } 
			catch (IOException e) {
		        e.printStackTrace();
		    }
		    scene = new Scene(root);
		    scene.getRoot().requestFocus();
		    
		    // Fade out of current scene
		    FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), ((Node) event.getSource()).getScene().getRoot());
		    fadeOut.setFromValue(1.0);
		    fadeOut.setToValue(0.0);
		    fadeOut.setOnFinished(e -> {		      
		        stage.setScene(scene);		        
		    });
		    
		   
		    fadeOut.play();
		}

}
