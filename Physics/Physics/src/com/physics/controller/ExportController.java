package com.physics.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.physics.entity.User;
import com.physics.entity.enumeration.AttachmentType;
import com.physics.util.DownloadUtil;
import com.physics.service.base.StoragePathService;
import com.physics.service.base.UserService;
import com.physics.service.base.file.ExportService;


@Controller
@RequestMapping("/export")
public class ExportController {
	
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="storagePathService")
	private StoragePathService storagePathService;
	@Resource(name="exportService")
	private ExportService exportService;
	
	
	@RequestMapping("expotUsers/{userType}")
	public void unitUserExport(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,@PathVariable(value="userType")String userType) throws Exception {
		User user = (User) session.getAttribute("userSession");
		if ( !user.getUserType().equals("3")) {
			return;
		}
		String rootPath = storagePathService.getOccasionPath(AttachmentType.EXCEL, "user/");
		String fileString = exportService.exportUsersByType(userType, rootPath);
		JSONObject path = JSONObject.fromObject(fileString);
		String fileName = (String) path.get("filename");
		String filePath = (String) path.get("filepath");
		DownloadUtil.down(request, response, fileName, filePath);
	}

}
