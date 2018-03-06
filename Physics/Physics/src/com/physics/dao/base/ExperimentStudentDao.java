package com.physics.dao.base;

import java.util.List;

import com.physics.dao.common.Dao;
import com.physics.entity.ExperimentStudent;

public interface ExperimentStudentDao extends Dao<ExperimentStudent, String>{

	public abstract List<ExperimentStudent> getExperimentStudentsByStudent(String id,String state,boolean flag);
	
	public abstract List<ExperimentStudent> hqlExperimentStudents(String hql, Object[] values);
}
