package inventoryManagementSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InventoryStaff extends Admin {
	private SessionUser user;
	private List<ProductItem> productItems = new ArrayList<>();
	Database db = new Database();
	
	public InventoryStaff(SessionUser user) {
		// TODO Auto-generated constructor stub
		super(user);
	}
	
	@Override
	public void displayOptions() {
		// TODO Auto-generated method stub
		System.out.println("\t[1] View Inventory");
		System.out.println("\t[2] Log Out");
	}

	@Override
	public void dashboardMain() {
		// TODO Auto-generated method stub
		Scanner userIN = new Scanner(System.in);
		System.out.print(" Select Option: ");
		int choice = userIN.nextInt();
		switch (choice) {
			case 1:
				super.displayProducts();
				break;
			case 2:
				productItems.clear();
				user = null;
				db.closeConnection();
				Main main = new Main();
				main.startPage();
				break;
			default:
				break;
		}
	}
}
