package application.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.Database;
import application.Instructor;
import application.User;

class instructorTest {

	Instructor teacher = new Instructor(1);
	Database db = new Database();
	
	@Test
    void testGetName() {
		 
		assertTrue(!teacher.getName().isBlank(), "falied to return Instructors name");   
}

    @Test
    void testGetClassID() {
    	
        assertEquals(db.getAdminID(teacher.getClassID()), 1, "ClassID is incorrect.");
    }

    @Test
    void testGetClassroom() {
        assertEquals(db.getClassIDAdmin(1), teacher.getClassID(), "ClassID should match the expected value." );
        // Further assertions can be made depending on the behavior of Classroom class
    }

}
