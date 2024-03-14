package application;

public class Instructor {
	
	
	private String name;
	private int ClassID;
	
	
	public Instructor(int AdminID) {
		name = AccountManager.db.getAdminName(AdminID);
		ClassID = AccountManager.db.getClassID(AdminID);
	}
	

//	public Classroom getClassroom() {
//		
//		
//		AccountManager.db.getClassID(AccountManager.getCurrentUser());
//		
//		
//		return void;
//	
//}
	

}
