package com.physics.common.exception;

import java.net.URL;

import com.physics.dao.base.StorageDao;

public class PathProvider {
	private StorageDao storageDao;
	public static URL getClassResource(String resourcePath){
		ClassLoader classLoader= Thread.currentThread().getContextClassLoader();
		return classLoader.getResource(resourcePath);
	}
	public static URL getBasicResource(String resource){
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String subRes = resource.startsWith("/") ?
						resource.substring(1) : resource;
		return classLoader.getResource("../../" + subRes);
	}
	public static String getClassPath(){
		return PathProvider.getClassResource("").getPath().replace("%20", " ");
	}
	public static String getBasicPath(){
		return PathProvider.getBasicResource("").getPath().replace("%20", " ");
	}
	
	
}
