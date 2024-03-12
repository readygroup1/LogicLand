package application;

import java.util.ArrayList;

public class AccountManager {
	// Instance variable
	private int currentUserID;
	private boolean isAdmin;
	public static Database db = new Database();
	
	public AccountManager(int currentUserID, boolean isTeacher) {
		this.currentUserID = currentUserID;
		isAdmin = isTeacher;
	}
	
	public AccountManager() {
		currentUserID = -1;
		isAdmin = false;
		return;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}
	
	public int getCurrentUser() {
		return currentUserID;
	}
	
	public void newPlayerAccount(String username, String initals, String password, String email, int classID) {
		
		db.addAdmin("andres", "123", "dj@hbkac", "lnscd");
		db.addClassroom("1", 1);
		db.addPlayer("pler", "PLA", "123", "1227", 1);
		currentUserID = db.addPlayer(username, initals, password, email, classID);
		isAdmin = false;
		db.printDB(); // Just for testing
		User player1 = new User("1");
		System.out.print(player1.getUsername());
		
	}
	
	public void newAdminAccount(String username, String initals, String password, String email, String className) {
		currentUserID = db.addAdmin(username, initals, password, email);
		db.addClassroom(className, currentUserID);
		isAdmin = true;
		db.printDB(); // Just for testing
	}
	
	
	public ArrayList<String> getClassrooms() {
		return db.getClassrooms();
	}
	
	public int getClassID(String name) {
		return db.getClassID(name);
	}
	
	
	public boolean verifyLogin(String name, String password, boolean isAdmin) {
		if(isAdmin) {
			if(db.verifyAdmin(name, password)) {
				currentUserID = db.getAdminID(name);
				this.isAdmin = true;
				return true;
			}
		} else {
			if(db.verifyPlayer(name, password)) {
				currentUserID = db.getPlayerID(name);
				this.isAdmin = false;
				return true;
			}
		}
		return false;
	}
	
}