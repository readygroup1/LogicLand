package application.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.AccountManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class instructorDashboard implements Initializable{

	audioPlayer audio = new audioPlayer();
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	//FX variables
	@FXML
	Text className;
	@FXML 
	ScrollPane pane;
	
	public void initialize(URL url, ResourceBundle resourceBundle) {
		className.setText("Class: "+ AccountManager.getClassName(AccountManager.getCurrentClassID()));
		VBox contentBox = new VBox(10); // 10 is the spacing between elements
		ArrayList<String> list = AccountManager.getClassList();
		for(int i = 0; i < list.size(); i++) {
            Text studentName = new Text(list.get(i));
            contentBox.getChildren().add(studentName);
        }
        
        // Set the VBox as the content of the ScrollPane
        pane.setContent(contentBox);
	}
	
	
	//-------------------Buttons-------------------------------
	public void back(ActionEvent event) throws IOException {			
		try {		
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/roadmap.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
}
