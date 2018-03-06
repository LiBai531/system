package com.physics.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.physics.entity.Experiment;
import com.physics.vm.ExperimentVM;
import com.physics.vm.PageVM;

public interface ExperimentService {

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract boolean saveExperiment(Experiment experiment);
	
	public abstract List<Experiment> getExperimentsByTeacherId(String userId,String state);
	
	public abstract List<Experiment> getExperimentsByStudentId(String userId);
	
	public abstract Experiment getExperiment(String id);
	
	public abstract List<Experiment> getAllExperiments();
	
	abstract public List<Experiment> getAllExperiments(boolean flag);

	/**
	 * 教师获得已经结束的实验的列表
	 * @param pageIndex
	 * @param pageSize
	 * @param b
	 * @param string
	 * @return
	 */
	public abstract PageVM<ExperimentVM> getEndingExperiment(String userId, int pageIndex,
			int pageSize, boolean b, String string);
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public void deleteExperiment(String expId);
}
