package application;

/**
 * 
 * This class represents an Instructor of a course. 
 * Each instructor has a name, ID and a class that they teach.
 * 
 * @author Andres Pedreros
 */
public class Instructor {
	
	private String name;
	private int ClassID;
	private Classroom myRoom;
	
	/**
	 * 
	 * @param AdminID - an AdminID is used by an instructor or developer to use admin-exclusive functions.
	 */
	public Instructor(int AdminID) {
		name = AccountManager.db.getAdminName(AdminID);
		ClassID = AccountManager.db.getClassIDAdmin(AdminID);
		myRoom = new Classroom(AdminID);
	}

	/**
	 * 
	 * @return Name of the instructor.
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return ID of the class that this instructor teaches.
	 */
	public int getClassID() {
		return ClassID;
	}

	/**
	 * 
	 * @return Classroom which is the Classroom object associated with this instructor.
	 */
	public Classroom getClassroom() {
		return myRoom;
	}
	

}
