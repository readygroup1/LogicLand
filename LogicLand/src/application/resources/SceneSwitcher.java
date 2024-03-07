package application.resources;


import java.io.IOException;
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
		
		public void switchScene(ActionEvent event, String fxml) throws IOException {
			try {
				  // copied code. Need to document.
				  root = FXMLLoader.load(getClass().getResource(fxml));
				  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				  scene = new Scene(root);
				  stage.setScene(scene);
				  // This removes the default highlighting of a single button. Comment it out to see what I mean. 
				  scene.getRoot().requestFocus();
					
				  stage.show();
			}
			
			catch(Exception e){
				
				 e.printStackTrace();
			}
				
		}
		// Copied Code
		public void fadeSwitchScene(ActionEvent event, String fxml) {
			try {
				  //kinda copied code. Need to document.
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
