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
* The {@code notController} class represents a NOT gate controller extending {@link gateObject}.
* It contains functionality specific to NOT gate behavior in a circuit simulation.
*
* @version 1.0
* @since 1.0
* @authors Kalundi Serumaga, Nick Howard
*
*/
public class notController extends gateObject implements Initializable {
	
	//----------------Variables---------------------------
		boolean state = false;
		sandboxController sboxController;
		Pane circuitBoardPane;
		Type type = Type.not;
		
		@FXML
		Ellipse body;
		@FXML
		Rectangle input1;
		@FXML
		Rectangle output;	
		@FXML
		Pane pane;
		@FXML
		ImageView notImage;
		
		Double dragStartX;
		Double dragStartY;
		
		String saveState = ""; //not has code 'n'
		
		
		
		//-------------------Resources--------------------------
		Image notOff = new Image(getClass().getResourceAsStream("/application/resources/images/not.png"));
	 	Image notOn = new Image(getClass().getResourceAsStream("/application/resources/images/notOn.png")); 
		
		//----------------Initializer---------------------------
	 	/**
	     * Initializes the NOT gate controller.
	     * Sets the body as draggable and initializes node properties on the terminals.
	     */
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			super.setBody(body);
			
			body.setOnMousePressed(event ->{			
				 dragStartX = event.getSceneX() - pane.getLayoutX();
				 dragStartY = event.getSceneY() - pane.getLayoutY();
				 AccountManager.removeOldPosition(pane.getLayoutX(), pane.getLayoutY(), "n");
			});
			
			body.setOnMouseDragged(event -> {
				pane.setLayoutX(event.getSceneX() - dragStartX);
				pane.setLayoutY(event.getSceneY() - dragStartY);
			});
			
			body.setOnMouseReleased(event -> {
				saveState = "n," + Double.toString(pane.getLayoutX()) + "," + Double.toString(pane.getLayoutY()) + ",";
				AccountManager.setIndividualGate(saveState);
			});
			
			input1.getProperties().put("type", "input");
			input1.getProperties().put("state", false);		//Andres
			input1.getProperties().put("parentGate", this);//Andres
			input1.getProperties().put("put", null);
			input1.getProperties().put("ClassType", "NOT");
			
			
			output.getProperties().put("type", "output");
			output.getProperties().put("state", false);//Andres
			output.getProperties().put("parentGate", this);//Andres
			output.getProperties().put("put", null);	//Andres
			output.getProperties().put("ClassType", "NOT");
			
			
			body.getProperties().put("type", "body");
		}
		
		//----------------Terminal Buttons------------
		
		/**
	     * Passes the event to the sandboxController.
	     *
	     * @param event the MouseEvent representing the event
	     */
		public void startConnection(MouseEvent event) {
			
			sboxController.beginConnection(event);
			
		}
		
		//----------------Getters & Setters-----------
	
		
		/**
	     * Sets the image of the NOT gate to ON.
	     */
		public void setImageOn() {
			notImage.setImage(notOn);
		}
		
		
		/**
	     * Sets the image of the NOT gate to OFF.
	     */
		public void setImageOff() {
			notImage.setImage(notOff);
		}
		
		/**
	     * Sets the sandbox controller and circuit board pane for the NOT gate.
	     *
	     * @param board the sandbox controller to associate with the NOT gate
	     */
		public void setBoard (sandboxController board) {
			sboxController = board;
			circuitBoardPane = sboxController.getCircuitBoardPane();
		}
		
		
		/**
	     * Gets the state of the NOT gate.
	     *
	     * @return the current state of the NOT gate
	     */
		public Boolean getState() {
			return state;
		}
		
		
		/**
	     * Sets the state of the NOT gate.
	     *
	     * @param inputState the new state to set for the NOT gate
	     */
		public void setState(Boolean inputState) {
			this.state = inputState;		
		}
		
		
		/**
	     * Gets the type of the NOT gate.
	     *
	     * @return the type of the NOT gate
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
	     * Checks the type of the NOT gate.
	     * If input1 is connected and in the ON state, output is OFF. Otherwise, output is ON.
	     */
		public void checktype() {
			
			if(((Rectangle)input1.getProperties().get("put") != null) ) {
			
				if (((boolean)((Rectangle)input1.getProperties().get("put")).getProperties().get("state")) ) {
					
					output.getProperties().put("state", false);//Andres
					if(	((Rectangle)output.getProperties().get("put")) != null) {
						((Rectangle)output.getProperties().get("put")).getProperties().put("state", false);
						callChecktype((Rectangle)output.getProperties().get("put"));
					}
					setImageOff();
					
				}
				else {
					
					output.getProperties().put("state", true);//Andres
					if(	((Rectangle)output.getProperties().get("put")) != null) {
						((Rectangle)output.getProperties().get("put")).getProperties().put("state", true);
						callChecktype((Rectangle)output.getProperties().get("put"));
					}
					setImageOn();
					
				}
			}	
			else {
				
				output.getProperties().put("state", false);//Andres
				if(	((Rectangle)output.getProperties().get("put")) != null) {
					((Rectangle)output.getProperties().get("put")).getProperties().put("state", false);
					callChecktype((Rectangle)output.getProperties().get("put"));
				}
				setImageOff();
			}
		}
		
		
		/**
	     * Gets the input1 Rectangle of the NOT gate.
	     *
	     * @return the input1 Rectangle
	     */
		public Rectangle getInput1() {
			return input1;		
		}
		
		/**
	     * Gets the output Rectangle of the NOT gate.
	     *
	     * @return the output Rectangle
	     */
		public Rectangle getOutput() {
			return output;		
		}

}