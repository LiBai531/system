package com.physics.dao.base.impl;

import java.util.List;

import com.physics.dao.base.ExperimentStudentDao;
import com.physics.dao.common.impl.DaoImpl;
import com.physics.entity.ExperimentStudent;

public class ExperimentStudentDaoImpl extends DaoImpl<ExperimentStudent, String> implements ExperimentStudentDao{

	
	public List<ExperimentStudent> getExperimentStudentsByStudent(String id,String state,boolean flag) {
		String hql = "";
		if (flag) {
			hql = "from ExperimentStudent where userId = ? and state = ? and state <> ?";
		} else {
			hql = "from ExperimentStudent where userId = ? and state <> ? and state <> ?";
		}
		return super.hqlFind(hql, new Object[]{id,state,"4"});
	}

	
	public List<ExperimentStudent> hqlExperimentStudents(String hql, Object[] values) {
		return super.hqlFind(hql, values);
	}

	
	
}
