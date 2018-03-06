package com.physics.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import com.physics.dao.base.ExperimentStudentDao;
import com.physics.entity.ExperimentStudent;
import com.physics.service.base.ExperimentStudentService;
import com.physics.vm.ExperimentStudentVM;
import com.physics.vm.PageVM;

public class ExperimentStudentServiceImpl implements ExperimentStudentService{

	private ExperimentStudentDao experimentStudentDao;
	public ExperimentStudentDao getExperimentStudentDao() {
		return experimentStudentDao;
	}

	public void setExperimentStudentDao(ExperimentStudentDao experimentStudentDao) {
		this.experimentStudentDao = experimentStudentDao;
	}

	
	public ExperimentStudent getExperimentStudent(String id) {
		
		return experimentStudentDao.get(id);
	}

	
	public List<ExperimentStudent> getAllExperimentStudents() {
		return experimentStudentDao.getAll();
	}

	
	public String saveExperimentStudent(ExperimentStudent experimentStudent) {
		experimentStudentDao.saveOrUpdate(experimentStudent);
		return experimentStudent.getId();
	}

	
	public List<ExperimentStudentVM> getExperimentStudentsByStudents(String id,String state,boolean flag) {
		List<ExperimentStudent> experimentStudents = experimentStudentDao.getExperimentStudentsByStudent(id,state,flag);
		List<ExperimentStudentVM> experimentStudentVM = new ArrayList<ExperimentStudentVM>();
		for (ExperimentStudent experimentStudent : experimentStudents) {
			if( "4".equals(experimentStudent.getState()))
				continue;
			ExperimentStudentVM experimentVM = new ExperimentStudentVM(experimentStudent);
			experimentStudentVM.add(experimentVM);
		}
		
		return experimentStudentVM;
	}

	
	public boolean isExist(String userId, String expId) {
		String hql = "from ExperimentStudent where userId = ? and experimentId = ? and state <> 4";
		List<ExperimentStudent> experimentStudents = experimentStudentDao.hqlExperimentStudents(hql, new Object[]{userId,expId});
		if (experimentStudents.size() != 0) {
			return true;
		}
		return false;
	}

	
	public List<ExperimentStudent> getExperimentStudentsByExperimentId(String expId) {
		String hql = "from ExperimentStudent where experimentId = ?";
		List<ExperimentStudent> experimentStudents = experimentStudentDao.hqlExperimentStudents(hql, new Object[]{expId});
		return experimentStudents;
	}

	
	public boolean deleteExperimentStudentById(ExperimentStudent experimentStudent) {
		experimentStudentDao.delete(experimentStudent);
		return true;
	}

}
