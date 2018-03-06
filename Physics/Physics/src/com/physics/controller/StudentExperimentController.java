package com.physics.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.physics.entity.Experiment;
import com.physics.entity.ExperimentCount;
import com.physics.entity.ExperimentStudent;
import com.physics.entity.User;
import com.physics.service.base.ExperimentCountService;
import com.physics.service.base.ExperimentInfoService;
import com.physics.service.base.ExperimentService;
import com.physics.service.base.ExperimentStudentService;
import com.physics.util.JsonConvertor;
import com.physics.vm.ExperimentStudentVM;
import com.physics.vm.ExperimentVM;
import com.physics.vm.PageVM;

@Controller
@RequestMapping("/student")
public class StudentExperimentController {
	
	@Resource(name="experimentInfoService")
	private ExperimentInfoService experimentInfoService;
	@Resource(name="experimentCountService")
	private ExperimentCountService experimentCountService;
	@Resource(name="experimentService")
	private ExperimentService experimentService;
	@Resource(name="experimentStudentService")
	private ExperimentStudentService experimentStudentService;
	
	@RequestMapping("/getExperiment")
	public String getExperimentList(HttpSession session,HttpServletRequest request, Model model){
		User user = (User)session.getAttribute("userSession");
		if ("1".equals(user.getUserType())) {
			return "experimentS/studentChoose";
		}
		return "error";
	}
	
	@RequestMapping("/studentExperiment")
	public String studentExperiment(HttpSession session,HttpServletRequest request, Model model){
		User user = (User)session.getAttribute("userSession");
		if ("1".equals(user.getUserType())) {
			return "experimentS/studentExperiment";
		}
		return "error";
	}
	
	@RequestMapping("/getStudentExperiment")
	@ResponseBody
	public String getStudentExperiment(HttpSession session,HttpServletRequest request, Model model){
		String sord = request.getParameter("sord");//排序方式
		String sidx = request.getParameter("sidx");//排序字段
		int pageIndex = Integer.parseInt(request.getParameter("page")); //当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); //每一页的数据条数
		boolean asc = false;
		if ("asc".equals(sord)) {
			asc = true;
		}
		User user = (User)session.getAttribute("userSession");
		List<ExperimentStudentVM> experimentStudentVM = experimentStudentService.getExperimentStudentsByStudents(user.getId(),"2",false);
		
		PageVM<ExperimentStudentVM> pageVM = new PageVM<ExperimentStudentVM>(pageIndex, experimentStudentVM.size(), pageSize, experimentStudentVM);
		String result=JsonConvertor.obj2JSON(pageVM.getGridData());
		return result;
	}
	
	@RequestMapping("/getOnexperimentByStudent")
	@ResponseBody
	public String getOnexperimentByStudent(HttpSession session,HttpServletRequest request, Model model){
		String sord = request.getParameter("sord");//排序方式
		String sidx = request.getParameter("sidx");//排序字段
		int pageIndex = Integer.parseInt(request.getParameter("page")); //当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); //每一页的数据条数
		boolean asc = false;
		if ("asc".equals(sord)) {
			asc = true;
		}
		User user = (User)session.getAttribute("userSession");
		List<Experiment> experiments = experimentService.getAllExperiments(true);
		List<ExperimentVM> experimentVMs = new ArrayList<ExperimentVM>();
		for (Experiment experiment : experiments) {
			ExperimentVM experimentVM = new ExperimentVM(experiment);
			experimentVMs.add(experimentVM);
		}
		PageVM<ExperimentVM> pageVM = new PageVM<ExperimentVM>(pageIndex, experiments.size(), pageSize, experimentVMs);
		String result=JsonConvertor.obj2JSON(pageVM.getGridData());
		return result;
	}
	
	@RequestMapping("/chooseExperiment")
	@ResponseBody
	public String chooseExperiment(HttpSession session,HttpServletRequest request, Model model){
		String expId = request.getParameter("id");
		User user = (User)session.getAttribute("userSession");
		
		if (!experimentStudentService.isExist(user.getId(), expId)) {
			Experiment experiment = experimentService.getExperiment(expId);
			
			ExperimentStudent experimentStudent = new ExperimentStudent();
			experimentStudent.setCalendar(new Date().toString());
			experimentStudent.setExperiment(experiment);
			experimentStudent.setExperimentId(expId);
			experimentStudent.setScore(0);
			experimentStudent.setState("0");
			experimentStudent.setStudent(user);
			experimentStudent.setUserId(user.getId());
			
			ExperimentCount experimentCount = experiment.getExperimentCount();
			experimentCount.setCount(experimentCount.getCount()-1);
			
			experimentStudentService.saveExperimentStudent(experimentStudent);
			experimentCountService.saveExperimentCount(experimentCount);
			return "success";
		}
		return "error1";
	}
	
	@RequestMapping("/deleteExperiment")
	@ResponseBody
	public String deleteExperiment(HttpSession session,HttpServletRequest request, Model model){
		String expStuId = request.getParameter("id");
		User user = (User)session.getAttribute("userSession");
		
		ExperimentStudent experimentStudent = experimentStudentService.getExperimentStudent(expStuId);
//		experimentStudent.setState("4");
		experimentStudentService.deleteExperimentStudentById(experimentStudent);
		ExperimentCount experimentCount = experimentStudent.getExperiment().getExperimentCount();
		experimentCount.setCount(experimentCount.getCount()+1);
		experimentCountService.saveExperimentCount(experimentCount);
			return "success";
		
	}

}
