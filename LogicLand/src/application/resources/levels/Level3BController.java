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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**

 * It extends the `sandboxController` class and implements the `Initializable` interface.
 * 
 * The `Level3BController` class is responsible for managing the user interface and logic of Level 3B.
 * It handles the generation of gate objects, pre-loading of objects, and checking if the game is won.
 * 
 * The class contains various instance variables, including the circuit board pane, buttons, image views,
 * labels, and pre-loaded object panes. It also defines an enum `Type` to represent different types of objects.
 * 
 * The class provides methods for initializing the level, loading pre-loaded objects, generating gate objects,
 * and checking if the game is won. It also includes getter and setter methods for accessing the circuit board pane.
 * 
 * The `Level3BController` class follows the JavaFX controller pattern and is designed to work with the LogicLand application.
 * It interacts with the user interface and handles user input to create and manipulate gate objects on the circuit board.
 * 
 * Credit where credit is due, some of the code for generating draggable nodes, as well as linking them with 
 * a line (makeWire()), was inspired by / borrowed from Joell Graff's tutorial "Drag-and-Drop in JavaFX" found
 * here https://monograff76.wordpress.com/2015/02/20/. 
 * 
 * @version 1.0
 * @since 1.0
 * @author Andres Pedreros Castro
 * @author Nicholas Howard
 * @author Kalundi Serumaga
 */
public class Level3BController extends sandboxController implements Initializable {

	// ----------------Variables ---------------------------------------
		public Boolean deleteState = false;
		
		@FXML
		Pane circuitBoardPane;
		@FXML
		Button roadMap;
		
		//Uncomment @FXML above any generator/delete objects that you want to include in the level and make sure the names match in the fxml file
		//@FXML
		ImageView andGen;
		@FXML
		ImageView batteryGen;
		@FXML
		ImageView notGen;
		//@FXML
		ImageView orGen;
		//@FXML
		ImageView bulbGen;
		//@FXML
		ImageView deleteImage;
		//@FXML
		ImageView nandGen;
		//@FXML
		ImageView norGen;
		//@FXML
		ImageView xorGen;
		
		
		
		// I used these panes a way to store the coordinates of where I wanted the objects.
		@FXML
		Pane battery1;
		//@FXML
		//Pane battery2;
		@FXML 
		Pane not1;
		
		@FXML
		Pane bulb;
		
		// Elements from the table
		@FXML
		Button checkOutput;
		@FXML
        TextField Textoutput1;
		@FXML
        TextField Textoutput2;
		
		
		@FXML
		Label title;
		

	    @FXML
	    private Label currentLevelLabel; // Inject the Label from FXML
	    
	    MultiMediaPlayer audio = new MultiMediaPlayer();
		
		//-------------Constants / Resources--------------------------------------------
		
	    /**
	     * Enumeration representing different types of gate objects.
	     * The types include: BATTERY, AND, OR, NOT, NOR, XOR, NAND, and BULB.
	     */
	    public enum Type{
			BATTERY, AND, OR, NOT, NOR, XOR, NAND, BULB		
		}
		
		Map<Type, String> fxmlPath = new EnumMap<>(Type.class);
		
		
		Image deleteOn = new Image(getClass().getResourceAsStream("/application/resources/images/deleteOn.png"));
	 	Image deleteOff = new Image(getClass().getResourceAsStream("/application/resources/images/deleteOff.png"));
		
		// --------------------Initializer-----------------------------------------
		
