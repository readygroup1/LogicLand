package application.resources;


import java.io.IOException;
import java.util.EventObject;

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
		// Kinda copied code. I change it a bit. Might Need to document.
		public void switchScene(EventObject event, String fxml) throws IOException {
			try {
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
		// Copied Code
		public void fadeSwitchScene(ActionEvent event, String fxml) {
			try {
				  //kinda copied code. Might Need to document.
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
