package application.resources.gates;

import application.resources.sandboxController;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;

public class gateObject {
	
	Boolean state;
	Type type;
	sandboxController sboxController;
	Pane circuitBoardPane;
	Ellipse body;
	
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
	
	public void checktype() {
		
		
	}
	
	public void setBody(Ellipse body) {
		this.body = body;
	}

	public void setImmovable() {
		
		if(body != null) {
			body.setOnMouseDragged(null);
			body.setOnMouseReleased(null);
		}
	}

}
