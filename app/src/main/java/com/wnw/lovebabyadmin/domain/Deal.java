package com.wnw.lovebabyadmin.domain;

import java.io.Serializable;

public class Deal implements Serializable{
	
	private int id;
	private int orderId;
	private int productId;
	private String productName;
	private int productCount;
	private long sumPrice;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	public long getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(long sumPrice) {
		this.sumPrice = sumPrice;
	}
	
}
