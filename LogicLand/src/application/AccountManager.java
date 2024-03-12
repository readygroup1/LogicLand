package application;

public class AccountManager {
	// Instance variable
	private int currentUserID;
	public static Database db = new Database();
	
	public AccountManager(int currentUserID) {
		this.currentUserID = currentUserID;
	}
	
	public int getCurrentUser() {
		return currentUserID;
	}
	
	public void saveAccount(String username, String password, String email, int classID) {
		db.addPlayer(username, password, email, classID);
	}
	
	
	//public boolean verifyLogin(String name, String password) {
		
	//}
	
}