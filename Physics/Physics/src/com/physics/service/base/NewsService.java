package com.physics.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.physics.entity.ExperimentInfo;
import com.physics.entity.News;
import com.physics.vm.NewsVM;
import com.physics.vm.PageVM;

public interface NewsService {

	public abstract List<News> getAllNews();
	
	public abstract News getNews(String id);
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract boolean updateOrSaveNews(News news);
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract boolean deleteNews(String id);
	
	public abstract List<News> getByhql(String hql, Object[] objects);
	
	public abstract PageVM<NewsVM> getNewsManager(int pageIndex, int pageSize, boolean asc, String sidx);

}
