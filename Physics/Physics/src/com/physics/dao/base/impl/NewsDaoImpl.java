package com.physics.dao.base.impl;

import java.util.List;

import com.physics.dao.base.NewsDao;
import com.physics.dao.common.impl.DaoImpl;
import com.physics.entity.News;

public class NewsDaoImpl extends DaoImpl<News, String> implements NewsDao{

	public List<News> getAllNews() {
		
		return super.getAll();
	}

	public News getNews(String id) {
		return super.get(id);
	}

	public boolean updateOrSaveNews(News news) {
		super.saveOrUpdate(news);
		return true;
	}

	public boolean deleteNews(String id) {
		String hql = "delete from News where id = ?";
		super.hqlBulkUpdate(hql, new Object[]{id});
		return true;
	}

	public List<News> getByhql(String hql, Object[] objects) {
		return super.hqlFind(hql, objects);
	}

}
