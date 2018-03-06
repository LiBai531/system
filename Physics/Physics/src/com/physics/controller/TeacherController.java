package com.physics.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.physics.entity.Attachment;
import com.physics.entity.Experiment;
import com.physics.entity.ExperimentStudent;
import com.physics.entity.User;
import com.physics.service.base.AttachmentService;
import com.physics.service.base.DeleteFileService;
import com.physics.service.base.ExperimentService;
import com.physics.service.base.ExperimentStudentService;
import com.physics.service.base.StoragePathService;
import com.physics.util.DownloadUtil;
import com.physics.util.JsonConvertor;
import com.physics.util.pdf.BriefPDF;
import com.physics.vm.ExperimentVM;
import com.physics.vm.PageVM;

import freemarker.template.TemplateException;


@Controller
@RequestMapping("/teacher")
public class TeacherController {
	@Resource(name="storagePathService")
	private StoragePathService storagePathService;
	@Resource(name="attachmentService")
	private AttachmentService attachmentService;
	@Resource(name="experimentStudentService")
	private ExperimentStudentService experimentStudentService;
	@Resource(name="experimentService")
	private ExperimentService experimentService;
	
	@RequestMapping("/homework")
	public String userLogout(HttpSession session,HttpServletRequest request, Model model) throws IOException{
		String experimentStudentId = request.getParameter("experimentStudentId");
		String storage = storagePathService.getActiveRootPath();
		ExperimentStudent experimentStudent = experimentStudentService.getExperimentStudent(experimentStudentId);
		Attachment attachment = experimentStudent.getAttachment();
		String relativePath = attachment.getPath();
		
		File from = new File(storage+relativePath);
		File to = new File(request.getSession().getServletContext().getRealPath("/")+attachment.getPath());
		
		FileUtils.copyFile(from, to);
		//DeleteFileService.deleteFile(request.getSession().getServletContext().getRealPath("/")+"COLLECTION/");
		model.addAttribute("fileUri", "/Physics/"+attachment.getPath());
		model.addAttribute("experimentStudentId", experimentStudentId);
		model.addAttribute("score",experimentStudent.getScore());
		model.addAttribute("experimentState", experimentStudent.getExperiment().getState());
		return "experimentT/homework";
	}
	
	@RequestMapping("/setScore")
	@ResponseBody
	public String setScore(HttpSession session,HttpServletRequest request, Model model){
		
		String experimentStudentId = request.getParameter("experimentStudentId");
		String score = request.getParameter("score");
		
		ExperimentStudent experimentStudent = experimentStudentService.getExperimentStudent(experimentStudentId);
		if (!"0".equals(experimentStudent.getState()) && !"1".equals(experimentStudent.getState())) {
			return "error";
		}
		
		experimentStudent.setScore(Integer.valueOf(score));
		experimentStudent.setState("1");
		
		experimentStudentService.saveExperimentStudent(experimentStudent);
		return "success";
	}
	
	@RequestMapping("/downloadHomework")
	@ResponseBody
	public void downloadHomework(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String experimentStudentId = request.getParameter("experimentStudentId");
		
		ExperimentStudent experimentStudent = experimentStudentService.getExperimentStudent(experimentStudentId);
		Attachment attachment = experimentStudent.getAttachment();
		
		String rootPath = storagePathService.getActiveRootPath();
		rootPath += attachment.getPath();
		
		String fileName = attachment.getName();
		
		DownloadUtil.down(request, response, fileName, rootPath);
	}
	
