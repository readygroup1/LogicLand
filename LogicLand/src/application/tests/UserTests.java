package application.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.User;

class UserTests {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void getUsernameTest() {
		int playerID = 1;
		User player = new User(playerID);
		assertTrue(!player.getUsername().isBlank(), "falied to return username");
	}
	
	@Test
	void getInitialsTest() {
		int playerID = 1;
		User player = new User(playerID);
		assertTrue( player.getInitials().length() == 3, "falied to return user initials");
	}
	
	@Test
	void getEmailTest() {
		int playerID = 1;
		User player = new User(playerID);
		assertTrue(!player.getEmail().isBlank(), "falied to return email");
	}
	
	@Test
	void getHighScoreTest() {
		int playerID = 1;
		User player = new User(playerID);
		assertTrue(player.getHighScore() < 1000 && player.getHighScore() > -1, "falied to return high score");
	}
	
	@Test
	void getSandboxIDTest() {
		int playerID = 1;
		User player = new User(playerID);
		assertTrue(player.getSandboxID()> -1, "falied to return sandboxID");
	}
	
	@Test
	void getIntutorialTest() {
		int playerID = 1;
		User player = new User(playerID);
		assertTrue(player.getinTutorial() == "true" || player.getinTutorial() == "false", "falied to return tutorial state");
	}
	
	@Test
	void getClassIDTest() {
		int playerID = 1;
		User player = new User(playerID);
		assertTrue(player.getPlayerID() == 1, "falied to return playerID");
	}
	
	@Test
	void getClassNameTest() {
		int playerID = 1;
		User player = new User(playerID);
		assertTrue(!player.getClassName().isBlank(), "falied to return sandboxID");
	}

}
