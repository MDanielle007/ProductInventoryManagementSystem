package inventoryManagementSystem;import java.sql.ResultSet;
import java.util.Scanner;

public class LoginMain{
	Database db = new Database();
	protected SessionUser user;
	
	public LoginMain(SessionUser user) {
		this.user = user;
	}
	
	public void loginForm() {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("=========================================================================");
		System.out.println("\t\tLogin");
		System.out.println("=========================================================================\n");
		System.out.print("\tEnter Username: ");
		String username = scanner.nextLine();
		System.out.print("\tEnter Password: ");
		String password = scanner.nextLine();
		System.out.println("\n=========================================================================\n");
		
		verifyLogin(username, password);
		System.out.println("\n=========================================================================\n");
		String userType = user.getUserType();
		db.closeConnection();
		if(userType.equals("Cashier")) {
			Cashier cashier = new Cashier(user);
			cashier.dashboardForm();
		}else if (userType.equals("Inventory Staff")) {
			InventoryStaff inventoryStaff = new InventoryStaff(user);
			inventoryStaff.dashboardForm();
		}else if (userType.equals("Admin")) {
			Admin admin = new Admin(user);
			admin.dashboardForm();
		}
	}

	public void verifyLogin(String username, String password) {
		String sql = String.format("Select userID, firstName, lastName, userType from users where username = '%s' and password = '%s'",username, password);
		ResultSet resultSet = db.executeSelect(sql);
		try {
			if (resultSet.next()) {
				String userFullName = String.format("%s %s",resultSet.getString("firstName"),resultSet.getString("lastName"));
				System.out.println(String.format("   %s Login Successful",userFullName));
				user.setUserID(resultSet.getInt("userID"));
				user.setUsername(userFullName);
				user.setUserType(resultSet.getString("userType"));
				return;
			}else {
				System.out.println("\tInvalid username or password\n");
				loginForm();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
