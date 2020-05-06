package com.domain;

public class Reply {
	private int reply_id;
	private int article_id;
	private int reply_user_id;
	private int reply_to_user_id;
	private String reply_content;
	
	public Reply() {
		super();
	}
	
	
	public Reply(int article_id, int reply_user_id, int reply_to_user_id, String reply_content) {
		super();
		this.article_id = article_id;
		this.reply_user_id = reply_user_id;
		this.reply_to_user_id = reply_to_user_id;
		this.reply_content = reply_content;
	}



	public Reply(int reply_id, int article_id, int reply_user_id, int reply_to_user_id, String reply_content) {
		this.reply_id = reply_id;
		this.article_id = article_id;
		this.reply_user_id = reply_user_id;
		this.reply_to_user_id = reply_to_user_id;
		this.reply_content = reply_content;
	}

	public int getReply_id() {
		return reply_id;
	}

	public void setReply_id(int reply_id) {
		this.reply_id = reply_id;
	}

	public int getArticle_id() {
		return article_id;
	}

	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}

	public int getReply_user_id() {
		return reply_user_id;
	}

	public void setReply_user_id(int reply_user_id) {
		this.reply_user_id = reply_user_id;
	}

	public int getReply_to_user_id() {
		return reply_to_user_id;
	}

	public void setReply_to_user_id(int reply_to_user_id) {
		this.reply_to_user_id = reply_to_user_id;
	}

	public String getReply_content() {
		return reply_content;
	}

	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}

}
