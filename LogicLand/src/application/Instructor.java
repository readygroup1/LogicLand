package application;

public class Instructor {
	
	
	private String name;
	private int ClassID;
	private Classroom myRoom;
	
	
	public Instructor(int AdminID) {
		name = AccountManager.db.getAdminName(AdminID);
		ClassID = AccountManager.db.getClassIDAdmin(AdminID);
		myRoom = new Classroom(AdminID);
	}
	
	public String getName() {
		return name;
	}
	
	public int getClassID() {
		return ClassID;
	}
	

	public Classroom getClassroom() {
		return myRoom;
	}
	

}
