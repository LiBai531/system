package com.physics.service.base.impl;

import java.util.List;

import com.physics.dao.base.MenuFirstDao;
import com.physics.entity.MenuFirst;
import com.physics.service.base.MenuFirstService;

public class MenuFirstServiceImpl implements MenuFirstService{

	private MenuFirstDao menuFirstDao;
	
	public MenuFirstDao getMenuFirstDao() {
		return menuFirstDao;
	}
	public void setMenuFirstDao(MenuFirstDao menuFirstDao) {
		this.menuFirstDao = menuFirstDao;
	}

	public List<MenuFirst> getMenusByUsertype(String userType) {
		String hql = "from MenuFirst where userType = ? ";
		return menuFirstDao.hqlFind(hql, new Object[]{userType});
	}

	
	public boolean changeMenusOfUsertype(List<String> menuIds, String userType) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean saveMenus(MenuFirst menuFirst) {
		menuFirstDao.merge(menuFirst);
		return true;
	}
	
	public MenuFirst getMenuFirstById(String id) {

		return menuFirstDao.get(id);
	}

	
}
