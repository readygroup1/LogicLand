package application;

import java.util.Timer;

import java.util.TimerTask;

import application.resources.loginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

/**
 * This Main Class is responsible for the initialization of the game. 
 * It will open the window and determine where in the game a user will begin.
 * 
 * @author Nick Howard 
 * @author Callum Thompson
 * @author Andres Pedreros 
 * @author Kalundi Serumaga
 * @author Thomas Llamzon
 * @version 1.0
 * 
 * This class contains some auto-generated code for opening up a window in javafx.
 */
public class Main extends Application {

	@Override
	/**
	 * This class is called by the javaFX function launch. Launch provides the input parameter stage.
	 * start() will open a window and display the main menu
	 * 
	 * @parm Stage - automatically provided my javafx when launch is called in public static void main
	 * @throws - Exception of the fmxl file or any of its dependencies cannot be loaded
	 */
	public void start(Stage stage) {		
		
		try {
			// Load fonts
			Font.loadFont(getClass().getResourceAsStream("/application/resources/Vermin Vibes 1989.ttf"), 12);
			Font.loadFont(getClass().getResourceAsStream("/application/resources/tahoma.ttf"), 12);
			// Load FXML file
			Parent root = FXMLLoader.load(getClass().getResource("/application/resources/mainMenu.fxml"));;
			
			Scene scene = new Scene(root);
			Image applicationIcon = new Image(getClass().getResourceAsStream("/application/resources/images/Arcadebox.png"));
	        stage.getIcons().add(applicationIcon);
			stage.setScene(scene);
			stage.setTitle("Logic Land");
			stage.setResizable(false);
			
			stage.show();	
			stage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent t) {
			        Platform.exit();
			        System.exit(0);
			    }
			});
			
		}
		
		catch(Exception e) {
			e.printStackTrace();
		} 						
	}
	
	/**
	 * This is the beginning. Run this to open window for game.
	 * @param args - default parameters. No need to input anything.
	 */
	public static void main(String[] args) {	
		MusicPlayer.playBackgroundMusic();
		
		launch(args);
		
	}
}