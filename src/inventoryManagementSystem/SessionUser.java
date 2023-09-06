package inventoryManagementSystem;

public class SessionUser {
	private int userID;
	private String userName;
	private String userType;
	
	SessionUser() {
		
	}
	
	SessionUser(String username, String userType){
		this.userName = username;
		this.userType = userType;
	}
	
	public String getUsername() {
		return userName;
	}
	
	public void setUsername(String username) {
		this.userName = username;
	}
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
}
