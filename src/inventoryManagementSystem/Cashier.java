package inventoryManagementSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cashier extends Admin {
	private SessionUser user;
	private List<OrderedItem> productInCart = new ArrayList<>();
	private List<ProductItem> productItems = new ArrayList<>();
	Database db = new Database();
	
	public Cashier(SessionUser user) {
		// TODO Auto-generated constructor stub
		super(user);
	}
	
	@Override
	public void dashboardForm() {
		System.out.println("\n=========================================================================\n");
		System.out.println("\tWelcome to JCG Stock Control");
		System.out.println("\n=========================================================================\n");
		displayOptions();
		System.out.println("\n=========================================================================\n");
		dashboardMain();
	}
	
	@Override
	public void displayOptions() {
		int itemsNum = productInCart.size();
		System.out.println("    [1] Add Product");
		if(itemsNum > 0) {
			System.out.println("    [2] View Products in Cart");
			System.out.println("    [3] Calculate Products");
		}
		System.out.println("    [" + (itemsNum > 0? "4":"2") + "] Log Out");
	}

	@Override
	public void dashboardMain() {
		Scanner userIN = new Scanner(System.in);
		System.out.print(" Select Option: ");
		int choice = userIN.nextInt();
		Main main = new Main();
		if(productInCart.size() > 0) {
			switch (choice) {
				case 1:
					displayProducts();
					addingToCart();
					break;
				case 2:
					displayProductsInCart();
					break;
				case 3:
					CalculateProductsInCart();
					break;
				case 4:
					productInCart.clear();
					productItems.clear();
					user = null;
					db.closeConnection();
					main.startPage();
					break;
				default:
					break;
			}
		}else {
			switch (choice) {
				case 1:
					displayProducts();
					addingToCart();
					break;
				case 2:
					productInCart.clear();
					productItems.clear();
					user = null;
					db.closeConnection();
					main.startPage();
					break;
				default:
					break;
			}
		}
	}
	
	@Override
	protected void displayProducts() {
		super.getProducts();
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
	}
	
	private void displayProductsInCart() {
		Scanner scanner = new Scanner(System.in);
		double totalPrice = CalculateProductsInCart();
		System.out.println("\n=========================================================================\n");
		System.out.println("\t\tProducts In Cart");
		System.out.println("\n=========================================================================\n");
		System.out.println("Product ID\t| Product Name\t\t| Quantity \t| Subtotal");
		for (OrderedItem orderedItem : productInCart) {
		    System.out.println(String.format(
		        "%d\t\t| %-20s\t| %d \t\t| %.2f",
		        orderedItem.getOrderedItemID(),
		        orderedItem.getOrderedItemName(),
		        orderedItem.getQuantity(),
		        orderedItem.getSubtotal()
		    ));
		}
		System.out.println("_________________________________________________________________________");
		System.out.println(String.format("Total\t\t\t\t\t\t\t  %.2f",totalPrice));        
		System.out.println("=========================================================================\n");
		System.out.println("\t[1] Proceed Transaction\t[2] Back");
		System.out.println("\n=========================================================================\n");
		System.out.print(" Select Option: ");
		int choice = scanner.nextInt();
		switch (choice) {
		case 1:
			productsPayment(totalPrice);
			break;
		case 2:
			dashboardForm();
			break;
		default:
			break;
		}
	}
	
	private void addingToCart() {
		Scanner productScanner = new Scanner(System.in);
		
	    System.out.print("Select Product ID: ");
	    int prodID = productScanner.nextInt();
	    
	    // Find the product with the given ID
	    ProductItem selectedProduct = productItems.stream()
	            .filter(product -> product.getProductID() == prodID)
	            .findFirst() // Get the first matching product, if any
	            .orElse(null); // If no matching product, return null
	    
	    if (selectedProduct != null) {
	        String prodName = selectedProduct.getProductName();
	        System.out.println("Selected Product: " + prodName);
	        System.out.println("Product Price: " + selectedProduct.getPrice());
	        // Add your cart logic here
	        System.out.print("Enter Desired Quantity: ");
	        int quantity = productScanner.nextInt();
	        double subtotal = quantity * selectedProduct.getPrice();
	        System.out.println(String.format("Sub Total: %.2f", subtotal));
	        System.out.print("Confirm Adding to Cart: [Y/N]");
	        productScanner.nextLine();
	        String confirmation = productScanner.nextLine();
	        System.out.println(confirmation.toUpperCase());
	        if(confirmation.toUpperCase().equals("Y")) {
	        	productInCart.add(new OrderedItem(selectedProduct.getProductID(), selectedProduct.getProductName(),quantity,subtotal));
	        	dashboardForm();
	        }
	    } else {
	        System.out.println("No product found with the given ID.");
	    }
	}
	
	private int getLastTransactionNo() {
		String getTN = "SELECT `OrderNo` FROM `ordertransaction` order by OrderNo DESC Limit 1";
		ResultSet resultSet = db.executeSelect(getTN);
		try {
			if(resultSet.next()) {
				return resultSet.getInt("OrderNo") + 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	
	private void productsPayment(double totalPrice) {
		Scanner scanner = new Scanner(System.in);
		boolean validPayment = true;
		do {
			System.out.print("Enter Payment Amount: ");
			double payment = scanner.nextDouble();
			if (payment < totalPrice) {
				validPayment = false;
			}
			double changeAmount = payment - totalPrice;
			System.out.println(String.format("Change: %.2f",changeAmount));
			System.out.println("Confirm Transaction: [Y/N] ");
			scanner.nextLine();
			String confirmString = scanner.nextLine();
			if(confirmString.toUpperCase().equals("Y")) {
				int orderNo = getLastTransactionNo();
				commitTransaction(payment, totalPrice, changeAmount);
				updateProductsQuantity();
				recordOrderedItems(orderNo);
				productInCart.clear();
				productItems.clear();
				dashboardForm();
			}else {
				return;
			}
		} while (!validPayment);
	}
	
	private double CalculateProductsInCart() {
		double TotalPrice = 0;
		if (productInCart.size() > 0) {
			for (OrderedItem orderedItem : productInCart) {
				TotalPrice += orderedItem.getSubtotal();
			}
			return TotalPrice;
		}else {
			return 0;
		}
		
	}
	
	private void recordOrderedItems(int orderNo) {
		for (OrderedItem orderedItem : productInCart) {
			String sqlString = "INSERT INTO ordereditems(OrderNo, ProductID, Quantity, SubTotal)"
					+ " VALUES "
					+ "(?,?,?,?)";
			db.executeCUD(sqlString,orderNo,orderedItem.getOrderedItemID(),orderedItem.getQuantity(),orderedItem.getSubtotal());
		}
	}
	
	private void updateProductsQuantity() {
		for (OrderedItem orderedItem : productInCart) {
			String sqlString = "UPDATE products SET StockQuantity=? WHERE ProductID = ?";
			ProductItem selectedProduct = productItems.stream()
		            .filter(product -> product.getProductID() == orderedItem.getOrderedItemID())
		            .findFirst() // Get the first matching product, if any
		            .orElse(null); // If no matching product, return null
			int newQuantity = selectedProduct.getStockQuantity() - orderedItem.getQuantity();
			db.executeCUD(sqlString,newQuantity,orderedItem.getOrderedItemID());
		}
	}
	
	private void commitTransaction(double AmountPaid, double TotalPrice, double ChangeAmount) {
		String sql = "INSERT INTO ordertransaction (UserID, AmountPaid, TotalPrice, ChangeAmount, Date)\r\n"
				+ "VALUES \r\n"
				+ "(?,?,?,?,Now())";
		int resultSet = db.executeCUD(sql, user.getUserID(),AmountPaid,TotalPrice,ChangeAmount);
		if (resultSet > 0) {
			System.out.println("Transaction completed successfully");
		}else {
			System.err.println("Failed to complete transaction");
		}
		
	}
}
