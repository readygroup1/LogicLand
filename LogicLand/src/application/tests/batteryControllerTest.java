package application.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import application.resources.sandboxController;
import application.resources.gates.batteryController;
import application.resources.gates.gateObject;
import application.resources.gates.gateObject.Type;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

class batteryControllerTest {

	static Pane pane;
	static Pane battery;
	static batteryController batController;
	static FXMLLoader loader;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Platform.startup(() -> {
			
			try {
				
				loader = new FXMLLoader(sandboxControllerTest.class.getResource("/application/resources/gates/battery.fxml"));
				battery = loader.load();
				batController = loader.getController();
				
			}
				
			 catch (Exception e) {
			    e.printStackTrace();
			}

		});	
			
		
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void setStatetest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			batController.setState(true);
			if(batController.getState()) {
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
	void clicktest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			batController.setState(false);
			batController.batteryClick(null);
			if(batController.getState()) {
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
			
			if(batController.getType() == gateObject.Type.battery) {
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
	void getOutput1Test() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			
			if(batController.getOutput1() instanceof Rectangle) {
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
	void getOutput2Test() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			
			if(batController.getOutput2() instanceof Rectangle) {
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
