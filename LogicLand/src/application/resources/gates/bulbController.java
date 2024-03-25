package application.resources.gates;

import java.net.URL;
import java.util.Map;
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
 * This class controls functionality of the lightBulbs throughout the game. It handles image switching ---.
 * 
 * <b>Example use:</b>
 * 
 * @version 1.0
 * @since 1.0
 * @author Group 1
 */
public class bulbController extends gateObject implements Initializable {
	//----------------Variables----------------------
	boolean state = false;
	sandboxController sboxController;
	Pane circuitBoardPane;
	Type type = Type.bulb;
	
	@FXML
	Ellipse body;
	@FXML
	Rectangle input1;
	@FXML
	Pane pane;
	@FXML
	ImageView bulb;
	
	Double dragStartX;
	Double dragStartY;
	
	String saveState = ""; //Bulb has code 'l'
	
	// --------------Constants & Resources----------------
	
	Image bulbOn = new Image(getClass().getResourceAsStream("/application/resources/images/bulbOn.png"));
 	Image bulbOff = new Image(getClass().getResourceAsStream("/application/resources/images/bulbOff.png")); 
	
	
	//----------------Initializer-------------------------
	
 	/**
 	 * Initializes the gate to be draggable and sets properties on the input1 terminal.
 	 * This method sets up event handlers for mouse press and drag events to enable
 	 * dragging of the gate. It also initializes properties on the input1 terminal,
 	 * such as type, state, parentGate, put, and ClassType.
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
			AccountManager.removeOldPosition(pane.getLayoutX(), pane.getLayoutY(), "l");
			pane.setLayoutX(event.getSceneX() - dragStartX); 
			pane.setLayoutY(event.getSceneY() - dragStartY);
			
			saveState = "l," + Double.toString(pane.getLayoutX()) + "," + Double.toString(pane.getLayoutY()) + ",";
			AccountManager.setIndividualGate(saveState);
		});
		
		input1.getProperties().put("type", "input");
		body.getProperties().put("type", "body");
		input1.getProperties().put("state", false);		//Andres
		input1.getProperties().put("parentGate", this);//Andres
		input1.getProperties().put("put", null);
		input1.getProperties().put("ClassType", "BULB");
		input1.getProperties().put("wire", null);
		
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
	
	
	//----------------Functions------------------
	
	
	
	//----------------Getters & Setters-----------
	
	/**
	 * Sets the sandboxController and circuitBoardPane instance variables for the gate.
	 * This method is used by the sandbox Controller to set the instance variables of
	 * sandboxPane and sandboxController in every gate that is created.
	 *
	 * @param board The sandboxController instance to set for this gate.
	 *             
	 */
	public void setBoard (sandboxController board) {
		sboxController = board;
		circuitBoardPane = sboxController.getCircuitBoardPane();
	}
	
	
	/**
	 * Returns the current saved state of the gate in a String format.
	 *
	 * @return The saved state of the gate.
	 */
	public String getSaveState() {
		return saveState;
	}
	
	
	/**
	 * Returns the current state of the gate.
	 *
	 * @return The state of the gate as a Boolean value.
	 */
	public Boolean getState() {
		return state;
	}
	
	
	/**
	 * Sets the state of the gate to the specified inputState.
	 * If the inputState is true, the gate is considered ON and updates the bulb image to ON.
	 * If the inputState is false, the gate is considered OFF and updates the bulb image to OFF.
	 *
	 * @param inputState The state to set the gate to.
	 */
	public void setState(Boolean inputState) {
		this.state = inputState;
		if(state) {
			bulb.setImage(bulbOn);
		}
		else {
			bulb.setImage(bulbOff);
		}
	}
	
	
	/**
	 * Returns the -------.
	 *
	 * @return The type of --------.
	 */
	public Type getType() {
		return type;
	}
	
	
	/**
	 * Returns the input1 --------
	 *
	 * @return The input1 ----.
	 */
	public Rectangle getInput1() {
		return input1;		
	}
	

	
	/**
	 * Checks the state of the input terminal (input1) to determine the state of the bulb.
	 * If the input terminal has a connected object ("put") and its state property is true,
	 * sets the bulb state to ON and updates the bulb image accordingly.
	 * If the input terminal does not have a connected object or its state property is false,
	 * sets the bulb state to OFF and updates the bulb image accordingly.
	 * This method is typically used to update the state of the bulb based on the input terminal's state.
	 */
	public void checktype() {
		
		if(((Rectangle)input1.getProperties().get("put") != null) ) {
		
			if (((boolean)((Rectangle)input1.getProperties().get("put")).getProperties().get("state")) ) {
				
				setState(true);
				bulb.setImage(bulbOn);
				
			}
			else {
				
				setState(false);
				bulb.setImage(bulbOff);
			}
		}	
		else {
			
			setState(false);
			bulb.setImage(bulbOff);
		}
	}


	

}