package com.wnw.lovebabyadmin.domain;

import java.io.Serializable;

public class Article implements Serializable{
	private int id;
	private String author;
	private String time;
	private String title;
	private String content;
	private String coverImg;
	private int readTimes;
	private int likeTimes;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCoverImg() {
		return coverImg;
	}
	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
	public int getReadTimes() {
		return readTimes;
	}
	public void setReadTimes(int readTimes) {
		this.readTimes = readTimes;
	}
	public int getLikeTimes() {
		return likeTimes;
	}
	public void setLikeTimes(int likeTimes) {
		this.likeTimes = likeTimes;
	}
	
	
}
