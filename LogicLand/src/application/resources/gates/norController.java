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

public class norController extends gateObject implements Initializable{
	//----------------Variables-------------------
		boolean state = false;
		sandboxController sboxController;
		Pane circuitBoardPane;
		Type type = Type.nor;
		
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
		ImageView norImage;
		
		Double dragStartX;
		Double dragStartY;
		
		String saveState = ""; //Nor has code r
		
		
		// ---------------Resources----------------------------
		Image norOff = new Image(getClass().getResourceAsStream("/application/resources/images/nor.png"));
	 	Image norOn = new Image(getClass().getResourceAsStream("/application/resources/images/norOn.png")); 
		
		//----------------Initializer------------------
		/**The first two blocks initialized the gate to be draggable. 
		 * The third block initialized node properties on the terminals.
		 * Every gate and terminal will have properties such as type that can be accessed using
		 * (*Any Node*).getProperties().get("type"). */
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			super.setBody(body);
			
			body.setOnMousePressed(event ->{			
				 dragStartX = event.getSceneX() - pane.getLayoutX();
				 dragStartY = event.getSceneY() - pane.getLayoutY();
				 AccountManager.removeOldPosition(pane.getLayoutX(), pane.getLayoutY(), "r");
			});
			
			body.setOnMouseDragged(event -> {
				
				pane.setLayoutX(event.getSceneX() - dragStartX);
				pane.setLayoutY(event.getSceneY() - dragStartY);	
			});
			
			body.setOnMouseReleased(event -> {
				saveState = "r," + Double.toString(pane.getLayoutX()) + "," + Double.toString(pane.getLayoutY()) + ",";
				AccountManager.setIndividualGate(saveState);
			});
			
			input1.getProperties().put("type", "input");
			input1.getProperties().put("state", false);		//Andres
			input1.getProperties().put("parentGate", this);//Andres
			input1.getProperties().put("put", null);
			input1.getProperties().put("ClassType", "NOR");
			
			
			input2.getProperties().put("type", "input");
			input2.getProperties().put("state", false);		//Andres
			input2.getProperties().put("parentGate", this);//Andres
			input2.getProperties().put("put", null);
			input2.getProperties().put("ClassType", "NOR");
			
			
			
			output.getProperties().put("type", "output");
			output.getProperties().put("state", false);//Andres
			output.getProperties().put("parentGate", this);//Andres
			output.getProperties().put("put", null);	//Andres
			output.getProperties().put("ClassType", "NOR");
			
			
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
		public void setImageOn() {
			norImage.setImage(norOn);
		}
		
		public void setImageOff() {
			norImage.setImage(norOff);
		}
		
		public void setBoard (sandboxController board) {
			sboxController = board;
			circuitBoardPane = sboxController.getCircuitBoardPane();
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

		/**  this calls checktype on the given node  */
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
		
		public void checktype() {
			
			//check if both inputs are null
			if( !( 
					((Rectangle)input1.getProperties().get("put") != null && ((boolean)((Rectangle)input1.getProperties().get("put")).getProperties().get("state")) ) 
				||  ((Rectangle)input2.getProperties().get("put") != null && ((boolean)((Rectangle)input2.getProperties().get("put")).getProperties().get("state"))))
					
				) {
				
					output.getProperties().put("state", true);
		
	
					if(	((Rectangle)output.getProperties().get("put")) != null) {
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
