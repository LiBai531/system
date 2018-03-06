package com.physics.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="PHY_CONTENT")
@Inheritance(strategy=InheritanceType.JOINED)
@JsonAutoDetect
public class Content {

	private String id;
	private String head;
	private String content;
	
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
	
	@Column(name="HEAD" ,length=100)
	public String getHead() {
		return head;
	}
	public void setHead(String hear) {
		this.head = hear;
	}
	
	@Column(name="CONTENT" ,length=500)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
