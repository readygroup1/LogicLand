package application;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class is used to interact with the database. It is used to create the database, add, delete, and update players, and get player information.
 * It is used directly in the class called {@link application.AccountManager.java}, which is static so each class can freely access the database.
 * 
 * <b>Example use:</b>
 * <pre>
 * {@code
 * Database db = new Database();
 * db.addPlayer("John", "JDO", "password", "john@doe.com", 1);
 * db.getPlayerID("John");
 * }
 * </pre>
 * <b>Output:</b>
 * <pre>
 * <b>Output:</b> <code>1</code>
 * <br>
 * @version 1.0
 * @since 1.0
 * @author Callum Thompson
 */
public class Database {
    /** The URL to connect to the database. This URL has creating a new database disabled. */
    private String dbURLnocreate = "jdbc:derby:LogicLandDB;dataEncryption=true;encryptionAlgorithm=DES/CBC/NoPadding;bootPassword=brianstorm";
    /** The URL to connect to the database. This URL has creating a new database enabled. */
    private String dbURL = dbURLnocreate + ";create=true";
    /** The number of levels in the game. */
    private int numLevels = 8;
   
    /**
     * The constructor for the database. It creates the database if it does not exist.
     */
    public Database() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            createDB();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createDB() {
        if (databaseExists()) {
            return;
        }
        executeSQL(
                "CREATE TABLE SANDBOX (SandboxID INT PRIMARY KEY, ProjectTitle VARCHAR(255), LastModified DATE, SaveState VARCHAR(8000))");
        executeSQL("CREATE TABLE ADMIN (AdminID INT PRIMARY KEY, AdminName VARCHAR(255), Initials VARCHAR(255), AdminPassword VARCHAR(255), AdminEmail VARCHAR(255), SandboxID INT, FOREIGN KEY (SandboxID) REFERENCES SANDBOX(SandboxID))");
        executeSQL(
                "CREATE TABLE CLASSROOM (ClassID INT PRIMARY KEY, ClassName VARCHAR(255), AdminID INT, FOREIGN KEY (AdminID) REFERENCES ADMIN(AdminID))");
        executeSQL("CREATE TABLE PLAYER (" +
                "PlayerID INT PRIMARY KEY, " +
                "Name VARCHAR(255), " +
                "Initials VARCHAR(255), " +
                "Password VARCHAR(255), " +
                "Email VARCHAR(255), " +
                "SandboxID INT, " +
                "inTutorial BOOLEAN, " +
                "ClassID INT, " +
                "FOREIGN KEY (SandboxID) REFERENCES SANDBOX(SandboxID), " +
                "FOREIGN KEY (ClassID) REFERENCES CLASSROOM(ClassID) " +
                ")");
        executeSQL(
                "CREATE TABLE LEVELS (LevelID INT PRIMARY KEY, CurrentLevel INT, LevelScore INT, CurrentLevelSaveState VARCHAR(8000), PlayerID INT, FOREIGN KEY (PlayerID) REFERENCES PLAYER(PlayerID))");
        executeSQL(
                "CREATE TABLE HIGHSCORE (PlayerID INT, Initials VARCHAR(255), UserScore INT, FOREIGN KEY (PlayerID) REFERENCES PLAYER(PlayerID))");

        addAdmin("default", "df", "password", "default@email.com");
        addClassroom("<public>", 1);
    }

