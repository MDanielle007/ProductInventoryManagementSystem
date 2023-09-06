package inventoryManagementSystem;

public class Sales {
	private int orderNo;
	private double amountPaid;
	private double totalPrice;
	private double changeAmount;
	private String salesDate;
	private String userInCharge;
	
	public Sales() {
		// TODO Auto-generated constructor stub
	}
	
	public Sales(int orderNo, double amountPaid, double totalPrice, double changeAmount, String salesDate, String userInCharge) {
		// TODO Auto-generated constructor stub
		setOrderNo(orderNo);
		setAmountPaid(amountPaid);
		setTotalPrice(totalPrice);
		setChangeAmount(changeAmount);
		setSalesDate(salesDate);
		setUserInCharge(userInCharge);
	}
	
	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(double changeAmount) {
		this.changeAmount = changeAmount;
	}

	public String getUserInCharge() {
		return userInCharge;
	}

	public void setUserInCharge(String userInCharge) {
		this.userInCharge = userInCharge;
	}

	public String getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}
}
