package com.physics.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadUtil {
	public static void down(HttpServletRequest request, HttpServletResponse response
			,String filename,String filepath) throws Exception{
	 	
		//response.setContentType("application/x-msdownload");
		response.setContentType(" application/x-xls;charset=uft-8");
		response.setContentType("application/octet-stream");
	 	response.setHeader("Content-disposition","attachment; filename=" + new String(filename.getBytes("utf-8"),"iso8859-1")); 
		
	 	java.io.FileInputStream in = null;     
	 	java.io.BufferedInputStream binpu = null;  
	 	java.io.BufferedOutputStream bout = null; 
	 	   
	 	OutputStream output = null;
		output = response.getOutputStream();
		File file = new File(filepath);
		if(file.isDirectory()){//如果传入的是文件的目录，则添加文件名称
			filepath+= filename;
		}
		if(filepath.startsWith("ftp://"))//如果是ftp,则用FTP下载
		{
			FTPHelper ftp = new FTPHelper();
			String serverFile = ftp.parseFTPAddress(filepath);
	       	ftp.get(serverFile, output);
	        output.flush();	
	        
		}else{
			FileInputStream fis = null;
			try{
				
				in = new java.io.FileInputStream(filepath);     
			    binpu = new java.io.BufferedInputStream(in);  
			    bout = new java.io.BufferedOutputStream(response.getOutputStream());         
			    byte[] b = new byte[1024];   
			    
				int i = 0;
			    while((i = binpu.read(b,0,b.length)) > 0){     
			        bout.write(b, 0, i);     
			    }         
			    bout.flush();   
			    //要加以下两句话，否则会报错      
			    response.flushBuffer();  
			}catch(Exception e){
				e.printStackTrace();
		 	}
			finally{
				if(fis != null){
					fis.close();
		 	  		fis = null;
		 	  	}			
		 	}
		}
		if(output != null){
			output.close();
			output = null;
		}
	}
}