	 	/**
	     * Initializes the controller after its root element has been completely processed.
	     * This method sets up the initial state of the sandbox controller, pre-loading gate objects
	     * into placeholders, creating connections between them, and starting a video demo.
	     *
	     * @param arg0 URL for the root object initialization, not used in this method
	     * @param arg1 ResourceBundle, not used in this method
	     */
	 	@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
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
//			 try {
//				battery2 = this.load(battery2, Type.BATTERY);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			 try {
				bulb = this.load(bulb, Type.BULB);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			try {
				not1 = this.load(not1, Type.NOT);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			((batteryController)battery1.getProperties().get("controller")).getOutput1().getProperties().put("put",((notController) not1.getProperties().get("controller")).getInput1());
			((notController) not1.getProperties().get("controller")).getInput1().getProperties().put("put", ((batteryController)battery1.getProperties().get("controller")).getOutput1());
			 
		
			((notController) not1.getProperties().get("controller")).getOutput().getProperties().put("put", ( (bulbController) bulb.getProperties().get("controller")).getInput1());
			( (bulbController) bulb.getProperties().get("controller")).getInput1().getProperties().put("put",((notController) not1.getProperties().get("controller")).getOutput());
			
			
			
			// Create function calls to make wire if you want connections. There is definitely a better way to do this. I will work on it.
			this.makeWire(((batteryController)battery1.getProperties().get("controller")).getOutput1(), ((notController) not1.getProperties().get("controller")).getInput1());
			this.makeWire(((notController) not1.getProperties().get("controller")).getOutput(), ( (bulbController) bulb.getProperties().get("controller")).getInput1());
			
			
			((batteryController)battery1.getProperties().get("controller")).checktype();

		}
		
		///----------------Check For Win -----------------------------------
		
		
		/**
	     * Checks if the current state of the outputs indicates a win condition for the level.
	     * If the outputs match the expected values, updates the level score in the AccountManager
	     * and displays a congratulatory message using an Alert dialog.
	     * If the outputs do not match, displays a message indicating to try again.
	     */
		public void CheckToWin() {
			if(Textoutput1.getText().equals("1") && Textoutput2.getText().equals("0") ) {
				title.setText("Great Job! Head to the next Level!");
				Alert alert = new Alert(AlertType.INFORMATION);
			 	// Apply inline styling
		        alert.getDialogPane().setStyle("-fx-background-color: #F38307;");
		        alert.getDialogPane().lookupButton(ButtonType.OK).setStyle("-fx-background-color: #2a80b9; -fx-text-fill: #ffffff;");
		        
		        alert.setTitle("Level Completed");
		        alert.setHeaderText(null);
		        alert.setContentText("Great Job! Level 3B Completed!");

		        // This will block the user input until the modal dialog is dismissed
		        alert.showAndWait();
				if(AccountManager.getLevelScore(AccountManager.getLevelID(3)) != 75) {
					AccountManager.setLevelScore(AccountManager.getLevelID(3), 75);
				}
			} else {
				title.setText("Not quite! Try again.");
			}
		}
		
		
		/**
	     * Calls the checktype method of the appropriate gateController based on the ClassType of the given node.
	     * This method is used to update the gate's state based on its inputs.
	     *
	     * @param node The Rectangle node representing a gate's terminal.
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
	     *
	     * @return The circuit board pane, which represents the area where gate objects are placed and wired.
	     */
		public Pane getCircuitBoardPane() {
			
			return circuitBoardPane;
			
		}
		
		//------------------Object Pre-Load Functions-----------------------
		
