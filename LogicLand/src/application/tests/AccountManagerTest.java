package application.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.AccountManager;

class AccountManagerTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		AccountManager.newAdminAccount("testName", "testInitials", "testPassword", "testEmail", "testNewClass");
		AccountManager.newPlayerAccount("testName", "testInitials", "testPassword", "testEmail", AccountManager.getClassID("testNewClass"));
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
	void testAccountManagerIntBoolean() {
	}

	@Test
	void testAccountManager() {
	}

	@Test
	void testGetSandboxSaveState() {
		AccountManager.getSandboxSaveState();
	}

	@Test
	void testGetCurrentUser() {
		assertEquals(AccountManager.getCurrentUser(), AccountManager.getCurrentUser());
	}

	@Test
	void testGetClassrooms() {
		AccountManager.getClassrooms();
	}

	@Test
	void testGetClassID() {
		AccountManager.getClassID("testClass");
	}

	@Test
	void testGetClassName() {
		AccountManager.getClassName(2);
	}

	@Test
	void testGetPlayer() {
		assertEquals(AccountManager.getPlayer(1), AccountManager.getPlayer(1));
	}

	@Test
	void testGetLevelScore() {
		AccountManager.getLevelScore(1);
	}

	@Test
	void testGetLevelID() {
		AccountManager.getLevelID(1);
	}

	@Test
	void testGetCurrentClassID() {
		assertEquals(AccountManager.getCurrentClassID(), 2);
	}

	@Test
	void testGetTopFiveNames() {	
	}

	@Test
	void testGetTopFiveNamesInt() {
	}

	@Test
	void testGetTopFiveScores() {
	}

	@Test
	void testGetTopFiveScoresInt() {
	}

	@Test
	void testIsAdmin() {
		assertEquals(AccountManager.isAdmin(), false);
	}

	@Test
	void testIsInSandbox() {
		AccountManager.isInSandbox();
	}

	@Test
	void testVerifyLogin() {
		assertEquals(AccountManager.verifyLogin("testName", "testPassword", true), true);
	}

	@Test
	void testSetInSandbox() {
		AccountManager.setInSandbox(true);
		assertEquals(AccountManager.isInSandbox(), true);
	}

	@Test
	void testSetLevelScore() {
		AccountManager.setLevelScore(1, 100);
		assertEquals(AccountManager.getLevelScore(1), 100);
	}

	@Test
	void testSetHighScore() {
		AccountManager.setHighScore();
	}

	@Test
	void testSetSandboxSaveState() {
		AccountManager.setSandboxSaveState("test");
		assertEquals(AccountManager.getSandboxSaveState(), "test");
	}

	@Test
	void testSetIndividualGate() {
	}

	@Test
	void testRemoveOldPosition() {
	}

	@Test
	void testNewPlayerAccount() {
	}

	@Test
	void testNewAdminAccount() {
	}

}
