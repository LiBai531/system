package com.physics.vm;

import com.physics.entity.Content;
import com.physics.entity.News;
import com.physics.entity.User;

public class NewsVM {

	private User user;
	private Content content;
	private String calander;
	private String id;
	
	public NewsVM(News news){
		setCalander(news);
		setContent(news);
		setId(news);
		setUser(news);
	}

	public User getUser() {
		return user;
	}

	public void setUser(News news) {
		this.user = news.getUser();
	}

	public Content getContent() {
		return content;
	}

	public void setContent(News news) {
		this.content = news.getContent();
	}

	public String getCalander() {
		return calander;
	}

	public void setCalander(News news) {
		this.calander = news.getCalendar().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(News news) {
		this.id = news.getId();
	}
	
	
	
}
