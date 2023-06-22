package com.spring.batch;

import java.math.BigDecimal;
import java.util.Date;

public class Order {

	private long orderId;
	private String firstName;
	private String lastName;
	private BigDecimal cost;
	private String email;
	private long itemId;
	private String itemName;
	private Date purchaseDate;
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", firstName=" + firstName + ", lastName=" + lastName + ", cost=" + cost
				+ ", email=" + email + ", itemId=" + itemId + ", itemName=" + itemName + ", purchaseDate="
				+ purchaseDate + "]";
	}
	
	
	
	
}
