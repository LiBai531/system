package com.physics.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="PHY_EXPERIMENT_INFO")
@Inheritance(strategy=InheritanceType.JOINED)
@JsonAutoDetect
public class ExperimentInfo {

	private String id;
	private String name;
	private String info;
	private String address;
	private int personLimit;
	private String state;
	
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
	
	@Column(name="name" ,length=50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="info" ,length=200)
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	@Column(name="ADDRESS" ,length=50)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name="personLimit")
	public int getPersonLimit() {
		return personLimit;
	}
	public void setPersonLimit(int personLimit) {
		this.personLimit = personLimit;
	}
	
	@Column(name="STATE" ,length=1)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
