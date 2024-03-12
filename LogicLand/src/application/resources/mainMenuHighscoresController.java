package application.resources;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class mainMenuHighscoresController {
	
	//--------Constants/Resources---------
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	//---------------Variables--------------------	
	@FXML
	Button backButton;
	@FXML
	TableView highScoreTable;
	
	// ----------------Buttons Functions-----------------------
	public void back(ActionEvent event) throws IOException {			
		try {			
			sceneSwitcher.switchScene(event, "mainMenu.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}

}
