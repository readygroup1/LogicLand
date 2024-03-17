package application.resources.levels;

import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;

import application.resources.SceneSwitcher;
import application.resources.sandboxController;
import application.resources.gates.andController;
import application.resources.gates.batteryController;
import application.resources.gates.bulbController;
import application.resources.gates.gateObject;
import application.resources.levels.LevelControllerTemplate.Type;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Level1BController extends sandboxController implements Initializable {

	// ----------------Variables ---------------------------------------
		public Boolean deleteState = false;
		
		@FXML
		Pane circuitBoardPane;
		@FXML
		Button back;
		
		//Uncomment @FXML above any generator/delete objects that you want to include in the level and make sure the names match in the fxml file
		//@FXML
		ImageView andGen;
		//@FXML
		ImageView batteryGen;
		//@FXML
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
		@FXML
		Pane battery2;
		@FXML 
		Pane and1;
		
		@FXML
		Pane bulb;
		
		//-------------Constants / Resources--------------------------------------------
		
		public enum Type{
			BATTERY, AND, OR, NOT, NOR, XOR, NAND, BULB		
		}
		
		Map<Type, String> fxmlPath = new EnumMap<>(Type.class);
		
		
		Image deleteOn = new Image(getClass().getResourceAsStream("/application/resources/images/deleteOn.png"));
	 	Image deleteOff = new Image(getClass().getResourceAsStream("/application/resources/images/deleteOff.png"));
		
		// --------------------Initializer-----------------------------------------
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
			 
			try {
				and1 = this.load(and1, Type.AND);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			// Create function calls to make wire if you want connections. There is definitely a better way to do this. I will work on it.
			this.makeWire(((batteryController)battery1.getProperties().get("controller")).getOutput1(), ((andController) and1.getProperties().get("controller")).getInput1());
			this.makeWire(((batteryController)battery2.getProperties().get("controller")).getOutput1(), ((andController) and1.getProperties().get("controller")).getInput2());
			this.makeWire(((andController) and1.getProperties().get("controller")).getOutput(), ( (bulbController) bulb.getProperties().get("controller")).getInput1());
			
			
		}
		//----------------Getter and Setter Functions ---------------------
		
		public Pane getCircuitBoardPane() {
			
			return circuitBoardPane;
			
		}
		
		//------------------Object Pre-Load Functions-----------------------
		// use these to pre-load up objects into the level, not object generator.
		
		public Pane load(Pane origin,  Type type) throws IOException{
			try {
				
				// Create the object and set up the properties
				FXMLLoader loader = new FXMLLoader(getClass().getResource((String) fxmlPath.get(type)));
				Pane object = loader.load();
				gateObject controller = loader.getController();
				object.getProperties().put("controller", controller);
				object.getProperties().put("type", type);
				controller.setBoard(this);
				
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
		
		// Use these to let the user add objects to the level

		/** This is the button that generates gatesObjects. The first in the block of code where all the object generator will be.
		 * In the user interface, every gate is represented as a node. Nodes are what will be used to pass information from the 
		 * user interface events to the sandboxController.
		 * To get information from a node use .getProperties.get(KEY).
		 * I set two keys below. "controller" will be a unique instance of the gate object. 
		 * You can use "andController ctl = node..getProperties.get("controller);" to retrieve the controller.
		 * Then you would be to call any function andControllers have  it like ctl.getState().
		 * I also set this instance of the sandboxController in every gate that is created. That may come in useful to pass information
		 * to a central source.*/
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
				object.setLayoutY(origin.getLayoutY() - 100);
				object.setLayoutX(origin.getLayoutX());
			}		
			catch(Exception e) {
				e.printStackTrace();
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
						}
						
						else {
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
			
			// Add to the pane and push behind the gates so the user can click the terminal again.
			circuitBoardPane.getChildren().add(connectLine);
			connectLine.setViewOrder(0);
			
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
			//System.out.println("deleteState: " + deleteState + ", X: " + event.getX() + ", Y: " +event.getY());
			if(deleteState && event.getY() < 570 && event.getY() > 155 && event.getX() < 1000 && event.getX() > 260 ){
				//If it is wire
				if(event.getPickResult().getIntersectedNode() instanceof Line) {
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
		
		public void back(ActionEvent event) throws IOException {			
			try {			
				sceneSwitcher.switchScene(event, "/application/resources/roadmap.fxml");
			}			
			catch(IOException exception) {				
				exception.printStackTrace();				
			}		
		}
}		