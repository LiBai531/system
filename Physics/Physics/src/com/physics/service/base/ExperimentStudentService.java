package com.physics.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.physics.entity.ExperimentStudent;
import com.physics.vm.ExperimentStudentVM;

public interface ExperimentStudentService {

	public abstract ExperimentStudent getExperimentStudent(String id);
	
	public abstract List<ExperimentStudent> getAllExperimentStudents();
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract String saveExperimentStudent(ExperimentStudent experimentStudent);
	
	public abstract List<ExperimentStudentVM> getExperimentStudentsByStudents(String id,String state,boolean flag);
	
	public abstract boolean isExist(String userId, String expId);
	
	public abstract List<ExperimentStudent> getExperimentStudentsByExperimentId(String id);
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	public abstract boolean deleteExperimentStudentById(ExperimentStudent experimentStudent);

}
