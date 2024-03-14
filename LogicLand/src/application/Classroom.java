package application;

public class Classroom {
	private String className;
	private int classID;
	private int teacherID;
	private String teacherName;
	private Database db = new Database();
	public Classroom(int playerID) {
		
		classID = db.getClassID(playerID);
		teacherID = playerID;
		className = db.getClassName(classID);
		teacherName = db.getAdminName(teacherID);
		
	}
	
	public String getClassInstructor() {
		return teacherName;
	}
	
	public int getClassID(){
		return classID;
	}
	
	public int getTeacherID() {
		return teacherID;
	}
	
	public String getTeacherName() {
		return teacherName;
	}
	
	public String getClassName() {
		return className;
	}
	
	
}