	@RequestMapping("/getExperimentStudent")
	public String getExperimentStudent(HttpSession session,HttpServletRequest request,HttpServletResponse response,Model model){
		String experimentStudentId = request.getParameter("experimentStudentId");
		ExperimentStudent experimentStudent = experimentStudentService.getExperimentStudent(experimentStudentId);
		
		model.addAttribute("expId", experimentStudent.getExperimentId());
		model.addAttribute("experimentState", experimentStudent.getExperiment().getState());
		return "experimentT/experimentStudent";
	}
	@RequestMapping("/completeExperiment")
	public String completeExperiment(HttpSession session,HttpServletRequest request,HttpServletResponse response,Model model){
		String experimentId = request.getParameter("experimentId");
		Experiment experiment = experimentService.getExperiment(experimentId);
		List<ExperimentStudent> experimentStudents = experimentStudentService.getExperimentStudentsByExperimentId(experimentId);
		
		for (ExperimentStudent experimentStudent : experimentStudents) {
			experimentStudent.setState("2");
			experimentStudentService.saveExperimentStudent(experimentStudent);
		}
		experiment.setState("1");
		boolean flag = experimentService.saveExperiment(experiment);
		
		if (flag) {
			return "experimentT/onExperiment";
		}
		model.addAttribute("errorMessage", "实验结束失败，请联系管理员！");
		return "error";
	}
	
	@RequestMapping("getEndExperimentPage")
	public String getEndExperimentPage(HttpSession session,HttpServletRequest request,HttpServletResponse response,Model model){
		return "experimentT/experimentEnd";
	}

	@RequestMapping("/getEndingExperiment")
	@ResponseBody
	public String getEndingExperiment(HttpSession session,HttpServletRequest request,HttpServletResponse response,Model model){
		User user = (User) session.getAttribute("userSession");
		String sord = request.getParameter("sord");
		String sidx = request.getParameter("sidx");
		int pageIndex = Integer.valueOf(request.getParameter("page"));
		int pageSize = Integer.valueOf(request.getParameter("rows"));
		
		PageVM<ExperimentVM> pageVM = experimentService.getEndingExperiment(user.getId(),pageIndex,pageSize,true,"calendar");
		String result = JsonConvertor.obj2JSON(pageVM.getGridData());
		
		return result;
	}
	
	@RequestMapping("/pdfDownload")
	@ResponseBody
	public void pdfDownload(HttpSession session,HttpServletRequest request,HttpServletResponse response,Model model) throws IOException, TemplateException, Exception{
		String experimentId = request.getParameter("experimentId");
		Experiment experiment = experimentService.getExperiment(experimentId);
		String rootPath = storagePathService.getActiveRootPath();
		String templatePath = rootPath+"PDF/";
		String pdfName = "";
		String templateName = "";
		BriefPDF briefPDF = null;
		if (experiment.getState().equals("1")) {
			pdfName = "学生完成实验情况";
			templateName = "ExperimentStudentEnd.ftl";
		} else {
			pdfName = "学生选择实验情况";
			templateName = "ExperimentStudent.ftl";
		}
		briefPDF = new BriefPDF(rootPath, pdfName, null, null, templateName, templatePath);
		Object experimentStudents = experimentStudentService.getExperimentStudentsByExperimentId(experimentId);
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put("experimentStudents", experimentStudents);
		dataModel.put("experimentName", experiment.getExperimentInfo().getName());
		dataModel.put("experimentDate", experiment.getCalendar());
		dataModel.put("experimentTime", "第"+experiment.getStartTime()+" 至 "+experiment.getEndTime()+" 节");
		dataModel.put("number", experiment.getExperimentInfo().getPersonLimit()-experiment.getExperimentCount().getCount());
		briefPDF.generate(dataModel);
		
		DownloadUtil.down(request, response, pdfName+".pdf", rootPath);
	}
	
	@RequestMapping("/deleteExperiment")
	@ResponseBody
	public void deleteExperiment(HttpSession session,HttpServletRequest request,HttpServletResponse response,Model model){
		String expId = request.getParameter("expId");
		List<ExperimentStudent> experimentStudents = experimentStudentService.getExperimentStudentsByExperimentId(expId);
		for (ExperimentStudent experimentStudent : experimentStudents) {
			experimentStudentService.deleteExperimentStudentById(experimentStudent);
		}
		experimentService.deleteExperiment(expId);
		
		
	}
}
