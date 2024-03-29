package application.resources.gates;

import application.resources.sandboxController;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;

/**
 * All gates inherit from this object as a template. 
 * 
 * @see andController
 * @version 1.0
 * @since 1.0
 * @author Group 1
 */
public class gateObject {
	
	Boolean state;
	Type type;
	sandboxController sboxController;
	Pane circuitBoardPane;
	Ellipse body;
	
	/**
	 * Enum representing the type of the gate object.
	 */
	public enum Type{
		battery,and,or,not,nor,nand,xor,bulb		
	}
	
	/**
	 * Returns the state of the gate object.
	 * @return the state of the gate object
	 */
	public Boolean getState() {
		return state;
	}
	
	/**
	 * Sets the state of the gate object.
	 * @param inputState the state to set
	 */
	public void setState(Boolean inputState) {
		this.state = inputState;		
	}
	
	/**
	 * Returns the type of the gate object.
	 * @return the type of the gate object
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * Sets the circuit board and sandbox controller for the gate object.
	 * @param board the sandbox controller to set
	 */
	public void setBoard (sandboxController board) {
		sboxController = board;
		circuitBoardPane = sboxController.getCircuitBoardPane();
	}
	
	/**
	 * Checks the type of the gate object.
	 */
	public void checktype() {
		// TODO: Implement the logic to check the type of the gate object
	}
	
	/**
	 * Sets the body of the gate object.
	 * @param body the body to set
	 */
	public void setBody(Ellipse body) {
		this.body = body;
	}

	/**
	 * Sets the gate object as immovable.
	 */
	public void setImmovable() {
		if(body != null) {
			body.setOnMouseDragged(null);
		}
	}

}