		/**
	     * Loads a pre-defined gate object onto the circuit board.
	     * This method is used to pre-load objects into the level (not object generator).
	     * The pre-defined gate objects are created based on the specified type.
	     * The gate object loaded by this method is set to be immovable.
	     *
	     * @param origin The origin pane where the gate object will be loaded from.
	     * @param type   The type of gate (e.g., Type.AND, Type.OR, Type.NOT, etc.).
	     * @return The loaded gate object as a Pane.
	     * @throws IOException Signals that an I/O exception has occurred.
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
	     * Generates a gate object on the circuit board based on the provided parameters.
	     * Each gate object is represented as a node in the user interface.
	     * Nodes are used to pass information from user interface events to the sandboxController.
	     *
	     * @param fxml   The path to the FXML file that defines the gate object.
	     * @param type   The type of gate (e.g., Type.AND, Type.OR, Type.NOT, etc.).
	     * @param origin The ImageView representing the origin of the gate generation (e.g., button image).
	     * @throws IOException Signals that an I/O exception has occurred.
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
				audio.gatePlay();

				object.setViewOrder(-1);
				object.setLayoutY(origin.getLayoutY() - 100);
				object.setLayoutX(origin.getLayoutX());
			}		
			catch(Exception e) {
				e.printStackTrace();
			}	
		}
		
		
		/**
	     * Generates an AND gate on the circuit board when called.
	     * This method is responsible for creating an AND gate by calling the general
	     * generator method with specific parameters for the AND gate.
	     * If an exception occurs during the generation process, it is caught and printed.
	     *
	     * @throws IOException Signals that an I/O exception has occurred.
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
	     * Generates a BATTERY gate on the circuit board when called.
	     * This method is responsible for creating a BATTERY gate by calling the general
	     * generator method with specific parameters for the BATTERY gate.
	     * If an exception occurs during the generation process, it is caught and printed.
	     *
	     * @throws IOException Signals that an I/O exception has occurred.
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
	     * Generates an OR gate on the circuit board when called.
	     * This method is responsible for creating an OR gate by calling the general
	     * generator method with specific parameters for the OR gate.
	     * If an exception occurs during the generation process, it is caught and printed.
	     *
	     * @throws IOException Signals that an I/O exception has occurred.
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
	     * Generates a BULB gate on the circuit board when called.
	     * This method is responsible for creating a BULB gate by calling the general
	     * generator method with specific parameters for the BULB gate.
	     * If an exception occurs during the generation process, it is caught and printed.
	     *
	     * @throws IOException Signals that an I/O exception has occurred.
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
	     * Generates a NOT gate on the circuit board when called.
	     * This method is responsible for creating a NOT gate by calling the general
	     * generator method with specific parameters for the NOT gate.
	     * If an exception occurs during the generation process, it is caught and printed.
	     *
	     * @throws IOException Signals that an I/O exception has occurred.
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
	     * Generates a NOR gate on the circuit board when called.
	     * This method is responsible for creating a NOR gate by calling the general
	     * generator method with specific parameters for the NOR gate.
	     * If an exception occurs during the generation process, it is caught and printed.
	     *
	     * @throws IOException Signals that an I/O exception has occurred.
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
	     * Generates a NAND gate on the circuit board when called.
	     * This method is responsible for creating a NAND gate by calling the general
	     * generator method with specific parameters for the NAND gate.
	     * If an exception occurs during the generation process, it is caught and printed.
	     *
	     * @throws IOException Signals that an I/O exception has occurred.
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
	     * Generates an XOR gate on the circuit board when called.
	     * This method is responsible for creating an XOR gate by calling the general
	     * generator method with specific parameters for the XOR gate.
	     * If an exception occurs during the generation process, it is caught and printed.
	     *
	     * @throws IOException Signals that an I/O exception has occurred.
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
	     * Initiates the process of creating a wire connection when a mouse click and drag occurs on a terminal of a gate.
	     * Draws a temporary line on the screen from the terminal to the mouse cursor while the mouse is dragged.
	     * Records the starting node of the drag and the ending node of the drag.
	     * All terminal buttons are represented as invisible rectangles that trigger events when clicked.
	     * If both the start and stop of the drag are terminals, and the terminals are of different types,
	     * then the two nodes are passed to the function makeWire to create a wire connection.
	     *
	     * @param event The MouseEvent triggered when the user clicks on a terminal.
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
		
		/**
	     * Creates a visual wire connecting two terminals on the circuit board.
	     * The wire is drawn as a Line between the output and input terminals.
	     * Adjusts the Line's position based on the terminals' positions within their parent panes.
	     * Handles the logic for replacing old wires when new ones are created.
	     *
	     * @param outputTerminal The Rectangle representing the output terminal.
	     * @param inputTerminal  The Rectangle representing the input terminal.
	     */
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
			
