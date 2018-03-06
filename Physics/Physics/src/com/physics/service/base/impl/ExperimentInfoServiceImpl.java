package com.physics.service.base.impl;

import java.util.List;

import com.physics.dao.base.ExperimentInfoDao;
import com.physics.entity.ExperimentInfo;
import com.physics.service.base.ExperimentInfoService;
import com.physics.vm.PageVM;

public class ExperimentInfoServiceImpl implements ExperimentInfoService{

	private ExperimentInfoDao experimentInfoDao;
	public ExperimentInfoDao getExperimentInfoDao() {
		return experimentInfoDao;
	}

	public void setExperimentInfoDao(ExperimentInfoDao experimentInfoDao) {
		this.experimentInfoDao = experimentInfoDao;
	}

	
	public boolean saveExperimentInfo(ExperimentInfo experimentInfo) {
		experimentInfoDao.saveOrUpdate(experimentInfo);
		return true;
	}

	
	public List<ExperimentInfo> getAllExperimentInfos() {
		return experimentInfoDao.getAll();
	}

	
	public ExperimentInfo getExperimentInfoById(String id) {
		return experimentInfoDao.get(id);
	}

	
	public PageVM<ExperimentInfo> getExperimentInfo(int pageIndex, int pageSize, boolean asc, String sidx) {
		List<ExperimentInfo> experimentInfos = experimentInfoDao.getExperimentInfosByPage(pageIndex, pageSize, asc, sidx);
		PageVM<ExperimentInfo> exPageVM = new PageVM<ExperimentInfo>(pageIndex, experimentInfos.size(), pageSize,experimentInfos);
		return exPageVM;
	}

	
	public boolean deleteExperimentInfo(ExperimentInfo experimentInfo) {
		experimentInfoDao.delete(experimentInfo);
		return true;
	}

}
