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
    private int currentLevel; // currentLevel as its integer value

    @FXML
    private Label currentLevelLabel; // Inject the Label from FXML

    
    
    
   
  
    // Button and Helper Functions

    /**
     * Switches the scene to the roadmap view when the "Back" button is clicked.
     *
     * @param event The ActionEvent triggered by clicking the "Back" button.
     * @throws IOException If an I/O exception occurs during scene switching.
     */
    public void back(ActionEvent event) throws IOException {
        try {
            sceneSwitcher.switchScene(event, "roadmap.fxml");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Switches the scene to the previous level view when the "Previous Level" button is pressed.
     * 
     *
     * @param event The ActionEvent triggered by clicking the "Previous Level" button.
     * @throws IOException If an I/O exception occurs during scene switching.
     */
    public void previousLevel(ActionEvent event) throws IOException {
        try {
            currentLevel = extractLevelNumber(currentLevelLabel.getText());
            currentLevel--;
            String previousLevelFXML = "Level" + currentLevel + ".fxml";
            sceneSwitcher.switchScene(event, previousLevelFXML);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Switches the scene to the next level view when the "Next Level" button is clicked.
     *
     * @param event The ActionEvent triggered by clicking the "Next Level" button.
     * @throws IOException If an I/O exception occurs during scene switching.
     */
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

    /**
     * Extracts the level number from the given label text.
     * Assumes the label text is in the format "LevelX".
     *
     * @param labelText The text of the label, assumed to be in the format "LevelX".
     * @return The extracted level number.
     */
    private int extractLevelNumber(String labelText) {
        return Integer.parseInt(labelText.substring(5)); // Assuming "LevelX" format
    }
}
