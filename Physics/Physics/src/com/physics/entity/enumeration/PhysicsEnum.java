package com.physics.entity.enumeration;

public class PhysicsEnum {
	
	private String status;
	private String showing;


	public String getStatus(){
		return status;
	}
	
	public String getShowing(){
		return showing;
	}
	
	public PhysicsEnum(String status,String showing){
		this.status = status;
		this.showing = showing;
	}
}
