package application.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import application.resources.sandboxController;
import application.resources.sandboxController.Type;
import application.resources.gates.batteryController;
import application.resources.gates.bulbController;
import application.resources.gates.gateObject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

class sandboxControllerTest {
	static Pane pane;
	static sandboxController controller;
	static Pane battery;
	static Pane bulb;
	static batteryController batController;
	static bulbController bulbyController;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Platform.startup(() -> {
			FXMLLoader loader;
		try {
			loader = new FXMLLoader(sandboxControllerTest.class.getResource("/application/resources/sandbox.fxml"));
			pane = loader.load();
			controller = loader.getController();
			
			loader = new FXMLLoader(sandboxControllerTest.class.getResource("/application/resources/gates/battery.fxml"));
			battery = loader.load();
			batController = loader.getController();
			
			loader = new FXMLLoader(sandboxControllerTest.class.getResource("/application/resources/gates/bulb.fxml"));
			bulb = loader.load();
			bulbyController = loader.getController();
			
		} catch (Exception e) {
		    e.printStackTrace();
		}

		});	
			
		
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Platform.exit();
	}

//---------------- Generator Tests-------------------------------------
	
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
	
	@Test
	void andGeneratorTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			try {
//			
			int children  = pane.getChildren().size() ;
			controller.andGenerator();
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
			
			e.printStackTrace();
		}
        Assertions.assertTrue(testStatus.get());
	}
	@Test
	void batteryGeneratorTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			try {
//			
			int children  = pane.getChildren().size() ;
			controller.batteryGenerator();
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
			
			e.printStackTrace();
		}
        Assertions.assertTrue(testStatus.get());
	}
	
	@Test
	void orGeneratorTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			try {
//			
			int children  = pane.getChildren().size() ;
			controller.orGenerator();
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
			
			e.printStackTrace();
		} 
        Assertions.assertTrue(testStatus.get());
	}
	
	@Test
	void bulbGeneratorTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			try {
//			
			int children  = pane.getChildren().size() ;
			controller.bulbGenerator();
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
			
			e.printStackTrace();
		} 
        Assertions.assertTrue(testStatus.get());
	}

	@Test
	void notGeneratorTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			try {
//			
			int children  = pane.getChildren().size() ;
			controller.notGenerator();
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
			
			e.printStackTrace();
		}
        Assertions.assertTrue(testStatus.get());
	}
	
	@Test
	void norGeneratorTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			try {
//			
			int children  = pane.getChildren().size() ;
			controller.norGenerator();
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
			
			e.printStackTrace();
		} 
        Assertions.assertTrue(testStatus.get());
	}
	
	@Test
	void nandGeneratorTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			try {
//			
			int children  = pane.getChildren().size() ;
			controller.nandGenerator();
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
			
			e.printStackTrace();
		} 
        Assertions.assertTrue(testStatus.get());
	}
	
	@Test
	void xorGeneratorTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(false);
		Platform.runLater(() -> {
			try {
//			
			int children  = pane.getChildren().size() ;
			controller.xorGenerator();
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

// --------------Getters and Setters Test --------------------
	@Test
	void getCircuitboardTest() {
		final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean testStatus = new AtomicBoolean(true);
		Platform.runLater(() -> {
			try {
				Pane paney = controller.getCircuitBoardPane();
				if(paney == null) {
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
