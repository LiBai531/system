package com.physics.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.physics.entity.Attachment;
import com.physics.entity.User;
import com.physics.service.base.AttachmentService;
import com.physics.service.base.ExperimentStudentService;
import com.physics.service.base.UserService;
import com.physics.util.JsonConvertor;
import com.physics.vm.ExperimentStudentVM;
import com.physics.vm.PageVM;
import com.physics.vm.UserVM;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Resource(name="userService")
	private UserService userService;
	@Resource(name="experimentStudentService")
	private ExperimentStudentService experimentStudentService;
	@Resource(name="attachmentService")
	public AttachmentService attachmentService;
	
	@RequestMapping("getVideo")
	public String getVideoList(HttpServletRequest request,HttpServletResponse response,Model model){
		model.addAttribute("url", "http://localhost/video/test.mp4");
		return "common/video";
	}
	
	@RequestMapping("getEndingExperiment")
	public String getEndingExperimentFromStudent(HttpServletRequest request,HttpServletResponse response,Model model,HttpSession session){
		return "experimentS/experimentEnd";
	}
	
	@RequestMapping("getExperimentEndList")
	@ResponseBody
	public String getExperimentEndList(HttpServletRequest request,HttpServletResponse response,Model model,HttpSession session){
		
		String sord = request.getParameter("sord");//排序方式
		String sidx = request.getParameter("sidx");//排序字段
		int pageIndex = Integer.parseInt(request.getParameter("page")); //当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); //每一页的数据条数
		boolean asc = false;
		if ("asc".equals(sord)) {
			asc = true;
		}
		User user = (User)session.getAttribute("userSession");
		List<ExperimentStudentVM> experimentStudentVM = experimentStudentService.getExperimentStudentsByStudents(user.getId(),"2",true);
		
		PageVM<ExperimentStudentVM> pageVM = new PageVM<ExperimentStudentVM>(pageIndex, experimentStudentVM.size(), pageSize, experimentStudentVM);
		String result=JsonConvertor.obj2JSON(pageVM.getGridData());
		return result;
	}
	
	@RequestMapping("lookVideo")
	public String lookVideo(HttpServletRequest request,HttpServletResponse response,Model model,HttpSession session){
		String id = request.getParameter("id");
		Attachment attachment = attachmentService.getAttachment(id);
		String url = attachment.getStorage().getWebUrl()+attachment.getPath();
		model.addAttribute("url", url);
		model.addAttribute("videoName", attachment.getName());
		return "common/video";
	}
}
