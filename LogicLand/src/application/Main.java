package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

/**
 * This Main Class is responsible for the initialization of the game. 
 * It will open the window and determine where in the game a user will begin.
 * 
 * @author Nick Howard
 * @version 1.0
 */
public class Main extends Application {
	
	// ***** For Development. Remove before handing in!!! ******
	boolean skipLogin = true;

	
	@Override
	/**
	 * This is boiler plate code to open up a window. 
	// There is an explanation for how this works in SceneSwitcher
	 */
	public void start(Stage stage) {		
		
		try {
			Font vermin = Font.loadFont(getClass().getResourceAsStream("/application/resources/Vermin Vibes 1989.ttf"), 12);
			Font tahoma = Font.loadFont(getClass().getResourceAsStream("/application/resources/tahoma.ttf"), 12);
			if (vermin == null) {
			    // Handle the error, font not loaded
			    System.out.println("There was an error loading the font vermin.");
			} if (tahoma == null) {
			    // Handle the error, font not loaded
			    System.out.println("There was an error loading the font tahoma.");
			}
			else {
			    System.out.println("Font loaded: " + vermin.getName());
			    System.out.println("Font loaded: " + tahoma.getName());
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
	
	/**
	 * To open window for game.
	 * @param args
	 */
	public static void main(String[] args) {		
		launch(args);
	}
}