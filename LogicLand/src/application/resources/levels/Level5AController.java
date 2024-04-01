package application.resources.levels;

import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;

import application.AccountManager;
import application.MusicPlayer;
import application.resources.SceneSwitcher;
import application.resources.MultiMediaPlayer;
import application.resources.sandboxController;
import application.resources.gates.andController;
import application.resources.gates.batteryController;
import application.resources.gates.bulbController;
import application.resources.gates.gateObject;
import application.resources.gates.nandController;
import application.resources.gates.norController;
import application.resources.gates.notController;
import application.resources.gates.orController;
import application.resources.gates.xorController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


/**
 * This class represents the controller for Level 4A in the LogicLand application.
 * It extends the `sandboxController` class and implements the `Initializable` interface.
 * 
 * The `Level4AController` class is responsible for managing the user interface and logic of Level 4A.
 * It handles the generation of gate objects, pre-loading of objects, and checking if the game is won.
 * 
 * The class contains various instance variables, including the circuit board pane, buttons, image views,
 * labels, and pre-loaded object panes. It also defines an enum `Type` to represent different types of objects.
 * 
 * The class provides methods for initializing the level, loading pre-loaded objects, generating gate objects,
 * and checking if the game is won. It also includes getter and setter methods for accessing the circuit board pane.
 * 
 * The `Level4AController` class follows the JavaFX controller pattern and is designed to work with the LogicLand application.
 * It interacts with the user interface and handles user input to create and manipulate gate objects on the circuit board.
 * 
 * Credit where credit is due, some of the code for generating draggable nodes, as well as linking them with 
 * a line (makeWire()), was inspired by / borrowed from Joell Graff's tutorial "Drag-and-Drop in JavaFX" found
 * here https://monograff76.wordpress.com/2015/02/20/. 
 * 
 * @see sandboxController
 * @version 1.0
 * @since 1.0
 * @author Andres Pedreros Castro
 * @author Nicholas Howard
 * @author Kalundi Serumaga
 */
public class Level5AController extends sandboxController implements Initializable {

	// ----------------Variables ---------------------------------------
		public Boolean deleteState = false;
		
		@FXML
		Pane circuitBoardPane;
		@FXML
		Button roadMap;
		
		//Uncomment @FXML above any generator/delete objects that you want to include in the level and make sure the names match in the fxml file
		@FXML
		ImageView andGen;
		//@FXML
		ImageView batteryGen;
		//@FXML
		ImageView notGen;
		//@FXML
		ImageView orGen;
		//@FXML
		ImageView bulbGen;
		@FXML
		ImageView deleteImage;
		//@FXML
		ImageView nandGen;
		@FXML
		ImageView norGen;
		//@FXML
		ImageView xorGen;
		
		@FXML
		Label title;
		
		@FXML
		Button checkWin;
		
		 MultiMediaPlayer audio = new MultiMediaPlayer();
		
		// I used these panes a way to store the coordinates of where I wanted the preloaded objects.
		@FXML
		Pane battery1;
		@FXML
		Pane battery2;
		
		@FXML
		Pane bulb;
		
		
		private bulbController endBulb;
		
		//-------------Constants / Resources--------------------------------------------
		
		public enum Type{
			BATTERY, AND, OR, NOT, NOR, XOR, NAND, BULB		
		}
		
		Map<Type, String> fxmlPath = new EnumMap<>(Type.class);
		
		
		Image deleteOn = new Image(getClass().getResourceAsStream("/application/resources/images/deleteOn.png"));
	 	Image deleteOff = new Image(getClass().getResourceAsStream("/application/resources/images/deleteOff.png"));
		
		// --------------------Initializer-----------------------------------------
		/**
		 * Initializes the Level5AController.
		 * This method is called after the FXML file has been loaded and the controller object has been created.
		 * It sets up the initial state of the controller, including playing level music, initializing the fxmlPath map,
		 * setting up the delete functionality, loading preloaded objects, handling key events, and playing a video demo.
		 *
		 * @param arg0 The URL of the FXML file.
		 * @param arg1 The ResourceBundle for the FXML file.
		 */
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			//play level music
			MusicPlayer.stopMusic();
			MusicPlayer.playLevelMusic();
			
			
			// Create Enum Map to file locations. This is used in the parameters for load. There is probably somewhere better I could put this initialization.
			