			// Remove existing wires connected to outputTerminal
			if(outputTerminal.getProperties().get("wire")!= null) {
				circuitBoardPane.getChildren().remove(outputTerminal.getProperties().get("wire"));
				outputTerminal.getProperties().put("wire", null);

			}
			
			// Remove existing wires connected to inputTerminal
			if(inputTerminal.getProperties().get("wire")!= null) {
				circuitBoardPane.getChildren().remove(inputTerminal.getProperties().get("wire"));
				inputTerminal.getProperties().put("wire", null);

			}
			
			// Remove 'put' property from previous connections
			if(outputTerminal.getProperties().get("put") != null) {
				((Rectangle)outputTerminal.getProperties().get("put")).getProperties().put("put", null);
			}
			if(inputTerminal.getProperties().get("put") != null) {
				((Rectangle)inputTerminal.getProperties().get("put")).getProperties().put("put", null);

			}
			
			// Update properties to establish the wire connection between terminals
			outputTerminal.getProperties().put("wire", connectLine);
			outputTerminal.getProperties().put("put", inputTerminal);	
			
			inputTerminal.getProperties().put("wire", connectLine);
			inputTerminal.getProperties().put("put", outputTerminal);
			
			
		}
		
		
		/**
	     * Toggles the delete state and cursor style for deleting components on the circuit board.
	     * Plays a sound effect and changes the delete button's appearance based on the state.
	     *
	     * @param event The MouseEvent triggered by clicking the delete button.
	     */
		public void deleteButton(MouseEvent event) {
			// Play a sound effect
			audio.boopPlay();
			
			// Toggle the delete state and update the delete button's appearance
			if(deleteState) {
				// If deleteState is true, set it to false and update the button appearance
				deleteState = false;
				deleteImage.setImage(deleteOff);
				circuitBoardPane.setCursor(Cursor.DEFAULT);
			}
			
			else {
				// If deleteState is false, set it to true and update the button appearance
				deleteState = true;
				deleteImage.setImage(deleteOn);
				circuitBoardPane.setCursor(Cursor.CROSSHAIR);			
			}		
		}
		
		
		/**
	     * Handles the deletion of components in the LogicLand circuit board.
	     * Plays a sound effect and deletes either a wire connection or a gate based on the user's interaction.
	     *
	     * @param event The MouseEvent triggered by the user's interaction with the circuit board.
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
	     * Handles the "Back" button action event to take user back to the roadmap.
	     * Plays a sound effect and switches the scene to the roadmap.
	     *
	     * @param event The ActionEvent triggered by clicking the "Back" button.
	     * @throws IOException If an error occurs while switching scenes.
	     */
		public void roadMap(ActionEvent event) throws IOException {			
			audio.boopPlay();
			try {			
				sceneSwitcher.switchScene(event, "/application/resources/roadmap.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
		
		/**
	     * Handles the action event for going back to Part A of Level 3.
	     * This method plays a "boop" sound, then switches the scene to Level 3A.
	     *
	     * @param event The ActionEvent that triggers this method.
	     * @throws IOException If an I/O exception occurs while switching scenes.
	     */
		public void backPart(ActionEvent event) throws IOException {			
			audio.boopPlay();
			try {			
				sceneSwitcher.switchScene(event, "/application/resources/levels/level3A.fxml");
			}			
			catch(IOException exception) {				
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
	    	audio.boopPlay();
	    	try {
	            String previousLevelFXML = "/application/resources/levels/level3A.fxml";
	            sceneSwitcher.switchScene(event, previousLevelFXML);
	            audio.boopPlay();
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
	    	if(AccountManager.getLevelScore(AccountManager.getLevelID(3)) < 75) {
				return;
			}
	    	audio.boopPlay();
	    	//play level music
			MusicPlayer.stopMusic();
			MusicPlayer.playBackgroundMusic();
	    	try {
	            String nextLevelFXML = "/application/resources/levels/level4A.fxml";
	            sceneSwitcher.switchScene(event, nextLevelFXML);
	            audio.boopPlay();
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