package application.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.AccountManager;
import application.Database;

class DBAccountManagerTest {

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        AccountManager.db = new Database();
        // Set up a new admin and a new player in a specific class
        AccountManager.newAdminAccount("John Doe", "JHD", "1234", "john@doe.com", "JohnsClass");
        AccountManager.newPlayerAccount("Student", "STU", "1234", "student@uwo.ca", AccountManager.getClassID("JohnsClass"));
    }

    @BeforeEach
    void setUp() throws Exception {
        // Log in as admin before each test to ensure consistent state
        boolean adminLogin = AccountManager.verifyLogin("John Doe", "1234", true);
        assertTrue(adminLogin, "Admin login failed");
    }

    @AfterEach
    void tearDown() throws Exception {
        // Reset user context
        AccountManager.logout();
    }

    @Test
    void testLoginAndSandboxState() {
        AccountManager.logout(); // Log out admin to test login
        assertTrue(AccountManager.verifyLogin("Student", "1234", false), "Student login failed");
        assertTrue(!AccountManager.isAdmin(), "Logged-in user should be student");

        AccountManager.setInSandbox(true);
        assertTrue(AccountManager.isInSandbox(), "Sandbox state not set correctly");

        AccountManager.setSandboxSaveState("testState");
        assertEquals("testState", AccountManager.getSandboxSaveState(), "Sandbox save state not set/retrieved correctly");
    }

    @Test
    void testClassroomManagement() {
        int classID = AccountManager.getClassID("JohnsClass");
        assertTrue(classID > 0, "Class ID should be positive");

        String className = AccountManager.getClassName(classID);
        assertEquals("JohnsClass", className, "Class name mismatch");

        assertTrue(AccountManager.getClassrooms().contains("JohnsClass"), "Classrooms list should contain 'JohnsClass'");
    }

    @Test
    void testPlayerManagementAndClassTransition() {
        // Log out as admin and log in as the player for this test
        AccountManager.logout();
        assertTrue(AccountManager.verifyLogin("Student", "1234", false), "Student login failed");
        
        int playerID = AccountManager.getCurrentUser();
        assertTrue(playerID > 0, "Failed to retrieve current player ID");

        // Perform actions as the player
        AccountManager.movePlayerToPublicClass(playerID);
        int currentClassID = AccountManager.getCurrentClassID();
        assertEquals(1, currentClassID, "Player was not moved to Public class correctly");
    }
    
    @Test
    void testUserLoginVerification() {
        // Verify admin login
        boolean adminLoginSuccess = AccountManager.verifyLogin("John Doe", "1234", true);
        assertTrue(adminLoginSuccess, "Admin should be able to log in");
        assertTrue(AccountManager.isAdmin(), "The logged-in user should be recognized as an admin");

        AccountManager.logout(); // Reset state

        // Verify player login
        boolean playerLoginSuccess = AccountManager.verifyLogin("Student", "1234", false);
        assertTrue(playerLoginSuccess, "Player should be able to log in");
        assertFalse(AccountManager.isAdmin(), "The logged-in user should not be recognized as an admin");
    }
    
    @Test
    void testSandboxModeAndStateManagement() {
        AccountManager.logout(); // Ensure we're starting fresh
        AccountManager.verifyLogin("Student", "1234", false); // Log in as student

        // Activate sandbox mode
        AccountManager.setInSandbox(true);
        assertTrue(AccountManager.isInSandbox(), "Sandbox mode should be activated");

        // Set and verify sandbox state
        String sandboxState = "Level1:Complete,Level2:Incomplete";
        AccountManager.setSandboxSaveState(sandboxState);
        assertEquals(sandboxState, AccountManager.getSandboxSaveState(), "Sandbox save state should match the set value");
    }

    @Test
    void testHighScoreManagementAndRetrieval() {
        AccountManager.logout(); // Reset for clean state
        AccountManager.verifyLogin("Student", "1234", false); // Log in as student

        int levelID = 1; // Assuming a level ID for setting scores
        int score = 100; // Sample score

        // Set a level score (assuming method exists and affects high scores)
        AccountManager.setLevelScore(levelID, score);
        
        // Assuming getHighscore returns the highest score of the current user
        assertEquals(score, AccountManager.getHighscore(AccountManager.getCurrentUser()), "High score should match the set score");

        // Retrieve top scores (global and class-specific) assuming these methods return non-empty lists for test data
        assertFalse(AccountManager.getTopFiveScores().isEmpty(), "Global top five scores list should not be empty");
        assertFalse(AccountManager.getTopFiveNames().isEmpty(), "Global top five names list should not be empty");
        
        int classID = AccountManager.getCurrentClassID(); // Get current class ID
        // Assuming these methods return lists specific to a class and are not empty for the current class
        assertFalse(AccountManager.getTopFiveScores(classID).isEmpty(), "Class-specific top five scores list should not be empty");
        assertFalse(AccountManager.getTopFiveNames(classID).isEmpty(), "Class-specific top five names list should not be empty");
    }

}
