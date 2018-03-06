package com.physics.entity;

import java.sql.Time;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="PHY_EXPERIMENT")
@Inheritance(strategy=InheritanceType.JOINED)
@JsonAutoDetect
public class Experiment {

	private String id;
	private String experimentInfoId;
	private String userId;
	private String calendar;
	private String startTime;
	private String endTime;
	private String experimentCountId;
	private String state;
	
	private ExperimentCount experimentCount;
	private ExperimentInfo experimentInfo;
	private User teacher;
	
	@Id
	@GenericGenerator(name="hibernate-uuid",strategy="uuid")
	@GeneratedValue(generator="hibernate-uuid")
	@Column(name="ID" ,length=32)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	@ManyToOne @JoinColumn(name="EXP_INFO_ID")
	public ExperimentInfo getExperimentInfo() {
		return experimentInfo;
	}
	public void setExperimentInfo(ExperimentInfo experimentInfo) {
		this.experimentInfo = experimentInfo;
	}
	
	@ManyToOne @JoinColumn(name="USER_ID")
	public User getTeacher() {
		return teacher;
	}
	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}
	
	@Column(name="CALENDAR",length=20)
	public String getCalendar() {
		return calendar;
	}
	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}
	
	@Column(name="STARTTIME" ,length=2)
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	@Column(name="ENDTIME",length=2)
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	@OneToOne(cascade=CascadeType.ALL,optional=true) @JoinColumn(name="EXPERIMENT_COUNT_ID")
	public ExperimentCount getExperimentCount() {
		return experimentCount;
	}
	public void setExperimentCount(ExperimentCount experimentCount) {
		this.experimentCount = experimentCount;
	}
	
	@Column(name="experimentInfoId",length=32)
	public String getExperimentInfoId() {
		return experimentInfoId;
	}
	public void setExperimentInfoId(String experimentInfoId) {
		this.experimentInfoId = experimentInfoId;
	}
	
	@Column(name="userId",length=32)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name="experimentCountId",length=32)
	public String getExperimentCountId() {
		return experimentCountId;
	}
	public void setExperimentCountId(String experimentCountId) {
		this.experimentCountId = experimentCountId;
	}
	
	@Column(name="state",length=1)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	
	
}
