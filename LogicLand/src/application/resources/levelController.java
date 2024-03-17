package application.resources;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class levelController {

    // Constants/Resources
    SceneSwitcher sceneSwitcher = new SceneSwitcher();

    // Store the current level
    private int currentLevel; // Default to Level 1

    @FXML
    private Label currentLevelLabel; // Inject the Label from FXML

    // Button Functions
    @FXML
    private Button backButton;

    public void back(ActionEvent event) throws IOException {
        try {
            sceneSwitcher.switchScene(event, "roadmap.fxml");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void previousLevel(ActionEvent event) throws IOException {
        try {
            currentLevel = extractLevelNumber(currentLevelLabel.getText());

            if (currentLevel > 1) {
                currentLevel--;
                String previousLevelFXML = "Level" + currentLevel + ".fxml";
                sceneSwitcher.switchScene(event, previousLevelFXML);
            } else {
                System.out.println("Already at the first level.");
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void nextLevel(ActionEvent event) throws IOException {
        try {
        	currentLevel = extractLevelNumber(currentLevelLabel.getText());
            currentLevel++;
            String nextLevelFXML = "Level" + currentLevel + ".fxml";
            sceneSwitcher.switchScene(event, nextLevelFXML);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    // Method to extract the level number from the label text
    private int extractLevelNumber(String labelText) {
        return Integer.parseInt(labelText.substring(5)); // Assuming "LevelX" format
    }
}
