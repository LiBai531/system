package com.physics.dao.base;

import java.util.List;

import com.physics.dao.common.Dao;
import com.physics.entity.News;

public interface NewsDao extends Dao<News, String>{

	public abstract boolean updateOrSaveNews(News news);
	
	public abstract boolean deleteNews(String id);
	
	public abstract List<News> getByhql(String hql, Object[] objects);
}
