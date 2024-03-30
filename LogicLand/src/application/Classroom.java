package application;

/**
 * 
 * This class represents a classroom. Each class is associated with a name, ID, teacher (name and ID), and a database.
 * This class is used to keep track of students within a class and their progress.
 * <b>Example use:</b>
 * <pre>
 * {@code
 * Classroom class = new Classroom(currentPlayerID);
 * 
 * class.getClassID();
 *
 * }
 * 
 * 
 * <br>
 * @version 1.0
 * @since 1.0
 * @author Andres Pedreros
 */
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
	
	/**
	 * @return ID of this class.
	 */
	public int getClassID(){
		return classID;
	}
	
	/**
	 * @return ID of the instructor of the course.
	 */
	public int getTeacherID() {
		return teacherID;
	}
	
	/**
	 * @return Name of the instructor of the course.
	 */
	public String getTeacherName() {
		return teacherName;
	}
	
	/**
	 * @return Name of the course.
	 */
	public String getClassName() {
		return className;
	}
}


