@echo off
java --module-path ".\javafx-sdk-22\lib" --add-modules javafx.web,javafx.controls,javafx.fxml,javafx.graphics,javafx.media -jar Main.jar
pause