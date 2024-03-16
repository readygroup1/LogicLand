package application.resources.gates;


import java.net.URL;
import java.util.ResourceBundle;

import application.resources.sandboxController;
import application.resources.gates.gateObject.Type;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class andController extends gateObject implements Initializable {
	
	//----------------Variables-------------------
	boolean state = false;
	boolean firstInput = false;
	boolean secondInput = false;
	
	
	sandboxController sboxController;
	Pane circuitBoardPane;
	Type type = Type.and;
	
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
	
	//----------------Initializer------------------
	/**The first two blocks initialized the gate to be draggable. 
	 * The third block initialized node properties on the terminals.
	 * Every gate and terminal will have properties such as type that can be accessed using
	 * (*Any Node*).getProperties().get("type"). */
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		body.setOnMousePressed(event ->{			
			 dragStartX = event.getSceneX() - pane.getLayoutX();
			 dragStartY = event.getSceneY() - pane.getLayoutY();
		});
		
		body.setOnMouseDragged(event -> {
			pane.setLayoutX(event.getSceneX() - dragStartX);
			pane.setLayoutY(event.getSceneY() - dragStartY);			
		});
		
		input1.getProperties().put("type", "input");
		input1.getProperties().put("state", false);		//Andres
		input1.getProperties().put("parentGate", this);//Andres
		
		input2.getProperties().put("type", "input");
		input2.getProperties().put("state", false);		//Andres
		input2.getProperties().put("parentGate", this);//Andres
		
		output.getProperties().put("type", "output");
		output.getProperties().put("state", false);//Andres
		output.getProperties().put("parentGate", this);//Andres
		
		body.getProperties().put("type", "body");
	}
	
	//----------------Terminal Buttons------------
	/** This just passes the event to the sandboxController */
	public void startConnection(MouseEvent event) {
		
		sboxController.beginConnection(event);
		
	}
	
	
	//----------------Getters & Setters-----------
	/** This function is used by sandbox Controller to set instance variables of the sandboxPane and sandboxController
	 * in every gate that is created. It is helpful for communicating events back to a central source.
	 * You can call any function in sandbox controller on this instance.
	 * The board instance can be used to draw things on the screen from a gate if needed.  */
	public void setBoard (sandboxController board) {
		sboxController = board;
		circuitBoardPane = sboxController.getCircuitBoardPane();
	}
	
	public Boolean getState() {
		return state;
	}
	
	public void setfirstInput(Boolean state) {
		this.firstInput = state;		
	}
	
	public void setSecondInput(Boolean state) {
		this.secondInput = state;		
	}
	
	public void checktype() {
		if (((boolean)input1.getProperties().get("state")) && (boolean) input2.getProperties().get("state")) {
			output.getProperties().put("state", true);
			
			System.out.println("Switched to true");
		}
		else {
			System.out.println("Switched to false");
		}
	}
	
	public Type getType() {
		return type;
	}
	
	public Rectangle getInput1() {
		return input1;		
	}
	public Rectangle getInput2() {
		return input2;		
	}
	public Rectangle getOutput() {
		return output;		
	}


}