    /** 
     * @param SQL
     */
    private void executeSQL(String SQL) {
        try (Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement()) {
            stmt.execute(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /** 
     * @return boolean
     */
    private boolean databaseExists() {
        try (Connection conn = DriverManager.getConnection(dbURLnocreate)) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /** 
     * @param SQLquery
     * @return int
     */
    private int executeQueryGetInt(String SQLquery) {
        try (Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQLquery);
            return rs.next() ? rs.getInt(1) : -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    /**
     * This method gets the score of a specific level given the level ID.
     * @param levelID The ID of the level.
     * @return int
     */
    public int getLevelScore(int levelID) {
        return executeQueryGetInt("SELECT LevelScore FROM LEVELS WHERE LevelID = " + levelID);
    }
    
    /**
     * This method gets the level number given the level ID.
     * @param levelID The ID of the level.
     * @return int
     */
    public int getLevelNumber(int levelID) {
        return executeQueryGetInt("SELECT CurrentLevel FROM LEVELS WHERE LevelID = " + levelID);
    }

    /**
     * This method gets the ID of a player given the player's name.
     * @param name The name of the player.
     * @return int
     */
    public int getPlayerID(String name) {
        return executeQueryGetInt("SELECT PlayerID FROM PLAYER WHERE Name = '" + name + "'");
    }

    /**
     * This method gets the ID of the admin given the admin's name.
     * @param name The name of the admin.
     * @return int
     */
    public int getAdminID(String name) {
        return executeQueryGetInt("SELECT AdminID FROM ADMIN WHERE AdminName = '" + name + "'");
    }

    /**
     * This method gets the ID of the admin given the admin's class ID.
     * @param int The ID of the class.
     * @return int
     */
    public int getAdminID(int ClassID) {
        return executeQueryGetInt("SELECT AdminID FROM CLASSROOM WHERE ClassID = " + ClassID);
    }
    
    /**
     * This method gets the class ID of a player given the player's ID.
     * @param playerID The ID of the player.
     * @return int
     */
    public int getClassID(int playerID) {
        return executeQueryGetInt("SELECT ClassID FROM PLAYER WHERE PlayerID = " + playerID);
    }

    /**
     * This method gets the class ID of the admin given the admin's ID.
     * @param adminID The ID of the admin.
     * @return int
     */
    public int getClassIDAdmin(int adminID) {
        return executeQueryGetInt("SELECT ClassID FROM CLASSROOM WHERE AdminID = " + adminID);
    }

    /**
     * This method gets the name of an admin given their ID.
     * @param AdminID The ID of the admin.
     * @return String
     */
    public String getAdminName(int AdminID) {
        String name = "Player not found";
        String SQLquery = "SELECT AdminName FROM ADMIN WHERE AdminID = " + AdminID;

        try (Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLquery)) {
            if (rs.next()) {
                name = rs.getString("AdminName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            name = "Error retrieving player: " + e.getMessage();
        }
        return name;
    }
    
    /**
     * This method gets the string of how the terminals are placed in the sandbox, given the player's ID.
     * @param PlayerID The ID of the player.
     * @return String
     */
    public String getSandboxSaveState(int PlayerID) {
        try (Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT SaveState FROM SANDBOX WHERE SandboxID = (SELECT SandboxID FROM PLAYER WHERE PlayerID = " + PlayerID + ")")) {
            if(rs.next()) {
                return rs.getString(1);
            } else {
            	return "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    /**
     * This method gets the string of how the terminals are placed in the sandbox, given the admin's ID.
     * @param AdminID The ID of the admin.
     * @return String
     */
    public String getSandboxSaveStateAdmin(int AdminID) {
        try (Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT SaveState FROM SANDBOX WHERE SandboxID = (SELECT SandboxID FROM ADMIN WHERE AdminID = " + AdminID + ")")) {
            if(rs.next()) {
                return rs.getString(1);
            } else {
            	return "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Error";
    }
    
    /**
     * This method gets the information of a player given the player's ID.
     * @param PlayerID The ID of the player.
     * @return String
     */
    public String getPlayer(int PlayerID) {
        String playerInfo = "Player not found";
        String SQLquery = "SELECT * FROM PLAYER WHERE PlayerID = " + PlayerID;

        try (Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLquery)) {
            if (rs.next()) {
                String playerID = rs.getString("PlayerID");
                String name = rs.getString("Name");
                String initials = rs.getString("Initials");
                String password = rs.getString("Password"); 
                String email = rs.getString("Email");
                int sandboxID = rs.getInt("SandboxID");
                boolean inTutorial = rs.getBoolean("inTutorial");
                int classID = rs.getInt("ClassID");

                playerInfo = String.format("%s,%s,%s,%s,%d,%b,%d,%s",
                        name, initials, password, email, sandboxID, inTutorial, classID, playerID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            playerInfo = "Error retrieving player: " + e.getMessage();
        }

        return playerInfo;
    }
    
    /**
     * This method gets the ID of a sanbox from the player's ID.
     * @param PlayerID The ID of the player.
     * @return int
     */
    public int getSandboxID(int PlayerID) {
        return executeQueryGetInt("SELECT SandboxID FROM PLAYER WHERE PlayerID = " + PlayerID);
    }

    /**
     * This method gets the ID of a sanbox from the admin's ID.
     * @param AdminID The ID of the admin.
     * @return int
     */
    public int getSandboxIDAdmin(int AdminID) {
        return executeQueryGetInt("SELECT SandboxID FROM ADMIN WHERE AdminID = " + AdminID);
    }

    /**
     * This method gets the ID of a specific level given the player's ID and the current level that they are on.
     * @param PlayerID The ID of the player. 
     * @param currentLevel The current level that the player is on.
     * @return int
     */
    public int getLevelID(int PlayerID, int currentLevel) {
        return executeQueryGetInt("SELECT LevelID FROM LEVELS WHERE PlayerID = " + PlayerID + " AND CurrentLevel = "
                + currentLevel);
    }

    /**
     * This method gets the current level that the player is on given the player's ID.
     * @param PlayerID The ID of the player.
     * @return int
     */
    public int getPlayerCurrentLevel(int PlayerID) {
        return executeQueryGetInt("SELECT CurrentLevel FROM LEVELS WHERE PlayerID = " + PlayerID);
    }

    /**
     * This method gets the highscore of a specific player given their ID.
     * @param PlayerID The ID of the player.
     * @return int
     */
    public int getHighScore(int PlayerID) {
        return executeQueryGetInt("SELECT UserScore FROM HIGHSCORE WHERE PlayerID = " + PlayerID);
    }

    /**
     * This method gets the top 5 names of users for the highscore page. 
     * This is for the global highscore page. 
     * @return ArrayList<String>
     * @throws SQLException
     */
    public ArrayList<String> getTopFiveNames() throws SQLException {
        // get the top 5 initials from high score table
        try {
            ArrayList<String> initials = new ArrayList<>();
            try (Connection conn = DriverManager.getConnection(dbURL);
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT Initials FROM HIGHSCORE ORDER BY UserScore DESC")) {
                for (int i = 0; i < 5; i++) {
                    if (rs.next()) {
                        initials.add(rs.getString(1));
                    }
                }
            }
            if(initials.size() < 5) {
            	for (int i = initials.size(); i < 5; i++) {
            		initials.add("AAA");
            	}
            }
            return initials;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method gets the top 5 scores of users for the highscore page. 
     * This is for the global highscore page. 
     * @return ArrayList<Integer>
     * @throws SQLException
     */
    public ArrayList<Integer> getTopFiveScores() throws SQLException {
        // get the top 5 scores from high score table
        try {
            ArrayList<Integer> scores = new ArrayList<>();
            try (Connection conn = DriverManager.getConnection(dbURL);
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT UserScore FROM HIGHSCORE ORDER BY UserScore DESC")) {
                for (int i = 0; i < 5; i++) {
                    if (rs.next()) {
                        scores.add(rs.getInt(1));
                    }
                }
            }
            if(scores.size() < 5) {
            	for (int i = scores.size(); i < 5; i++) {
            		scores.add(0);
            	}
            }
            return scores;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method gets the top 5 names of users for the highscore page. 
     * This is for the classroom highscore page. 
     * @param classID The ID of the classroom.
     * @return ArrayList<String>
     * @throws SQLException
     */
    public ArrayList<String> getTop5NamesClassroom(int classID) throws SQLException{
        // get the top 5 initials from high score table, each player must have same classroom ID
        try {
            ArrayList<String> initials = new ArrayList<>();
            try (Connection conn = DriverManager.getConnection(dbURL);
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT Initials FROM HIGHSCORE WHERE PlayerID IN (SELECT PlayerID FROM PLAYER WHERE ClassID = " + classID + ") ORDER BY UserScore DESC")) {
                for (int i = 0; i < 5; i++) {
                    if (rs.next()) {
                        initials.add(rs.getString(1));
                    }
                }
            }
            if(initials.size() < 5) {
            	for (int i = initials.size(); i < 5; i++) {
            		initials.add("AAA");
            	}
            }
            return initials;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method gets the top 5 scores of users for the highscore page. 
     * This is for the classroom highscore page. 
     * @param classID The ID of the classroom.
     * @return ArrayList<Integer>
     * @throws SQLException
     */
    public ArrayList<Integer> getTop5ScoresClassroom(int classID) throws SQLException {
        // get the top 5 scores from high score table, each player must have same classroom ID
        try {
            ArrayList<Integer> scores = new ArrayList<>();
            try (Connection conn = DriverManager.getConnection(dbURL);
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT UserScore FROM HIGHSCORE WHERE PlayerID IN (SELECT PlayerID FROM PLAYER WHERE ClassID = " + classID + ") ORDER BY UserScore DESC")) {
                for (int i = 0; i < 5; i++) {
                    if (rs.next()) {
                        scores.add(rs.getInt(1));
                    }
                }
            }
            if(scores.size() < 5) {
            	for (int i = scores.size(); i < 5; i++) {
            		scores.add(0);
            	}
            }
            return scores;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method gets a list of every classroom.
     * @return ArrayList<String>
     */
    public ArrayList<String> getClassrooms() {
        ArrayList<String> classrooms = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT ClassName FROM CLASSROOM")) {
            while (rs.next()) {
                classrooms.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classrooms;
    }
    
    /** 
     * This method gets the ID of a classroom given the classroom's name.
     * @param className The name of the classroom.
     * @return int
     */
    public int getClassID(String className) {
        try {
            return executeQueryGetInt("SELECT ClassID FROM CLASSROOM WHERE ClassName = '" + className + "'");
        } catch (Exception e) {
            return -1;
        }
    }

    /** 
     * This method gets the name of a classroom given the classroom's ID.
     * @param classID
     * @return String 
     */
    public String getClassName(int classID) {
    	try (Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT ClassName FROM CLASSROOM WHERE ClassID = " + classID)) {
           if(rs.next()) {
                return rs.getString(1);
           } else {
        	   return "Class not found";
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return "Error";
    }

    /**
     * This method gets the list of players in a specific classroom.
     * @param AdminID
     * @return
     */
    public ArrayList<String> getClassList(int AdminID) {
        ArrayList<String> classList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT Name FROM PLAYER WHERE ClassID = (SELECT ClassID FROM CLASSROOM WHERE AdminID = " + AdminID + ")")) {
            while (rs.next()) {
                classList.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classList;
    }
    
    /**
     * This method returns the highscore of a specific user.
     * @param userID
     * @return
     */
    public int getUserHighscore(int userID) {
        return executeQueryGetInt("SELECT UserScore FROM HIGHSCORE WHERE PlayerID = " + userID);
    }

    /**
     * This method returns a list of all the players in the database.
     * @return
     */
    public ArrayList<String> getAllStudentNames() {
        ArrayList<String> studentNames = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT Name FROM PLAYER")) {
            while (rs.next()) {
                studentNames.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentNames;
    }

    /**
     * This method returns a list of all the students in the database except for the ones in the class of the admin.
     * @param adminID
     * @return
     */
    public ArrayList<String> getAllStudentsExceptInClass(int adminID) {
        ArrayList<String> studentNames = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT Name FROM PLAYER WHERE ClassID != (SELECT ClassID FROM CLASSROOM WHERE AdminID = " + adminID + ")")) {
            while (rs.next()) {
                studentNames.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentNames;
    }

    /** 
     * This method verifies the admin's credentials. Used for login
     * @param name The name of the admin.
     * @param password The password of the admin.
     * @return boolean
     */
    public boolean verifyAdmin(String name, String password) {
        return executeQueryGetInt("SELECT AdminID FROM ADMIN WHERE AdminName = '" + name + "' AND AdminPassword = '"
                + password + "'") != -1;
    }

    /** 
     * This method verifies the player's credentials. Used for login
     * @param name The name of the player.
     * @param password The password of the player.
     * @return boolean
     */
    public boolean verifyPlayer(String name, String password) {
        return executeQueryGetInt("SELECT PlayerID FROM PLAYER WHERE Name = '" + name + "' AND Password = '"
                + password + "'") != -1;
    }

    /** 
     * This method checks to see if the user name of a player already exists.
     * @param name
     * @return boolean
     */
    public boolean userNameExists(String name) {
        return executeQueryGetInt("SELECT PlayerID FROM PLAYER WHERE Name = '" + name + "'") != -1;
    }

    /** 
     * This method checks to see if a classroom name already exists.
     * @param name The name of the classroom.
     * @return boolean
     */
    public boolean classroomNameExists(String name) {
        return executeQueryGetInt("SELECT ClassID FROM CLASSROOM WHERE ClassName = '" + name + "'") != -1;
    }
    
    /** 
     * This method adds a player to the database. Also creates tables for all associated data.
     * @param name The name of the player.
     * @param initials The initials of the player.
     * @param password The password of the player.
     * @param email The email of the player.
     * @param ClassID The ID of the classroom the player chooses to join.
     * @return int The ID of the player.
     */
    public int addPlayer(String name, String initials, String password, String email, int ClassID) {
        int primaryKey = executeQueryGetInt("SELECT COUNT(*) FROM PLAYER") + 1;
        int sandboxID = executeQueryGetInt("SELECT COUNT(*) FROM SANDBOX") + 1;
        executeSQL("INSERT INTO SANDBOX VALUES (" + sandboxID + ", 'New Project', CURRENT_DATE, '')");

        executeSQL("INSERT INTO PLAYER VALUES (" + primaryKey + ", '" + name + "', '" + initials + "', '" + password
                + "', '" + email
                + "', " + sandboxID + ", " + false + ", " + ClassID + ")");
        for (int i = 1; i <= numLevels; i++) {
            int levelPrimaryKey = executeQueryGetInt("SELECT COUNT(*) FROM LEVELS") + 1;
            executeSQL("INSERT INTO LEVELS VALUES (" + levelPrimaryKey + ", " + i + ", 0, '0000', " + primaryKey
                    + ")");
        }
        executeSQL("INSERT INTO HIGHSCORE VALUES (" + primaryKey + ", '" + initials + "', 0)");
        return primaryKey;
    }

    public void movePlayerClass(int PlayerID, int ClassID) {
        executeSQL("UPDATE PLAYER SET ClassID = " + ClassID + " WHERE PlayerID = " + PlayerID);
    }
    
    /** 
     * This method adds an admin to the database. Also creates tables for all associated data.
     * @param name The name of the admin.
     * @param initials The initials of the admin.
     * @param password The password of the admin.
     * @param email The email of the admin.
     * @return int The ID of the admin.
     */
    public int addAdmin(String name, String initials, String password, String email) {
        // Count how many rows in table to find next primary key
        int primaryKey = executeQueryGetInt("SELECT COUNT(*) FROM ADMIN") + 1;

        int sandboxID = executeQueryGetInt("SELECT COUNT(*) FROM SANDBOX") + 1;
        // Create a new sandbox for the player
        executeSQL("INSERT INTO SANDBOX VALUES (" + sandboxID + ", 'New Project', CURRENT_DATE, '')");

        executeSQL("INSERT INTO ADMIN VALUES (" + primaryKey + ", '" + name + "', '" + initials + "', '" + password
                + "', '" + email + "', " + sandboxID + ")");
        return primaryKey;
    }
    
    /** 
     * This method adds a classroom to the database. 
     * @param name The name of the classroom.
     * @param AdminID The ID of the admin.
     */
    public void addClassroom(String name, int AdminID) {
        // Count how many rows in table to find next primary key
        int primaryKey = executeQueryGetInt("SELECT COUNT(*) FROM CLASSROOM") + 1;
        executeSQL("INSERT INTO CLASSROOM VALUES (" + primaryKey + ", '" + name + "', " + AdminID + ")");
    }
    
    /** 
     * This method updates the player's sandbox.
     * @param PlayerID The ID of the player.
     * @param projectTitle The title of the project.
     * @param saveState The state of the sandbox.
     */
    public void updatePlayerSandbox(int PlayerID, String projectTitle, String saveState) {
        // First get the sandbox ID from playerID
        int sandboxID = getSandboxID(PlayerID);
        executeSQL("UPDATE SANDBOX SET ProjectTitle = '" + projectTitle
                + "', LastModified = CURRENT_DATE, SaveState = '" + saveState + "' WHERE SandboxID = " + sandboxID);
    }
    
    /** 
     * This method updates the players sandbox but only the save state.
     * @param PlayerID The ID of the player.
     * @param saveState The new save state of the sandbox.
     */
    public void updatePlayerSandboxSave(int PlayerID, String saveState) {
        // First get the sandbox ID from playerID
        int sandboxID = getSandboxID(PlayerID);
        executeSQL("UPDATE SANDBOX SET SaveState = '" + saveState + "' WHERE SandboxID = " + sandboxID);
    }
    
    /** 
     * This method updates the admin's sandbox.
     * @param AdminID The ID of the admin.
     * @param saveState The new save state of the sandbox.
     */
    public void updateAdminSandBoxSave(int AdminID, String saveState) {
        // First get the sandbox ID from playerID
        int sandboxID = getSandboxIDAdmin(AdminID);
        executeSQL("UPDATE SANDBOX SET SaveState = '" + saveState + "' WHERE SandboxID = " + sandboxID);
    }
    
    /** 
     * This method updates the player's progress in the levels.
     * @param LevelID The ID of the level.
     * @param LevelScore The score of the level.
     * @param CurrentLevelSaveState The state of the level.
     */
    public void updatePlayerProgress(int LevelID, int LevelScore, String CurrentLevelSaveState) {
        executeSQL("UPDATE LEVELS SET LevelScore = " + LevelScore + ", CurrentLevelSaveState = '"
                + CurrentLevelSaveState + "' WHERE LevelID = " + LevelID);
    }
    
    /** 
     * This method updates the player's level score in a specific level.
     * @param LevelID The ID of the level.
     * @param LevelScore The score of the level.
     */
    public void updateLevelScore(int LevelID, int LevelScore) {
        executeSQL("UPDATE LEVELS SET LevelScore = " + LevelScore + " WHERE LevelID = " + LevelID);
    }
    
    /** 
     * This method updates the player's information.
     * @param PlayerID The ID of the player.
     * @param name The name of the player.
     * @param password The password of the player.
     * @param email The email of the player.
     * @param ClassID The ID of the classroom the player is in.
     * @param inTutorial The state of the tutorial.
     */
    public void updatePlayer(int PlayerID, String name, String password, String email, int ClassID,
            boolean inTutorial) {
        executeSQL("UPDATE PLAYER SET Name = '" + name + "', Password = '" + password + "', Email = '" + email
                + "', ClassID = " + ClassID + ", inTutorial = " + inTutorial + " WHERE PlayerID = " + PlayerID);
    }
    
    /** 
     * This method updates the admin's information.
     * @param AdminID The ID of the admin.
     * @param name The name of the admin.
     * @param password The password of the admin.
     * @param email The email of the admin.
     */
    public void updateAdmin(int AdminID, String name, String password, String email) {
        executeSQL("UPDATE ADMIN SET AdminName = '" + name + "', AdminPassword = '" + password
                + "', AdminEmail = '" + email + "' WHERE AdminID = " + AdminID);
    }
    
    /** 
     * This method updates the highscore of a player.
     * @param PlayerID The ID of the player.
     * @param UserScore The score of the player.
     */
    public void updateHighScore(int PlayerID, int UserScore) {
        executeSQL("UPDATE HIGHSCORE SET UserScore = " + UserScore + " WHERE PlayerID = " + PlayerID);
    }
    
    /** 
     * This method deletes a player from the database.
     * @param PlayerID The ID of the player.
     */
    public void deletePlayer(int PlayerID) {
        // first delete highscore for player
        executeSQL("DELETE FROM HIGHSCORE WHERE PlayerID = " + PlayerID);
        // then delete levels for player
        executeSQL("DELETE FROM LEVELS WHERE PlayerID = " + PlayerID);
        // then delete player
        int sandboxID = getSandboxID(PlayerID);
        executeSQL("DELETE FROM PLAYER WHERE PlayerID = " + PlayerID);
        // finally delete sandbox for player
        executeSQL("DELETE FROM SANDBOX WHERE SandboxID = " + sandboxID);
    }
    
    /** 
     * This method prints the current state of the database to the console.
     */
    public void printDB() {
        try (Connection conn = DriverManager.getConnection(dbURL)) {
            DatabaseMetaData dbMetaData = conn.getMetaData();
            String[] types = { "TABLE" };
            ResultSet tables = dbMetaData.getTables(null, null, "%", types);

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                // Get columns for the table
                ResultSet columns = dbMetaData.getColumns(null, null, tableName, "%");
                StringBuilder columnNames = new StringBuilder("| ");
                while (columns.next()) {
                    columnNames.append(columns.getString("COLUMN_NAME")).append(" | ");
                }
                System.out.println(columnNames.toString());

                // Get data for the table
                try (Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {

                    while (rs.next()) {
                        StringBuilder rowData = new StringBuilder("| ");
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            rowData.append(rs.getString(i)).append(" | ");
                        }
                        System.out.println(rowData.toString());
                    }
                }
                System.out.println("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** 
     * This method resets the database to its original state.
     */
    public void resetDataBase() {
        executeSQL("DROP TABLE LEVELS");
        executeSQL("DROP TABLE HIGHSCORE");
        executeSQL("DROP TABLE PLAYER");
        executeSQL("DROP TABLE CLASSROOM");
        executeSQL("DROP TABLE ADMIN");
        executeSQL("DROP TABLE SANDBOX");
        createDB();
    }
}
