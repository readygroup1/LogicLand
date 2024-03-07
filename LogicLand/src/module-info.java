module LogicLand {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens application.resources to javafx.graphics, javafx.fxml;
	exports application;
}
