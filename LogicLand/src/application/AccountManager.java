package application;

import java.sql.SQLException;
import java.util.ArrayList;

public class AccountManager {
	// Instance variable
	private static int currentUserID;
	private static boolean isAdmin;
	public static Database db = new Database();
	
	public AccountManager(int currentUserID, boolean isTeacher) {
		AccountManager.currentUserID = currentUserID;
		AccountManager.isAdmin = isTeacher;
	}
	
	public AccountManager() {
		AccountManager.currentUserID = -1;
		AccountManager.isAdmin = false;
		return;
	}
	
	public static boolean isAdmin() {
		return AccountManager.isAdmin;
	}
	
	public static int getCurrentUser() {
		return AccountManager.currentUserID;
	}
	
	public static void newPlayerAccount(String username, String initials, String password, String email, int classID) {
		AccountManager.currentUserID = db.addPlayer(username, initials, password, email, classID);
		AccountManager.isAdmin = false;
		db.printDB(); // Just for testing
	}
	
	public static void newAdminAccount(String username, String initials, String password, String email, String className) {
		AccountManager.currentUserID = db.addAdmin(username, initials, password, email);
		db.addClassroom(className, AccountManager.currentUserID);
		AccountManager.isAdmin = true;
		db.printDB(); // Just for testing
	}
	
	
	public static ArrayList<String> getClassrooms() {
		return db.getClassrooms();
	}
	
	public static int getClassID(String name) {
		return db.getClassID(name);
	}
	
	public static String getPlayer(int playerID) {
		return db.getPlayer(playerID);
	}
	
	public static int getCurrentClassID() {
		if(AccountManager.isAdmin) {
			return db.getClassIDAdmin(AccountManager.currentUserID);
		} else {
			return db.getClassID(AccountManager.currentUserID);
		}
	}
	
	public static boolean verifyLogin(String name, String password, boolean isAdmin) {
		if(isAdmin) {
			if(db.verifyAdmin(name, password)) {
				AccountManager.currentUserID = db.getAdminID(name);
				AccountManager.isAdmin = true;
				return true;
			}
		} else {
			if(db.verifyPlayer(name, password)) {
				AccountManager.currentUserID = db.getPlayerID(name);
				AccountManager.isAdmin = false;
				return true;
			}
		}
		return false;
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
	
}