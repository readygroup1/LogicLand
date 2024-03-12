package application;

public class User {
	
	private String username;
	private String initials;
	private String email;
	private String highScore;
	private String sandboxID;
	private String intutorial;
	private String classID;
	private String playerID;
	
	public User (String PlayerID) {
		String player = AccountManager.db.getPlayer(Integer.parseInt(PlayerID));
		
		String info[] = player.split(",");
		
		for(int i = 0; i < info.length; i++) {
			System.out.print(info.length);
		}
		
		this.username = info[0];
		this.initials = info[1];
		this.email = info[3];
		this.sandboxID = info[4];
		this.intutorial = info[5];
		this.classID = info[6];
		this.playerID = info[7];
		
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

	public String getHighScore() {
		return highScore;
	}

	public String getSandboxID() {
		return sandboxID;
	}

	public String getIntutorial() {
		return intutorial;
	}

	public String getClassID() {
		return classID;
	}

	


	
	

	
	
	
}
