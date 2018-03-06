package com.physics.dao.base;

import java.util.List;

import com.physics.dao.common.Dao;
import com.physics.entity.ExperimentInfo;

public interface ExperimentInfoDao extends Dao<ExperimentInfo,String> {

	public abstract boolean saveExperimentInfo(ExperimentInfo experimentInfo);
	
	public abstract List<ExperimentInfo> getAllExperimentInfos();
	
	public abstract ExperimentInfo getExperimentInfoByid(String id);
	
	public abstract List<ExperimentInfo> getExperimentInfosByPage(int pageIndex, int pageSize, boolean asc, String sidx);
}
