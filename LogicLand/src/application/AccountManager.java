package application;

public class AccountManager {
	// Instance variable
	private int currentUserID;
	public static Database db = new Database();
	
	public AccountManager(int currentUserID, boolean isTeacher) {
		this.currentUserID = currentUserID;
	}
	
	public AccountManager() {
		return;
	}
	
	public int getCurrentUser() {
		return currentUserID;
	}
	
	public void newPlayerAccount(String username, String initals, String password, String email, int classID) {
		db.addAdmin("andres", "123", "dj@hbkac");
		db.addClassroom("1", 1);
		db.addPlayer("pler", "PLA", "123", "1227", 1);
		db.printDB(); // Just for testing
	}
	
	
	//public boolean verifyLogin(String name, String password) {
		
	//}
	
}