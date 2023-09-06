package inventoryManagementSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin implements Dashboard{
	private SessionUser user;
	private List<ProductItem> productItems = new ArrayList<>();
	private List<Sales> salesHistory = new ArrayList<>();
	private List<Users> usersList = new ArrayList<>();
	boolean accountsUpdated = false;
	
	Database db = new Database();
	
	public Admin(SessionUser user) {
		// TODO Auto-generated constructor stub
		this.user = user;
	}

	@Override
	public void dashboardForm() {
		// TODO Auto-generated method stub
		System.out.println("\n=========================================================================\n");
		System.out.println("\tWelcome to JCG Stock Control");
		System.out.println("\n=========================================================================\n");
		displayOptions();
		System.out.println("\n=========================================================================\n");
		dashboardMain();
	}

	@Override
	public void displayOptions() {
		// TODO Auto-generated method stub
		System.out.println("\t[1] View Inventory");
		System.out.println("\t[2] View Sales History");
		System.out.println("\t[3] View User Accounts");
		System.out.println("\t[4] Log Out");
	}

	@Override
	public void dashboardMain() {
		// TODO Auto-generated method stub
		Scanner userIN = new Scanner(System.in);
		System.out.print(" Select Option: ");
		int choice = userIN.nextInt();
		switch (choice) {
			case 1:
				displayProducts();
				break;
			case 2:
				displaySalesHistory();
				break;
			case 3:
				displayUserAccounts();
				break;
			case 4:
				productItems.clear();
				salesHistory.clear();
				usersList.clear();
				user = null;
				db.closeConnection();
				Main main = new Main();
				main.startPage();
				break;
			default:
				break;
		}
	}
	
	protected void getProducts() {
		String sqlString = "SELECT ProductID, ProductName, ProductDescription, ProductType, Price, StockQuantity FROM products";
		ResultSet resultSet = db.executeSelect(sqlString);
		try {
			while(resultSet.next()) {
				productItems.add(new ProductItem(
							resultSet.getInt("ProductID"),
							resultSet.getString("ProductName"),
							resultSet.getString("ProductDescription"),
							resultSet.getString("ProductType"),
							resultSet.getDouble("Price"),
							resultSet.getInt("StockQuantity")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void displayProducts() {
		Scanner scanner = new Scanner(System.in);
		getProducts();
		System.out.println("\n=========================================================================\n");
		System.out.println("\t\tList of Products Available");
		System.out.println("\n=========================================================================\n");
		System.out.println("Product ID\t| Product Name\t\t| Price \t| Stock Quantity");
		for (ProductItem productItem : productItems) {
		    System.out.println(String.format(
		        "%d\t\t| %-20s\t| %.2f \t| %d",
		        productItem.getProductID(),
		        productItem.getProductName(),
		        productItem.getPrice(),
		        productItem.getStockQuantity()
		    ));
		}
		System.out.println("\n=========================================================================\n");
		System.out.println("\t[1] Add Product\t\t\t[3] Delete Product");
		System.out.println("\t[2] Modify Product Info\t\t[4] Back");
		System.out.println("\n=========================================================================\n");
		System.out.print(" Select Option: ");
		int choice = scanner.nextInt();
		switch (choice) {
			case 1:
				addProducts();
				break;
			case 2:
				updateProducts();
				break;
			case 3:
				deleteProduct();
				break;
			case 4:
				dashboardForm();
				break;
			default:
				break;
		}
	}
	
	protected void addProducts() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Product Name: ");
		String productName = scanner.nextLine();
		System.out.print("Enter Product Description: ");
		String productDesc = scanner.nextLine();
		System.out.print("Enter Product Type: ");
		String productType = scanner.nextLine();
		System.out.print("Enter Price: ");
		double price = scanner.nextDouble();
		System.out.print("Enter Stock Quantity: ");
		int quantity = scanner.nextInt();
		scanner.nextLine();
		System.out.println(String.format("Confirm Adding %s Product: [Y/N]", productName));
		String choice = scanner.nextLine();
		if (choice.toUpperCase().equals("Y")) {
			String sql = "INSERT INTO `products`(`ProductName`, `ProductDescription`, `ProductType`, `Price`, `StockQuantity`) "
					+ "VALUES "
					+ "(?,?,?,?,'?)"; 
			int result = db.executeCUD(sql, productName, productDesc,productType,price,quantity);
			if (result > 0) {
				System.out.println("Product Successfully Added");
			}else {
				System.out.println("Failed to Add Product");
			}
		}
		productItems.clear();
		displayProducts();
	}
	
	protected void updateProducts() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Product ID: ");
		int prodID = scanner.nextInt();
		ProductItem selectedProduct = productItems.stream()
	            .filter(product -> product.getProductID() == prodID)
	            .findFirst() // Get the first matching product, if any
	            .orElse(null); // If no matching product, return null
		System.out.println(String.format(
		        "%d\t\t| %-10s\t| %-30s\t| %-10s\t| %.2f \t| %d",
		        selectedProduct.getProductID(),
		        selectedProduct.getProductName(),
		        selectedProduct.getProductDescription(),
		        selectedProduct.getProductType(),
		        selectedProduct.getPrice(),
		        selectedProduct.getStockQuantity()
		    ));
		scanner.nextLine();
		System.out.println("Just Press Enter to not change the value...");
		System.out.print("Enter Product Name: ");
		String productName = scanner.nextLine();
		if (productName.length() == 0) {
			productName = selectedProduct.getProductName();
		}
		System.out.print("Enter Product Description: ");
		String productDesc = scanner.nextLine();
		if (productDesc.length() == 0) {
			productDesc = selectedProduct.getProductDescription();
		}
		System.out.print("Enter Product Type: ");
		String productType = scanner.nextLine();
		if (productType.length() == 0) {
			productType = selectedProduct.getProductType();
		}
		System.out.print("Enter Price: ");
		String priceString = scanner.nextLine();
		double price;
		if (priceString.length() == 0) {
			price = selectedProduct.getPrice();
		}else {
			price = Double.parseDouble(priceString);
		}
		System.out.print("Enter Stock Quantity: ");
		String quantityString = scanner.nextLine();
		int quantity;
		if (quantityString.length() == 0) {
			quantity = selectedProduct.getStockQuantity();
		}else {
			quantity = Integer.parseInt(quantityString);
		}
		
		System.out.println(String.format(
		        "%d\t| %-10s\t| %-20s\t| %-10s\t| %.2f \t| %d",
		        prodID,
		        productName,
		        productDesc,
		        productType,
		        price,
		        quantity
		));
		
		System.out.println(String.format("Confirm Updating %s Product Info: [Y/N]", productName));
		String choice = scanner.nextLine();
		if (choice.toUpperCase().equals("Y")) {
			String sql = "UPDATE products SET ProductName=?,"
					+ "ProductDescription=?,"
					+ "ProductType=?,"
					+ "Price=?,"
					+ "StockQuantity=?"
					+ " WHERE ProductID=?"; 
			int result = db.executeCUD(sql, productName, productDesc,productType,price,quantity,prodID);
			if (result > 0) {
				System.out.println("Product Successfully Updated");
			}else {
				System.out.println("Failed to Update Product");
			}
		}
		productItems.clear();
		displayProducts();
	}
	
	protected void deleteProduct() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Product Name: ");
		int prodID = scanner.nextInt();
		ProductItem selectedProduct = productItems.stream()
	            .filter(product -> product.getProductID() == prodID)
	            .findFirst() // Get the first matching product, if any
	            .orElse(null); // If no matching product, return null
		String productName = selectedProduct.getProductName();
		System.out.println(String.format("Confirm Deletion of %s Product: [Y/N]", productName));
		String choice = scanner.nextLine();
		if (choice.toUpperCase().equals("Y")) {
			String sql = "DELETE FROM products WHERE ProductID = ?"; 
			int result = db.executeCUD(sql, prodID);
			if (result > 0) {
				System.out.println("Product Deleted Successfully");
			}else {
				System.out.println("Failed to Delete Product");
			}
		}
		productItems.clear();
		displayProducts();
	}
	
	private void displaySalesHistory() {
		if (salesHistory.isEmpty()) {
			getSalesHistory();
		}
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n=========================================================================\n");
	 	System.out.println("\t\tSales History");
	 	System.out.println("\n=========================================================================\n");
	 	System.out.println("OrderNo\t|   Amount Paid\t    |     Total Price    | Change\t| Date\t\t| Cashier in-charge");
	 	for (Sales sales : salesHistory) {
	 	    System.out.println(String.format(
	 	        "%d\t| %12.2f\t    | %12.2f\t | %12.2f\t| %s\t| %s",
	 	        sales.getOrderNo(),
	 	        sales.getAmountPaid(),
	 	        sales.getTotalPrice(),
	 	        sales.getChangeAmount(),
	 	        sales.getSalesDate(),
	 	        sales.getUserInCharge()
	 	    ));
	 	}
	 	System.out.println("\n=========================================================================");
	 	System.out.println("\tPress any key to go back...");
		scanner.nextLine();
		System.out.println("=========================================================================\n");
		dashboardForm();
	}
	
	private void getSalesHistory() {
		String sql = "SELECT ordertransaction.OrderNo,"
				+ " ordertransaction.AmountPaid, ordertransaction.TotalPrice, "
				+ "ordertransaction.ChangeAmount, ordertransaction.Date, "
				+ "users.FirstName "
				+ "from ordertransaction INNER JOIN users on ordertransaction.UserID = users.userID;";
		
		ResultSet resultSet = db.executeSelect(sql);
		try {
			
			while(resultSet.next()) {
				salesHistory.add(new Sales(
							
							resultSet.getInt("OrderNo"),
							resultSet.getDouble("AmountPaid"),
							resultSet.getDouble("TotalPrice"),
							resultSet.getDouble("ChangeAmount"),
							resultSet.getDate("Date").toString(),
							resultSet.getString("FirstName")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getUserAccounts() {
		String sql = "SELECT userID, username, password, FirstName,"
				+ " MiddleName, LastName, Gender, Address, ContactNumber,"
				+ " EmailAddress, UserType FROM users";
		
		ResultSet resultSet = db.executeSelect(sql);
		try {
			
			while(resultSet.next()) {
				usersList.add(new Users(
							resultSet.getInt("userID"),
							resultSet.getString("username"),
							resultSet.getString("password"),
							resultSet.getString("FirstName"),
							resultSet.getString("MiddleName"),
							resultSet.getString("LastName"),
							resultSet.getString("Gender"),
							resultSet.getString("Address"),
							resultSet.getString("ContactNumber"),
							resultSet.getString("EmailAddress"),
							resultSet.getString("UserType")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void displayUserAccounts() {
		if(usersList.isEmpty() || accountsUpdated == true) {
			usersList.clear();
			getUserAccounts();
		}
		accountsUpdated = false;
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n=========================================================================\n");
	 	System.out.println("\t\tUser Accounts");
	 	System.out.println("\n=========================================================================\n");
	 	System.out.println("User ID | Full Name\t\t\t\t| User Type");
	 	for (Users userAcc : usersList) {
	 		String fullname = String.format("%s %s %s",userAcc.getfName(),userAcc.getmName(),userAcc.getlName());
	 	    System.out.println(String.format(
	 	    		"%d\t| %s\t| %s",
	 	    			userAcc.getUserID(),
	 	    			fullname,
	 	    			userAcc.getUserType()
	 	    		));
	 	}
	 	System.out.println("\n=========================================================================");
	 	System.out.println("\t[1] Add User\t\t\t[3] Delete User\t\t[5] Back");
		System.out.println("\t[2] Modify User Info\t\t[4] View One User");
		System.out.println("\n=========================================================================\n");
		System.out.print(" Select Option: ");
		int choice = scanner.nextInt();
		switch (choice) {
			case 1:
				addUser();
				break;
			case 2:
				updateUser();
				break;
			case 3:
				deleteUser();
				break;
			case 4:
				displayOneUser(getUser());
			 	System.out.println("\tPress any key to go back...");
				scanner.nextLine();
				scanner.nextLine();
				System.out.println("=========================================================================\n");
				displayUserAccounts();
				break;
			case 5:
				dashboardForm();
				break;
			default:
				break;
		}
	}
	
	private Users getUser() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter User ID: ");
		int userID = scanner.nextInt();
		Users selectedUser = usersList.stream()
	            .filter(user -> user.getUserID() == userID)
	            .findFirst() // Get the first matching product, if any
	            .orElse(null); // If no matching product, return null
		return selectedUser;
	}
	
	private void addUser() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Username: ");
		String usernameString = scanner.nextLine();
		
		System.out.print("Enter Password: ");
		String passwordString = scanner.nextLine();
		
		System.out.print("Enter First Name: ");
		String fnameString = scanner.nextLine();
		
		System.out.print("Enter Middle Name: ");
		String mNameString = scanner.nextLine();
		
		System.out.print("Enter Last Name: ");
		String lNameString = scanner.nextLine();
		
		boolean genderValid = true;
		String genderString = "";
		do {
			System.out.println("Choose Gender:\n"
					+ "[1] Male\n"
					+ "[2] Female\n"
					+ "[3] Non-binary");
			System.out.print("Enter Gender Option: ");
			int genderChoice = scanner.nextInt();
			switch (genderChoice) {
				case 1:
					genderString = "Male";
					break;
				case 2:
					genderString = "Female";
					break;
				case 3:
					genderString = "Non-Binary";
					break;
				default:
					genderValid = false;
					break;
			}
		} while (!genderValid);
		
		scanner.nextLine();
		System.out.print("Enter Address: ");
		String addressString = scanner.nextLine();
		
		System.out.print("Enter Contact Number: ");
		String contactNumString = scanner.nextLine();
		
		System.out.print("Enter Email Address: ");
		String emailaddressString = scanner.nextLine();
		
		boolean userTypeValid = true;
		String userTypeString = "";
		do {
			System.out.println("Choose Gender:\n"
					+ "[1] Admin\n"
					+ "[2] Cashier\n"
					+ "[3] Inventory Staff");
			System.out.print("Enter User Type Option: ");
			int userTypeChoice = scanner.nextInt();
			switch (userTypeChoice) {
				case 1:
					userTypeString = "Admin";
					break;
				case 2:
					userTypeString = "Cashier";
					break;
				case 3:
					userTypeString = "Inventory Staff";
					break;
				default:
					userTypeValid = false;
					break;
			}
		} while (!userTypeValid);
		
		scanner.nextLine();
		System.out.println("Confirm Adding User: [Y/N]");
		String confirmAdd = scanner.nextLine();
		if (confirmAdd.toUpperCase().equals("Y")) {
			String sqlString = "INSERT INTO users(username, password, FirstName, MiddleName, LastName,"
					+ " Gender, Address, ContactNumber, EmailAddress, UserType)"
					+ " VALUES (?,?,?,?,?,?,?,?,?,?)";
			int result = db.executeCUD(sqlString,
						usernameString,passwordString,
						fnameString,mNameString,lNameString,
						genderString,addressString,contactNumString,
						emailaddressString,userTypeString
					);
			if (result > 0) {
				System.out.println("User Account Successfully Added");
				accountsUpdated = true;
			}else {
				System.out.println("Failed to Add User Account");
			}
		}
		displayUserAccounts();
	}
	
	private void updateUser() {
		Scanner scanner = new Scanner(System.in);
		Users selectedUser = getUser();
		displayOneUser(selectedUser);
		int userID = selectedUser.getUserID();
		System.out.println("Just Press Enter to not change the value...");
		
		System.out.print("Enter Username: ");
		String usernameString = scanner.nextLine();
		if (usernameString.length() == 0) {
			usernameString = selectedUser.getUsername();
		}
		
		System.out.print("Enter Password: ");
		String passwordString = scanner.nextLine();
		if (passwordString.length() == 0) {
			passwordString = selectedUser.getPassword();
		}
		
		System.out.print("Enter First Name: ");
		String fnameString = scanner.nextLine();
		if (fnameString.length() == 0) {
			fnameString = selectedUser.getfName();
		}
		
		System.out.print("Enter Middle Name: ");
		String mNameString = scanner.nextLine();
		if (mNameString.length() == 0) {
			mNameString = selectedUser.getmName();
		}
		
		System.out.print("Enter Last Name: ");
		String lNameString = scanner.nextLine();
		if (lNameString.length() == 0) {
			lNameString = selectedUser.getlName();
		}
		
		String genderString = "";
		System.out.println("Choose Gender:\n"
				+ "[1] Male\n"
				+ "[2] Female\n"
				+ "[3] Non-binary");
		System.out.print("Enter Gender Option: ");
		String genderChoice = scanner.nextLine();
		switch (genderChoice) {
			case "1":
				genderString = "Male";
				break;
			case "2":
				genderString = "Female";
				break;
			case "3":
				genderString = "Non-Binary";
				break;
			default:
				genderString = selectedUser.getGender();
				break;
		}
		
		System.out.print("Enter Address: ");
		String addressString = scanner.nextLine();
		if (addressString.length() == 0) {
			addressString = selectedUser.getAddress();
		}
		
		System.out.print("Enter Contact Number: ");
		String contactNumString = scanner.nextLine();
		if (contactNumString.length() == 0) {
			contactNumString = selectedUser.getContactNum();
		}
		
		System.out.print("Enter Email Address: ");
		String emailaddressString = scanner.nextLine();
		if (emailaddressString.length() == 0) {
			emailaddressString = selectedUser.getEmailAdd();
		}
		
		String userTypeString = "";
		System.out.println("Choose Gender:\n"
				+ "[1] Admin\n"
				+ "[2] Cashier\n"
				+ "[3] Inventory Staff");
		System.out.print("Enter User Type Option: ");
		String userTypeChoice = scanner.nextLine();
		switch (userTypeChoice) {
			case "1":
				userTypeString = "Admin";
				break;
			case "2":
				userTypeString = "Cashier";
				break;
			case "3":
				userTypeString = "Inventory Staff";
				break;
			default:
				userTypeString = selectedUser.getUserType();
				break;
		}
		
		System.out.println("Confirm Adding User: [Y/N]");
		String confirmAdd = scanner.nextLine();
		if (confirmAdd.toUpperCase().equals("Y")) {
			String sqlString = "UPDATE users SET username=?,password=?,"
					+ "FirstName=?,MiddleName=?,LastName=?,"
					+ "Gender=?,Address=?,ContactNumber=?,"
					+ "EmailAddress=?,UserType=? WHERE userID=?";
			int result = db.executeCUD(sqlString,
						usernameString,passwordString,
						fnameString,mNameString,lNameString,
						genderString,addressString,contactNumString,
						emailaddressString,userTypeString,userID
					);
			if (result > 0) {
				System.out.println("User Info Successfully Updated");
				accountsUpdated = true;
			}else {
				System.out.println("Failed to Update User Info");
			}
		}
		displayUserAccounts();
	}
	
	private void displayOneUser(Users selectedUser) {
		System.out.println("\n=========================================================================\n");
		System.out.println("\t\t\tUser Account");
		System.out.println("\n=========================================================================\n");
		System.out.println(String.format("User ID\t\t:\t%d\n"
					+ "Username\t:\t%s\n"
					+ "Password\t:\t%s\n"
					+ "First Name\t:\t%s\n"
					+ "Middle Name\t:\t%s\n"
					+ "Last Name\t:\t%s\n"
					+ "Gender\t\t:\t%s\n"
					+ "Address\t\t:\t%s\n"
					+ "Contact Number\t:\t%s\n"
					+ "Email Address\t:\t%s\n"
					+ "User Type\t:\t%s",
					selectedUser.getUserID(),
					selectedUser.getUsername(),
					selectedUser.getPassword(),
					selectedUser.getfName(),
					selectedUser.getmName(),
					selectedUser.getlName(),
					selectedUser.getGender(),
					selectedUser.getAddress(),
					selectedUser.getContactNum(),
					selectedUser.getEmailAdd(),
					selectedUser.getUserType()
				));
		System.out.println("\n=========================================================================\n");
	}
	
	private void deleteUser() {
		Scanner scanner = new Scanner(System.in);
		Users selectedUser = getUser();
		int userID = selectedUser.getUserID();
		String fullname = String.format("%s %s",selectedUser.getfName(),selectedUser.getlName());
		System.out.println(String.format("Confirm Deletion of %s User Account: [Y/N]", fullname));
		String choice = scanner.nextLine();
		if (choice.toUpperCase().equals("Y")) {
			String sql = "DELETE FROM users WHERE userID =  ?"; 
			int result = db.executeCUD(sql, userID);
			if (result > 0) {
				System.out.println("User Account Successfully Deleted");
				accountsUpdated = true;
			}else {
				System.out.println("Failed to Delete User Account");
			}
		};
		displayUserAccounts();
	}
}
