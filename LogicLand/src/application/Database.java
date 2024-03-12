package application;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
	// Instance variables
	private String dbURLnocreate = "jdbc:derby:LogicLandDB;dataEncryption=true;encryptionAlgorithm=DES/CBC/NoPadding;bootPassword=brianstorm";
    private String dbURL = dbURLnocreate + ";create=true";
    private int numLevels = 8;
    
    public Database() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            createDB();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void createDB() {
        if(databaseExists()) {
            return;
        }
        executeSQL(
                "CREATE TABLE ADMIN (AdminID INT PRIMARY KEY, AdminName VARCHAR(255), Initals VARCHAR(255), AdminPassword VARCHAR(255), AdminEmail VARCHAR(255))");
        executeSQL(
                "CREATE TABLE CLASSROOM (ClassID INT PRIMARY KEY, ClassName VARCHAR(255), AdminID INT, FOREIGN KEY (AdminID) REFERENCES ADMIN(AdminID))");
        executeSQL(
                "CREATE TABLE SANDBOX (SandboxID INT PRIMARY KEY, ProjectTitle VARCHAR(255), LastModified DATE, SaveState VARCHAR(255))");
        executeSQL("CREATE TABLE PLAYER (" +
                "PlayerID INT PRIMARY KEY, " +
                "Name VARCHAR(255), " +
                "Initals VARCHAR(255), " +
                "Password VARCHAR(255), " +
                "Email VARCHAR(255), " +
                "SandboxID INT, " +
                "inTutorial BOOLEAN, " +
                "ClassID INT, " +
                "FOREIGN KEY (SandboxID) REFERENCES SANDBOX(SandboxID), " +
                "FOREIGN KEY (ClassID) REFERENCES CLASSROOM(ClassID) " +
                ")");
        executeSQL(
                "CREATE TABLE LEVELS (LevelID INT PRIMARY KEY, CurrentLevel INT, LevelScore INT, CurrentLevelSaveState VARCHAR(255), PlayerID INT, FOREIGN KEY (PlayerID) REFERENCES PLAYER(PlayerID))");
        executeSQL(
                "CREATE TABLE HIGHSCORE (PlayerID INT, Initals VARCHAR(255), UserScore INT, FOREIGN KEY (PlayerID) REFERENCES PLAYER(PlayerID))");
        
        addAdmin("defualt", "df", "password", "default@email.com");
        addClassroom("defaultClass", 1);
    }
    
    private boolean databaseExists() {
        try (Connection conn = DriverManager.getConnection(dbURLnocreate)) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private void executeSQL(String SQL) {
        try (Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement()) {
            stmt.execute(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unused")
    private ResultSet executeQuery(String SQLquery) {
        try (Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQLquery);
            return rs.next() ? rs : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unused")
    private int getInt(ResultSet rs, int index) {
        try (Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement()) {
            return rs.getInt(index);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
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
    
    // VERY IMPORTANT: Cannot add a player if no classrooms exist
    public void addPlayer(String name, String initals, String password, String email, int ClassID) {
        // Count how many rows in table to find next primary key
        int primaryKey = executeQueryGetInt("SELECT COUNT(*) FROM PLAYER") + 1;
        // Count all rows in sandbox table to find next sandboxID
        int sandboxID = executeQueryGetInt("SELECT COUNT(*) FROM SANDBOX") + 1;
        // Create a new sandbox for the player
        executeSQL("INSERT INTO SANDBOX VALUES (" + sandboxID + ", 'New Project', CURRENT_DATE, '0000')");

        executeSQL("INSERT INTO PLAYER VALUES (" + primaryKey + ", '" + name + "', '" + initals + "', '" + password + "', '" + email
                + "', " + sandboxID + ", " + false + ", " + ClassID + ")");
        // Create 15 new levels for the player
        for (int i = 1; i <=numLevels; i++) {
            // count to find level primary key
            int levelPrimaryKey = executeQueryGetInt("SELECT COUNT(*) FROM LEVELS") + 1;
            executeSQL("INSERT INTO LEVELS VALUES (" + levelPrimaryKey + ", " + i + ", 0, '0000', " + primaryKey
                    + ")");
        }
        // Create a new highscore for the player
        executeSQL("INSERT INTO HIGHSCORE VALUES (" + primaryKey + ", '" + initals + "', 0)");
    }
    
    public int addAdmin(String name, String initals, String password, String email) {
        // Count how many rows in table to find next primary key
        int primaryKey = executeQueryGetInt("SELECT COUNT(*) FROM ADMIN") + 1;
        executeSQL("INSERT INTO ADMIN VALUES (" + primaryKey + ", '" + name + "', '" + initals + "', '" + password + "', '" + email
                + "')");
        return primaryKey;
    }

    // VERY IMPORTANT: Cannot add a classroom if no admins exist
    public void addClassroom(String name, int AdminID) {
        // Count how many rows in table to find next primary key
        int primaryKey = executeQueryGetInt("SELECT COUNT(*) FROM CLASSROOM") + 1;
        executeSQL("INSERT INTO CLASSROOM VALUES (" + primaryKey + ", '" + name + "', " + AdminID + ")");
    }

    public void updatePlayerSandbox(int PlayerID, String projectTitle, String saveState) {
        // First get the sandbox ID from playerID
        int sandboxID = getSandboxID(PlayerID);
        executeSQL("UPDATE SANDBOX SET ProjectTitle = '" + projectTitle
                + "', LastModified = CURRENT_DATE, SaveState = '" + saveState + "' WHERE SandboxID = " + sandboxID);
    }

    public void updatePlayerProgress(int LevelID, int LevelScore, String CurrentLevelSaveState) {
        executeSQL("UPDATE LEVELS SET LevelScore = " + LevelScore + ", CurrentLevelSaveState = '"
                + CurrentLevelSaveState + "' WHERE LevelID = " + LevelID);
    }

    public void updatePlayer(int PlayerID, String name, String password, String email, int ClassID,
            boolean inTutorial) {
        executeSQL("UPDATE PLAYER SET Name = '" + name + "', Password = '" + password + "', Email = '" + email
                + "', ClassID = " + ClassID + ", inTutorial = " + inTutorial + " WHERE PlayerID = " + PlayerID);
    }

    public void updateAdmin(int AdminID, String name, String password, String email) {
        executeSQL("UPDATE ADMIN SET AdminName = '" + name + "', AdminPassword = '" + password
                + "', AdminEmail = '" + email + "' WHERE AdminID = " + AdminID);
    }

    public void updateHighScore(int PlayerID, int UserScore) {
        executeSQL("UPDATE HIGHSCORE SET UserScore = " + UserScore + " WHERE PlayerID = " + PlayerID);
    }

    public void deletePlayer(int PlayerID) {
        // first delete highscore for player
        executeSQL("DELETE FROM HIGHSCORE WHERE PlayerID = " + PlayerID);
        // then delete levels for player
        executeSQL("DELETE FROM LEVELS WHERE PlayerID = " + PlayerID);
        // then delete player
        int sandboxID = getSandboxID(PlayerID);
        executeSQL("DELETE FROM PLAYER WHERE PlayerID = " + PlayerID);
        //finally delete sandbox for player
        executeSQL("DELETE FROM SANDBOX WHERE SandboxID = " + sandboxID);
    }

    public void printDB() {
        try (Connection conn = DriverManager.getConnection(dbURL)) {
            DatabaseMetaData dbMetaData = conn.getMetaData();
            String[] types = { "TABLE" };
            ResultSet tables = dbMetaData.getTables(null, null, "%", types);

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);

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

    public int getAdminID(int ClassID) {
        return executeQueryGetInt("SELECT AdminID FROM CLASSROOM WHERE ClassID = " + ClassID);
    }

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
                String name = rs.getString("Name");
                String initials = rs.getString("Initals");
                String password = rs.getString("Password"); // Consider security implications of handling passwords
                String email = rs.getString("Email");
                int sandboxID = rs.getInt("SandboxID");
                boolean inTutorial = rs.getBoolean("inTutorial");
                int classID = rs.getInt("ClassID");
                
                // Constructing the info string. Note: handling of the password might need to be reconsidered for security reasons.
                playerInfo = String.format("Name: %s, Initials: %s, Password: %s, Email: %s, SandboxID: %d, InTutorial: %b, ClassID: %d",
                                            name, initials, password, email, sandboxID, inTutorial, classID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            playerInfo = "Error retrieving player: " + e.getMessage();
        }
        
        return playerInfo;
    }


    

    public int getSandboxID(int PlayerID) {
        return executeQueryGetInt("SELECT SandboxID FROM PLAYER WHERE PlayerID = " + PlayerID);
    }

    public int getLevelID(int PlayerID, int currentLevel) {
        return executeQueryGetInt("SELECT LevelID FROM LEVELS WHERE PlayerID = " + PlayerID + " AND CurrentLevel = "
                + currentLevel);
    }
    
    public int getPlayerCurrentLevel(int PlayerID) {
        return executeQueryGetInt("SELECT CurrentLevel FROM LEVELS WHERE PlayerID = " + PlayerID);
    }

    public int getHighScore(int PlayerID) {
        return executeQueryGetInt("SELECT UserScore FROM HIGHSCORE WHERE PlayerID = " + PlayerID);
    }
    
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

    public int getClassID(String className) {
    	try {
    		return executeQueryGetInt("SELECT ClassID FROM CLASSROOM WHERE ClassName = '" + className + "'");
    	} catch (Exception e) {
    		return -1;
    	}
    }

    public void resetDataBase() {
        executeSQL("DROP TABLE LEVELS");
        executeSQL("DROP TABLE HIGHSCORE");
        executeSQL("DROP TABLE PLAYER");
        executeSQL("DROP TABLE SANDBOX");
        executeSQL("DROP TABLE CLASSROOM");
        executeSQL("DROP TABLE ADMIN");
        createDB();
    }

}
