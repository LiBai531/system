package com.physics.service.base.impl;

import java.util.List;

import com.physics.dao.base.ExperimentCountDao;
import com.physics.entity.ExperimentCount;
import com.physics.service.base.ExperimentCountService;

public class ExperimentCountServiceImpl implements ExperimentCountService{

	private ExperimentCountDao experimentCountDao;
	public ExperimentCountDao getExperimentCountDao() {
		return experimentCountDao;
	}

	public void setExperimentCountDao(ExperimentCountDao experimentCountDao) {
		this.experimentCountDao = experimentCountDao;
	}

	
	public String saveExperimentCount(ExperimentCount experimentCount) {
		experimentCountDao.saveOrUpdate(experimentCount);
		return experimentCount.getId();
	}

	
	public List<ExperimentCount> getExperimentCounts() {
		
		return experimentCountDao.getAll();
	}

	
	public ExperimentCount getExperimentCount(String id) {
		return experimentCountDao.get(id);
	}

	
	public boolean updateExperimentCount(ExperimentCount experimentCount) {
		experimentCountDao.saveOrUpdate(experimentCount);

		return true;
	}

}