			fxmlPath.put(Type.BATTERY, "/application/resources/gates/battery.fxml");
			fxmlPath.put( Type.OR, "/application/resources/gates/or.fxml");
			fxmlPath.put(Type.AND, "/application/resources/gates/and.fxml");
			fxmlPath.put(Type.NOR, "/application/resources/gates/nor.fxml" );
			fxmlPath.put(Type.BULB,"/application/resources/gates/bulb.fxml" );
			fxmlPath.put(Type.NOT,"/application/resources/gates/not.fxml");
			fxmlPath.put(Type.NAND,"/application/resources/gates/nand.fxml");
			fxmlPath.put(Type.XOR,"/application/resources/gates/xor.fxml");
			
			// Delete is always bound to the click function but only works when deleteState is set to true by the delete button.
			circuitBoardPane.setOnMousePressed(event ->{this.delete(event);});
			circuitBoardPane.setViewOrder(1);
			
			// Use Load to create the preloaded object and store them in the previous placeholders.
			 try {
				battery1 = this.load(battery1, Type.BATTERY);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 try {
				battery2 = this.load(battery2, Type.BATTERY);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 try {
				bulb = this.load(bulb, Type.BULB);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 circuitBoardPane.setOnKeyPressed(event ->{
					
					try {
						
						
						
						switch(event.getCode()) {
						
						case W:
							if(this.andGen != null) {
								
								this.andGenerator();}
							break;
						case E:
							if(this.orGen != null) {
								
								this.orGenerator();}
							break;
						case R:
							if(this.notGen != null) {
								
								this.notGenerator();}
							break;
						case T:
							if(this.nandGen != null) {
								
								this.nandGenerator();}
							break;
						case Y:
							if(this.norGen != null) {
								
								this.norGenerator();}
							break;
						case U:
							if(this.xorGen != null) {
								
								this.xorGenerator();}
							break;
		
						case O:
		
								this.deleteButton(null);
							
							break;
						
					}
				}
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			 
			 endBulb = ((bulbController) bulb.getProperties().get("controller"));
			// Create function calls to make wire if you want connections
			
			 
			
			
		}
		
		//----------------Check if game is won----------------------------
		/**
		 * Checks if the game is won by verifying that the bulb is on.
		 * 
		 */
		public void CheckWin() {
			//if bulb is on win
			if(endBulb.getState()) {
				title.setText("Great Job! Head to the next part!");
				Alert alert = new Alert(AlertType.INFORMATION);
			 	// Apply inline styling
		        alert.getDialogPane().setStyle("-fx-background-color: #F38307;");
		        alert.getDialogPane().lookupButton(ButtonType.OK).setStyle("-fx-background-color: #2a80b9; -fx-text-fill: #ffffff;");
		        
		        alert.setTitle("Level Completed");
		        alert.setHeaderText(null);
		        alert.setContentText("Great Job! Level 5A Completed!");

		        // This will block the user input until the modal dialog is dismissed
		        alert.showAndWait();
				if(AccountManager.getLevelScore(AccountManager.getLevelID(5)) != 50) {
					AccountManager.setLevelScore(AccountManager.getLevelID(5), 50);
				}
			} else {
				title.setText("Not quite! Try again.");
			}
		}
		/**
		 * Calls the CheckWin function when the checkWin button is clicked.
		 * 
		 * @param node
		 */
		public void callChecktype(Rectangle node) {
			switch( (String)(node.getProperties().get("ClassType")) ) {
			case "AND":
				((andController)(node.getProperties().get("parentGate"))).checktype();			//Andres
				break;
			case "BATTERY":
				((batteryController)node.getProperties().get("parentGate")).checktype();			//Andres
				break;
			case "BULB":
				((bulbController)node.getProperties().get("parentGate")).checktype();
				break;
			case "NAND":
				((nandController)node.getProperties().get("parentGate")).checktype();
				break;
			case "NOR":
				((norController)node.getProperties().get("parentGate")).checktype();
				break;
			case "XOR":
				((xorController)node.getProperties().get("parentGate")).checktype();
				break;
			case "NOT":
				((notController)node.getProperties().get("parentGate")).checktype();
				break;
			case "OR":
				((orController)node.getProperties().get("parentGate")).checktype();
				break;

			}
		}

		
		
		
		
		//----------------Getter and Setter Functions ---------------------
		/**
		 * Gets the circuit board pane.
		 */
		public Pane getCircuitBoardPane() {
			
			return circuitBoardPane;
			
		}
		
		//------------------Object Pre-Load Functions-----------------------
		/**
		 * This function is used to load the preloaded objects into the level.
		 * @param origin The pane that the object will be loaded into.
		 * @param type The type of object that will be loaded.
		 */
		public Pane load(Pane origin,  Type type) throws IOException{
			try {
				
				// Create the object and set up the properties
				FXMLLoader loader = new FXMLLoader(getClass().getResource((String) fxmlPath.get(type)));
				Pane object = loader.load();
				gateObject controller = loader.getController();
				object.getProperties().put("controller", controller);
				object.getProperties().put("type", type);
				controller.setBoard(this);
				controller.setImmovable();
				
				// Display the object
				circuitBoardPane.getChildren().add(object);
				object.setViewOrder(-1);
				object.setLayoutY(origin.getLayoutY());
				object.setLayoutX(origin.getLayoutX());
				
				return object;
			}		
			catch(Exception e) {
				e.printStackTrace();
				return origin;
			}	
		}
		
		
		// ---------------- Object Generator Buttons -----------------------
		
		/**
		 * Generates a new object based on the specified FXML file, type, and origin ImageView.
		 * 
		 * @param fxml   the path to the FXML file
		 * @param type   the type of the object
		 * @param origin the origin ImageView
		 * @throws IOException if an I/O error occurs while loading the FXML file
		 */
		public void generator(String fxml, Type type, ImageView origin) throws IOException{
			try {
				
				// Create the object and set up the properties
				FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
				Pane object = loader.load();
				gateObject controller = loader.getController();
				object.getProperties().put("controller", controller);
				object.getProperties().put("type", type);
				controller.setBoard(this);
				
				// Display the object
				circuitBoardPane.getChildren().add(object);
				object.setViewOrder(-1);
				audio.gatePlay();
				object.setLayoutY(origin.getLayoutY() - 100);
				object.setLayoutX(origin.getLayoutX());
			}		
			catch(Exception e) {
				e.printStackTrace();
			}	
		}
		/**
		 * Generates an AND gate object.
		 */
		public void andGenerator() throws IOException {			
				
				try {
					this.generator("/application/resources/gates/and.fxml", Type.AND, andGen);
				}			
				catch(Exception e) {
					e.printStackTrace();
				}	
			}
		
		/**
		 * Generates a battery object.
		 */		
		public void batteryGenerator() throws IOException{
			
			try {		
				this.generator("/application/resources/gates/battery.fxml", Type.BATTERY, batteryGen);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * Generates an OR gate object.
		 */
		public void orGenerator() throws IOException{
				
				try {		
					this.generator("/application/resources/gates/or.fxml", Type.OR, orGen);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}	
		/**
		 * Generates a bulb object.
		 */
		public void bulbGenerator() throws IOException{
			
			try {		
				this.generator("/application/resources/gates/bulb.fxml", Type.BULB, bulbGen);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * Generates a NOT gate object.
		 */
		public void notGenerator() throws IOException{
				
				try {		
					this.generator("/application/resources/gates/not.fxml", Type.NOT, notGen);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		/**
		 * Generates a NOR gate object.
		 */
		public void norGenerator() throws IOException{
			
			try {		
				this.generator("/application/resources/gates/nor.fxml", Type.NOR, norGen);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}	
		/**
		 * Generates a NAND gate object.
		 */
		public void nandGenerator() throws IOException{
			
			try {		
				this.generator("/application/resources/gates/nand.fxml", Type.NAND, nandGen);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}	
		/**
		 * Generates a XOR gate object.
		 */
		public void xorGenerator() throws IOException{
			
			try {		
				this.generator("/application/resources/gates/xor.fxml", Type.XOR, xorGen);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}	

		
		
		//--------------Object Interaction Functions -------------------------
		
		/**
		 * This function is called when a terminal is clicked. It checks the type of the terminal and calls the checkType function.
		 * This is used to update the state of the gate when a wire is deleted.
		 * @param event The mouse event that triggers the function.
		 */
		public void beginConnection(MouseEvent event) {
			
			// Store the start node information while the line is being drawn.
			Rectangle startNode = (Rectangle) event.getSource();
			String startType =((String)((Rectangle) event.getSource()).getProperties().get("type"));
			
			double startX = event.getSceneX();
			double startY = event.getSceneY();
			
			// Add the temporary line to the pane.
			Line connectLine = new Line(startX, startY, startX, startY);
			connectLine.setStrokeWidth(5);
			circuitBoardPane.getChildren().add(connectLine);
			
			
			// This draws the line as the user makes the connection
			// +5 to make line not directly under the cursor so we can check what object it is hovering over when click is released.
			circuitBoardPane.setOnMouseDragged(event1 ->{			
				connectLine.setEndX(event1.getSceneX());
				connectLine.setEndY(event1.getSceneY());
				connectLine.setViewOrder(0);
			});
			
			circuitBoardPane.setOnMouseReleased(event2 ->{
				
				// Remove the temporary line when mouse released
				circuitBoardPane.getChildren().remove(connectLine);
				
				// If the mouse was released over a terminal
				if(event2.getPickResult().getIntersectedNode() instanceof Rectangle) {
					Rectangle endNode = (Rectangle) event2.getPickResult().getIntersectedNode();
					String endType = (String) endNode.getProperties().get("type");
					
					if((startNode.getProperties().get("ClassType") == "BATTERY" && endNode.getProperties().get("ClassType") == "BULB")||(startNode.getProperties().get("ClassType") == "BULB" && endNode.getProperties().get("ClassType") == "BATTERY")) {
						return;
					}

					
					if(startType != endType) {
						
						// Check which is the output to match the parameter order of makeWire.
						if (startType == "output") {
							
							this.makeWire(startNode, endNode);
							audio.wirePlay();
							callChecktype(startNode);
							
							
						
						}
						
						else {
							
							
							
							
							this.makeWire(endNode, startNode);
							audio.wirePlay();
							callChecktype(endNode);
						}
					}
				}
				else {
					// Release the mouse binding if the mouse was not release over a terminal.
					circuitBoardPane.setOnMouseReleased(null);				
				}
				
				
			});
			
			
			
		}
		
		/** This function is called when a wire is made. It takes the two terminals that are connected and draws a line between them.
		 * It also stores the wire in the terminal properties so it can be deleted later. 
		 * @param outputTerminal The output terminal of the wire.
		 * @param inputTerminal The input terminal of the wire.
		 * 
		 * */
		public void makeWire(Rectangle outputTerminal, Rectangle inputTerminal) {		
			
			// Get the parent node of the terminal, the gate, so I can figure out the absolute position of the terminal. 
			// outputTerminal.layoutXProperty() only give the relative x,y coordiniates inside the gate
			Pane outputPane = (Pane) outputTerminal.getParent();
			Pane inputPane = (Pane)inputTerminal.getParent();			
			
			Line connectLine = new Line();
			connectLine.setStrokeWidth(5);
			
			// Set the bindings. These four lines are Kinda copied code. Need to document or change.
			connectLine.startXProperty().bind(outputTerminal.layoutXProperty().add(outputPane.layoutXProperty().add(outputTerminal.getBoundsInParent().getWidth() / 1.5)));
			connectLine.startYProperty().bind(outputTerminal.layoutYProperty().add(outputPane.layoutYProperty().add(outputTerminal.getBoundsInParent().getHeight() / 2)));

			connectLine.endXProperty().bind(inputTerminal.layoutXProperty().add(inputPane.layoutXProperty().add(inputTerminal.getBoundsInParent().getWidth() / 4)));
			connectLine.endYProperty().bind(inputTerminal.layoutYProperty().add(inputPane.layoutYProperty().add(inputTerminal.getBoundsInParent().getHeight() / 2)));
			
			connectLine.getProperties().put("connection1",outputTerminal);
			connectLine.getProperties().put("connection2", inputTerminal);
			
			// Add to the pane and push behind the gates so the user can click the terminal again.
			circuitBoardPane.getChildren().add(connectLine);
			connectLine.setViewOrder(0);
			
			// Release the mouse binding from beginConnection so the line isn't created again.
			circuitBoardPane.setOnMouseReleased(null);
			
			// replaces old wire when a new one is made
			if(outputTerminal.getProperties().get("wire")!= null && inputTerminal.getProperties().get("wire")!= null && outputTerminal.getProperties().get("wire")== inputTerminal.getProperties().get("wire")) {
				circuitBoardPane.getChildren().remove(outputTerminal.getProperties().get("wire"));
				outputTerminal.getProperties().put("wire", null);
				inputTerminal.getProperties().put("wire", null);
			}
			
			if(outputTerminal.getProperties().get("wire")!= null) {
				circuitBoardPane.getChildren().remove(outputTerminal.getProperties().get("wire"));
				outputTerminal.getProperties().put("wire", null);

			}
			
			if(inputTerminal.getProperties().get("wire")!= null) {
				circuitBoardPane.getChildren().remove(inputTerminal.getProperties().get("wire"));
				inputTerminal.getProperties().put("wire", null);

			}
			
			if(outputTerminal.getProperties().get("put") != null) {
				((Rectangle)outputTerminal.getProperties().get("put")).getProperties().put("put", null);
			}
			if(inputTerminal.getProperties().get("put") != null) {
				((Rectangle)inputTerminal.getProperties().get("put")).getProperties().put("put", null);

			}
			
			
			outputTerminal.getProperties().put("wire", connectLine);
			outputTerminal.getProperties().put("put", inputTerminal);	//Andres
			
			inputTerminal.getProperties().put("wire", connectLine);
			inputTerminal.getProperties().put("put", outputTerminal);
			
			
		}
		
		
		
		
		
		/**
		 * This functions puts the player into delete mode where they can delete gates and wires
		 * @param event The mouse event that triggers the function.
		 */
		public void deleteButton(MouseEvent event) {
			audio.boopPlay();
			if(deleteState) {
				deleteState = false;
				deleteImage.setImage(deleteOff);
				circuitBoardPane.setCursor(Cursor.DEFAULT);
			}
			
			else {
				deleteState = true;
				deleteImage.setImage(deleteOn);
				circuitBoardPane.setCursor(Cursor.CROSSHAIR);			
			}		
		}
		/**
		 * This function is called when a gate is deleted. It checks the type of the terminal and calls the checkType function.
		 * This is used to update the state of the gate when a wire is deleted.
		 * @param event The mouse event that triggers the function.
		 */
		public void delete(MouseEvent event) {
			audio.boopPlay();
			if(deleteState && event.getY() < 570 && event.getY() > 155 && event.getX() < 1000 && event.getX() > 260 ){
				//If it is wire
				if(event.getPickResult().getIntersectedNode() instanceof Line) {
					
					//deletes connection between wires
					
					Rectangle input = ((Rectangle)(event.getPickResult().getIntersectedNode().getProperties().get("connection1")));
					Rectangle output = ((Rectangle)(event.getPickResult().getIntersectedNode().getProperties().get("connection2")));
					input.getProperties().put("put", null);
					output.getProperties().put("put", null);
					
					callChecktype(input);
					callChecktype(output);
						
					
					
					
					
					//removes wire from screen 
					circuitBoardPane.getChildren().remove(event.getPickResult().getIntersectedNode());
				}
				
				// If it is a gate
				if(event.getPickResult().getIntersectedNode() instanceof Ellipse) {
					circuitBoardPane.getChildren().remove(((Node) event.getPickResult().getIntersectedNode().getParent()));
				}
			}
		}
			
			
		
		
		// ---------------- Button Functions -----------------------
		
		SceneSwitcher sceneSwitcher = new SceneSwitcher();
		/**
		 * This function switches the scene to the roadmap when the back button is clicked.
		 * 
		 * @param event The action event that triggers the function.
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		public void roadMap(ActionEvent event) throws IOException {			
			audio.boopPlay();
			//play level music
			MusicPlayer.stopMusic();
			MusicPlayer.playBackgroundMusic();
			try {			
				sceneSwitcher.switchScene(event, "/application/resources/roadmap.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		/**
		 * This function switches the scene to the next level when the next button is clicked.
		 * 
		 * @param event The action event that triggers the function.
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		public void next(ActionEvent event) throws IOException {
			if(AccountManager.getLevelScore(AccountManager.getLevelID(5)) < 50) {
				return;
			}
			audio.boopPlay();
			try {			
				sceneSwitcher.switchScene(event, "/application/resources/levels/level5B.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		/**
		 * This function switches the scene to the previous level when the previous button is clicked.
		 * 
		 * @param event The action event that triggers the function.
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		public void prev(ActionEvent event) throws IOException {
			audio.boopPlay();
			try {			
				sceneSwitcher.switchScene(event, "/application/resources/levels/skillTest2.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
}		