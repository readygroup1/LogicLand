module LogicLand {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	
	opens application.resources to javafx.graphics, javafx.fxml;
	exports application;
}
