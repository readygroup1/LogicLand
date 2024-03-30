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
* The {@code nandController} class represents a NAND gate controller extending {@link gateObject}.
* It contains functionality specific to NAND gate behavior in a circuit simulation.
*
* @version 1.0
* @since 1.0
* @authors Kalundi Serumaga, Nick Howard
*
*/
public class nandController extends gateObject implements Initializable {

	//----------------Variables-------------------
			boolean state = false;
			sandboxController sboxController;
			Pane circuitBoardPane;
			Type type = Type.nand;
			
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
			@FXML
			ImageView nandImage;
			
			Double dragStartX;
			Double dragStartY;
			
			String saveState = ""; //nand has code 'd'
			
			//---------------Resources--------------------------------
			Image nandOff = new Image(getClass().getResourceAsStream("/application/resources/images/nand.png"));
		 	Image nandOn = new Image(getClass().getResourceAsStream("/application/resources/images/nandOn.png")); 
			
			//----------------Initializer----------------------------
		 	/**
		     * Initializes the NAND gate controller.
		     * Sets the body as draggable and initializes node properties on the terminals.
		     * 
		     */
			public void initialize(URL arg0, ResourceBundle arg1) {
				
				super.setBody(body);
				
				body.setOnMousePressed(event ->{			
					 dragStartX = event.getSceneX() - pane.getLayoutX();
					 dragStartY = event.getSceneY() - pane.getLayoutY();
				});
				
				body.setOnMouseDragged(event -> {
					AccountManager.removeOldPosition(pane.getLayoutX(), pane.getLayoutY(), "d");
					pane.setLayoutX(event.getSceneX() - dragStartX);
					pane.setLayoutY(event.getSceneY() - dragStartY);	
					
					saveState = "d," + Double.toString(pane.getLayoutX()) + "," + Double.toString(pane.getLayoutY()) + ",";
					AccountManager.setIndividualGate(saveState);
				});
				
				input1.getProperties().put("type", "input");
				input1.getProperties().put("state", false);		//Andres
				input1.getProperties().put("parentGate", this);//Andres
				input1.getProperties().put("put", null);
				input1.getProperties().put("ClassType", "NAND");
				
				
				
				input2.getProperties().put("type", "input");
				input2.getProperties().put("state", false);		//Andres
				input2.getProperties().put("parentGate", this);//Andres
				input2.getProperties().put("put", null);
				input2.getProperties().put("ClassType", "NAND");
				
				output.getProperties().put("type", "output");
				output.getProperties().put("state", false);//Andres
				output.getProperties().put("parentGate", this);//Andres
				output.getProperties().put("put", null);	//Andres
				output.getProperties().put("ClassType", "NAND");
				
				
				
				body.getProperties().put("type", "body");
			}
			
			//----------------Terminal Buttons------------
			
			
			/**
		     * Passes the event to the sandboxController to begin connection.
		     *
		     * @param event the MouseEvent representing the event
		     */
			public void startConnection(MouseEvent event) {
				
				sboxController.beginConnection(event);
				
			}
			
			//----------------Getters & Setters-----------
			/** This function is used by sandbox Controller to set instance variables of the sandboxPane and sandboxController
			 * in every gate that is created. It is helpful for communicating events back to a central source.
			 * You can call any function in sandbox controller on this instance.
			 * The board instance can be used to draw things on the screen from a gate if needed.  */
			
			/**
		     * Sets the image of the NAND gate to ON.
		     */
			public void setImageOn() {
				nandImage.setImage(nandOn);
			}
			
			
			/**
		     * Sets the image of the NAND gate to OFF.
		     */
			public void setImageOff() {
				nandImage.setImage(nandOff);
			}
			
			/**
		     * Sets the sandbox controller and circuit board pane for the NAND gate.
		     *
		     * @param board the sandbox controller to associate with the NAND gate
		     */
			public void setBoard (sandboxController board) {
				sboxController = board;
				circuitBoardPane = sboxController.getCircuitBoardPane();
			}
			
			
			/**
		     * Gets the state of the NAND gate.
		     *
		     * @return the current state of the NAND gate
		     */
			public Boolean getState() {
				return state;
			}
			
			
			/**
		     * Sets the state of the NAND gate.
		     *
		     * @param inputState the new state to set for the NAND gate
		     */
			public void setState(Boolean inputState) {
				this.state = inputState;		
			}
			
			
			/**
		     * Gets the type of the NAND gate.
		     *
		     * @return the type of the NAND gate
		     */
			public Type getType() {
				return type;
			}
			
			
			/**
		     * Calls the checktype method on the given node.
		     *
		     * @param node the Rectangle node to call checktype on
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
			
			
			/**
		     * Checks the type of the NAND gate and updates its state accordingly.
		     * If both inputs are false, the output is true, otherwise false.
		     */
			public void checktype() {
				
				if((( (Rectangle)input1.getProperties().get("put") != null) && ((Rectangle)input2.getProperties().get("put")) != null)) {
				
					if ( !(((boolean)((Rectangle)input1.getProperties().get("put")).getProperties().get("state")) && ((boolean)((Rectangle)input2.getProperties().get("put")).getProperties().get("state")))) {
						output.getProperties().put("state", true);
						if( ( (Rectangle)output.getProperties().get("put")) != null) {
							((Rectangle)output.getProperties().get("put")).getProperties().put("state", true);
							
							
							callChecktype((Rectangle)output.getProperties().get("put"));			
						}
						
						
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
		     * Gets the input1 Rectangle of the NAND gate.
		     *
		     * @return the input1 Rectangle
		     */
			public Rectangle getInput1() {
				return input1;		
			}
			
			/**
		     * Gets the input2 Rectangle of the NAND gate.
		     *
		     * @return the input2 Rectangle
		     */
			public Rectangle getInput2() {
				return input2;		
			}
			
			/**
		     * Gets the output Rectangle of the NAND gate.
		     *
		     * @return the output Rectangle
		     */
			public Rectangle getOutput() {
				return output;		
			}
			
			
			


}