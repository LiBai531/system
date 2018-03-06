package com.physics.dao.base;

import com.physics.dao.common.Dao;
import com.physics.entity.Storage;

public interface StorageDao extends Dao<Storage, String>{
	
	abstract public Storage getActiveStorage();
	
	abstract public Storage getStorageById(String id);
}
