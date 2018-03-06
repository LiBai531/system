package com.physics.service.base.file.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.physics.util.FileOperate;
import com.physics.util.JsonConvertor;
import com.physics.entity.User;
import com.physics.service.base.UserService;
import com.physics.service.base.file.ExportService;


public class ExportServiceImpl  implements ExportService {

	private UserService userService;
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	
	public String exportUsersByType(String userType,String rootPath) {
		List<User> users = userService.getTypeUsers(userType).getList();
		
		List<String> sheetName = new LinkedList<String>();
		sheetName.add("用户列表");
		List<List<String>> titleNames = new LinkedList<List<String>>();
		titleNames.add(new LinkedList<String>());
		titleNames.get(0).add("用户登陆ID");
		titleNames.get(0).add("用户初始密码");
		titleNames.get(0).add("用户名");
		titleNames.get(0).add("用户类型");
		titleNames.get(0).add("上次登陆时间");
		titleNames.get(0).add("上次登陆IP");
		titleNames.get(0).add("用户状态");
		
		List<List<String[]>> rowStrings =new LinkedList<List<String[]>>();
		rowStrings.add(new ArrayList<String[]>());
		
		for (User user : users) {
			String[] row =new String[titleNames.get(0).size()];
			row[0] = user.getLoginId();
			row[1]=user.getInitPassword();
			row[2]=user.getName();
			row[3]=user.getUserType().equals("1")?"学生":user.getUserType().equals("2")?"老师":"管理员";
			row[4]=user.getLoginTime() == null?"":user.getLoginTime();
			row[5]=user.getLoginIP() == null?"":user.getLoginIP();
			row[6]=user.getState().equals("1")?"初始用户":"正常用户";
			
 			rowStrings.get(0).add(row);
		}
		
		String sheetTitle="用户汇总";
		Map<String, String> excelPathMap=FileOperate.exportExcel(titleNames, rowStrings, sheetName, sheetTitle,rootPath, "excel");
		return JsonConvertor.obj2JSON(excelPathMap);
	}

}

