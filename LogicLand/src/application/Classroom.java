package application;

public class Classroom {
	private String ClassName;
	private int ClassID;
	private int TeacherID;
	
	public Classroom(int playerID) {
		Database db = new Database();
		ClassID = db.getClassID(playerID);
		
	}
}
