package com.physics.vm;

import com.physics.entity.MenuFirst;
import com.physics.entity.MenuSecond;

public class ZtreeVM {
	
	private String id;
	private String pId;
	private String name;
	private boolean checked;
	private boolean open;
	
	public ZtreeVM(MenuFirst menuFirst){
		setId(menuFirst.getId());
		setName(menuFirst.getName());
		setpId("0");
		setChecked(menuFirst.getState().equals("1")?true:false);
		setOpen(true);
	}
	
	public ZtreeVM(MenuSecond menuSecond){
		setId(menuSecond.getId());
		setName(menuSecond.getName());
		setpId(menuSecond.getMenuFirst().getId());
		setChecked(menuSecond.getState().equals("1")?true:false);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
	
}
