package application.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import application.resources.sandboxController;
import application.resources.gates.bulbController;
import application.resources.gates.gateObject;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

class bulbControllerTest {

	static Pane pane;
	static Pane bulb;
	static bulbController bulbController;
	static FXMLLoader loader;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Platform.startup(() -> {
			
			try {
				
				loader = new FXMLLoader(sandboxControllerTest.class.getResource("/application/resources/gates/bulb.fxml"));
				bulb = loader.load();
				bulbController = loader.getController();
				
			}
				
			 catch (Exception e) {
			    e.printStackTrace();
			}
			
		});	
			
		
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Platform.exit();
	}
	
	@Test
	void getStatetest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			if(!bulbController.getState()) {
				testStatus.set(true);
			}
			latch.countDown();
		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        Assertions.assertTrue(testStatus.get());
	}

	@Test
	void setStatetest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			bulbController.setState(true);
			if(bulbController.getState()) {
				testStatus.set(true);
			}
			latch.countDown();
		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        Assertions.assertTrue(testStatus.get());
	}
	
	
	@Test
	void typeTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			
			if(bulbController.getType() == gateObject.Type.bulb) {
				testStatus.set(true);
			}
			latch.countDown();
		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        Assertions.assertTrue(testStatus.get());
	}
	@Test
	void getInputTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			
			if(bulbController.getInput1()  instanceof Rectangle) {
				testStatus.set(true);
			}
			latch.countDown();
		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        Assertions.assertTrue(testStatus.get());
	}
	
	
	
	

}