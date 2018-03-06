package com.physics.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="PHY_EXPERIMENT_STUDENT")
@Inheritance(strategy=InheritanceType.JOINED)
@JsonAutoDetect
public class ExperimentStudent {

	/**
	 * state  0刚刚选择实验   1代表已经打分  2教师结束此实验  4代表实验删除
	 */
	private String id;
	private String userId;
	private String experimentId;
	private int score;
	private String state;
	private String calendar;
	
	private User student;
	private Experiment experiment;
	private Attachment attachment;
	
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
	
	
	
	@OneToOne(optional=true) @JoinColumn(name="USER_ID")
	public User getStudent() {
		return student;
	}
	public void setStudent(User student) {
		this.student = student;
	}
	
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH},optional=false) @JoinColumn(name="EXPERIMENT_ID")
	public Experiment getExperiment() {
		return experiment;
	}
	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}
	
	@Column(name="SCORE")
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	@Column(name="STATE" ,length=1)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Column(name="CALENDAR",length=32)
	public String getCalendar() {
		return calendar;
	}
	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}
	
	@Column(name="userId",length=32)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(name="experimentId",length=32)
	public String getExperimentId() {
		return experimentId;
	}
	public void setExperimentId(String experimentId) {
		this.experimentId = experimentId;
	}
	
	@OneToOne(cascade=CascadeType.ALL,optional=true) @JoinColumn(name="ATTACHMENT_ID")
	public Attachment getAttachment() {
		return attachment;
	}
	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
	
}
