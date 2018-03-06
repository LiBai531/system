package com.physics.service.base;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.physics.util.DeleteFile;

public class DeleteFileService {
	
	public static ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
	
	public static void deleteFile(String file){
		
		final String fileNameString = file;
		Runnable runnable = new Runnable() {//线程中进行的函数
			
			
			public void run() {
				// TODO Auto-generated method stub
				String fileName = fileNameString;
				if (DeleteFile.deleteFile(fileName)) {
					scheduledExecutorService.shutdown();
				}
			}
		};
		
		scheduledExecutorService.scheduleAtFixedRate(runnable, 5, 5, TimeUnit.MINUTES);//开启一个线程
	}
}
