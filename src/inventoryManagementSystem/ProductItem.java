package inventoryManagementSystem;

public class ProductItem {
	private int productID;
	private String productName;
	private String productDescription;
	private String productType;
	private double price;
	private int stockQuantity;
	
	ProductItem(){
		
	}
	
	ProductItem(int productID, String productName, String productDescription, String productType, double price, int stockQuantity){
		this.productID = productID;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productType = productType;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}
	
	public int getProductID() {
		return productID;
	}
	
	public void setProductID(int productID) {
		this.productID = productID;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProductDescription() {
		return productDescription;
	}
	
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	
	public String getProductType() {
		return productType;
	}
	
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public int getStockQuantity() {
		return stockQuantity;
	}
	
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
}
