package inventoryManagementSystem;

public class Users {
	private int userID;
	private String username;
	private String password;
	private String fName;
	private String mName;
	private String lName;
	private String gender;
	private String address;
	private String contactNum;
	private String emailAdd;
	private String userType;
	
	public Users() {
		// TODO Auto-generated constructor stub
	}
	
	public Users(int userID, String username, String password, String fName, String mName,
            String lName, String gender, String address, String contactNum,
            String emailAdd, String userType) {
		// TODO Auto-generated constructor stub
		setUserID(userID);
		setfName(fName);
		setmName(mName);
		setlName(lName);
		setUsername(username);
		setPassword(password);
		setGender(gender);
		setAddress(address);
		setContactNum(contactNum);
		setEmailAdd(emailAdd);
		setUserType(userType);
	}
	
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactNum() {
		return contactNum;
	}
	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}
	public String getEmailAdd() {
		return emailAdd;
	}
	public void setEmailAdd(String emailAdd) {
		this.emailAdd = emailAdd;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
}
