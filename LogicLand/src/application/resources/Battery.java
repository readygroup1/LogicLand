package application.resources;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


// This Class represents the on/off button and the battery. 
public class Battery {
	
	// ----------------Load Resources -----------------------
	Image on = new Image(getClass().getResourceAsStream("images/on.png"));
 	Image off = new Image(getClass().getResourceAsStream("images/off.png")); 	
	Image batteryOn = new Image(getClass().getResourceAsStream("images/batteryOn.png"));
 	Image batteryOff = new Image(getClass().getResourceAsStream("images/batteryOff.png"));
	
   // ----------------Variables -----------------------
	Boolean state = false;
	ImageView button;
	ImageView battery;
 	
	// ----------------Functions -----------------------
 	public Battery() {
 		
 		button = new ImageView();
 		battery = new ImageView();
 		button.setImage(off);
 		battery.setImage(batteryOff);
 		battery.setPickOnBounds(true);
 		battery.setFitHeight(100);
 		battery.setFitWidth(100);
 		button.setFitHeight(100);
 		button.setFitWidth(100);
 		
 	}
 	
 	public ImageView getBattery() {
 		return battery;
 	}
 	
 	public ImageView getButton() {
 		return button;
 	}
 	
 	public void switchState() {
 		
 		if(state) {
 			button.setImage(off);
 	 		battery.setImage(batteryOff);
 	 		state = false;
 		}
 		else {
 			button.setImage(on);
 	 		battery.setImage(batteryOn);
 	 		state = true;
 		}		
 		
 	}

}
