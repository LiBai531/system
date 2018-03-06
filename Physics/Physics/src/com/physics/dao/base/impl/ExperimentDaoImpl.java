package com.physics.dao.base.impl;

import java.util.List;

import com.physics.dao.base.ExperimentDao;
import com.physics.dao.common.impl.DaoImpl;
import com.physics.entity.Experiment;

public class ExperimentDaoImpl extends DaoImpl<Experiment, String> implements ExperimentDao{

	
	public List<Experiment> getExperimentsByTeacherId(String userId,String state) {
		String hqlString = "from Experiment as t where userId = ? and state = ?";
		return super.hqlFind(hqlString,new Object[]{userId,state});
	}

	
	public List<Experiment> getExperimentsByStudentId(String userId) {
		String hqlString = "";
		return super.hqlFind(hqlString);
	}


	public List<Experiment> getEndingExperimentByHql(String hql,
			Object[] objects, int pageIndex, int pageSize, boolean b,
			String string) {
		List<Experiment> experiments = super.hqlPage(hql, pageIndex, pageSize, b, string, objects);
		return experiments;
	}

}
