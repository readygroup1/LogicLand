package application;

/**
 * This class is used for storing information about the current user.
 * <br><br>
 * 
 * Information is collected through the {@link Database} in {@link AccountManager} and then stored in variables.
 *
 * <b>Example use:</b>
 * <pre>
 * {@code
 * 		User user = new User(AccountManager.currentUserID);
 * 		user.getUsername();
 * 		user.initials();
 * 		user.getEmail();
 * }
 * 
 * @version 1.0
 * @since 1.0
 * @see AccountManager
 * @see Database
 * @author Andres Pedreros 
 * */
public class User {
	/** The username of the current user */
	private String username;
	/** The initials of the current user */
	private String initials;
	/** The email of the current user */
	private String email;
	/** The high score of the current user */
	private int highScore;
	/** The sandbox ID of the current user */
	private int sandboxID;
	/** The intutorial of the current user */
	private String intutorial;
	/** The class ID of the current user */
	private int classID;
	/** The class name of the current user */
	private String className;
	/** The player ID of the current user */
	private int playerID;
	
	/**
	 * Constructor for the User class. It retrieves the information of the current user from the database, using the current playerID from account manager.
	 * @param PlayerID
	 */
	public User (int PlayerID) {
		String player = AccountManager.getPlayer(PlayerID);	//gets the information about a player in string format
		String info[] = player.split(",");	//divides the string into an array of the players attributes
		// sorts the information into the correct variables
		if(info.length > 7) {
			this.username = info[0];
			this.initials = info[1];
			this.email = info[3];
			this.sandboxID = Integer.parseInt(info[4]);
			this.intutorial = info[5];
			this.classID = Integer.parseInt(info[6]);   
			this.playerID = Integer.parseInt(info[7]);  
			this.highScore = AccountManager.db.getHighScore(playerID);
			this.className = AccountManager.db.getClassName(AccountManager.db.getClassID(playerID));
		}
		
		
	}

	
	/** 
	 * Gets the username of the current user.
	 * 
	 * @return String
	 */
	public String getUsername() {
		return username;
	}
	
	/** 
	 * Gets the initials of the current user.
	 * 
	 * @return String
	 */
	public String getInitials() {
		return initials;
	}

	
	/** 
	 * Gets the email of the current user.
	 * 
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	
	/** 
	 * Gets the high score of the current user.
	 * 
	 * @return int
	 */
	public int getHighScore() {
		return highScore;
	}

	
	/** 
	 * Gets the sandbox ID of the current user.
	 * 
	 * @return int
	 */
	public int getSandboxID() {
		return sandboxID;
	}

	
	/** 
	 * Gets the intutorial of the current user.
	 * 
	 * @return String
	 */
	public String getIntutorial() {
		return intutorial;
	}

	
	/** 
	 * Gets the class ID of the current user.
	 * 
	 * @return int
	 */
	public int getClassID() {
		return classID;
	}
	
	
	/** 
	 * Gets the player ID of the current user.
	 * 
	 * 
	 * @return int
	 */
	public int getPlayerID() {
		return playerID;
	}
	
	
	/** 
	 * Gets the class name of the current user.
	 * 
	 * @return String
	 */
	public String getClassName() {
		return className;
	}

	


	
	

	
	
	
}
