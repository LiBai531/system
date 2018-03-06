package com.physics.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.physics.entity.MenuFirst;

public interface MenuFirstService {

	abstract List<MenuFirst> getMenusByUsertype(String userType);
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract boolean changeMenusOfUsertype(List<String> menuIds,String userType);
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract boolean saveMenus(MenuFirst menuFirst);
	
	abstract MenuFirst getMenuFirstById(String id);
}
