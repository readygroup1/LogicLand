package application.tests;

import application.resources.MultiMediaPlayer;
import javafx.application.Platform;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;

public class MultiMediaTest {

    @BeforeAll
    static void setUpClass() throws InterruptedException {
        // Initialize the JavaFX runtime
        // This block ensures that JavaFX runtime is initialized before any tests run
        final CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> latch.countDown());
        latch.await();
    }

    @AfterAll
    static void tearDownClass() {
        // Properly shutdown JavaFX platform to clean up after tests
        Platform.exit();
    }

    @Test
    void testVolumeAdjustments() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        // Interact with JavaFX components on the JavaFX application thread
        Platform.runLater(() -> {
            try {
                MultiMediaPlayer mmp = new MultiMediaPlayer();

                // Example of testing setVolume functionality
                mmp.setBoopVolume(0.5);
                assertEquals(0.5, MultiMediaPlayer.defaultBoop, "Boop volume should be set to 0.5");

                mmp.setDefaultVolume(0.8);
                assertEquals(0.8, MultiMediaPlayer.defaultVolume, "Default volume should be set to 0.8");
            } finally {
                latch.countDown();
            }
        });

        latch.await(); // Wait for the assertions to be executed on the JavaFX thread
    }

}
