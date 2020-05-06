package com.domain;

public class Account {
	private int bill_id;
	private int user_id;
	private float bill_count;
	private String bill_inexType;
	private String bill_detailType;
	private String bill_imgRes;
	private String bill_time;
	private String bill_note;
	
	public Account() {
		super();
	
	}
	
	public Account(float bill_count, String bill_inexType, String bill_detailType, String bill_imgRes, String bill_time,
			String bill_note) {
		super();
		this.bill_count = bill_count;
		this.bill_inexType = bill_inexType;
		this.bill_detailType = bill_detailType;
		this.bill_imgRes = bill_imgRes;
		this.bill_time = bill_time;
		this.bill_note = bill_note;
	}

	public Account(int bill_id, int user_id, float bill_count, String bill_inexType, String bill_detailType,
			String bill_imgRes, String bill_time, String bill_note) {
		this.bill_id = bill_id;
		this.user_id = user_id;
		this.bill_count = bill_count;
		this.bill_inexType = bill_inexType;
		this.bill_detailType = bill_detailType;
		this.bill_imgRes = bill_imgRes;
		this.bill_time = bill_time;
		this.bill_note = bill_note;
	}
	public int getBill_id() {
		return bill_id;
	}
	public void setBill_id(int bill_id) {
		this.bill_id = bill_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public float getBill_count() {
		return bill_count;
	}
	public void setBill_count(float bill_count) {
		this.bill_count = bill_count;
	}
	public String getBill_inexType() {
		return bill_inexType;
	}
	public void setBill_inexType(String bill_inexType) {
		this.bill_inexType = bill_inexType;
	}
	public String getBill_detailType() {
		return bill_detailType;
	}
	public void setBill_detailType(String bill_detailType) {
		this.bill_detailType = bill_detailType;
	}
	public String getBill_imgRes() {
		return bill_imgRes;
	}
	public void setBill_imgRes(String bill_imgRes) {
		this.bill_imgRes = bill_imgRes;
	}
	public String getBill_time() {
		return bill_time;
	}
	public void setBill_time(String bill_time) {
		this.bill_time = bill_time;
	}
	public String getBill_note() {
		return bill_note;
	}
	public void setBill_note(String bill_note) {
		this.bill_note = bill_note;
	}
	
	
}
