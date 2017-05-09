package com.wnw.lovebabyadmin.domain;

/**
 * Created by wnw on 2017/4/16.
 */

public class Wallet {
    private int id;
    private int userId;
    private int money;
    private int password;
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
    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public int getPassword() {
        return password;
    }
    public void setPassword(int password) {
        this.password = password;
    }
}
