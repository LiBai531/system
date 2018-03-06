package com.physics.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="PHY_MENUSECOND")
public class MenuSecond {

	private String id;
	private String name;
	private String href;
	private String state;
	
	private MenuFirst menuFirst;
	
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
	
	@Column(name="STATE" ,length=1)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)  
    @JoinColumn(name = "menuFirst_id",referencedColumnName="id") 
	public MenuFirst getMenuFirst() {
		return menuFirst;
	}
	public void setMenuFirst(MenuFirst menuFirst) {
		this.menuFirst = menuFirst;
	}
	
	
	
	
}
