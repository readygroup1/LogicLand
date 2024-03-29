package application;

import java.util.concurrent.*;

/**
 * This class is used to track the time a user has been logged into a current game. 
 * 
 * <b>Example use:</b>
 * <pre>
 * {@code
 * GaemSession session = new GameSession(1);
 * session.startTracking();
 * // Game is played for a while
 * session.finalizeSession();
 * }
 * </pre>
 * <br>
 * @version 1.0
 * @since 1.0
 * @author Callum Thompson
 */

public class GameSession {
    /** The unique session id for the user */
    private final long sessionId;
    /** The user id of the user */
    private final long userId;
    /** The time the session started */
    private long startTime;
    /** The scheduler to save the time played */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    /**
     * Constructor for the GameSession class.
     * @param userId
     */
    public GameSession(int userId) {
        this.userId = userId;
        this.sessionId = generateSessionId(); // Implement this to generate a unique session ID
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Start tracking the time played when this method is called.
     */
    public void startTracking() {
        // Schedule a task to save play time every minute
        scheduler.scheduleAtFixedRate(this::saveSessionTime, 1, 1, TimeUnit.MINUTES);
    }

    /**
     * Save the time played in the session.
     */
    private void saveSessionTime() {
        long now = System.currentTimeMillis();
        long elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(now - startTime);
        AccountManager.incrementTimePlayed(1);
    }

    /**
     * Finalize the session and save the time played.
     */
    public void finalizeSession() {
        saveSessionTime();
        scheduler.shutdown();
        try {
            scheduler.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Generate a unique session id.
     * @return long
     */
    private long generateSessionId() {
        return System.nanoTime();
    }
}

