package application.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import application.resources.gates.xorController;
import application.resources.gates.gateObject;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

class xorControllerTest extends platformTest {

	static Pane pane;
	static Pane gate;
	static xorController controller;
	static FXMLLoader loader;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception  {
		
		if (! platformStarted) {
		Platform.startup(() -> {
			
			try {
				
				loader = new FXMLLoader(sandboxControllerTest.class.getResource("/application/resources/gates/xor.fxml"));
				gate = loader.load();
				controller = loader.getController();
				
			}
				
			 catch (Exception e) {
			    e.printStackTrace();
			}

		});
		platformStarted = true;
		}
		else {
try {
				
				loader = new FXMLLoader(sandboxControllerTest.class.getResource("/application/resources/gates/xor.fxml"));
				gate = loader.load();
				controller = loader.getController();
				
			}
				
			 catch (Exception e) {
			    e.printStackTrace();
			}
		}
			
		
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
	}

	@Test
	void typeTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			
			if(controller.getType() == gateObject.Type.xor) {
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
			
			if(!controller.getState()) {
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
			
			if(controller.getInput1()  instanceof Rectangle && controller.getInput1().getProperties().get("type")  == "input") {
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
			
			if(controller.getInput1()  instanceof Rectangle && controller.getInput2().getProperties().get("type")  == "input") {
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
			
			if(controller.getInput1()  instanceof Rectangle && controller.getOutput().getProperties().get("type")  == "output") {
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