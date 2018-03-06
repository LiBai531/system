package com.physics.dao.base.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.LockMode;

import com.physics.dao.base.ExperimentInfoDao;
import com.physics.dao.common.impl.DaoImpl;
import com.physics.entity.ExperimentInfo;

public class ExperimentInfoDaoImpl extends DaoImpl<ExperimentInfo, String> implements ExperimentInfoDao{

	
	public boolean saveExperimentInfo(ExperimentInfo experimentInfo) {
		super.saveOrUpdate(experimentInfo);
		return true;
	}

	
	public List<ExperimentInfo> getAllExperimentInfos() {
		return super.getAll();
	}

	
	public ExperimentInfo getExperimentInfoByid(String id) {
		return super.get(id);
	}

	
	public List<ExperimentInfo> getExperimentInfosByPage(int pageIndex,
			int pageSize, boolean asc, String sidx) {
		StringBuilder hql= new  StringBuilder(" from ExperimentInfo p where state <> '0'");
		List<Object> params = new LinkedList<Object>();
		return super.hqlPage(hql.toString(), pageIndex, pageSize,!asc, "id", params.toArray());
	
	}

	

}
