package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;



public class Main extends Application {
	
	// For DEvelopment. REmove before hanging in.
	boolean skipLogin = true;
	@Override
	
	// This is boiler plate code to open up a window. 
	// There is an explanation for how this works in SceneSwitcher
	public void start(Stage stage) {		
		
		try {
			Font customFont = Font.loadFont(getClass().getResourceAsStream("/application/resources/Vermin Vibes 1989.ttf"), 12);
			if (customFont == null) {
			    // Handle the error, font not loaded
			    System.out.println("There was an error loading the font.");
			} else {
			    System.out.println("Font loaded: " + customFont.getName());
			}
			
			Parent root;
			
			// Skip login Code
			if(skipLogin) {
				root = FXMLLoader.load(getClass().getResource("/application/resources/roadmap.fxml"));
			}
			else {
				root = FXMLLoader.load(getClass().getResource("/application/resources/mainMenu.fxml"));
			}
			
			Scene scene = new Scene(root);
			Image applicationIcon = new Image(getClass().getResourceAsStream("/application/resources/images/Arcadebox.png"));
	        stage.getIcons().add(applicationIcon);
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