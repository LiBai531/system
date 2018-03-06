package com.physics.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.physics.dao.common.Dao;
import com.physics.entity.ExperimentInfo;
import com.physics.vm.PageVM;

public interface ExperimentInfoService {

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract boolean saveExperimentInfo(ExperimentInfo experimentInfo);
	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract boolean deleteExperimentInfo(ExperimentInfo experimentInfo);
	public abstract List<ExperimentInfo> getAllExperimentInfos();
	
	public abstract ExperimentInfo getExperimentInfoById(String id);
	
	public abstract PageVM<ExperimentInfo> getExperimentInfo(int pageIndex, int pageSize, boolean asc, String sidx);
}
