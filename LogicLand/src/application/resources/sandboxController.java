/** sandboxController controls the user interface and some of the gate algorithms for making circuits and is the parent class of 
 * all levelControllers which control the circuits in the levels.
 * 
 * @author Andres Pedreros Castro
 * @author Callum Andrew Thompson
 * @author Nicholas Howard
 * @author Thomas Leonardo Llamzon
 * 
 * Credit where credit is due, some of the code for generating draggable nodes, as well as linking them with 
 * a line, was inspired by / borrowed from Joell Graff's tutorial "Drag-and-Drop in JavaFX" found
 * here https://monograff76.wordpress.com/2015/02/20/.
 * 
 * */


package application.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.AccountManager;
import application.Instructor;
import application.User;
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
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class sandboxController implements Initializable{
	
	
	
	// ----------------Variables ---------------------------------------
	Boolean deleteState = false;
	
	@FXML
	Pane circuitBoardPane;
	@FXML
	ImageView andGen;
	@FXML
	ImageView batteryGen;
	@FXML
	ImageView notGen;
	@FXML
	ImageView orGen;
	@FXML
	ImageView bulbGen;
	@FXML
	ImageView deleteImage;
	@FXML
	ImageView nandGen;
	@FXML
	ImageView norGen;
	@FXML
	ImageView xorGen;
	
	@FXML
	Text name;
		
	Boolean isTeacher = AccountManager.isAdmin();
	
	MultiMediaPlayer audio = new MultiMediaPlayer();
	
	static private ArrayList<Node> connectedTerminals = new ArrayList<>();

	
	//-------------Constants / Resources--------------------------------------------
	
	public enum Type{
		BATTERY, AND, OR, NOT, NOR, XOR, NAND, BULB		
	}
	Image deleteOn = new Image(getClass().getResourceAsStream("/application/resources/images/deleteOn.png"));
 	Image deleteOff = new Image(getClass().getResourceAsStream("/application/resources/images/deleteOff.png"));
	
	// --------------------Initializer-----------------------------------------
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			try {
				if(!AccountManager.isAdmin()) {
					if(AccountManager.getLevelScore(AccountManager.getLevelID(1)) < 75) {
						BoxBlur bb1 = new BoxBlur();
						bb1.setWidth(30);
				        bb1.setHeight(30);
				        bb1.setIterations(25);
					    andGen.setEffect(bb1);
					    andGen.setDisable(true);
					}
					if(AccountManager.getLevelScore(AccountManager.getLevelID(2)) < 75) {
						BoxBlur bb2 = new BoxBlur();
						bb2.setWidth(30);
				        bb2.setHeight(30);
				        bb2.setIterations(25);
					    orGen.setEffect(bb2);
					    orGen.setDisable(true);
					}
					if(AccountManager.getLevelScore(AccountManager.getLevelID(3)) < 75) {
						BoxBlur bb3 = new BoxBlur();
						bb3.setWidth(30);
				        bb3.setHeight(30);
				        bb3.setIterations(25);
					    notGen.setEffect(bb3);
					    notGen.setDisable(true);
					}
					if(AccountManager.getLevelScore(AccountManager.getLevelID(4)) < 75) {
						BoxBlur bb4 = new BoxBlur();
						bb4.setWidth(30);
				        bb4.setHeight(30);
				        bb4.setIterations(25);
					    nandGen.setEffect(bb4);
					    nandGen.setDisable(true);
					}
					if(AccountManager.getLevelScore(AccountManager.getLevelID(5)) < 75) {
						BoxBlur bb5 = new BoxBlur();
						bb5.setWidth(30);
				        bb5.setHeight(30);
				        bb5.setIterations(25);
					    norGen.setEffect(bb5);
					    norGen.setDisable(true);
					}
					if(AccountManager.getLevelScore(AccountManager.getLevelID(6)) < 75) {
						BoxBlur bb6 = new BoxBlur();
						bb6.setWidth(30);
				        bb6.setHeight(30);
				        bb6.setIterations(25);
					    xorGen.setEffect(bb6);
					    xorGen.setDisable(true);
					}
				}
				generateFromSave();
				if(AccountManager.isAdmin()) {
					  Instructor teacher = new Instructor(AccountManager.getCurrentUser());
					  name.setText("Created By: " + teacher.getName());
					  
				} else {
					User player = new User(AccountManager.getCurrentUser());
					name.setText("Created By: " + player.getUsername());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			andGen.setPickOnBounds(true);
			deleteImage.setPickOnBounds(true);
			
			// Delete is always bound to the click function but only works when deleteState is set to true by the delete button.
			circuitBoardPane.setOnMousePressed(event ->{this.delete(event);});
			
			circuitBoardPane.setOnKeyPressed(event ->{
				
				try {				
					
					
					switch(event.getCode()) {
					
					case Q:
							this.batteryGenerator();
						break;
					case W:
							if(AccountManager.isAdmin() || AccountManager.getLevelScore(AccountManager.getLevelID(1)) > 75) {
							this.andGenerator();}
						break;
					case E:
							if(AccountManager.isAdmin() || AccountManager.getLevelScore(AccountManager.getLevelID(2)) > 75) {
							this.orGenerator();}
						break;
					case R:
							if(AccountManager.isAdmin() ||AccountManager.getLevelScore(AccountManager.getLevelID(3)) > 75) {
							this.notGenerator();}
						break;
					case T:
							if(AccountManager.isAdmin() || AccountManager.getLevelScore(AccountManager.getLevelID(4)) > 75) {
							this.nandGenerator();}
						break;
					case Y:
							if(AccountManager.isAdmin() || AccountManager.getLevelScore(AccountManager.getLevelID(5)) > 75) {
							this.norGenerator();}
						break;
					case U:
							if(AccountManager.isAdmin() || AccountManager.getLevelScore(AccountManager.getLevelID(6)) > 75) {
							this.xorGenerator();}
						break;
					case I:
							this.bulbGenerator();
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
			
			
			
		}
		
		
	//-------------- Circuit Stuff ----------------------
		
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
		
	/** Gets the pane which is the root of the scene
	 * @return The Pane root of the scene*/
		
	public Pane getCircuitBoardPane() {
		
		return circuitBoardPane;
		
	}
	
	// ---------------- Object Generator Buttons -----------------------

	/** This is a button which generates all types of gate objects. The objects it generates are able to
	 * be dragged by the user and connected with each other. As Parameter is takes an emum that describes its type, a string which represents the location 
	 * of the fxml file for that particular gate object, and an object on the scene where to button to generate the gate is called origin. The new gate object will be 
	 * inserted into the scene just above the origin on the y axis.
	 * 
	 *  @param String - location of the new gates fxml file.
	 *  @param Type - an enum describing the type of gate.
	 *  @param ImageView origin - the object that serves as the button which the user uses to make new gates.
	 *  @throws IOException*/
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
			object.setLayoutY(origin.getLayoutY() - 100);
			object.setLayoutX(origin.getLayoutX());
		}		
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	/** generateFromSave is used to pull a file that represents a saved state of the sandbox
	 * from the database. I takes that file and automatically calls generator to re-make the objects.
	 * @see generator()
	 * @throws IO exception*/
	public void generateFromSave() throws IOException {
		if(AccountManager.getSandboxSaveState() == null) {
			return;
		}
		String saveState = AccountManager.getSandboxSaveState();
		while(!saveState.trim().isEmpty()) {
			String type = saveState.split(",")[0];
			String locationX = saveState.split(",")[1];
			String locationY = saveState.split(",")[2];
			double locationXD = Double.parseDouble(locationX);
			double locationYD = Double.parseDouble(locationY);
			String toRemove = type +","+ locationX +","+ locationY + ",";
			saveState = saveState.replace(toRemove, ""); 
			ImageView origin = new ImageView();
			origin.setLayoutX(locationXD);
			origin.setLayoutY(locationYD + 100);
			
			if(type.equals("l")) {
				try {
					this.generator("/application/resources/gates/bulb.fxml", Type.BULB, origin);
				}		
				catch(Exception e) {
					e.printStackTrace();
				}	
			} else if(type.equals("a")) {
				try {
					this.generator("/application/resources/gates/and.fxml", Type.AND, origin);
				}		
				catch(Exception e) {
					e.printStackTrace();
				}	
			} else if(type.equals("b")) {
				try {
					this.generator("/application/resources/gates/battery.fxml", Type.BATTERY, origin);
				}		
				catch(Exception e) {
					e.printStackTrace();
				}	
			} else if(type.equals("d")) {
				try {
					this.generator("/application/resources/gates/nand.fxml", Type.NAND, origin);
				}		
				catch(Exception e) {
					e.printStackTrace();
				}	
			} else if(type.equals("r")) {
				try {
					this.generator("/application/resources/gates/nor.fxml", Type.NOR, origin);
				}		
				catch(Exception e) {
					e.printStackTrace();
				}	
			} else if(type.equals("n")) {
				try {
					this.generator("/application/resources/gates/not.fxml", Type.NOT, origin);
				}		
				catch(Exception e) {
					e.printStackTrace();
				}	
			} else if(type.equals("o")) {
				try {
					this.generator("/application/resources/gates/or.fxml", Type.OR, origin);
				}		
				catch(Exception e) {
					e.printStackTrace();
				}	
			} else if(type.equals("x")) {
				try {
					this.generator("/application/resources/gates/xor.fxml", Type.XOR, origin);
				}		
				catch(Exception e) {
					e.printStackTrace();
				}	
			}
		}
	}
	/** andGenerator calls generator to make and gates.
	 * It is a wrapper class for generator. Wrapper classes were used so there could be an individual function for each button used to make each gate.
	 * @throws IOException */
	public void andGenerator() throws IOException {			
			
			try {
				this.generator("/application/resources/gates/and.fxml", Type.AND, andGen);
			}			
			catch(Exception e) {
				e.printStackTrace();
			}	
		}
	
	/** batteryGenerator calls generator to make and gates.
	 * It is a wrapper class for generator. Wrapper classes were used so there could be an individual function for each button used to make each gate.
	 * @throws IOException */	
	public void batteryGenerator() throws IOException{
		
		try {		
			this.generator("/application/resources/gates/battery.fxml", Type.BATTERY, batteryGen);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	/** orGenerator calls generator to make and gates.
	 * It is a wrapper class for generator. Wrapper classes were used so there could be an individual function for each button used to make each gate.
	 * @throws IOException */
	public void orGenerator() throws IOException{
			
			try {		
				this.generator("/application/resources/gates/or.fxml", Type.OR, orGen);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}	
	/** bulbGenerator calls generator to make and gates.
	 * It is a wrapper class for generator. Wrapper classes were used so there could be an individual function for each button used to make each gate.
	 * @throws IOException */
	public void bulbGenerator() throws IOException{
		
		try {		
			this.generator("/application/resources/gates/bulb.fxml", Type.BULB, bulbGen);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	/** notGenerator calls generator to make and gates.
	 * It is a wrapper class for generator. Wrapper classes were used so there could be an individual function for each button used to make each gate.
	 * @throws IOException */
	public void notGenerator() throws IOException{
			
			try {		
				this.generator("/application/resources/gates/not.fxml", Type.NOT, notGen);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	/** norGenerator calls generator to make and gates.
	 * It is a wrapper class for generator. Wrapper classes were used so there could be an individual function for each button used to make each gate.
	 * @throws IOException */
	public void norGenerator() throws IOException{
		
		try {		
			this.generator("/application/resources/gates/nor.fxml", Type.NOR, norGen);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}	
	/** nandGenerator calls generator to make and gates.
	 * It is a wrapper class for generator. Wrapper classes were used so there could be an individual function for each button used to make each gate.
	 * @throws IOException */
	public void nandGenerator() throws IOException{
		
		try {		
			this.generator("/application/resources/gates/nand.fxml", Type.NAND, nandGen);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}	
	/** xorGenerator calls generator to make and gates.
	 * It is a wrapper class for generator. Wrapper classes were used so there could be an individual function for each button used to make each gate.
	 * @throws IOException */
	public void xorGenerator() throws IOException{
		
		try {		
			this.generator("/application/resources/gates/xor.fxml", Type.XOR, xorGen);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}	

	
	
	//--------------Object Interaction Functions -------------------------
	
	/**beginConnection is called when a click is made on any terminal of a gate and the mouse is dragged,
	 *  beginConnection draws a line on the screen from a terminal to the mouse while the mouse is dragged.
	 * It also records the starting node of the drag, and the ending node of the drag.
	 * All terminal buttons are invisible rectangles that trigger events when clicked.
	 * If both the start and stop of the drag are terminals, and the terminals are of different types,
	 *   then the two nodes are passed to the function makeWire. 
	 *   
	 *   @param MouseEvent event - the event that represents the mouse click on a terminal of a gate object*/
	public void beginConnection(MouseEvent event) {
		
		
		// Store the start node information while the line is being drawn.
		Rectangle startNode = (Rectangle) event.getSource();
		String startType =((String)((Rectangle) event.getSource()).getProperties().get("type"));
		
		// Calculate the center of the Rectangle relative to the scene.
		double startX = startNode.localToScene(startNode.getBoundsInLocal()).getMinX() + startNode.getWidth() / 1.5;
		double startY = startNode.localToScene(startNode.getBoundsInLocal()).getMinY() + startNode.getHeight() / 2;

		
		// Add the temporary line to the pane.
		Line connectLine = new Line(startX, startY, startX, startY);
		connectLine.setStrokeWidth(5);
		circuitBoardPane.getChildren().add(connectLine);
		
		
		// This draws the line as the user makes the connection
		// +5 to make line not directly under the cursor so we can check what object it is hovering over when click is released.
		circuitBoardPane.setOnMouseDragged(event1 ->{			
			connectLine.setEndX(event1.getSceneX());
			connectLine.setEndY(event1.getSceneY());
			connectLine.toBack();
			
		});
		
		circuitBoardPane.setOnMouseReleased(event2 ->{
			
			// Remove the temporary line when mouse released
			circuitBoardPane.getChildren().remove(connectLine);
			audio.boopPlay();//Play sound on connection doesnt work for every connection
			
			// If the mouse was released over a terminal
			if(event2.getPickResult().getIntersectedNode() instanceof Rectangle) {
				Rectangle endNode = (Rectangle) event2.getPickResult().getIntersectedNode();
				String endType = (String) endNode.getProperties().get("type");
				if(startType != endType) {
					
					// Check which is the output to match the parameter order of makeWire.
					if (startType == "output") {
						
						this.makeWire(startNode, endNode);
						audio.wirePlay();
						
						switch( (String)(startNode.getProperties().get("ClassType")) ) {
						case "AND":
							((andController)(startNode.getProperties().get("parentGate"))).checktype();			//Andres
							break;
						case "BATTERY":
							((batteryController)startNode.getProperties().get("parentGate")).checktype();			//Andres
							break;
						case "BULB":
							((bulbController)startNode.getProperties().get("parentGate")).checktype();
							break;
						case "NAND":
							((nandController)startNode.getProperties().get("parentGate")).checktype();
							break;
						case "NOR":
							((norController)startNode.getProperties().get("parentGate")).checktype();
							break;
						case "XOR":
							((xorController)startNode.getProperties().get("parentGate")).checktype();
							break;
						case "NOT":
							((notController)startNode.getProperties().get("parentGate")).checktype();
							break;
						case "OR":
							((orController)startNode.getProperties().get("parentGate")).checktype();
							break;
							
			
					
						
						
						}
						
						
						
					
					}
					
					else {
						
						this.makeWire(endNode, startNode);
						audio.wirePlay();
				
						switch( (String)(endNode.getProperties().get("ClassType")) ) {
						case "AND":
							((andController)(endNode.getProperties().get("parentGate"))).checktype();			//Andres
							break;
						case "BATTERY":
							((batteryController)endNode.getProperties().get("parentGate")).checktype();			//Andres
							break;
						case "BULB":
							((bulbController)endNode.getProperties().get("parentGate")).checktype();
							break;
						case "NAND":
							((nandController)endNode.getProperties().get("parentGate")).checktype();
							break;
						case "NOR":
							((norController)endNode.getProperties().get("parentGate")).checktype();
							break;
						case "XOR":
							((xorController)endNode.getProperties().get("parentGate")).checktype();
							break;
						case "NOT":
							((notController)endNode.getProperties().get("parentGate")).checktype();
							break;
						case "OR":
							((orController)endNode.getProperties().get("parentGate")).checktype();
							break;
						}
						
						
					}
				}
			}
			else {
				// Release the mouse binding if the mouse was not release over a terminal.
				circuitBoardPane.setOnMouseReleased(null);				
			}
		});
	}
	
	/**Make wire is called by begin connection when a user has drawn a line between two valid terminals signaling that they want to have them connected by a wire.
	 * This draws a persistent wire between two valid terminals a user has connected with beginConnection.
	 * The end points of the line that makes the connection is bound to the x, y coordinates of the terminal nodes. So when they move the line is moved as well.
	 * To find the instance gates the terminals belong to use *terminal*.getParent().getProperties().get("controller") 
	 * 
	 * @param Rectangle output terminal - the object that represents the output of a circuit object
	 * @param Rectangle input terminal - the object that represents the input of a gate
	 * 
	 * Credit to Joell Graff and his tutorial "Drag-and-Drop in JavaFX" for the idea of binding the end points of the wire to the x y 
	 * coordinates of the terminals to make a persistent connection. The code in this method was inspired by / borrowed from that tutorial.
	 * */
	
	
	public void makeWire(Rectangle outputTerminal, Rectangle inputTerminal) {		
		
		// Get the parent node of the terminal, the gate, so I can figure out the absolute position of the terminal. 
		// outputTerminal.layoutXProperty() only give the relative x,y coordiniates inside the gate
		Pane outputPane = (Pane) outputTerminal.getParent();
		Pane inputPane = (Pane)inputTerminal.getParent();
		Line connectLine = new Line();
		connectLine.setStrokeWidth(5);
		
		// Set the bindings. These four lines are Kinda copied code. Need to document or change.
		connectLine.startXProperty().bind(outputTerminal.layoutXProperty().add(outputPane.layoutXProperty().add(outputTerminal.getBoundsInParent().getWidth() / 1.5 )));
		connectLine.startYProperty().bind(outputTerminal.layoutYProperty().add(outputPane.layoutYProperty().add(outputTerminal.getBoundsInParent().getHeight() / 2)));

		connectLine.endXProperty().bind(inputTerminal.layoutXProperty().add(inputPane.layoutXProperty().add(inputTerminal.getBoundsInParent().getWidth() / 4)));
		connectLine.endYProperty().bind(inputTerminal.layoutYProperty().add(inputPane.layoutYProperty().add(inputTerminal.getBoundsInParent().getHeight() / 2)));
		
		connectLine.getProperties().put("connection1",outputTerminal);
		connectLine.getProperties().put("connection2", inputTerminal);
		
		
		// Add to the pane and push behind the gates so the user can click the terminal again.
		circuitBoardPane.getChildren().add(connectLine);
		connectLine.toBack();
		
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
	
	/**  deleteButon switched the image of the trash can that represents the deleted function
	 * cyclically from the color black to the color red on each user click.
	 *  @param MouseEvent event - the object that represent the mouse click of the user*/
	public void deleteButton(MouseEvent event) {
		
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
	/** Delete is the function that actually removes gate objects from the jave fx scene.
	 * This function is bound to the mouse clicks at all times but only executes when the delete variable is set to true,
	 * which is done when the user clicks the delete icon.
	 * 
	 *  @param MouseEvent - the location of a mouse click*/
	public void delete(MouseEvent event) {
				
		if(deleteState && ((Node) event.getSource()).getLayoutY() < 600) {
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
				double locationX = (event.getPickResult().getIntersectedNode().getParent().getLayoutX());
				double locationY = (event.getPickResult().getIntersectedNode().getParent().getLayoutY());
				String[] codes = {"a", "b", "l", "d", "r", "n", "o", "x"};
				int i = 0;
				String toRemove = "";
				do {
					if(i == 8) {
						break;
					}
					AccountManager.removeOldPosition(locationX, locationY, codes[i]);
					toRemove = codes[i] +","+ locationX +","+ locationY + ",";
					i++;
				} while(AccountManager.getSandboxSaveState().replace(toRemove, "") != "");
				circuitBoardPane.getChildren().remove(((Node) event.getPickResult().getIntersectedNode().getParent()));
			}
		}
	}
	/** This function removes all user generated objects from the screen.
	 * 
	 * @param ActionEvent Event - the click on the clear all button*/	
	public void clearAll(ActionEvent event) {
		AccountManager.setSandboxSaveState("");
		try {
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/sandbox.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}	
	}
		
	
	
	// ----------------UserDashboard Button Functions -----------------------
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	/** This is a button that moves the user to different screen within the game.
	 * @param ActionEvent event - the mouse click on the button.
	 * @throws I0Exception - if the file representing the new screen, or any of it's dependencies,  is not found.*/
	public void roadmap(ActionEvent event) throws IOException {			
		try {
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/roadmap.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	/** This is a button that moves the user to different screen within the game.
	 * @param ActionEvent event - the mouse click on the button.
	 * @throws I0Exception - if the file representing the new screen, or any of it's dependencies, is not found.*/
	public void sandbox(ActionEvent event) throws IOException {			
		try {
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/sandbox.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	/** This is a button that moves the user to different screen within the game.
	 * @param ActionEvent event - the mouse click on the button.
	 * @throws I0Exception - if the file representing the new screen, or any of it's dependencies, is not found.*/
	public void highscore(ActionEvent event) throws IOException {			
		try {	
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/highscore.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	/** This is a button that moves the user to different screen within the game.
	 * @param ActionEvent event - the mouse click on the button.
	 * @throws I0Exception - if the file representing the new screen, or any of it's dependencies, is not found.*/
	public void discoveries(ActionEvent event) throws IOException {			
		try {	
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/discoveries.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	/** This is a button that moves the user to different screen within the game.
	 * @param ActionEvent event - the mouse click on the button.
	 * @throws I0Exception - if the file representing the new screen, or any of it's dependencies, is not found.*/
	public void options(ActionEvent event) throws IOException {			
		try {	
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/options.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	
	/*---SANDBOX OPTIONS HOVER EFFECTS---*/
	
	//Battery Hover
	/** This is a function that increases the size of an icon
	 * when the mouse enters its bounds.*/
	public void onBatteryHover() {
		batteryGen.setScaleX(1.1);
		batteryGen.setScaleY(1.1);
	}
	/** This is a function that decreases the size of an icon
	 * when the mouse exits its bounds.*/
	public void offBatteryHover() {
		batteryGen.setScaleX(0.9);
		batteryGen.setScaleY(0.9);
	}

	// AND HOVER
	/** This is a function that increases the size of an icon
	 * when the mouse enters its bounds.*/
	public void onANDHover() {
		andGen.setScaleX(1.1);
		andGen.setScaleY(1.1);
	}
	/** This is a function that decreases the size of an icon
	 * when the mouse exits its bounds.*/
	public void offANDHover() {
		andGen.setScaleX(0.9);
		andGen.setScaleY(0.9);
	}

	// OR HOVER
	/** This is a function that increases the size of an icon
	 * when the mouse enters its bounds.*/
	public void onORHover() {
		orGen.setScaleX(1.1);
		orGen.setScaleY(1.1);
	}
	/** This is a function that decreases the size of an icon
	 * when the mouse exits its bounds.*/
	public void offORHover() {
		orGen.setScaleX(0.9);
		orGen.setScaleY(0.9);
	}

	// NOT HOVER
	/** This is a function that increases the size of an icon
	 * when the mouse enters its bounds.*/
	public void onNOTHover() {
		notGen.setScaleX(1.1);
		notGen.setScaleY(1.1);
	}
	/** This is a function that decreases the size of an icon
	 * when the mouse exits its bounds.*/
	public void offNOTHover() {
		notGen.setScaleX(0.9);
		notGen.setScaleY(0.9);
	}

	// NAND HOVER
	/** This is a function that increases the size of an icon
	 * when the mouse enters its bounds.*/
	public void onNANDHover() {
		nandGen.setScaleX(1.1);
		nandGen.setScaleY(1.1);
	}
	/** This is a function that decreases the size of an icon
	 * when the mouse exits its bounds.*/
	public void offNANDHover() {
		nandGen.setScaleX(0.9);
		nandGen.setScaleY(0.9);
	}

	// NOR HOVER
	/** This is a function that increases the size of an icon
	 * when the mouse enters its bounds.*/
	public void onNORHover() {
		norGen.setScaleX(1.1);
		norGen.setScaleY(1.1);
	}
	/** This is a function that decreases the size of an icon
	 * when the mouse exits its bounds.*/
	public void offNORHover() {
		norGen.setScaleX(0.9);
		norGen.setScaleY(0.9);
	}

	// XOR HOVER
	/** This is a function that increases the size of an icon
	 * when the mouse enters its bounds.*/
	public void onXORHover() {
		xorGen.setScaleX(1.1);
		xorGen.setScaleY(1.1);
	}
	/** This is a function that decreases the size of an icon
	 * when the mouse exits its bounds.*/
	public void offXORHover() {
		xorGen.setScaleX(0.9);
		xorGen.setScaleY(0.9);
	}

	// BULB HOVER
	/** This is a function that increases the size of an icon
	 * when the mouse enters its bounds.*/
	public void onBULBHover() {
		bulbGen.setScaleX(1.1);
		bulbGen.setScaleY(1.1);
	}
	/** This is a function that decreases the size of an icon
	 * when the mouse exits its bounds.*/
	public void offBULBHover() {
		bulbGen.setScaleX(0.9);
		bulbGen.setScaleY(0.9);
	}
	
	// TRASH HOVER
	/** This is a function that increases the size of an icon
	 * when the mouse enters its bounds.*/
	public void onTRASHHover() {
		deleteImage.setScaleX(1.1);
		deleteImage.setScaleY(1.1);
	}
	/** This is a function that decreases the size of an icon
	 * when the mouse exits its bounds.*/
	public void offTRASHHover() {
		deleteImage.setScaleX(0.9);
		deleteImage.setScaleY(0.9);
	}
}
