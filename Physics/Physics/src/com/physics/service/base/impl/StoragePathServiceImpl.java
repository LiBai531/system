package com.physics.service.base.impl;

import java.io.File;

import com.physics.dao.base.StorageDao;
import com.physics.entity.Storage;
import com.physics.entity.enumeration.PhysicsEnum;
import com.physics.service.base.StoragePathService;

public class StoragePathServiceImpl implements StoragePathService {
	private StorageDao storageDao;
	
	public String getRootPath(Storage storage){
		if(storage.getProtocol().equals("local"))
			return storage.getPath();
		else
			return storage.getWebUrl();
	}
	
	public String getActiveRootPath() {
		Storage storage = storageDao.getActiveStorage();
		return this.getRootPath(storage);
	}
	
	public String getActiveDomainPath() {
		String rootPath = this.getActiveRootPath();
		return rootPath  + "/";
	}
	
	public String getOccasionPath(PhysicsEnum occasion, String subPath) {
		String path = this.getActiveDomainPath() + occasion.getShowing() + "/" + subPath;
		if(!path.endsWith("/")){
			path += "/";
		}
		return path;
	}
	
	public String getPath(String parent, String subPath, boolean flag) {
		File file = new File(parent, subPath);
		if(!file.exists()){
			if(flag){
				file.mkdirs();
				return parent + subPath;
			}else{
				return null;
			}
		}else
			return parent + subPath;
	}

	
	public String getStoragePathById(String id, String subPath) {
		Storage storage = storageDao.getStorageById(id);
		String rootPath = this.getRootPath(storage);
		File file = new File(rootPath, subPath);
		if(file.exists())
			return file.getAbsolutePath();
		else
			return null;
	}

	
	public String getOccasionRelativePath(PhysicsEnum occasion,
			String subPath) {
		String parent = this.getActiveDomainPath() + occasion.getShowing() + "/";
		File file = new File(parent, subPath);
		if(!file.exists()){
			file.mkdirs();
		}
		 
		String path =occasion.getShowing() + "/" + subPath;
		return path.endsWith("/") || path.endsWith("\\") ? path : path + "/";
	}

	public StorageDao getStorageDao() {
		return storageDao;
	}

	public void setStorageDao(StorageDao storageDao) {
		this.storageDao = storageDao;
	}

	
	public String getTemplateRootPath(String subPath) {
		String path = this.getActiveDomainPath() + "BRIEFTEMPLATES/" + subPath;
		return path.endsWith("/") || path.endsWith("\\") ? path : path + "/";
	}
	
}
