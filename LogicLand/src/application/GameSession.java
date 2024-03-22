package application;

import java.util.concurrent.*;

public class GameSession {
    private final long sessionId; // Unique identifier for the session
    private final long userId; // Identifier for the user
    private long startTime;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    public GameSession(int userId) {
        this.userId = userId;
        this.sessionId = generateSessionId(); // Implement this to generate a unique session ID
        this.startTime = System.currentTimeMillis();
    }

    public void startTracking() {
        // Schedule a task to save play time every minute
        scheduler.scheduleAtFixedRate(this::saveSessionTime, 1, 1, TimeUnit.MINUTES);
    }

    private void saveSessionTime() {
        long now = System.currentTimeMillis();
        long elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(now - startTime);
        AccountManager.incrementTimePlayed(1);
    }

    // Call this method when the game is closing, if possible, to perform a final save.
    public void finalizeSession() {
        saveSessionTime();
        scheduler.shutdown();
        try {
            scheduler.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // Utility method to generate a unique session ID
    private long generateSessionId() {
        // Implement this method based on your application's needs
        return System.nanoTime(); // Simple example, ensure uniqueness as required
    }
}

