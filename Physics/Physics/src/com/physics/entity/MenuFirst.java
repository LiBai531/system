package com.physics.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="PHY_MENUFIRST")
public class MenuFirst {

	private String id;
	private String name;
	private String href;
	private String userType;
	private String state;
	private List<MenuSecond> menuSeconds;
	
	
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
	
	
	@Column(name="NAME" ,length=30)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="HREF" ,length=50)
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	
	@Column(name="TYPE" ,length=1)
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	@Column(name="STATE" ,length=1)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	@OneToMany(mappedBy = "menuFirst", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  
    @OrderBy(value = "id ASC")
	public List<MenuSecond> getMenuSeconds() {
		return menuSeconds;
	}
	public void setMenuSeconds(List<MenuSecond> menuSeconds) {
		this.menuSeconds = menuSeconds;
	}
	
	
}
