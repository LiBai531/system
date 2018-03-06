package com.physics.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import com.physics.dao.base.NewsDao;
import com.physics.entity.News;
import com.physics.service.base.NewsService;
import com.physics.vm.NewsVM;
import com.physics.vm.PageVM;

public class NewsServiceImpl implements NewsService{
	
	private NewsDao newsDao;

	public NewsDao getNewsDao() {
		return newsDao;
	}

	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}

	public List<News> getAllNews() {
		return newsDao.getAll();
	}

	public News getNews(String id) {
		return newsDao.get(id);
	}

	public boolean updateOrSaveNews(News news) {
		newsDao.saveOrUpdate(news);
		return true;
	}

	public boolean deleteNews(String id) {
		newsDao.deleteNews(id);
		return true;
	}

	public List<News> getByhql(String hql, Object[] objects) {
		return newsDao.getByhql(hql, objects);
	}

	
	public PageVM<NewsVM> getNewsManager(int pageIndex, int pageSize,
			boolean asc, String sidx) {
		List<News> newsList = this.getAllNews();
		List<NewsVM> newsVMs = new ArrayList<NewsVM>();
		for (News news : newsList) {
			if( news.getType() == 2)
				continue;
			NewsVM newsVM = new NewsVM(news);
			newsVMs.add(newsVM);
		}
		
		PageVM<NewsVM> pageVM = new PageVM<NewsVM>(pageIndex, newsVMs.size(), pageSize, newsVMs);
		return pageVM;
	}

}
