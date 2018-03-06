package com.physics.service.base.impl;

import com.physics.dao.base.MenuSecondDao;
import com.physics.entity.MenuSecond;
import com.physics.service.base.MenuSecondService;

public class MenuSecondServiceImpl implements MenuSecondService{

	private MenuSecondDao menuSecondDao;
	public MenuSecondDao getMenuSecondDao() {
		return menuSecondDao;
	}
	public void setMenuSecondDao(MenuSecondDao menuSecondDao) {
		this.menuSecondDao = menuSecondDao;
	}

	
	public void saveMenuSecond(MenuSecond menuSecond) {
		menuSecondDao.saveOrUpdate(menuSecond);
	}
	
	public MenuSecond getMenuSecondById(String id) {
		return menuSecondDao.get(id);
	}

}
