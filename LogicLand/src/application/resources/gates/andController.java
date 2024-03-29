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


/**
 * Generators in Sandbox and Levels create instances of AND gates. This class then controls the AND logic cases of those gates, while also handling dragging and dropping.
 * 
 * @see sandboxController
 * @version 1.0
 * @since 1.0
 * @author Group 1
 */

public class andController extends gateObject implements Initializable {
	
	//----------------Variables-------------------
	boolean state = false;
	boolean firstInput = false;
	boolean secondInput = false;
	
	
	sandboxController sboxController;
	Pane circuitBoardPane;
	Type type = Type.and;
	
	@FXML
	ImageView andImage;
	@FXML
	Ellipse body;
	@FXML
	Rectangle input1;
	@FXML
	Rectangle input2;
	@FXML
	Rectangle output;	
	@FXML
	Pane pane;
	
	Double dragStartX;
	Double dragStartY;
	
	String saveState = ""; //and has code 'a'
	
	//---------------Resources------------------------------
	
	Image andOff = new Image(getClass().getResourceAsStream("/application/resources/images/and.png"));
 	Image andOn = new Image(getClass().getResourceAsStream("/application/resources/images/andOn.png")); 
	
	//----------------Initializer---------------------------
	
 	/**
 	 * Initializes the gate to be draggable and sets node properties on the terminals.
 	 * Every gate and terminal will have properties such as type that can be accessed using
 	 * (*Any Node*).getProperties().get("type").
 	 *
 	 * @param arg0 The URL used for initialization.
 	 * @param arg1 The ResourceBundle used for initialization.
 	 * 
 	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		super.setBody(body);
		
		body.setOnMousePressed(event ->{			
			 dragStartX = event.getSceneX() - pane.getLayoutX();
			 dragStartY = event.getSceneY() - pane.getLayoutY();
		});
		
		body.setOnMouseDragged(event -> {
			AccountManager.removeOldPosition(pane.getLayoutX(), pane.getLayoutY(), "a");
			pane.setLayoutX(event.getSceneX() - dragStartX);
			pane.setLayoutY(event.getSceneY() - dragStartY);	
			
			saveState = "a," + Double.toString(pane.getLayoutX()) + "," + Double.toString(pane.getLayoutY()) + ",";
			AccountManager.setIndividualGate(saveState);
		});
		
		//Stores information about terminals 
		input1.getProperties().put("type", "input");	//type of terminal
		input1.getProperties().put("state", false);		//State of terminal on/off
		input1.getProperties().put("parentGate", this);	//returns the instance of the class holding the terminal
		input1.getProperties().put("put", null);		//Connect to another terminal in a different gate
		input1.getProperties().put("ClassType", "AND");	//the type of gate the terminals parent is
		input1.getProperties().put("wire", null);		//holds the instance of the wire connecting 2 terminals

		//same for the rest
		input2.getProperties().put("type", "input");
		input2.getProperties().put("state", false);		
		input2.getProperties().put("parentGate", this);
		input2.getProperties().put("put", null);
		input2.getProperties().put("ClassType", "AND");
		input2.getProperties().put("wire", null);
		
		output.getProperties().put("type", "output");
		output.getProperties().put("state", false);
		output.getProperties().put("parentGate", this);
		output.getProperties().put("put", null);	
		output.getProperties().put("ClassType", "AND");
		output.getProperties().put("wire", null);
		
		
		body.getProperties().put("type", "body");
	}
	
	//----------------Terminal Buttons------------
	
	/**
	 * Initiates a connection by passing the MouseEvent event to the sandboxController's
	 * {@link sandboxController#beginConnection(MouseEvent)} method.
	 * This method is designed to be used as a button action for starting a connection
	 * process from a user interaction.
	 *
	 * @param event The MouseEvent that triggers the start of the connection process.
	 *              
	 */
	public void startConnection(MouseEvent event) {
		
		sboxController.beginConnection(event);
		
	}
	
	
	
	//----------------Getters & Setters-----------
	
	
	/**
	 * Sets the image of the AND gate to the "on" state.
	 */
	public void setImageOn() {
	    andImage.setImage(andOn);
	}

	/**
	 * Sets the image of the AND gate to the "off" state.
	 */
	public void setImageOff() {
	    andImage.setImage(andOff);
	}

	/**
	 * Sets the sandbox controller for communication.
	 *
	 * @param board The sandbox controller instance.
	 */
	public void setBoard(sandboxController board) {
	    sboxController = board;
	    circuitBoardPane = sboxController.getCircuitBoardPane();
	}

	/**
	 * Gets the state of the AND gate.
	 *
	 * @return The state of the AND gate.
	 */
	public Boolean getState() {
	    return state;
	}

	/**
	 * Sets the state of the first input of the AND gate.
	 *
	 * @param state The state to set.
	 */
	public void setFirstInput(Boolean state) {
	    this.firstInput = state;
	}

	/**
	 * Sets the state of the second input of the AND gate.
	 *
	 * @param state The state to set.
	 */
	public void setSecondInput(Boolean state) {
	    this.secondInput = state;
	}

	
	/**
	 * Calls the checktype method based on the node's class type.
	 *
	 * @param node The rectangle node to check.
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
	 * Checks the type of the AND gate based on its inputs.
	 * If both inputs are connected and active, the output is set to true.
	 * If either input is not connected or inactive, the output is set to false.
	 * Updates the output state and calls {@link #callCheckType(Rectangle)} on the connected node.
	 */
	public void checktype() {
		
		//checks if both terminals have a connection 
		if((( (Rectangle)input1.getProperties().get("put") != null) && ((Rectangle)input2.getProperties().get("put")) != null)) {
			
			//checks if the state of the terminals satisfy the AND gate
			if (((boolean)((Rectangle)input1.getProperties().get("put")).getProperties().get("state")) && ((boolean)((Rectangle)input2.getProperties().get("put")).getProperties().get("state"))) {
				
				//if both inputs are true the output is true
				output.getProperties().put("state", true);
				
				//if the output is connected to a terminal update that terminal
				if( ( (Rectangle)output.getProperties().get("put")) != null) {
					
					((Rectangle)output.getProperties().get("put")).getProperties().put("state", true);
					//recursively check the rest of the circuit
					callChecktype((Rectangle)output.getProperties().get("put"));				
				}
				
				//update image
				setImageOn();
			}
			
			else {

				output.getProperties().put("state", false);
				if( ( (Rectangle)output.getProperties().get("put")) != null) {
					
					((Rectangle)output.getProperties().get("put")).getProperties().put("state", false);
					callChecktype((Rectangle)output.getProperties().get("put"));
				}
				
				setImageOff();
			}
		}	
		else {
			output.getProperties().put("state", false);
			if( ( (Rectangle)output.getProperties().get("put")) != null) {
				
				((Rectangle)output.getProperties().get("put")).getProperties().put("state", false);
				callChecktype((Rectangle)output.getProperties().get("put"));			
			}
			
			setImageOff();
		}
	}
	
	/**
	 * Gets the type of the gate.
	 *
	 * @return The Type of the gate.
	 */
	public Type getType() {
	    return type;
	}

	/**
	 * Gets the first input rectangle of the gate.
	 *
	 * @return The first input rectangle.
	 */
	public Rectangle getInput1() {
	    return input1;
	}

	/**
	 * Gets the second input rectangle of the gate.
	 *
	 * @return The second input rectangle.
	 */
	public Rectangle getInput2() {
	    return input2;
	}

	/**
	 * Gets the output rectangle of the gate.
	 *
	 * @return The output rectangle.
	 */
	public Rectangle getOutput() {
	    return output;
	}


}
