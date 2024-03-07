package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application {
	@Override
	
	// This is boiler plate code to open up a window. 
	public void start(Stage stage) {		
		
		try {
		
			Parent root = FXMLLoader.load(getClass().getResource("/application/resources/login.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Logic Land");
			stage.setResizable(false);
			// This removes the default highlighting of a single button. Comment it out to see what I mean. 
			scene.getRoot().requestFocus();
			
			stage.show();		
		}
		
		catch(Exception e) {
			e.printStackTrace();
		} 						
	}
	
	public static void main(String[] args) {		
		
		launch(args);
	}
}