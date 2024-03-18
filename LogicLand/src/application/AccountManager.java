package application;

import java.sql.SQLException;
import java.util.ArrayList;

public class AccountManager {
	// Variables ----------------------------------------------------
	private static int currentUserID;
	private static boolean isAdmin;
	public static Database db = new Database();
	private static String sandboxSaveState = "";
	private static boolean inSandbox = false;
	
	// Constructors --------------------------------------------------
	public AccountManager(int currentUserID, boolean isTeacher) {
		AccountManager.currentUserID = currentUserID;
		AccountManager.isAdmin = isTeacher;
	}
	
	public AccountManager() {
		AccountManager.currentUserID = -1;
		AccountManager.isAdmin = false;
		return;
	}
	
	// Boolean Methods -----------------------------------------------
	
	public static boolean isAdmin() {
		return AccountManager.isAdmin;
	}
	
	public static boolean isInSandbox() {
		return inSandbox;
	}
	
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
	
	// Getter methods -------------------------------------------------
	
	public static String getSandboxSaveState() {
		return sandboxSaveState;
	}
	
	public static int getCurrentUser() {
		return AccountManager.currentUserID;
	}
	
	public static ArrayList<String> getClassrooms() {
		return db.getClassrooms();
	}
	
	public static int getClassID(String name) {
		return db.getClassID(name);
	}

	public static String getClassName(int classID) {
		return db.getClassName(classID);
	}
	
	public static String getPlayer(int playerID) {
		return db.getPlayer(playerID);
	}

	public static int getLevelScore(int levelID) {
		return db.getLevelScore(levelID);
	}

	public static int getLevelID(int level) {
		return db.getLevelID(AccountManager.currentUserID, level);
	}
	
	public static int getCurrentClassID() {
		if(AccountManager.isAdmin) {
			return db.getClassIDAdmin(AccountManager.currentUserID);
		} else {
			return db.getClassID(AccountManager.currentUserID);
		}
	}
	
	public static ArrayList<String> getTopFiveNames() {
		ArrayList<String> list = null;
		try {
			list = db.getTopFiveNames();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static ArrayList<String> getTopFiveNames(int classID) {
		ArrayList<String> list = null;
		try {
			list = db.getTop5NamesClassroom(classID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static ArrayList<Integer> getTopFiveScores() {
		ArrayList<Integer> list = null;
		try {
			list = db.getTopFiveScores();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static ArrayList<Integer> getTopFiveScores(int classID) {
		ArrayList<Integer> list = null;
		try {
			list = db.getTop5ScoresClassroom(classID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// Setter methods ------------------------------------------------
	
	public static void setInSandbox(boolean inSandbox) {
		AccountManager.inSandbox = inSandbox;
	}
	
	public static void setLevelScore(int levelID, int score) {
		db.updateLevelScore(levelID, score);
	}

	public static void setSandboxSaveState(String state) {
		AccountManager.sandboxSaveState = state;
		if(isAdmin) {
			db.updateAdminSandBoxSave(AccountManager.currentUserID, AccountManager.sandboxSaveState);
		} else {
			db.updatePlayerSandboxSave(AccountManager.currentUserID, AccountManager.sandboxSaveState);
		}
	}
	
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
	
	public static void removeOldPosition(Double StartX, Double StartY, String type) {
		if(!AccountManager.isInSandbox()) {
			return;
		}
		//Look for string in the save state instance variable that contains and 'l'plug the dragStartX and dragStartY locations, will look
		// like this ex: "l,243.443,232.423"
		String oldPosition = type + "," + StartX + "," + StartY + ",";
		AccountManager.sandboxSaveState = AccountManager.sandboxSaveState.replace(oldPosition, "");
		if(isAdmin) {
			db.updateAdminSandBoxSave(AccountManager.currentUserID, AccountManager.sandboxSaveState);
		} else {
			db.updatePlayerSandboxSave(AccountManager.currentUserID, AccountManager.sandboxSaveState);
		}
	}
	
	public static void newPlayerAccount(String username, String initials, String password, String email, int classID) {
		AccountManager.currentUserID = db.addPlayer(username, initials, password, email, classID);
		AccountManager.isAdmin = false;
		AccountManager.setSandboxSaveState("");
		db.printDB(); // Just for testing
	}
	
	public static void newAdminAccount(String username, String initials, String password, String email, String className) {
		AccountManager.currentUserID = db.addAdmin(username, initials, password, email);
		db.addClassroom(className, AccountManager.currentUserID);
		AccountManager.isAdmin = true;
		AccountManager.setSandboxSaveState("");
		db.printDB(); // Just for testing
	}
	
	

	
}