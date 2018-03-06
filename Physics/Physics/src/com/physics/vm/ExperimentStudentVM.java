package com.physics.vm;

import com.physics.entity.Experiment;
import com.physics.entity.ExperimentCount;
import com.physics.entity.ExperimentInfo;
import com.physics.entity.ExperimentStudent;
import com.physics.entity.User;

public class ExperimentStudentVM {
	private ExperimentStudent experimentStudent;
	private Experiment experiment;
	private ExperimentInfo experimentInfo;
	private ExperimentCount experimentCount;
	private User user;
	private String time;
	private User teacher;
	
	
	public ExperimentStudentVM(ExperimentStudent experimentStudent){
		setExperimentStudent(experimentStudent );
		setExperiment(experimentStudent);
		setExperimentCount(experiment);
		setExperimentInfo(experiment);
		setUser(experimentStudent);
		setTime(experiment);
		setTeacher(experiment);
	}
	public ExperimentStudent getExperimentStudent() {
		return experimentStudent;
	}
	public void setExperimentStudent(ExperimentStudent experimentStudent) {
		this.experimentStudent = experimentStudent;
	}
	public String getTime() {
		return time;
	}
	public void setTime(Experiment experiment) {
		this.time = "第"+experiment.getStartTime()+" 至 "+experiment.getEndTime()+" 节";
	}
	public Experiment getExperiment() {
		return experiment;
	}
	public void setExperiment(ExperimentStudent experimentStudent) {
		this.experiment = experimentStudent.getExperiment();
	}
	public ExperimentInfo getExperimentInfo() {
		return experimentInfo;
	}
	public void setExperimentInfo(Experiment experiment) {
		this.experimentInfo = experiment.getExperimentInfo();
	}
	public ExperimentCount getExperimentCount() {
		return experimentCount;
	}
	public void setExperimentCount(Experiment experiment) {
		this.experimentCount = experiment.getExperimentCount();
	}
	public User getUser() {
		return user;
	}
	public void setUser(ExperimentStudent experimentStudent) {
		this.user = experimentStudent.getStudent();
	}
	
	public User getTeacher() {
		return teacher;
	}
	public void setTeacher(Experiment experiment) {
		this.teacher = experiment.getTeacher();
	}
}
