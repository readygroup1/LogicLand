package application.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import application.resources.gates.norController;
import application.resources.gates.gateObject.Type;
import application.resources.gates.andController;
import application.resources.gates.gateObject;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

class norControllerTest {

	static Pane pane;
	static Pane norGate;
	static andController norController;
	static FXMLLoader loader;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Platform.startup(() -> {
			
			try {
				
				loader = new FXMLLoader(norControllerTest.class.getResource("/application/resources/gates/nor.fxml"));
				norGate = loader.load();
				norController = loader.getController();
				
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
	void typeTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			
			if(norController.getType() == gateObject.Type.and) {
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
			
			if(norController.getInput1()  instanceof Rectangle) {
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
	void getStateTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {			
			
			if(!norController.getState()) {
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
	void getInput1Test() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			
			if(norController.getInput1()  instanceof Rectangle && norController.getInput1().getProperties().get("type")  == "input") {
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
	void getInput2Test() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			
			if(norController.getInput1()  instanceof Rectangle && norController.getInput2().getProperties().get("type")  == "input") {
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
	void getOutputTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			
			if(norController.getInput1()  instanceof Rectangle && norController.getOutput().getProperties().get("type")  == "output") {
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