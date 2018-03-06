package com.physics.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.physics.entity.ExperimentCount;

public interface ExperimentCountService {
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract String saveExperimentCount(ExperimentCount experimentCount);
	
	public abstract List<ExperimentCount> getExperimentCounts();
	
	public abstract ExperimentCount getExperimentCount(String id);
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract boolean updateExperimentCount(ExperimentCount experimentCount);
}
