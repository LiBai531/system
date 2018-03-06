package com.physics.vm;

import com.physics.entity.Experiment;
import com.physics.entity.ExperimentCount;
import com.physics.entity.ExperimentInfo;
import com.physics.entity.User;

public class ExperimentVM {

	private Experiment experiment;
	private ExperimentInfo experimentInfo;
	private ExperimentCount experimentCount;
	private User user;
	private String time;
	private String selectPersonNum;
	
	public ExperimentVM(Experiment experiment){
		setExperiment(experiment);
		setExperimentCount(experiment);
		setExperimentInfo(experiment);
		setUser(experiment);
		setTime(experiment);
		setSelectPersonNum(String.valueOf(experiment.getExperimentInfo().getPersonLimit() - experiment.getExperimentCount().getCount()));
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
	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
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
	public void setUser(Experiment experiment) {
		this.user = experiment.getTeacher();
	}
	public String getSelectPersonNum() {
		return selectPersonNum;
	}
	public void setSelectPersonNum(String selectPersonNum) {
		this.selectPersonNum = selectPersonNum;
	}
	
	
}
