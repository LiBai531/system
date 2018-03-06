package com.physics.dao.base;

import java.util.List;

import com.physics.dao.common.Dao;
import com.physics.entity.Experiment;

public interface ExperimentDao extends Dao<Experiment, String>{

	public abstract List<Experiment> getExperimentsByTeacherId(String userId,String state);
	
	public abstract List<Experiment> getExperimentsByStudentId(String userId);

	/**
	 * 教师获取已结束实验列表
	 * @param hql
	 * @param objects
	 * @param pageIndex
	 * @param pageSize
	 * @param b
	 * @param string
	 * @return
	 */
	public abstract List<Experiment> getEndingExperimentByHql(String hql,
			Object[] objects, int pageIndex, int pageSize, boolean b,
			String string);
}
