package application;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This is a static class that interacts with every other class. It acts as a bridge between the database class and the game itself.
 * It contains methods that allow the game to interact with the database and retrieve information from it. It also contains methods that allow the game to update the database.
 * 
 * <b>Example use:</b>
 * <pre>
 * {@code
 * AccountManager.verifyLogin("admin", "password", true);
 * AccountManager.setSandboxSaveState("l,243.443,232.423");
 * AccountManager.setIndividualGate("l,243.443,232.423");
 * }
 * <br>
 * @version 1.0
 * @since 1.0
 * @author Callum Thompson
 */
public class AccountManager {
	/** Stores the ID of the current user who is logged into the game */
	private static int currentUserID;
	/** Stores whether the current user is an admin or not */
	private static boolean isAdmin;
	/** The database object that is used to interact with the database */
	public static Database db = new Database();
	/** The string that stores the current state of the sandbox */
	private static String sandboxSaveState = "";
	/** The boolean that stores whether the user is in the sandbox or not */
	private static boolean inSandbox = false;
	
	/**
	 * Constructor for the AccountManager class. It sets the current user ID and whether the user is an admin or not.
	 * @param currentUserID
	 * @param isTeacher
	 */
	public AccountManager(int currentUserID, boolean isTeacher) {
		AccountManager.currentUserID = currentUserID;
		AccountManager.isAdmin = isTeacher;
	}
	
	/**
	 * Constructor for the AccountManager class. It sets the current user ID and whether the user is an admin or not.
	 */
	public AccountManager() {
		AccountManager.currentUserID = -1;
		AccountManager.isAdmin = false;
		return;
	}
	
	/**
	 * This method returns the current state of the sandbox.
	 * @return String
	 */
	public static String getSandboxSaveState() {
		return sandboxSaveState;
	}
	
	/**
	 * This method returns the current user ID.
	 * @return int
	 */
	public static int getCurrentUser() {
		return AccountManager.currentUserID;
	}
	
	/**
	 * This method returns a list of all the classrooms that exist in the database.
	 * @return ArrayList<String>
	 */
	public static ArrayList<String> getClassrooms() {
		return db.getClassrooms();
	}
	
	/**
	 * This method returns the ID of a classroom given its name.
	 * @param name
	 * @return int
	 */
	public static int getClassID(String name) {
		return db.getClassID(name);
	}

	/**
	 * This method returns the name of a classroom given its ID.
	 * @param classID
	 * @return String
	 */
	public static String getClassName(int classID) {
		return db.getClassName(classID);
	}
	
	/**
	 * This method returns the name of a player given their ID.
	 * @param playerID
	 * @return String
	 */
	public static String getPlayer(int playerID) {
		return db.getPlayer(playerID);
	}

	/**
	 * This method returns the score of a level given its ID.
	 * @param levelID
	 * @return int
	 */
	public static int getLevelScore(int levelID) {
		return db.getLevelScore(levelID);
	}

	/**
	 * This method returns the level ID of a level given its number. 
	 * Account Manager already knows the current user ID so it doesn't need to be passed in.
	 * @param level
	 * @return int
	 */
	public static int getLevelID(int level) {
		return db.getLevelID(AccountManager.currentUserID, level);
	}
	
	/**
	 * This method returns the current class ID of the user.
	 * @return int
	 */
	public static int getCurrentClassID() {
		if(AccountManager.isAdmin) {
			return db.getClassIDAdmin(AccountManager.currentUserID);
		} else {
			return db.getClassID(AccountManager.currentUserID);
		}
	}
	
