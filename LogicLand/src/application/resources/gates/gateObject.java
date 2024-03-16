package application.resources.gates;

import application.resources.sandboxController;
import javafx.scene.layout.Pane;

public class gateObject {
	
	Boolean state;
	Type type;
	sandboxController sboxController;
	Pane circuitBoardPane;
	
	public enum Type{
		battery,and,or,not,nor,nand,xor,bulb		
	}
	
	public Boolean getState() {
		return state;
	}
	
	public void setState(Boolean inputState) {
		this.state = inputState;		
	}
	
	public Type getType() {
		return type;
	}
	
	public void setBoard (sandboxController board) {
		sboxController = board;
		circuitBoardPane = sboxController.getCircuitBoardPane();
	}


}
