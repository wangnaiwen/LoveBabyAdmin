package com.wnw.lovebabyadmin.domain;

import java.io.Serializable;

public class Pr implements Serializable{
	private int id;
	private int dealId;
	private int userId;
	private String userNickName;
	private int productId;
	private int serviceScore;
	private int logisticsScore;
	private int productScore;
	private String evaluation;
	private String time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDealId() {
		return dealId;
	}
	public void setDealId(int dealId) {
		this.dealId = dealId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getServiceScore() {
		return serviceScore;
	}
	public void setServiceScore(int serviceScore) {
		this.serviceScore = serviceScore;
	}
	public int getLogisticsScore() {
		return logisticsScore;
	}
	public void setLogisticsScore(int logisticsScore) {
		this.logisticsScore = logisticsScore;
	}
	public int getProductScore() {
		return productScore;
	}
	public void setProductScore(int productScore) {
		this.productScore = productScore;
	}
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
