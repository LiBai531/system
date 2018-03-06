package com.physics.service.base;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.physics.entity.MenuSecond;

public interface MenuSecondService {

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,readOnly=false)
	abstract public void saveMenuSecond(MenuSecond menuSecond);
	
	abstract public MenuSecond getMenuSecondById(String id);
}
