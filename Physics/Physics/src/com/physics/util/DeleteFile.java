package com.physics.util;

import java.io.File;


public class DeleteFile {

	public static boolean deleteFile(String FileName){
		
		File file = new File(FileName);//找到文件
		
		if (!file.exists() || !file.isFile()) {//判断是否存在
			return false;
		}
		
		file.delete();//文件删除操作
		return true;
	}
	
	public static boolean deleteDirectory(String dir){
		if (!dir.endsWith(File.separator)) {//文件夹结尾
			dir+=File.separator;
		}
		File dirFile = new File(dir);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		
		boolean flag = true;//子删除步骤是否正确
		
		File[] files = dirFile.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = DeleteFile.deleteFile(files[i].getAbsolutePath());//删除目录下的文件
			}else {
				flag = DeleteFile.deleteDirectory(files[i].getAbsolutePath());//删除目录下子目录与其中文件
			}
		}
		
		if (!flag) {
			return false;
		}
		
		
		return true;
	}
}
