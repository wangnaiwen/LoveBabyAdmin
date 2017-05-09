package com.wnw.lovebabyadmin.domain;

import java.io.Serializable;

public class Shop implements Serializable{
	private int id;
	private int userId;
	private String name;
	private String owner;
	private String wechat;
	private long money;
	private String idCard;
	private int reviewType;
	private int invitee;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public int getReviewType() {
		return reviewType;
	}
	public void setReviewType(int reviewType) {
		this.reviewType = reviewType;
	}

	public int getInvitee() {
		return invitee;
	}

	public void setInvitee(int invitee) {
		this.invitee = invitee;
	}
}
