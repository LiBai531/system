package com.physics.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.physics.util.MySessionContext;
import com.physics.entity.Attachment;
import com.physics.entity.AttachmentHelper;
import com.physics.entity.ExperimentStudent;
import com.physics.entity.User;
import com.physics.entity.enumeration.AttachmentType;
import com.physics.service.base.AttachmentService;
import com.physics.service.base.ExperimentStudentService;
import com.physics.util.FileOperate;


@Controller
@RequestMapping("/upload")
public class UploadController {
	@Resource(name = "attachmentService")
	private AttachmentService attachmentService;
	@Resource(name = "experimentStudentService")
	private ExperimentStudentService experimentStudentService;
	
	@RequestMapping("/homework")
	@ResponseBody
	public String uploadFile(@RequestParam(value="pageAllowed",required=false) String pageAllowed,
			HttpServletRequest request){
		String sessionId = request.getParameter("jsessionid");
		String experimentStudentId = request.getParameter("experimentStudentId");
		MySessionContext myc= MySessionContext.getInstance();
		HttpSession session = myc.getSession(sessionId); 
		User user = (User) session.getAttribute("userSession");
		
		MultipartFile file = FileOperate.getFile(request);
		
		long size = file.getSize();
		long limit = 0;
		if(size>limit)
		{
			//throw new BusinessException2("SIZE_EXCESS");
		}
		if(!StringUtils.isEmpty(pageAllowed)){
			int pageSize=Integer.parseInt(pageAllowed);
			String filename=file.getOriginalFilename();
			String suffix = filename.substring(filename.lastIndexOf('.') + 1);
			if((pageSize!=0)&&(suffix.toUpperCase().equals("PDF"))){
				try {
					if(!FileOperate.chackPageNumbers(file,null)){
						return "PAGESIZE_ERROR";
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "error";
				}
			}
		}
		
		AttachmentHelper attachmentHelper = attachmentService.getAttachmentHelper(file,user.getId(),
				AttachmentType.COLLECTION, "");
		if(FileOperate.upload(file, attachmentHelper.getPath(), attachmentHelper.getStorageName())){
			Attachment attachment = attachmentHelper.getAttachment();
			attachmentService.addAttachment(attachment);
			ExperimentStudent experimentStudent = experimentStudentService.getExperimentStudent(experimentStudentId);
			experimentStudent.setAttachment(attachment);
			experimentStudentService.saveExperimentStudent(experimentStudent);
			return "success";
		}
		else
			return "error";
	}
	
	
	@RequestMapping("/video")
	@ResponseBody
	public String uploadVideo(@RequestParam(value="pageAllowed",required=false) String pageAllowed,
			HttpServletRequest request){
		String sessionId = request.getParameter("jsessionid");
		MySessionContext myc= MySessionContext.getInstance();
		HttpSession session = myc.getSession(sessionId); 
		User user = (User) session.getAttribute("userSession");
		
		MultipartFile file = FileOperate.getFile(request);
		
		long size = file.getSize();
		long limit = 0;
		if(size>limit)
		{
			//throw new BusinessException2("SIZE_EXCESS");
		}
		
		AttachmentHelper attachmentHelper = attachmentService.getAttachmentHelper(file,user.getId(),
				AttachmentType.VIDEO, "");
		if(FileOperate.upload(file, attachmentHelper.getPath(), attachmentHelper.getStorageName())){
			Attachment attachment = attachmentHelper.getAttachment();
			attachmentService.addAttachment(attachment);
			
			return "success";
		}
		else
			return "error";
	}
	
	
	@RequestMapping("/uploadPage")
	public String getExperimentList(HttpSession session,HttpServletRequest request, Model model){
		User user = (User) session.getAttribute("userSession");
		return "experimentS/collect_uploadFile";
		
	}
	
	@RequestMapping("/uploadPage2")
	public String getvideoUploadpage(HttpSession session,HttpServletRequest request, Model model){
		User user = (User) session.getAttribute("userSession");
		return "experimentA/video_uploadFile";
		
	}

}
