package com.domain;

public class Article {
	private int article_id;
	private int user_id;
	private String article_content;
	private String article_image;
	private String article_time;

	public Article() {
		super();
	}

	

	public Article(int user_id, String article_content, String article_image,
			String article_time) {
		super();
		this.user_id = user_id;
		this.article_content = article_content;
		this.article_image = article_image;
		this.article_time = article_time;
	}




	public int getArticle_id() {
		return article_id;
	}




	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}




	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	public String getArticle_content() {
		return article_content;
	}

	public void setArticle_content(String article_content) {
		this.article_content = article_content;
	}

	public String getArticle_image() {
		return article_image;
	}

	public void setArticle_image(String article_image) {
		this.article_image = article_image;
	}

	public String getArticle_time() {
		return article_time;
	}

	public void setArticle_time(String article_time) {
		this.article_time = article_time;
	}

}
