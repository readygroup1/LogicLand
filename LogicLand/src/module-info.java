module LogicLand {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	
	opens application.resources to javafx.graphics, javafx.fxml;
	exports application;
}
