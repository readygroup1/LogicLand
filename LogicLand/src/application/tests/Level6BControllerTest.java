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
import application.resources.gates.bulbController;
import application.resources.levels.Level6BController;
import application.resources.levels.Level6BController.Type;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

class Level6BControllerTest extends platformTest{

	static Pane pane;
	static Level6BController controller;
	static Pane battery;
	static Pane bulb;
	static batteryController batController;
	static bulbController bulbyController;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		if(!platformStarted) {
			platformStarted = true;
			Platform.startup(() -> {
				FXMLLoader loader;
			try {
				loader = new FXMLLoader(platformTest.class.getResource("/application/resources/levels/level6B.fxml"));
				pane = loader.load();
				controller = loader.getController();
				
				loader = new FXMLLoader(platformTest.class.getResource("/application/resources/gates/battery.fxml"));
				battery = loader.load();
				batController = loader.getController();
				
				loader = new FXMLLoader(platformTest.class.getResource("/application/resources/gates/bulb.fxml"));
				bulb = loader.load();
				bulbyController = loader.getController();
				
			} catch (Exception e) {
			    e.printStackTrace();
			}
	
			});	
		}
		
		else {
			FXMLLoader loader;
			try {
				loader = new FXMLLoader(platformTest.class.getResource("/application/resources/levels/level6B.fxml"));
				pane = loader.load();
				controller = loader.getController();
				
				loader = new FXMLLoader(platformTest.class.getResource("/application/resources/gates/battery.fxml"));
				battery = loader.load();
				batController = loader.getController();
				
				loader = new FXMLLoader(platformTest.class.getResource("/application/resources/gates/bulb.fxml"));
				bulb = loader.load();
				bulbyController = loader.getController();
				
			} catch (Exception e) {
			    e.printStackTrace();
			}
			
		}
			
		
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
	}

//---------------- Generator Tests-------------------------------------
	@Test
	void loadTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			try {
			Pane view = new Pane();
			pane.getChildren().add(view) ;
			int children  = pane.getChildren().size() ;
			controller.load( view, Type.BULB );
			if(pane.getChildren().size() - children == 1) {
				testStatus.set(true);
			};
		} 
		catch (Exception e) {
		    e.printStackTrace();
		}
		finally {
		    latch.countDown();
		}

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
	void generatorTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			try {
			ImageView view = new ImageView();
			pane.getChildren().add(view) ;
			int children  = pane.getChildren().size() ;
			controller.generator("/application/resources/gates/bulb.fxml", sandboxController.Type.BULB, view );
			if(pane.getChildren().size() - children == 1) {
				testStatus.set(true);
			};
		} 
		catch (Exception e) {
		    e.printStackTrace();
		}
		finally {
		    latch.countDown();
		}

		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        Assertions.assertTrue(testStatus.get());
	}
	
	
	
//---------------- Connection Tests-------------------------------------
	@Test
	void makeWireTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(true);
		Platform.runLater(() -> {
			try {
				
				pane.getChildren().add(battery);
				pane.getChildren().add(bulb);
				
				int children  = pane.getChildren().size() ;
				controller.makeWire(batController.getOutput1(), bulbyController.getInput1());
				if(pane.getChildren().size() - children != 1) {
					testStatus.set(false);
				}
				
		} 
		catch (Exception e) {
		    e.printStackTrace();
		    testStatus.set(false);
		}
		finally {
		    latch.countDown();
		}

		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		} 
		Assertions.assertTrue(testStatus.get());
	}
	@Test
	void connectionTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(true);
		Platform.runLater(() -> {
			try {
				batController.batteryClick(null);
				if(bulbyController.getState() != true) {
					testStatus.set(false);
				}
		} 
		catch (Exception e) {
		    e.printStackTrace();
		    testStatus.set(false);
		}
		finally {
		    latch.countDown();
		}

		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		} 
		Assertions.assertTrue(testStatus.get());
	}

}
