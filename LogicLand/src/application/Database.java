package application;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
    // Instance variables -------------------------------------------------------------------
    private String dbURLnocreate = "jdbc:derby:LogicLandDB;dataEncryption=true;encryptionAlgorithm=DES/CBC/NoPadding;bootPassword=brianstorm";
    private String dbURL = dbURLnocreate + ";create=true";
    private int numLevels = 8;
   
    // Constructor ---------------------------------------------------------------------------
    /**
     * Constructor for the Database class. This class is used to create and manage the database.
     */
    public Database() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            createDB();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Init -----------------------------------------------------------------------------------
    /**
     * This method is used to create the database if it does not exist. 
     */
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

    // Boolean methods ----------------------------------------------------------------------------------
    /** 
     * This method checks if the database exists. Used when creating the database.
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
     * This method verifies if the admin login exists in the database, returns true if there is a name and password that match.
     * @param name
     * @param password
     * @return boolean
     */
    public boolean verifyAdmin(String name, String password) {
        return executeQueryGetInt("SELECT AdminID FROM ADMIN WHERE AdminName = '" + name + "' AND AdminPassword = '"
                + password + "'") != -1;
    }

    
    /** 
     * This method verifies if the player login exists in the database, returns true if there is a name and password that match.
     * @param name
     * @param password
     * @return boolean
     */
    public boolean verifyPlayer(String name, String password) {
        return executeQueryGetInt("SELECT PlayerID FROM PLAYER WHERE Name = '" + name + "' AND Password = '"
                + password + "'") != -1;
    }

    
    /** 
     * This method verifies that the players username exists. Used when creating a new player to make sure there is no duplicate usernames.
     * @param name
     * @return boolean
     */
    public boolean userNameExists(String name) {
        return executeQueryGetInt("SELECT PlayerID FROM PLAYER WHERE Name = '" + name + "'") != -1;
    }

    
    /** 
     * This method verifies that the classroom name exists. Used when creating a new classroom to make sure there is no duplicate classroom names.
     * @param name
     * @return boolean
     */
    public boolean classroomNameExists(String name) {
        return executeQueryGetInt("SELECT ClassID FROM CLASSROOM WHERE ClassName = '" + name + "'") != -1;
    }

    
    /** 
     * This is a private method to execute an SQL statement. Used when a full method is not needed. Also as a helper method.
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

     // Getter methods ----------------------------------------------------------------------------------
    /** 
     * This method executes a query and returns the result as an int. Used when a full method is not needed. Also as a helper method.
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
     * This method gets the player ID given a player name.
     * @param name
     * @return int
     */
    public int getPlayerID(String name) {
        return executeQueryGetInt("SELECT PlayerID FROM PLAYER WHERE Name = '" + name + "'");
    }

    
    /** 
     * This method gets the admin ID given an admin name.
     * @param name
     * @return int
     */
    public int getAdminID(String name) {
        return executeQueryGetInt("SELECT AdminID FROM ADMIN WHERE AdminName = '" + name + "'");
    }

    
    /** 
     * This method gets the admin ID given a class ID.
     * @param ClassID
     * @return int
     */
    public int getAdminID(int ClassID) {
        return executeQueryGetInt("SELECT AdminID FROM CLASSROOM WHERE ClassID = " + ClassID);
    }
    
    
    /** 
     * This method gets the class ID given a player ID.
     * @param playerID
     * @return int
     */
    public int getClassID(int playerID) {
        return executeQueryGetInt("SELECT ClassID FROM PLAYER WHERE PlayerID = " + playerID);
    }

    
    /** 
     * This method gets the class ID given an admin ID.
     * @param adminID
     * @return int
     */
    public int getClassIDAdmin(int adminID) {
        return executeQueryGetInt("SELECT ClassID FROM CLASSROOM WHERE AdminID = " + adminID);
    }

    
    /** 
     * This method gets the admin name given the admin ID.
     * @param AdminID
     * @return String
     */
    public String getAdminName(int AdminID) {
        // Initialize a variable to hold player information
        String name = "Player not found";
        // SQL query to fetch all player details
        String SQLquery = "SELECT AdminName FROM ADMIN WHERE AdminID = " + AdminID;

        try (Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLquery)) {
            // Check if the player exists
            if (rs.next()) {
                // Construct the player information string

                
                name = rs.getString("AdminName");
                

                // Constructing the info string. Note: handling of the password might need to be
                // reconsidered for security reasons.
            }
        } catch (SQLException e) {
            e.printStackTrace();
            name = "Error retrieving player: " + e.getMessage();
        }

        return name;
    }
    
    
    /** 
     * @param PlayerID
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
     * @param AdminID
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
     * This method gets the player String for the user class given the player ID.
     * @param PlayerID
     * @return String
     */
    public String getPlayer(int PlayerID) {
        // Initialize a variable to hold player information
        String playerInfo = "Player not found";
        // SQL query to fetch all player details
        String SQLquery = "SELECT * FROM PLAYER WHERE PlayerID = " + PlayerID;

        try (Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLquery)) {
            // Check if the player exists
            if (rs.next()) {
                // Construct the player information string
                String playerID = rs.getString("PlayerID");
                String name = rs.getString("Name");
                String initials = rs.getString("Initials");
                String password = rs.getString("Password"); // Consider security implications of handling passwords
                String email = rs.getString("Email");
                int sandboxID = rs.getInt("SandboxID");
                boolean inTutorial = rs.getBoolean("inTutorial");
                int classID = rs.getInt("ClassID");

                // Constructing the info string. Note: handling of the password might need to be
                // reconsidered for security reasons.
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
     * This method gets the sandbox ID given the player ID.
     * @param PlayerID
     * @return int
     */
    public int getSandboxID(int PlayerID) {
        return executeQueryGetInt("SELECT SandboxID FROM PLAYER WHERE PlayerID = " + PlayerID);
    }

    
    /** 
     * @param AdminID
     * @return int
     */
    public int getSandboxIDAdmin(int AdminID) {
        return executeQueryGetInt("SELECT SandboxID FROM ADMIN WHERE AdminID = " + AdminID);
    }

    
    /** 
     * This method gets the level ID given the player ID and the current level.
     * @param PlayerID
     * @param currentLevel
     * @return int
     */
    public int getLevelID(int PlayerID, int currentLevel) {
        return executeQueryGetInt("SELECT LevelID FROM LEVELS WHERE PlayerID = " + PlayerID + " AND CurrentLevel = "
                + currentLevel);
    }

    
    /** 
     * This method gets the current level given the player ID.
     * @param PlayerID
     * @return int
     */
    public int getPlayerCurrentLevel(int PlayerID) {
        return executeQueryGetInt("SELECT CurrentLevel FROM LEVELS WHERE PlayerID = " + PlayerID);
    }

    
    /** 
     * This method gets the high score given the level ID.
     * @param PlayerID
     * @return int
     */
    public int getHighScore(int PlayerID) {
        return executeQueryGetInt("SELECT UserScore FROM HIGHSCORE WHERE PlayerID = " + PlayerID);
    }

    
    /** 
     * This method gets the top five names for the global high score.
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
     * This method gets the top five scores for the global high score.
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
     * This method gets the top five names for the classroom high score given the class ID.
     * @param classID
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
     * This method gets the top five scores for the classroom high score given the class ID.
     * @param classID
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
     * This method gets all available classrooms.
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
     * This method gets the class ID given a class name.
     * @param className
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
     * This method gets the class name given a class ID.
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
    
    // Boolean methods ----------------------------------------------------------------------------------
    /** 
     * This method adds a player and all associated tables. Returns the player ID.
     * @param name
     * @param initials
     * @param password
     * @param email
     * @param ClassID
     * @return int
     */
    public int addPlayer(String name, String initials, String password, String email, int ClassID) {
        // Count how many rows in table to find next primary key
        int primaryKey = executeQueryGetInt("SELECT COUNT(*) FROM PLAYER") + 1;
        // Count all rows in sandbox table to find next sandboxID
        int sandboxID = executeQueryGetInt("SELECT COUNT(*) FROM SANDBOX") + 1;
        // Create a new sandbox for the player
        executeSQL("INSERT INTO SANDBOX VALUES (" + sandboxID + ", 'New Project', CURRENT_DATE, '')");

        executeSQL("INSERT INTO PLAYER VALUES (" + primaryKey + ", '" + name + "', '" + initials + "', '" + password
                + "', '" + email
                + "', " + sandboxID + ", " + false + ", " + ClassID + ")");
        // Create 15 new levels for the player
        for (int i = 1; i <= numLevels; i++) {
            // count to find level primary key
            int levelPrimaryKey = executeQueryGetInt("SELECT COUNT(*) FROM LEVELS") + 1;
            executeSQL("INSERT INTO LEVELS VALUES (" + levelPrimaryKey + ", " + i + ", 0, '0000', " + primaryKey
                    + ")");
        }
        // Create a new highscore for the player
        executeSQL("INSERT INTO HIGHSCORE VALUES (" + primaryKey + ", '" + initials + "', 0)");
        return primaryKey;
    }

    
    /** 
     * This method adds a new admin to the database. Returns the admin ID.
     * @param name
     * @param initials
     * @param password
     * @param email
     * @return int
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
     * This method adds a new classroom to the database.
     * @param name
     * @param AdminID
     */
    public void addClassroom(String name, int AdminID) {
        // Count how many rows in table to find next primary key
        int primaryKey = executeQueryGetInt("SELECT COUNT(*) FROM CLASSROOM") + 1;
        executeSQL("INSERT INTO CLASSROOM VALUES (" + primaryKey + ", '" + name + "', " + AdminID + ")");
    }

    
    /** 
     * This method updates the save state of the player sandbox.
     * @param PlayerID
     * @param projectTitle
     * @param saveState
     */
    public void updatePlayerSandbox(int PlayerID, String projectTitle, String saveState) {
        // First get the sandbox ID from playerID
        int sandboxID = getSandboxID(PlayerID);
        executeSQL("UPDATE SANDBOX SET ProjectTitle = '" + projectTitle
                + "', LastModified = CURRENT_DATE, SaveState = '" + saveState + "' WHERE SandboxID = " + sandboxID);
    }

    
    /** 
     * This method updates the player's current sandbox save state.
     * @param PlayerID
     * @param saveState
     */
    public void updatePlayerSandboxSave(int PlayerID, String saveState) {
        // First get the sandbox ID from playerID
        int sandboxID = getSandboxID(PlayerID);
        executeSQL("UPDATE SANDBOX SET SaveState = '" + saveState + "' WHERE SandboxID = " + sandboxID);
    }

    
    /** 
     * @param AdminID
     * @param saveState
     */
    public void updateAdminSandBoxSave(int AdminID, String saveState) {
        // First get the sandbox ID from playerID
        int sandboxID = getSandboxIDAdmin(AdminID);
        executeSQL("UPDATE SANDBOX SET SaveState = '" + saveState + "' WHERE SandboxID = " + sandboxID);
    }
    
    /** 
     * This method updates the player's current level save state.
     * @param LevelID
     * @param LevelScore
     * @param CurrentLevelSaveState
     */
    public void updatePlayerProgress(int LevelID, int LevelScore, String CurrentLevelSaveState) {
        executeSQL("UPDATE LEVELS SET LevelScore = " + LevelScore + ", CurrentLevelSaveState = '"
                + CurrentLevelSaveState + "' WHERE LevelID = " + LevelID);
    }

    
    /** 
     * This method updates the player's general database.
     * @param PlayerID
     * @param name
     * @param password
     * @param email
     * @param ClassID
     * @param inTutorial
     */
    public void updatePlayer(int PlayerID, String name, String password, String email, int ClassID,
            boolean inTutorial) {
        executeSQL("UPDATE PLAYER SET Name = '" + name + "', Password = '" + password + "', Email = '" + email
                + "', ClassID = " + ClassID + ", inTutorial = " + inTutorial + " WHERE PlayerID = " + PlayerID);
    }

    
    /** 
     * This method updates the admin's general database.
     * @param AdminID
     * @param name
     * @param password
     * @param email
     */
    public void updateAdmin(int AdminID, String name, String password, String email) {
        executeSQL("UPDATE ADMIN SET AdminName = '" + name + "', AdminPassword = '" + password
                + "', AdminEmail = '" + email + "' WHERE AdminID = " + AdminID);
    }

    
    /** 
     * This method updates the high score for the player.
     * @param PlayerID
     * @param UserScore
     */
    public void updateHighScore(int PlayerID, int UserScore) {
        executeSQL("UPDATE HIGHSCORE SET UserScore = " + UserScore + " WHERE PlayerID = " + PlayerID);
    }

    
    /** 
     * This method deletes the player and all associated tables.
     * @param PlayerID
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

    // Misc methods -----------------------------------------------------------------------------------
    
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
