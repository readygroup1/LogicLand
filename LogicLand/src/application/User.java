package application;

public class User {
	
	private String username;
	private String initials;
	private String email;
	private int highScore;
	private int sandboxID;
	private String intutorial;
	private int classID;
	private String className;
	private int playerID;
	
	public User (int PlayerID) {
		String player = AccountManager.getPlayer(PlayerID);
		String info[] = player.split(",");
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

	public String getUsername() {
		return username;
	}
	public String getInitials() {
		return initials;
	}

	public String getEmail() {
		return email;
	}

	public int getHighScore() {
		return highScore;
	}

	public int getSandboxID() {
		return sandboxID;
	}

	public String getIntutorial() {
		return intutorial;
	}

	public int getClassID() {
		return classID;
	}
	
	public int getPlayerID() {
		return playerID;
	}
	
	public String getClassName() {
		return className;
	}

	


	
	

	
	
	
}
