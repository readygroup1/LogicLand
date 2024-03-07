package application.resources;


import java.io.IOException;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwitcher {	
	
		private Stage stage;
		private Scene scene;
		private Parent root;
		
		public SceneSwitcher() {
			
		}
		
		
		
		public void switchScene(ActionEvent event, String fxml) throws IOException {
			try {
				
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

}
