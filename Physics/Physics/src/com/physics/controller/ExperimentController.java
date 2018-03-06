package com.physics.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.physics.util.JsonConvertor;
import com.physics.vm.ExperimentStudentVM;
import com.physics.vm.ExperimentVM;
import com.physics.vm.PageVM;
import com.physics.entity.Experiment;
import com.physics.entity.ExperimentCount;
import com.physics.entity.ExperimentInfo;
import com.physics.entity.ExperimentStudent;
import com.physics.entity.User;
import com.physics.service.base.ExperimentCountService;
import com.physics.service.base.ExperimentInfoService;
import com.physics.service.base.ExperimentService;
import com.physics.service.base.ExperimentStudentService;

@Controller
@RequestMapping("/experiment")
public class ExperimentController {

	@Resource(name="experimentInfoService")
	private ExperimentInfoService experimentInfoService;
	@Resource(name="experimentCountService")
	private ExperimentCountService experimentCountService;
	@Resource(name="experimentService")
	private ExperimentService experimentService;
	@Resource(name="experimentStudentService")
	private ExperimentStudentService experimentStudentService;
	
	@RequestMapping("/getNewExperiment")
	public String getNewExperiment(HttpSession session,HttpServletRequest request, Model model){
		User user = (User)session.getAttribute("userSession");
		if ("2".equals(user.getUserType())) {
			return "experimentT/newExperiment";
		}
		return "error";
	}
	
	@RequestMapping("/expInfoPage")
	public String expInfoPage(HttpSession session,HttpServletRequest request, Model model){
		String id = request.getParameter("id");
		ExperimentInfo experimentInfo = experimentInfoService.getExperimentInfoById(id);
		model.addAttribute("experimentInfo", experimentInfo);
		return "common/expInfo";
	}
	
	@RequestMapping("/getExperimentById")
	public String getExperimentById(HttpSession session,HttpServletRequest request, Model model){
		String id = request.getParameter("id");
		ExperimentInfo experimentInfo = experimentInfoService.getExperimentInfoById(id);
		User user = (User)session.getAttribute("userSession");
		if ("2".equals(user.getUserType())) {
			model.addAttribute("experimentInfo", experimentInfo);
			return "experimentT/editExperiment";
		}
		return "error";
	}
	
	@RequestMapping("/getOnExperiment")
	public String getOnExperiment(HttpSession session,HttpServletRequest request, Model model){
		User user = (User)session.getAttribute("userSession");
		if ("2".equals(user.getUserType())) {
			return "experimentT/onExperiment";
		}
		return "error";
	}
	
	@RequestMapping("/getExperimentStudent")
	public String getExperimentStudent(HttpSession session,HttpServletRequest request, Model model){
		User user = (User)session.getAttribute("userSession");
		String id = request.getParameter("id");
		Experiment experiment = experimentService.getExperiment(id);
		if ("2".equals(user.getUserType())) {
			model.addAttribute("expId", id);
			model.addAttribute("experimentState", experiment.getState());
			return "experimentT/experimentStudent";
		}
		return "error";
	}
	
	@RequestMapping("/getExperimentInfoList")
	@ResponseBody
	public String getExperimentInfoList(HttpSession session,HttpServletRequest request, Model model){
		String sord = request.getParameter("sord");//排序方式
		String sidx = request.getParameter("sidx");//排序字段
		int pageIndex = Integer.parseInt(request.getParameter("page")); //当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); //每一页的数据条数
		boolean asc = false;
		if ("asc".equals(sord)) {
			asc = true;
		}
		
		PageVM<ExperimentInfo> pageVM = experimentInfoService.getExperimentInfo(pageIndex, pageSize,asc, sidx);
		String result=JsonConvertor.obj2JSON(pageVM.getGridData());
		return result;
	}
	
	@RequestMapping("/getOnexperimentByTeacher")
	@ResponseBody
	public String getOnexperimentByTeacher(HttpSession session,HttpServletRequest request, Model model){
		String sord = request.getParameter("sord");//排序方式
		String sidx = request.getParameter("sidx");//排序字段
		int pageIndex = Integer.parseInt(request.getParameter("page")); //当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); //每一页的数据条数
		boolean asc = false;
		if ("asc".equals(sord)) {
			asc = true;
		}
		User user = (User)session.getAttribute("userSession");
		List<Experiment> experiments = experimentService.getExperimentsByTeacherId(user.getId(),"0");
		List<ExperimentVM> experimentVMs = new ArrayList<ExperimentVM>();
		for (Experiment experiment : experiments) {
			ExperimentVM experimentVM = new ExperimentVM(experiment);
			experimentVMs.add(experimentVM);
		}
		PageVM<ExperimentVM> pageVM = new PageVM<ExperimentVM>(pageIndex, experiments.size(), pageSize, experimentVMs);
		String result=JsonConvertor.obj2JSON(pageVM.getGridData());
		return result;
	}
	
	@RequestMapping("/saveExperiment")
	@ResponseBody
	public String saveExperiment(HttpSession session,HttpServletRequest request, Model model){
		
		String expInfoId = request.getParameter("expInfoId");
		String expTime = request.getParameter("expTime");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		User user = (User)session.getAttribute("userSession");
		
		ExperimentInfo experimentInfo = experimentInfoService.getExperimentInfoById(expInfoId);
		ExperimentCount experimentCount = new ExperimentCount();
		Experiment experiment = new Experiment();
		
		experimentCount.setCount(experimentInfo.getPersonLimit());
		String ECId = experimentCountService.saveExperimentCount(experimentCount);
		
		experiment.setCalendar(expTime);
		experiment.setEndTime(endTime);
		experiment.setExperimentCount(experimentCount);
		experiment.setExperimentInfo(experimentInfo);
		experiment.setStartTime(startTime);
		experiment.setTeacher(user);
		experiment.setUserId(user.getId());
		experiment.setExperimentCountId(ECId);
		experiment.setExperimentInfoId(expInfoId);
		experiment.setState("0");
		
		experimentService.saveExperiment(experiment);
		return "success";
	}
	
	@RequestMapping("/getExperimentStudentList")
	@ResponseBody
	public String getStudentExperiment(HttpSession session,HttpServletRequest request, Model model){
		String sord = request.getParameter("sord");//排序方式
		String sidx = request.getParameter("sidx");//排序字段
		String expId = request.getParameter("expId");
		int pageIndex = Integer.parseInt(request.getParameter("page")); //当前页
		int pageSize = Integer.parseInt(request.getParameter("rows")); //每一页的数据条数
		boolean asc = false;
		if ("asc".equals(sord)) {
			asc = true;
		}
		User user = (User)session.getAttribute("userSession");
		
		List<ExperimentStudent> experimentStudents = experimentStudentService.getExperimentStudentsByExperimentId(expId);
		List<ExperimentStudentVM> experimentStudentVM = new ArrayList<ExperimentStudentVM>();
		for (ExperimentStudent experimentStudent : experimentStudents) {
			if( "4".equals(experimentStudent.getState()))
				continue;
			ExperimentStudentVM experimentVM = new ExperimentStudentVM(experimentStudent);
			experimentStudentVM.add(experimentVM);
		}
		PageVM<ExperimentStudentVM> pageVM = new PageVM<ExperimentStudentVM>(pageIndex, experimentStudents.size(), pageSize, experimentStudentVM);
		String result=JsonConvertor.obj2JSON(pageVM.getGridData());
		return result;
	}

}
