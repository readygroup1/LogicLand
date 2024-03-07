package application.resources;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class sandboxController {
	
	// ----------------Constants/Resources -----------------------
	
	DraggableMaker drag =  new DraggableMaker();
	
	// ----------------Variables -----------------------
	
	@FXML
	ImageView batteryGen;
	@FXML
	Pane pane;
	@FXML
	VBox switchPanel;
	
	
	// ---------------- Buttons -----------------------
	
	public void batteryGen() {
		
		Battery battery = new Battery();
		pane.getChildren().add(battery.getBattery());
		switchPanel.getChildren().add(battery.getButton());
		drag.makeDraggable(battery.getBattery());
		battery.getButton().setOnMouseClicked(event ->{battery.switchState();});
	}
	
	
	
	
	
	// ----------------UserDashboard Buttons -----------------------
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	public void roadmap(ActionEvent event) throws IOException {			
		try {			
			sceneSwitcher.switchScene(event, "/application/resources/roadmap.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void sandbox(ActionEvent event) throws IOException {			
		try {			
			sceneSwitcher.switchScene(event, "/application/resources/sandbox.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void highscore(ActionEvent event) throws IOException {			
		try {			
			sceneSwitcher.switchScene(event, "/application/resources/highscore.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void discoveries(ActionEvent event) throws IOException {			
		try {			
			sceneSwitcher.switchScene(event, "/application/resources/discoveries.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void options(ActionEvent event) throws IOException {			
		try {			
			sceneSwitcher.switchScene(event, "/application/resources/options.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}

}
