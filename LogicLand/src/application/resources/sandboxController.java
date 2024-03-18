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
	
	audioPlayer audio = new audioPlayer();
	
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
	
	public Pane getCircuitBoardPane() {
		
		return circuitBoardPane;
		
	}
	
	// ---------------- Object Generator Buttons -----------------------

	/** This is the button that generates gatesObjects. The first in the block of code where all the object generator will be.
	 * In the user interface, every gate is represented as a node. Nodes are what will be used to pass information from the 
	 * user interface events to the sandboxController.
	 * To get information from a node use .getProperties.get(KEY).
	 * I set two keys below. "controller" will be a unique instance of the gate object. 
	 * You can use "andController ctl = node..getProperties.get("controller);" to retreive the controller.
	 * Then you would be to call any function andControllers have  it like ctl.getState().
	 * I also set this instance of the sandboxController in every gate that is created. That may come in useful to pass information
	 * to a central source. */
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
			object.setLayoutY(origin.getLayoutY() - 100);
			object.setLayoutX(origin.getLayoutX());
		}		
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
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
	
	public void andGenerator() throws IOException {			
			
			try {
				this.generator("/application/resources/gates/and.fxml", Type.AND, andGen);
			}			
			catch(Exception e) {
				e.printStackTrace();
			}	
		}
	
		
	public void batteryGenerator() throws IOException{
		
		try {		
			this.generator("/application/resources/gates/battery.fxml", Type.BATTERY, batteryGen);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void orGenerator() throws IOException{
			
			try {		
				this.generator("/application/resources/gates/or.fxml", Type.OR, orGen);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}	
	
	public void bulbGenerator() throws IOException{
		
		try {		
			this.generator("/application/resources/gates/bulb.fxml", Type.BULB, bulbGen);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void notGenerator() throws IOException{
			
			try {		
				this.generator("/application/resources/gates/not.fxml", Type.NOT, notGen);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	
	public void norGenerator() throws IOException{
		
		try {		
			this.generator("/application/resources/gates/nor.fxml", Type.NOR, norGen);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	public void nandGenerator() throws IOException{
		
		try {		
			this.generator("/application/resources/gates/nand.fxml", Type.NAND, nandGen);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	public void xorGenerator() throws IOException{
		
		try {		
			this.generator("/application/resources/gates/xor.fxml", Type.XOR, xorGen);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}	

	
	
	//--------------Object Interaction Functions -------------------------
	
	/** When a click is made on any terminal of a gate and the mouse is dragged, beginConnection draws a line on the screen from a terminal to the mouse while the mouse is dragged.
	 * It also records the starting node of the drag, and the ending node of the drag.
	 * All terminal buttons are invisible rectangles that trigger events when clicked.
	 * If both the start and stop of the drag are terminals, and the terminals are of different types,
	 *   then the two nodes are passed to the function makeWire. */
	public void beginConnection(MouseEvent event) {
		
		
		
		
		
		// Store the start node information while the line is being drawn.
		Rectangle startNode = (Rectangle) event.getSource();
		String startType =((String)((Rectangle) event.getSource()).getProperties().get("type"));
		
		// Calculate the center of the Rectangle relative to the scene.
		double startX = startNode.localToScene(startNode.getBoundsInLocal()).getMinX() + startNode.getWidth() / 2;
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
			
			// If the mouse was released over a terminal
			if(event2.getPickResult().getIntersectedNode() instanceof Rectangle) {
				Rectangle endNode = (Rectangle) event2.getPickResult().getIntersectedNode();
				String endType = (String) endNode.getProperties().get("type");
				if(startType != endType) {
					
					// Check which is the output to match the parameter order of makeWire.
					if (startType == "output") {
						endNode.getProperties().put("put", startNode);	//Andres
						startNode.getProperties().put("put", endNode);
						
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
						
						
						this.makeWire(startNode, endNode);
					
					}
					
					else {
						
						
						
						endNode.getProperties().put("put", startNode);	//Andres
						startNode.getProperties().put("put", endNode);
						
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
						
						this.makeWire(endNode, startNode);
					}
				}
			}
			else {
				// Release the mouse binding if the mouse was not release over a terminal.
				circuitBoardPane.setOnMouseReleased(null);				
			}
		});
	}
	
	/**This is probably where you will want to add code to manage the connections.
	 * This draws a persistent wire between two valid terminals a user has connected with beginConnection.
	 * The endpoints of the line that makes the connection is bound to the terminal nodes. So when they move the line is moved as well.
	 * To find the instance gates the terminals belong to use *terminal*.getParent().getProperties().get("controller") */
	
	
	public void makeWire(Rectangle outputTerminal, Rectangle inputTerminal) {		
		
		// Get the parent node of the terminal, the gate, so I can figure out the absolute position of the terminal. 
		// outputTerminal.layoutXProperty() only give the relative x,y coordiniates inside the gate
		Pane outputPane = (Pane) outputTerminal.getParent();
		Pane inputPane = (Pane)inputTerminal.getParent();
		Line connectLine = new Line();
		connectLine.setStrokeWidth(5);
		
		// Set the bindings. These four lines are Kinda copied code. Need to document or change.
		connectLine.startXProperty().bind(outputTerminal.layoutXProperty().add(outputPane.layoutXProperty().add(outputTerminal.getBoundsInParent().getWidth() / 2)));
		connectLine.startYProperty().bind(outputTerminal.layoutYProperty().add(outputPane.layoutYProperty().add(outputTerminal.getBoundsInParent().getHeight() / 2)));

		connectLine.endXProperty().bind(inputTerminal.layoutXProperty().add(inputPane.layoutXProperty().add(inputTerminal.getBoundsInParent().getWidth() / 2)));
		connectLine.endYProperty().bind(inputTerminal.layoutYProperty().add(inputPane.layoutYProperty().add(inputTerminal.getBoundsInParent().getHeight() / 2)));
		
		connectLine.getProperties().put("connection1",outputTerminal);
		connectLine.getProperties().put("connection2", inputTerminal);
		
		
		// Add to the pane and push behind the gates so the user can click the terminal again.
		circuitBoardPane.getChildren().add(connectLine);
		connectLine.toBack();
		
		// Release the mouse binding from beginConnection so the line isn't created again.
		circuitBoardPane.setOnMouseReleased(null);
		
	}
	
	
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
	
	public void roadmap(ActionEvent event) throws IOException {			
		try {
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/roadmap.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void sandbox(ActionEvent event) throws IOException {			
		try {
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/sandbox.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void highscore(ActionEvent event) throws IOException {			
		try {	
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/highscore.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void discoveries(ActionEvent event) throws IOException {			
		try {	
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/discoveries.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}
	
	public void options(ActionEvent event) throws IOException {			
		try {	
			audio.boopPlay();
			sceneSwitcher.switchScene(event, "/application/resources/options.fxml");
		}			
		catch(IOException exception) {				
			exception.printStackTrace();				
		}		
	}

	

}