	/**
	 * This method returns the top five names of players with the highest scores.
	 * Global high scores.
	 * @return ArrayList<String>
	 */
	public static ArrayList<String> getTopFiveNames() {
		ArrayList<String> list = null;
		try {
			list = db.getTopFiveNames();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * This method returns the top five names of players with the highest scores.
	 * Class specific high scores.
	 * @param classID
	 * @return ArrayList<String>
	 */
	public static ArrayList<String> getTopFiveNames(int classID) {
		ArrayList<String> list = null;
		try {
			list = db.getTop5NamesClassroom(classID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * This method returns the top five scores of players with the highest scores.
	 * Global high scores.
	 * @return ArrayList<Integer>
	 */
	public static ArrayList<Integer> getTopFiveScores() {
		ArrayList<Integer> list = null;
		try {
			list = db.getTopFiveScores();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * This method returns the top five scores of players with the highest scores.
	 * Class specific high scores.
	 * @param classID
	 * @return ArrayList<Integer>
	 */
	public static ArrayList<Integer> getTopFiveScores(int classID) {
		ArrayList<Integer> list = null;
		try {
			list = db.getTop5ScoresClassroom(classID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * This method returns a string of users in a class.
	 * @return
	 */
	public static ArrayList<String> getClassList() {
		return db.getClassList(AccountManager.currentUserID);
	}

	/**
	 * This method returns the high score of a user.
	 * @param userID
	 * @return
	 */
	public static int getHighscore(int userID) {
		return db.getHighScore(userID);
	}

	/**
	 * Gets the played ID given their name
	 * @param name
	 * @return
	 */
	public static int getPlayerID(String name) {
		return db.getPlayerID(name);
	}

	/**
	 * This method checks if the current user is an admin or not.
	 * @return boolean
	 */
	public static boolean isAdmin() {
		return AccountManager.isAdmin;
	}
	
	/**
	 * This method checks if the current user is in the sandbox or not.
	 * @return boolean
	 */
	public static boolean isInSandbox() {
		return inSandbox;
	}
	
	/**
	 * This method is used to verify the login of a user.
	 * @param name
	 * @param password
	 * @param isAdmin
	 * @return boolean
	 */
	public static boolean verifyLogin(String name, String password, boolean isAdmin) {
		if(isAdmin) {
			if(db.verifyAdmin(name, password)) {
				AccountManager.currentUserID = db.getAdminID(name);
				AccountManager.isAdmin = true;
				AccountManager.setSandboxSaveState(db.getSandboxSaveStateAdmin(currentUserID));
				return true;
			}
		} else {
			if(db.verifyPlayer(name, password)) {
				AccountManager.currentUserID = db.getPlayerID(name);
				AccountManager.isAdmin = false;
				AccountManager.setSandboxSaveState(db.getSandboxSaveState(currentUserID));
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method is used to set the inSandbox boolean.
	 * True if the user is in the sandbox, false if they are not.
	 * @param inSandbox
	 */
	public static void setInSandbox(boolean inSandbox) {
		AccountManager.inSandbox = inSandbox;
	}
	
	/**
	 * This method sets the score of a level.
	 * @param levelID
	 * @param score
	 */
	public static void setLevelScore(int levelID, int score) {
		if(AccountManager.getLevelScore(levelID) <= score) {
			AccountManager.setHighScore(AccountManager.getHighscore(AccountManager.currentUserID) + score);
			db.updateLevelScore(levelID, db.getLevelScore(levelID) + score); 
		}
	}

	/**
	 * Sets the high score of the current user
	 * @param score
	 */
	public static void setHighScore(int score) {
		db.updateHighScore(AccountManager.currentUserID, score);
	}

	/**
	 * This method sets the save state of the sandbox.
	 * @param state
	 */
	public static void setSandboxSaveState(String state) {
		AccountManager.sandboxSaveState = state;
		if(isAdmin) {
			db.updateAdminSandBoxSave(AccountManager.currentUserID, AccountManager.sandboxSaveState);
		} else {
			db.updatePlayerSandboxSave(AccountManager.currentUserID, AccountManager.sandboxSaveState);
		}
	}
	
	/**
	 * This method updates the sandbox save state with the new position of an individual gate.
	 * @param state
	 */
	public static void setIndividualGate(String state) {
		if(!AccountManager.isInSandbox()) {
			return;
		}
		AccountManager.sandboxSaveState = AccountManager.sandboxSaveState + state;
		if(isAdmin) {
			db.updateAdminSandBoxSave(AccountManager.currentUserID, AccountManager.sandboxSaveState);
		} else {
			db.updatePlayerSandboxSave(AccountManager.currentUserID, AccountManager.sandboxSaveState);
		}
	}
	
	/**
	 * This method removes the old position of an individual gate from the sandbox save state.
	 * @param StartX
	 * @param StartY
	 * @param type
	 */
	public static void removeOldPosition(Double StartX, Double StartY, String type) {
		if(!AccountManager.isInSandbox()) {
			return;
		}
		String oldPosition = type + "," + StartX + "," + StartY + ",";
		AccountManager.sandboxSaveState = AccountManager.sandboxSaveState.replace(oldPosition, "");
		if(isAdmin) {
			db.updateAdminSandBoxSave(AccountManager.currentUserID, AccountManager.sandboxSaveState);
		} else {
			db.updatePlayerSandboxSave(AccountManager.currentUserID, AccountManager.sandboxSaveState);
		}
	}
	
	/**
	 * This method creates a new player account if a user signs up.
	 * @param username
	 * @param initials
	 * @param password
	 * @param email
	 * @param classID
	 */
	public static void newPlayerAccount(String username, String initials, String password, String email, int classID) {
		AccountManager.currentUserID = db.addPlayer(username, initials, password, email, classID);
		AccountManager.isAdmin = false;
		AccountManager.setSandboxSaveState("");
		db.printDB(); // Just for testing
	}
	
	/**
	 * This method creates a new admin account if a user signs up.
	 * @param username
	 * @param initials
	 * @param password
	 * @param email
	 * @param className
	 */
	public static void newAdminAccount(String username, String initials, String password, String email, String className) {
		AccountManager.currentUserID = db.addAdmin(username, initials, password, email);
		db.addClassroom(className, AccountManager.currentUserID);
		AccountManager.isAdmin = true;
		AccountManager.setSandboxSaveState("");
		db.printDB(); // Just for testing
	}
}