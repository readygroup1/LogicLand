@echo off
java --module-path ".\javafx-sdk-21.0.2\lib" --add-modules javafx.web,javafx.controls,javafx.fxml,javafx.graphics,javafx.media -jar Main.jar
pause