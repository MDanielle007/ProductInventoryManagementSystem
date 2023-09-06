package inventoryManagementSystem;

public class OrderedItem {
	private int orderedItemID;
	private String orderedItemName;
	private int quantity;
	private double subtotal;
	
	OrderedItem() {
		// TODO Auto-generated constructor stub
	}
	
	OrderedItem(int orderedItemID, String orderedItemName, int quantity, double subtotal){
		this.orderedItemID = orderedItemID;
		this.orderedItemName = orderedItemName;
		this.quantity = quantity;
		this.subtotal = subtotal;
	}
	
	public int getOrderedItemID() {
		return orderedItemID;
	}
	public void setOrderedItemID(int orderedItemID) {
		this.orderedItemID = orderedItemID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public String getOrderedItemName() {
		return orderedItemName;
	}

	public void setOrderedItemName(String orderedItemName) {
		this.orderedItemName = orderedItemName;
	}
}
