module LogicLand {
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires java.sql;
	requires javafx.media;
	requires org.junit.jupiter.api;
	
	opens application.resources to javafx.graphics, javafx.fxml;
	opens application.resources.gates to javafx.graphics, javafx.fxml;
	opens application.resources.levels to javafx.graphics, javafx.fxml;
	exports application;
}