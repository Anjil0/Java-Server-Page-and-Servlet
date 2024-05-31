package model;

import java.time.LocalDate;
import java.util.Date;

public class OrderModel {
	private int orderId;
	private String customerName;
	private String productName;
	private int quantity;
	private int totalPrice;
	private int productPrice;
	private String orderDate;
	private String orderStatus; 

	public OrderModel(int orderId, String customerName, String productName, int quantity, int totalPrice,
			int productPrice, String orderDate, String orderStatus) {
		super();
		this.orderId = orderId;
		this.customerName = customerName;
		this.productName = productName;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.productPrice = productPrice;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
	}

	public OrderModel() {
	
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

}
