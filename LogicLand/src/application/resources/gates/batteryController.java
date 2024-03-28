package application.resources.gates;

import java.net.URL;
import java.util.ResourceBundle;

import application.AccountManager;
import application.resources.sandboxController;
import application.resources.gates.gateObject.Type;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class batteryController extends gateObject implements Initializable{
	
	//----------------Variables-------------------
		boolean state = false;
		sandboxController sboxController;
		Pane circuitBoardPane;
		Type type = gateObject.Type.battery;
		
		boolean dragged = false;
		Double dragStartX;
		Double dragStartY;
		
		@FXML
		Ellipse body;
		@FXML
		Rectangle output1;
		@FXML
		Rectangle output;	
		@FXML
		Pane pane;
		@FXML
		ImageView batteryImage;
		
		String saveState = ""; //battery has code 'b'
		
		Image batteryOn = new Image(getClass().getResourceAsStream("/application/resources/images/batteryOn.png"));
	 	Image batteryOff = new Image(getClass().getResourceAsStream("/application/resources/images/batteryOff.png")); 
		
		
		
		//----------------Initializer------------------
		
	 	/**
	 	 * Initializes the gate to be draggable and sets properties on the terminals.
	 	 * The method sets up event handlers for mouse press and drag events to enable
	 	 * dragging of the gate. It also initializes properties on the output and output1 terminals,
	 	 * such as type, state, parentGate, put, and ClassType.
	 	 * 
	 	 *
	 	 * @param arg0 The URL location of the FXML file.
	 	 * @param arg1 The ResourceBundle for localization.
	 	 */
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			super.setBody(body);
			
			body.setOnMousePressed(event ->{			
				 dragStartX = event.getSceneX() - pane.getLayoutX();
				 dragStartY = event.getSceneY() - pane.getLayoutY();
			});
			
			body.setOnMouseDragged(event -> {
				AccountManager.removeOldPosition(pane.getLayoutX(), pane.getLayoutY(), "b");
				pane.setLayoutX(event.getSceneX() - dragStartX);
				pane.setLayoutY(event.getSceneY() - dragStartY);
				dragged = true;
				saveState = "b," + Double.toString(pane.getLayoutX()) + "," + Double.toString(pane.getLayoutY()) + ",";
				AccountManager.setIndividualGate(saveState);
			});
			
			output1.getProperties().put("type", "output");
			output1.getProperties().put("state", false);
			output1.getProperties().put("parentGate", this);//Andres
			output1.getProperties().put("put", null);
			output1.getProperties().put("ClassType", "BATTERY");
			output1.getProperties().put("wire", null);
			
			output.getProperties().put("type", "output");
			output.getProperties().put("state", false);
			output.getProperties().put("parentGate", this);//Andres
			output.getProperties().put("put", null);
			output.getProperties().put("ClassType", "BATTERY");
			output.getProperties().put("wire", null);
			
			body.getProperties().put("type", "body");
		}
		
		
		//----------------Buttons------------
		
		/**
		 * Initiates a connection from the terminal button by passing the MouseEvent event
		 * to the sandboxController's beginConnection method.
		 * This method is designed to be used as a button action for starting a connection
		 * process from a terminal.
		 *
		 * @param event The MouseEvent that triggers the start of the connection process.
		 *            
		 */
		public void startConnection(MouseEvent event) {
			
			sboxController.beginConnection(event);
			
		}
		
		/**
		 * Calls the checktype method on the given node based on its ClassType property.
		 * This method is used to invoke the checktype method of the appropriate gate controller
		 * based on the type of the node. The ClassType property of the node determines which
		 * specific gate controller's checktype method will be called.
		 *
		 * @param node The Rectangle node for which the checktype method is to be called.
		 *         
		 *             
		 */
		public void callChecktype(Rectangle node) {
			switch( (String)(node.getProperties().get("ClassType")) ) {
			case "AND":
				((andController)(node.getProperties().get("parentGate"))).checktype();			
				break;
			case "BATTERY":
				((batteryController)node.getProperties().get("parentGate")).checktype();		
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
		
		/**
		 * Changes the state of the battery when it is clicked, but only if the battery was not previously dragged.
		 * If the battery is currently off, clicking it turns it on and updates associated properties.
		 * If the battery is currently on, clicking it turns it off and updates associated properties.
		 * This method also checks for objects with the key "put" in the properties of output and output1,
		 * and calls the {@link #callChecktype(Rectangle)} method with the appropriate Rectangle object.
		 * This method is typically triggered by a MouseEvent.
		 *
		 * @param event The MouseEvent that triggers the battery click action.
		 */
		public void batteryClick(MouseEvent event) {
			if(!dragged) {
				if(state) {
		 	 		batteryImage.setImage(batteryOff);
		 	 		state = false;
		 	 		output1.getProperties().put("state", false);
		 	 		output.getProperties().put("state", false);
		 	 		if(output.getProperties().get("put") != null) {
		 	 			
		 	 			callChecktype((Rectangle)output.getProperties().get("put"));		 	 				 	 			
		 	 		}
		 	 		if(output1.getProperties().get("put") != null) {
		 	 			
		 	 			callChecktype((Rectangle)output1.getProperties().get("put"));		 	 				 	 			
		 	 		}
		 	 		
		 		}
		 		else {			 			
		 	 		batteryImage.setImage(batteryOn);			 	 		
		 	 		state = true;
		 	 		output1.getProperties().put("state", true);
		 	 		output.getProperties().put("state", true);
		 	 		if(output.getProperties().get("put") != null) {
		 	 			
		 	 			
		 	 			callChecktype((Rectangle)output.getProperties().get("put"));	 			
		 	 		}
		 	 		if(output1.getProperties().get("put") != null) {
		 	 			
		 	 			callChecktype((Rectangle)output1.getProperties().get("put"));	 	 			
		 	 			
		 	 			
		 	 			
		 	 		}
		 		}		
			}							
			dragged = false;
		}
		
		
		
		/**
		 * Checks the type of objects stored in the properties of the output and output1 variables.
		 * If an object with the key "put" is found in the properties of either output or output1,
		 * it calls the {@link #callChecktype(Rectangle)} method with the casted Rectangle object.
		 * This method is useful for determining the type of objects stored and taking appropriate actions.
		 */
		public void checktype() {
			if(output.getProperties().get("put") != null) {
 	 			 	 			
				callChecktype((Rectangle)output.getProperties().get("put"));
 	 				 	 			
 	 		}
 	 		if(output1.getProperties().get("put") != null) {
 	 			
 	 			callChecktype((Rectangle)output1.getProperties().get("put")); 				 	 			
 	 		}
		}
		
		//----------------Getters & Setters-----------
		
		/**
		 * This function is used by the sandbox Controller to set instance variables of the sandboxPane and sandboxController
		 * in every gate that is created. It is helpful for communicating events back to a central source.
		 * You can call any function in the sandbox controller on this instance.
		 * The board instance can be used to draw things on the screen from a gate if needed.
		 *
		 * @param board The sandboxController instance to set.
		 */
		public void setBoard (sandboxController board) {
			sboxController = board;
			circuitBoardPane = sboxController.getCircuitBoardPane();
		}
		
		
		/**
		 * Gets the state of the battery.
		 *
		 * @return The state of battery.
		 */
		public Boolean getState() {
			return state;
		}
		
		
		/**
		 * Sets the state of the batter to the input state
		 * passed in to the method
		 * 
		 * @param an input state of boolean type
		 *
		 * @return The state of battery.
		 */
		public void setState(Boolean inputState) {
			this.state = inputState;		
		}
		
		
		/**
		 * Gets the type of the battery.
		 *
		 * @return The Type of battery.
		 */
		public Type getType() {
			return type;
		}
		
		/**
		 * Gets the first output rectangle of the battery.
		 *
		 * @return The first output rectangle.
		 */
		public Rectangle getOutput1() {
			return output1;		
		}
		
		/**
		 * Gets the second output rectangle of the battery.
		 *
		 * @return The second output rectangle.
		 */
		public Rectangle getOutput2() {
			return output;		
		}

}
