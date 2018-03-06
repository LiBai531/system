package com.physics.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import com.physics.dao.base.ExperimentDao;
import com.physics.entity.Experiment;
import com.physics.service.base.ExperimentService;
import com.physics.vm.ExperimentVM;
import com.physics.vm.PageVM;

public class ExperimentServiceImpl implements ExperimentService{

	private ExperimentDao experimentDao;
	public ExperimentDao getExperimentDao() {
		return experimentDao;
	}

	public void setExperimentDao(ExperimentDao experimentDao) {
		this.experimentDao = experimentDao;
	}

	
	public boolean saveExperiment(Experiment experiment) {
		experimentDao.saveOrUpdate(experiment);
		return true;
	}

	
	public List<Experiment> getExperimentsByTeacherId(String userId,String state) {
		return experimentDao.getExperimentsByTeacherId(userId,state);
	}

	
	public List<Experiment> getExperimentsByStudentId(String userId) {
		return experimentDao.getExperimentsByStudentId(userId);
	}

	
	public Experiment getExperiment(String id) {
		return experimentDao.get(id);
	}

	
	public List<Experiment> getAllExperiments() {
		return experimentDao.getAll();
	}
	
	public List<Experiment> getAllExperiments(boolean flag) {
		String hql = "from Experiment where  state = ?";
		return experimentDao.hqlFind(hql, new Object[]{"0"});
	}

	public PageVM<ExperimentVM> getEndingExperiment(String userId, int pageIndex, int pageSize,
			boolean b, String string) {
		String hql = "from Experiment where userId = ? and state = ?";
		List<Experiment> experiments = experimentDao.getEndingExperimentByHql(hql,new Object[]{userId,"1"},pageIndex,pageSize,b,string);
		List<ExperimentVM> experimentVMs = new ArrayList<ExperimentVM>();
		for (Experiment experiment : experiments) {
			ExperimentVM experimentVM = new ExperimentVM(experiment);
			experimentVMs.add(experimentVM);
		}
		PageVM<ExperimentVM> pageVM = new PageVM<ExperimentVM>(pageIndex, experimentVMs.size(),pageSize, experimentVMs);
		return pageVM;
	}

	public void deleteExperiment(String expId){
		experimentDao.deleteByKey(expId);
	}
}
